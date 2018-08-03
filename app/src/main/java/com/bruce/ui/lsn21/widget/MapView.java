package com.bruce.ui.lsn21.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.graphics.PathParser;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bruce.ui.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapView extends View {

    public static final String TAG = "hzw";
    public static final int LOAD_SVG_COMPLETED = 0x123;

    private Context mContext;
    private String mMapSvgFileName;
    private int mMapWidth = -1;
    private int mMapHeight = -1;
    private List<MapRegion> mMapRegions;
    private LoadSvgHandler mHandler;
    private float mScale;
    private Paint mPaint;
    private int mSelectIndex;
    private int mScreenWidth;
    private int mScreenHeight;


    public MapView(Context context) {
        super(context);
        init(context, null);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.MapView);
            mMapSvgFileName = typedArray.getString(R.styleable.MapView_mapSvg);
            Log.i(TAG, "magSvg = " + mMapSvgFileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
        if (!TextUtils.isEmpty(mMapSvgFileName)) {
            LoadSvgThread thread = new LoadSvgThread();
            mHandler = new LoadSvgHandler(this);
            thread.start();

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            Log.i(TAG, "AT_MOST");
            if (mMapWidth > mScreenWidth) {
                width = mScreenWidth;
            }
            if (mMapHeight > mScreenHeight) {
                height = mScreenHeight;
            }
            mScale = 1.0f;
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        } else if (widthMode == MeasureSpec.EXACTLY) {
            Log.i(TAG, "EXACTLY");
            mScale = Math.min(width * 1.0f / mMapWidth, height * 1.0f / mMapHeight);
//            width = mMapWidth;
//            height = mMapHeight;
            Log.i(TAG, "mMapWidth = " + mMapWidth + ", mMapHeight = " + mMapHeight + ", width = " + width + ", height = " + height + ", mScale = " + mScale);
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Log.i(TAG, "mScale = " + mScale);
        canvas.scale(mScale, mScale);
        if (mMapRegions != null && mMapRegions.size() > 0) {
            for (MapRegion mapRegion : mMapRegions) {
                mapRegion.draw(canvas, mPaint);
            }
        }
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < mMapRegions.size(); i++) {
                    if (mMapRegions.get(i).isTouch((int) (event.getX() / mScale), (int) (event.getY() / mScale))) {
                        mSelectIndex = i;
                        break;
                    } else {
                        mSelectIndex = -1;
                    }
                }
                if (mSelectIndex != -1) {
                    mMapRegions.get(mSelectIndex).isSelected = true;
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mSelectIndex != -1) {
                    mMapRegions.get(mSelectIndex).isSelected = false;
                    invalidate();
                }
                break;
        }

        return true;
    }

    private void loadSvg() {
        InputStream is = null;
        List<MapRegion> mapRegions = new ArrayList<>();
        try {
            is = mContext.getResources().getAssets().open(mMapSvgFileName);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            NodeList pathNodeList = document.getDocumentElement().getElementsByTagName("path");

            int left = -1;
            int top = -1;
            int right = -1;
            int bottom = -1;

            for (int i = 0; i < pathNodeList.getLength(); i++) {
                Element element = (Element) pathNodeList.item(i);
                String pathData = element.getAttribute("android:pathData");
                @SuppressLint("RestrictedApi")
                Path path = PathParser.createPathFromPathData(pathData);
                RectF bounds = new RectF();
                path.computeBounds(bounds, true);

                left = (int) (left == -1 ? bounds.left : Math.min(left, bounds.left));
                top = (int) (top == -1 ? bounds.top : Math.min(top, bounds.top));
                right = (int) (right == -1 ? bounds.right : Math.max(right, bounds.right));
                bottom = (int) (bottom == -1 ? bounds.bottom : Math.max(bottom, bounds.bottom));
                mMapWidth = right - left;
                mMapHeight = bottom - top;

                String name = element.getAttribute("android:name");
                String fillColor = element.getAttribute("android:fillColor");
                String strokeColor = element.getAttribute("android:strokeColor");
                String strokeWidth = element.getAttribute("android:strokeWidth");

                MapRegion mapRegion = new MapRegion();
                mapRegion.mPath = path;
                mapRegion.mBounds = new Rect((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom);
                mapRegion.mName = name;
                mapRegion.mFillColor = Color.parseColor(fillColor);
                mapRegion.mStrokeColor = Color.parseColor(strokeColor);
                mapRegion.mStrokeWidth = Float.valueOf(strokeWidth);
                mapRegion.isSelected = false;

                mapRegions.add(mapRegion);
            }
            mMapRegions = mapRegions;
            Log.i(TAG, "mMapWidth = " + mMapWidth + ", mMapHeight = " + mMapHeight);
            mHandler.sendEmptyMessage(LOAD_SVG_COMPLETED);
            postInvalidate();
        } catch (Resources.NotFoundException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void refresh() {
        requestLayout();
    }

    class LoadSvgThread extends Thread {
        @Override
        public void run() {
            loadSvg();
        }
    }

    static class LoadSvgHandler extends Handler {
        private WeakReference<MapView> mWeakReference;

        LoadSvgHandler(MapView mapView) {
            mWeakReference = new WeakReference<>(mapView);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOAD_SVG_COMPLETED) {
                Log.i(TAG, "----------------");
                mWeakReference.get().refresh();
            }
        }
    }

    class MapRegion {
        String mName;
        int mFillColor;
        int mFillColorSelected = Color.parseColor("#64b5f6");
        int mStrokeColor;
        float mStrokeWidth;
        Rect mBounds;
        Path mPath;
        boolean isSelected;

        void draw(Canvas canvas, Paint paint) {
            canvas.save();
            paint.clearShadowLayer();
            mPaint.setStyle(Paint.Style.FILL);
            if (isSelected) {
                paint.setColor(mFillColorSelected);
            } else {
                paint.setColor(mFillColor);
            }
            canvas.drawPath(mPath, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(mStrokeWidth);
            paint.setColor(mStrokeColor);
            canvas.drawPath(mPath, mPaint);

            canvas.restore();
        }

        public boolean isTouch(int x, int y) {
            Region region = new Region();
            region.setPath(mPath, new Region(mBounds));
            return region.contains(x, y);
        }
    }
}

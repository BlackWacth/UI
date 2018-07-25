package com.bruce.ui.lsn11.utils;

public class Message {
	private int logo;
	private String name;
	private String lastMsg;
	private String time;
	private int pop;
	public Message() {
		// TODO Auto-generated constructor stub
	}
	public Message(int logo, String name, String lastMsg, String time) {
		super();
		this.logo = logo;
		this.name = name;
		this.lastMsg = lastMsg;
		this.time = time;
	}
	public Message(int logo, String name, String lastMsg, String time, int pop) {
		super();
		this.logo = logo;
		this.name = name;
		this.lastMsg = lastMsg;
		this.time = time;
		this.pop = pop;
	}
	public int getLogo() {
		return logo;
	}
	public int getPop() {
		return pop;
	}
	public void setPop(int pop) {
		this.pop = pop;
	}
	public void setLogo(int logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastMsg() {
		return lastMsg;
	}
	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Message [logo=" + logo + ", name=" + name + ", lastMsg="
				+ lastMsg + ", time=" + time + "]";
	}
	

}

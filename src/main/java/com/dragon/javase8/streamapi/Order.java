package com.dragon.javase8.streamapi;

public class Order {
	private String onlineTime;
	private String videoMoney;
	private String enjoyMoney;
	private String imMoney;
	
	public Order() {
		
	}
	
	public Order(String onlineTime, String videoMoney, String enjoyMoney, String imMoney) {
		super();
		this.onlineTime = onlineTime;
		this.videoMoney = videoMoney;
		this.enjoyMoney = enjoyMoney;
		this.imMoney = imMoney;
	}
	
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getVideoMoney() {
		return videoMoney;
	}
	public void setVideoMoney(String videoMoney) {
		this.videoMoney = videoMoney;
	}
	public String getEnjoyMoney() {
		return enjoyMoney;
	}
	public void setEnjoyMoney(String enjoyMoney) {
		this.enjoyMoney = enjoyMoney;
	}
	public String getImMoney() {
		return imMoney;
	}
	public void setImMoney(String imMoney) {
		this.imMoney = imMoney;
	}

	@Override
	public String toString() {
		return "Order [onlineTime=" + onlineTime + ", videoMoney=" + videoMoney + ", enjoyMoney=" + enjoyMoney
				+ ", imMoney=" + imMoney + "]";
	}
}

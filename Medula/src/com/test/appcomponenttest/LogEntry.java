package com.test.appcomponenttest;

public class LogEntry {
	private final int FALSE = 0;
	private final int TRUE = 1;
	private String date = "0/0/0";
	private String time = "00:00";
	private long id = -1;
	private int isCrocinTaken = FALSE;
	private int isNeprocinTaken = FALSE;
	private int isSunExposure = FALSE;
	private int isBookMarked = FALSE;
	private String severity = "low";
	private String other = "";

	public void setID(long n) {
		this.id = n;
	}

	public long getID() {
		return id;
	}

	public void setDate(String sdate) {
		this.date = sdate;
	}

	public String getDate() {
		return date;
	}
	
	public void setTime(String stime){
		this.time = stime;
	}
	
	public String getTime(){
		return time;
	}

	public void setSeverity(String svrty) {
		this.severity = svrty;
	}

	public String getSeverity() {
		return severity;
	}

	public void setisCrocinTaken(int crocinTaken) {
		this.isCrocinTaken = crocinTaken;
	}

	public int getisCrocinTaken() {
		return isCrocinTaken;
	}
	
	public void setisNeprocinTaken(int neprocinTaken) {
		this.isNeprocinTaken = neprocinTaken;
	}

	public int getisNeprocinTaken() {
		return isNeprocinTaken;
	}

	public void setIsSunExposure(int sunexpsre) {
		this.isSunExposure = sunexpsre;
	}

	public int getIsSunExposure() {
		return isSunExposure;
	}
	
	public void setIsBookMarked(int bm){
		this.isBookMarked = bm;
	}
	
	public int getIsBookMarked(){
		return isBookMarked;
	}

	public void setOther(String othr) {
		this.other = othr;
	}

	public String getOther() {
		return other;
	}

}

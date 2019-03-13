package model;

import java.util.Date;

public class Computer {

	private int id;
	private String name;
	private int manufacturerId;
	private Date dateIntroduced;
	private Date dateDiscontinued;
	
	/////////////CONSTRUCTOR///////////////
	
	public Computer() {
		
	}
	
	public Computer(int nId, String nName, int nManufac) {
		this.id = nId;
		this.name = nName;
		this.manufacturerId = nManufac;
	}
	
	public Computer(int nId, String nName, int nManufac, Date nDateIntro, Date nDateDiscon) {
		this.id = nId;
		this.name = nName;
		this.manufacturerId = nManufac;
		this.dateIntroduced = nDateIntro;
		this.dateDiscontinued = nDateDiscon;
	}
	
	//////////////SETTER | GETTER ///////////////////
	
	//ID
	public void setId(int nId) {
		this.id = nId;
	}
	
	public int getId() {
		return this.id;
	}
	
	//NAME
	public void setName(String nName) {
		this.name = nName;
	}
	
	public String getName() {
		return this.name;
	}
	
	//MANUFACTURER ID
	public void setManufacturerId(int nManId) {
		this.manufacturerId = nManId;
	}
	
	public int getManufacturerId() {
		return this.manufacturerId;
	}
	
	//DATE INTRODUCED
	public void setDateIntroduced(Date nDate) {
		this.dateIntroduced = nDate;
	}
	
	public Date getDateIntroduced() {
		return this.dateIntroduced;
	}
	
	//DATE DISCONTINUED
	public void setDateDiscontinued(Date nDate) {
		this.dateDiscontinued = nDate;
	}
	
	public Date getDateDiscontinued() {
		return this.dateDiscontinued;
	}
	
	////////TO STRING////////
	
	public String toString() {
		String ret = "";
		
		ret += " -Computer-\n";
		ret += "id : " + this.getId() + "\n";
		ret += "name : " + this.getName() + "\n";
		ret += "company id : " + this.getManufacturerId() + "\n";
		ret += "_____________\n";
		
		return ret;
	}
}

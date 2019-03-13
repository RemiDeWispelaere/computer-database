package model;

public class Company {

	private int id;
	private String name;

	/////////////CONSTRUCTOR///////////////

	public Company() {

	}

	public Company(int nId, String nName) {	
		this.id = nId;
		this.name = nName;
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

	////////TO STRING////////

	public String toString() {
		String ret = " -Company-\n"
				+ "id : " + this.getId() + "\n"
				+ "name : " + this.getName() + "\n"
				+ "_____________\n";

		return ret;
	}
}

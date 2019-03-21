package model;

import java.sql.Date;

/**
 * The representation of a computer in the DB
 * @author DE WISPELAERE Rémi
 *
 */
public class Computer {

	private int id;
	private String name;
	private Long manufacturerId;
	private Date introducedDate;
	private Date discontinuedDate;

	/////////////CONSTRUCTOR///////////////

	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.manufacturerId = builder.companyId;
	}

	/**
	 * Standard constructor
	 * 
	 * @param nId The computer's Id
	 * @param nName The computer's Name
	 * @param nManufac The computer's company Id
	 * @param nDateIntro The computer's introduced date
	 * @param nDateDiscon The computer's discontinued date
	 */
//	public Computer(int nId, String nName, Long nManufac, Date nDateIntro, Date nDateDiscon) {
//		this.id = nId;
//		this.name = nName;
//		this.manufacturerId = nManufac;
//		this.introducedDate = nDateIntro;
//		this.discontinuedDate = nDateDiscon;
//	}

	//////////////SETTER | GETTER ///////////////////

	//ID
	/**
	 * Set the computer's Id
	 * 
	 * @param nId The new computer's Id
	 */
	public void setId(int nId) {
		this.id = nId;
	}

	/**
	 * Get the computer's Id
	 * 
	 * @return The current computers's Id
	 */
	public int getId() {
		return this.id;
	}

	//NAME
	/**
	 * Set the computer's Name
	 * 
	 * @param nName The new computer's Name
	 */
	public void setName(String nName) {
		this.name = nName;
	}

	/**
	 * Get the computer's Name
	 * 
	 * @return The current computer's Name
	 */
	public String getName() {
		return this.name;
	}

	//MANUFACTURER ID
	/**
	 * Set the computer's company Id
	 * 
	 * @param nManId The new computer's company Id
	 */
	public void setManufacturerId(Long nManId) {
		this.manufacturerId = nManId;
	}

	/**
	 * Get the computer's company Id
	 * 
	 * @return The current computer's company Id
	 */
	public Long getManufacturerId() {
		return this.manufacturerId;
	}

	//DATE INTRODUCED
	/**
	 * Set the computer's introduced date
	 * 
	 * @param nDate The new computer's introduced date
	 */
	public void setIntroducedDate(Date nDate) {
		this.introducedDate = nDate;
	}

	/**
	 * Get the computer's introduced date
	 * 
	 * @return The current computer's introduced date
	 */
	public Date getIntroducedDate() {
		return this.introducedDate;
	}

	//DATE DISCONTINUED
	/**
	 * Set the computer's discontinued date
	 * 
	 * @param nDate The new computer's discontinued date
	 */
	public void setDiscontinuedDate(Date nDate) {
		this.discontinuedDate = nDate;
	}

	/**
	 * Get the computer's discontinued date
	 * 
	 * @return The current computer's discontinued date
	 */
	public Date getDiscontinuedDate() {
		return this.discontinuedDate;
	}

	////////TO STRING////////

	public String toString() {
		String ret = "\n -Computer-\n" 
				+ "| id : " + this.getId() + "\n"
				+ "| name : " + this.getName() + "\n"
				+ "| company id : " + this.getManufacturerId() + "\n"
				+ "| introduced date : " + this.getIntroducedDate() + "\n"
				+ "| discontinued date : " + this.getDiscontinuedDate() + "\n"
				+ "_________________\n";

		return ret;
	}

	public static class ComputerBuilder {

		private int id;
		private String name;
		private Date introducedDate;
		private Date discontinuedDate;
		private Long companyId;

		public ComputerBuilder() {

		}

		public ComputerBuilder withId(int id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder withIntroducedDate(Date introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public ComputerBuilder withDiscontinuedDate(Date discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}
		
		public ComputerBuilder withCompanyId(Long companyId) {
			this.companyId = companyId;
			return this;
		}

		public Computer build() {
			Computer computer = new Computer(this);
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroducedDate(this.introducedDate);
			computer.setDiscontinuedDate(this.discontinuedDate);
			computer.setManufacturerId(this.companyId);
			return computer;
		}
	}
}

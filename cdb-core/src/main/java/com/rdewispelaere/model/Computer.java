package com.rdewispelaere.model;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The representation of a computer in the DB
 * @author DE WISPELAERE Rémi
 *
 */
@Entity
@Table(name="computer")
public class Computer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="company_id")
	private Long companyId;
	
	@Column(name="introduced")
	private Date introducedDate;
	
	@Column(name="discontinued")
	private Date discontinuedDate;

	/////////////CONSTRUCTOR///////////////
	
	public Computer() {}

	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.companyId = builder.companyId;
	}

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
	public void setCompanyId(Long nManId) {
		this.companyId = nManId;
	}

	/**
	 * Get the computer's company Id
	 * 
	 * @return The current computer's company Id
	 */
	public Long getCompanyId() {
		return this.companyId;
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
	public Optional<Date> getIntroducedDate() {
		return Optional.ofNullable(this.introducedDate);
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
	public Optional<Date> getDiscontinuedDate() {
		return Optional.ofNullable(this.discontinuedDate);
	}

	////////TO STRING////////

	public String toString() {
		String ret = "\n -Computer-\n" 
				+ "| id : " + this.getId() + "\n"
				+ "| name : " + this.getName() + "\n"
				+ "| company id : " + this.getCompanyId() + "\n"
				+ "| introduced date : " + this.getIntroducedDate().orElse(null) + "\n"
				+ "| discontinued date : " + this.getDiscontinuedDate().orElse(null) + "\n"
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
			return computer;
		}
	}
}

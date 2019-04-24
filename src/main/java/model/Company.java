package main.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The representation of a company in the DB
 * @author DE WISPELAERE Rémi
 *
 */
@Entity
@Table(name="COMPANY")
public class Company {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="name")
	private String name;

	/////////////CONSTRUCTOR///////////////

	public Company() {

	}

	/**
	 * Standard constructor
	 * @param nId The company's Id
	 * @param nName The company's Name
	 */
	public Company(int nId, String nName) {	
		this.id = nId;
		this.name = nName;
	}

	//////////////SETTER | GETTER ///////////////////

	//ID
	/**
	 * Set the company's Id
	 * @param nId The new company's Id
	 */
	public void setId(int nId) {
		this.id = nId;
	}

	/**
	 * Get the company's Id
	 * @return The current company's Id
	 */
	public int getId() {
		return this.id;
	}

	//NAME
	/**
	 * Set the company's Name
	 * @param nName The new company's Name
	 */
	public void setName(String nName) {
		this.name = nName;
	}

	/**
	 * Get the company's Name
	 * @return The current company's Name
	 */
	public String getName() {
		return this.name;
	}

	////////TO STRING////////

	public String toString() {
		String ret = "\n -Company-\n"
				+ "| id : " + this.getId() + "\n"
				+ "| name : " + this.getName() + "\n"
				+ "_____________\n";

		return ret;
	}
}

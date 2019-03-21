package dto;

public class ComputerDto {

	private int id;
	private String name;
	private Long manufacturerId;
	private String introducedDate;
	private String discontinuedDate;

	/////////////CONSTRUCTOR///////////////

	public ComputerDto(ComputerDtoBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.manufacturerId = builder.companyId;
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
	public void setIntroducedDate(String nDate) {
		this.introducedDate = nDate;
	}

	/**
	 * Get the computer's introduced date
	 * 
	 * @return The current computer's introduced date
	 */
	public String getIntroducedDate() {
		return this.introducedDate;
	}

	//DATE DISCONTINUED
	/**
	 * Set the computer's discontinued date
	 * 
	 * @param nDate The new computer's discontinued date
	 */
	public void setDiscontinuedDate(String nDate) {
		this.discontinuedDate = nDate;
	}

	/**
	 * Get the computer's discontinued date
	 * 
	 * @return The current computer's discontinued date
	 */
	public String getDiscontinuedDate() {
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

	public static class ComputerDtoBuilder {

		private int id;
		private String name;
		private String introducedDate;
		private String discontinuedDate;
		private Long companyId;

		public ComputerDtoBuilder() {

		}

		public ComputerDtoBuilder withId(int id) {
			this.id = id;
			return this;
		}

		public ComputerDtoBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public ComputerDtoBuilder withIntroducedDate(String introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}

		public ComputerDtoBuilder withDiscontinuedDate(String discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}
		
		public ComputerDtoBuilder withCompanyId(Long companyId) {
			this.companyId = companyId;
			return this;
		}

		public ComputerDto build() {
			ComputerDto computer = new ComputerDto(this);
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroducedDate(this.introducedDate);
			computer.setDiscontinuedDate(this.discontinuedDate);
			computer.setManufacturerId(this.companyId);
			return computer;
		}
	}
}

package dto;

import model.Company;

public class CompanyMapper {

	public CompanyMapper() {
		
	}
	
	public CompanyDto parseToDto(Company company) {
		return new CompanyDto(company.getId(), company.getName());
	}
	
	public Company parseToCompany(CompanyDto dto) {
		return new Company(dto.getId(), dto.getName());
	}
}

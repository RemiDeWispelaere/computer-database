package dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<CompanyDto> parseToDtosList(List<Company> listCompany){
		List<CompanyDto> retList = new ArrayList<CompanyDto>();

		for(Company company : listCompany) {
			retList.add(parseToDto(company));
		}

		return retList;
	}

	public List<Company> parseToCompaniesList(List<CompanyDto> listDto) throws ParseException{
		List<Company> retList = new ArrayList<Company>();

		for(CompanyDto dto : listDto) {
			retList.add(parseToCompany(dto));
		}

		return retList;
	}
}

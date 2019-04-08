package service;

import java.util.List;

import dao.CompanyDao;
import dao.DAOFactory;
import dto.CompanyDto;
import dto.CompanyMapper;

public class CompanyService {

	private static final CompanyMapper mapper = new CompanyMapper();
	private static final CompanyDao companyDao = DAOFactory.getInstance().getCompanyDao();
	
	public CompanyService() {
		super();
	}
	
	public List<CompanyDto> getAllCompanies(){
		return mapper.parseToDtosList(companyDao.findAll());
	}
	
	public List<CompanyDto> searchByName(String search){
		return mapper.parseToDtosList(companyDao.searchByName(search));
	}
}

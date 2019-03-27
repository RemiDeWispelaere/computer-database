package service;

import java.util.List;

import dao.CompanyDao;
import dao.DAOFactory;
import dto.CompanyDto;
import dto.CompanyMapper;

public class CompanyService {

	private static final CompanyMapper mapper = new CompanyMapper();
	private static final CompanyDao computerDao = DAOFactory.getInstance().getCompanyDao();
	
	public CompanyService() {
		super();
	}
	
	public List<CompanyDto> getAllCompanies(){
		return mapper.parseToDtosList(computerDao.findAll());
	}
}

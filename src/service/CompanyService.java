package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import dao.CompanyDao;
import dto.CompanyDto;
import dto.CompanyMapper;

@Service("companyService")
@Scope("singleton")
public class CompanyService {

	@Autowired @Qualifier("companyMapper")
	private static CompanyMapper mapper;
	@Autowired @Qualifier("companyDao")
	private static CompanyDao companyDao;
	
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

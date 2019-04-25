package com.rdewispelaere.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.rdewispelaere.dao.CompanyDao;
import com.rdewispelaere.dto.CompanyDto;
import com.rdewispelaere.dto.CompanyMapper;

@Service("companyService")
@Scope("singleton")
public class CompanyService {

	@Autowired
	private CompanyMapper mapper;
	@Autowired
	private CompanyDao companyDao;
	
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

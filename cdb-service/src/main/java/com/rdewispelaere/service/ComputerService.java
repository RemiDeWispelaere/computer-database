package com.rdewispelaere.service;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.rdewispelaere.dao.ComputerDao;
import com.rdewispelaere.dao.DAOException;
import com.rdewispelaere.dto.ComputerDto;
import com.rdewispelaere.dto.ComputerMapper;
import com.rdewispelaere.model.Computer;

@Service("computerService")
@Scope("singleton")
public class ComputerService {

	private static final Logger logger = Logger.getLogger(ComputerService.class);

	@Autowired
	private ComputerMapper mapper;
	@Autowired
	private ComputerDao computerDao;

	public ComputerService() {
		super();
	}

	public List<ComputerDto> getAllComputers(){
		return mapper.parseToDtosList(computerDao.findAll());
	}

	public Optional<ComputerDto> getComputerById(int computerId){
		return computerDao.findById(computerId).map(mapper::parseToDto);
	}

	public List<ComputerDto> searchByName(String search){
		return mapper.parseToDtosList(computerDao.searchByName(search));
	}

	public List<ComputerDto> searchByCompany(String search){
		return mapper.parseToDtosList(computerDao.findByCompany(search));
	}
	
	public void addComputer(ComputerDto computerDto) {
		
		try{
			computerDao.add(mapper.parseToComputer(computerDto));
			
		}catch (DAOException e) {
			logger.warn("Can not add this computer");
			e.printStackTrace();
		}catch (ParseException e) {
			logger.warn("Can not parse dto to computer");
			e.printStackTrace();
		}
	}

	public void updateComputer(ComputerDto computerDto) {

			try {
				computerDao.update(mapper.parseToComputer(computerDto));
				
			} catch (DAOException e) {
				logger.warn("Can not update this computer");
				e.printStackTrace();
			} catch (ParseException e) {
				logger.warn("Can not parse dto to computer");
				e.printStackTrace();
			}
	}

	public void deleteComputer(Integer computerId) {
		
		try {
			computerDao.delete(new Computer.ComputerBuilder().withId(computerId).build());
			
		}catch (DAOException e) {
			logger.warn("Can not delete this computer");
			e.printStackTrace();
		}
	}

	public List<ComputerDto> sortComputers(List<ComputerDto> list, String sortType){

		switch(sortType) {
		case "nameAsc":
			Collections.sort(list, ComputerDto.cpuByNameAsc);
			break;
		case "nameDesc":
			Collections.sort(list, ComputerDto.cpuByNameDesc);
		}

		return list;
	}
}

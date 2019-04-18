package main.java.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import main.java.dao.ComputerDao;
import main.java.dao.DAOException;
import main.java.dto.ComputerDto;
import main.java.dto.ComputerMapper;
import main.java.model.Computer;

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

//	public void addComputer(HttpServletRequest request) {
//
//		String computerName = request.getParameter("computerName");
//		String stIntroducedDate = request.getParameter("introduced");
//		String stDiscontinuedDate = request.getParameter("discontinued");
//		Long companyId = Long.valueOf(request.getParameter("companyId"));
//
//		if(checkName(computerName) && checkDates(stIntroducedDate, stDiscontinuedDate) && checkCompanyId(companyId)) {
//			ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder()
//					.withName(computerName)
//					.withCompanyId(companyId)
//					.withIntroducedDate(stIntroducedDate)
//					.withDiscontinuedDate(stDiscontinuedDate)
//					.build();
//
//			try {
//				computerDao.add(mapper.parseToComputer(computerDto));
//			} catch (DAOException e) {
//				logger.warn("Can not add this computer");
//				e.printStackTrace();
//			} catch (ParseException e) {
//				logger.warn("Can not parse dto to computer");
//				e.printStackTrace();
//			}
//		}
//		else {
//			logger.info("Can not add a computer with these informations");
//		}
//
//	}
	
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

//		if(checkId(computerId) && checkName(computerName) && checkDates(stIntroducedDate, stDiscontinuedDate) && checkCompanyId(companyId)) {
//			ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder()
//					.withId(computerId)
//					.withName(computerName)
//					.withCompanyId(companyId)
//					.withIntroducedDate(stIntroducedDate)
//					.withDiscontinuedDate(stDiscontinuedDate)
//					.build();

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

	private boolean checkId(int id) {
		return id > 0;
	}

	private boolean checkName(String name) {
		return name != null && name != "";
	}

	private boolean checkDates(String introDate, String disconDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean ret = false;

		try {
			ret = introDate == null || introDate.equals("")
					|| disconDate == null || disconDate.equals("")
					|| dateFormat.parse(disconDate).compareTo(dateFormat.parse(introDate)) >= 0;

		}catch(ParseException e) {
			logger.warn("Impossible to parse dates : " + e);
			e.printStackTrace();
		}

		return ret;
	}

	private boolean checkCompanyId(Long id) {
		return id > 0;
	}
}

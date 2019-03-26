package service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dao.ComputerDao;
import dao.DAOException;
import dao.DAOFactory;
import dto.ComputerDto;
import dto.ComputerMapper;

public class ComputerService {

	private static final ComputerMapper mapper = new ComputerMapper();
	private static final ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();
	
	public ComputerService() {
		super();
	}
	
	public List<ComputerDto> getAllComputers(){
		return mapper.parseToDtosList(computerDao.findAll());
	}
	
	public void addComputer(HttpServletRequest request) {
		
		String computerName = request.getParameter("computerName");
		String stIntroducedDate = request.getParameter("introduced");
		String stDiscontinuedDate = request.getParameter("discontinued");
		String stCompanyId = request.getParameter("companyId");
		
		Long companyId = Long.valueOf(stCompanyId);
		
		ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder()
				.withName(computerName)
				.withCompanyId(companyId)
				.withIntroducedDate(stIntroducedDate)
				.withDiscontinuedDate(stDiscontinuedDate)
				.build();
		
		try {
			computerDao.add(mapper.parseToComputer(computerDto));
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//public ComputerDto editComputer()
}

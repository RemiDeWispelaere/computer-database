package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import dao.ComputerDao;
import dao.DAOException;
import dto.ComputerDto;
import dto.ComputerMapper;

@Service("computerService")
@Scope("singleton")
public class ComputerService {

	private static final String PARAM_COMPUTER_ID = "computerId";
	private static final String PARAM_COMPUTER_NAME = "computerName";
	private static final String PARAM_COMPUTER_INTRODUCED = "introduced";
	private static final String PARAM_COMPUTER_DISCONTINUED = "discontinued";
	private static final String PARAM_COMPANY_ID = "companyId";

	@Autowired @Qualifier("computerMapper")
	private ComputerMapper mapper;
	@Autowired @Qualifier("computerDao")
	private ComputerDao computerDao;

	public ComputerService() {
		super();
	}

	public List<ComputerDto> getAllComputers(){
		return mapper.parseToDtosList(computerDao.findAll());
	}

	public Optional<ComputerDto> getComputerById(HttpServletRequest request){
		int computerId = Integer.valueOf(request.getParameter(PARAM_COMPUTER_ID));

		return computerDao.findById(computerId).map(mapper::parseToDto);
	}

	public List<ComputerDto> searchByName(String search){
		return mapper.parseToDtosList(computerDao.searchByName(search));
	}

	public List<ComputerDto> searchByCompany(String search){
		return mapper.parseToDtosList(computerDao.findByCompany(search));
	}

	public void addComputer(HttpServletRequest request) {

		String computerName = request.getParameter("computerName");
		String stIntroducedDate = request.getParameter("introduced");
		String stDiscontinuedDate = request.getParameter("discontinued");
		Long companyId = Long.valueOf(request.getParameter("companyId"));

		try {
			if(checkName(computerName) && checkDates(stIntroducedDate, stDiscontinuedDate) && checkCompanyId(companyId)) {
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
			else {
				System.out.println("Impossible d'ajouter l'ordinateur avec ces informations");
			}
		} catch (ParseException e) {
			System.out.println("Impossible de vérifier les dates : " + e);
		}

	}

	public void updateComputer(HttpServletRequest request) {

		int computerId = Integer.valueOf(request.getParameter(PARAM_COMPUTER_ID));
		String computerName = request.getParameter(PARAM_COMPUTER_NAME);
		String stIntroducedDate = request.getParameter(PARAM_COMPUTER_INTRODUCED);
		String stDiscontinuedDate = request.getParameter(PARAM_COMPUTER_DISCONTINUED);
		Long companyId = Long.valueOf(request.getParameter(PARAM_COMPANY_ID));

		try {
			if(checkId(computerId) && checkName(computerName) && checkDates(stIntroducedDate, stDiscontinuedDate) && checkCompanyId(companyId)) {
				ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder()
						.withId(computerId)
						.withName(computerName)
						.withCompanyId(companyId)
						.withIntroducedDate(stIntroducedDate)
						.withDiscontinuedDate(stDiscontinuedDate)
						.build();

				try {
					computerDao.update(mapper.parseToComputer(computerDto));
				} catch (DAOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Impossible d'ajouter l'ordinateur avec ces informations");
			}
		} catch (ParseException e) {
			System.out.println("Impossible de vérifier les dates. " + e);
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

	public boolean checkId(int id) {
		return id > 0;
	}

	public boolean checkName(String name) {
		return name != null && name != "";
	}

	public boolean checkDates(String introDate, String disconDate) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return introDate == null || introDate.equals("")
				|| disconDate == null || disconDate.equals("")
				|| dateFormat.parse(disconDate).compareTo(dateFormat.parse(introDate)) >= 0;
	}

	public boolean checkCompanyId(Long id) {
		return id > 0;
	}
}

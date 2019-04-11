package dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import dao.CompanyDao;
import dao.DAOFactory;
import model.Company;
import model.Computer;

@Service("computerMapper")
@Scope("singleton")
public class ComputerMapper {
	
	@Autowired @Qualifier("companyDao")
	private CompanyDao companyDao;

	public ComputerMapper(){

	}

	public ComputerDto parseToDto(Computer cpu) {
		ComputerDto dto = new ComputerDto.ComputerDtoBuilder()
				.withId(cpu.getId())
				.withName(cpu.getName())
				.withCompanyId(cpu.getCompanyId())
				.withCompanyName(companyDao.findById(cpu.getCompanyId()).orElse(new Company(0, "")).getName())
				.build();
		
		if(cpu.getIntroducedDate().isPresent()) {
			dto.setIntroducedDate(cpu.getIntroducedDate().toString());
		}
		
		if(cpu.getDiscontinuedDate().isPresent()) {
			dto.setDiscontinuedDate(cpu.getDiscontinuedDate().toString());
		}

		return dto;
	}

	public Computer  parseToComputer(ComputerDto dto) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Computer cpu = new Computer.ComputerBuilder()
				.withId(dto.getId())
				.withName(dto.getName())
				.withCompanyId(dto.getCompanyId())
				.build();
		
		if(!dto.getIntroducedDate().equals("")) {
			cpu.setIntroducedDate(new Date(dateFormat.parse(dto.getIntroducedDate()).getTime()));
		}
		
		if(!dto.getDiscontinuedDate().equals("")) {
			cpu.setDiscontinuedDate(new Date(dateFormat.parse(dto.getDiscontinuedDate()).getTime()));
		}

		return cpu;
	}

	public List<ComputerDto> parseToDtosList(List<Computer> listComputer){
		List<ComputerDto> retList = new ArrayList<ComputerDto>();

		for(Computer cpu : listComputer) {
			retList.add(parseToDto(cpu));
		}

		return retList;
	}

	public List<Computer> parseToComputersList(List<ComputerDto> listDto) throws ParseException{
		List<Computer> retList = new ArrayList<Computer>();

		for(ComputerDto dto : listDto) {
			retList.add(parseToComputer(dto));
		}

		return retList;
	}
}

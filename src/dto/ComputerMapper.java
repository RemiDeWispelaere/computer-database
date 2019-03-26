package dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Computer;

public class ComputerMapper {

	public ComputerMapper(){

	}

	public ComputerDto parseToDto(Computer cpu) {
		ComputerDto dto = new ComputerDto.ComputerDtoBuilder()
				.withId(cpu.getId())
				.withName(cpu.getName())
				.withCompanyId(cpu.getCompanyId())
				.withIntroducedDate(cpu.getIntroducedDate().toString())
				.withDiscontinuedDate(cpu.getDiscontinuedDate().toString())
				.build();

		return dto;
	}

	public Computer  parseToComputer(ComputerDto dto) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		Computer cpu = new Computer.ComputerBuilder()
				.withId(dto.getId())
				.withName(dto.getName())
				.withCompanyId(dto.getCompanyId())
				.withIntroducedDate(new Date(dateFormat.parse(dto.getIntroducedDate()).getTime()))
				.withDiscontinuedDate(new Date(dateFormat.parse(dto.getDiscontinuedDate()).getTime()))
				.build();

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

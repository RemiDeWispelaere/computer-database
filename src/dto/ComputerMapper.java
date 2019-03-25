package dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
				.withCompanyId(dto.getManufacturerId())
				.withIntroducedDate(new Date(dateFormat.parse(dto.getIntroducedDate()).getTime()))
				.withDiscontinuedDate(new Date(dateFormat.parse(dto.getDiscontinuedDate()).getTime()))
				.build();
		
		return cpu;
	}
}

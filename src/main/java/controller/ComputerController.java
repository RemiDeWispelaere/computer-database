package main.java.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.dto.CompanyDto;
import main.java.dto.ComputerDto;
import main.java.model.PageManager;
import main.java.service.CompanyService;
import main.java.service.ComputerService;

@Controller
public class ComputerController {

	private static final String PAGE_LIST_COMPUTER = "dashboard";
	private static final String PAGE_EDIT_COMPUTER = "editComputer";
	private static final String PAGE_ADD_COMPUTER = "addComputer";
	private static final String PAGE_ERROR_404 = "404";
	private static final String PAGE_ERROR_500 = "500";
	
	private static final String PARAM_START_INDEX = "startIndex";
	private static final String PARAM_SEARCH_VALUE = "search";
	private static final String PARAM_SORT_TYPE = "sort";
	private static final String PARAM_NB_COMPUTERS = "nbOfComputers";
	private static final String PARAM_NO_COMPUTERS = "noComputers";
	private static final String PARAM_PAGE_MANAGER = "pageManager";
	private static final String PARAM_LIST_COMPUTER_TO_DELETE = "selection";
	private static final String PARAM_LIST_COMPANIES = "companies";
	private static final String PARAM_COMPUTER_DTO = "computerDto";

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	
	@ModelAttribute
	public ComputerDto initComputerDto() {
		return new ComputerDto.ComputerDtoBuilder().build();
	}
	
	@GetMapping({"/", "/ListComputer"})
	public String getListComputer(@RequestParam(required = false) Map<String, String> params, Model model) {
		
		String stStartIndex = (params.containsKey(PARAM_START_INDEX) && !params.get(PARAM_START_INDEX).contentEquals("")) ? params.get(PARAM_START_INDEX) : "";
		String stSearch = (params.containsKey(PARAM_SEARCH_VALUE) && !params.get(PARAM_SEARCH_VALUE).contentEquals("")) ? params.get(PARAM_SEARCH_VALUE) : "";
		String stSort = (params.containsKey(PARAM_SORT_TYPE) && !params.get(PARAM_SORT_TYPE).contentEquals("")) ? params.get(PARAM_SORT_TYPE) : "";
		
		List<ComputerDto> listComputers;
		
		int startIndex;
		if("".equals(stStartIndex)) {
			startIndex = 0;
		}
		else {
			startIndex = Integer.valueOf(stStartIndex);
		}

		if("".equals(stSearch)) {
			listComputers = computerService.getAllComputers();			
		}
		else {
			listComputers = computerService.searchByName(stSearch);
			List<CompanyDto> listCompanies = companyService.searchByName(stSearch);
			for(CompanyDto company : listCompanies) {
				listComputers.addAll(computerService.searchByCompany("" + company.getId()));
			}			
		}
		
		if(!"".equals(stSort)) {
			listComputers = computerService.sortComputers(listComputers, stSort);
		}
		
		PageManager<ComputerDto> pageManager = new PageManager<>(listComputers);
		pageManager.setIndex(startIndex);
		
		if(!stStartIndex.equals("")) {
			model.addAttribute(PARAM_START_INDEX, startIndex);
		}
		if(!stSearch.equals("")) {
			model.addAttribute(PARAM_SEARCH_VALUE, stSearch);
		}
		if(!stSort.equals("")) {
			model.addAttribute(PARAM_SORT_TYPE, stSort);
		}
		
		model.addAttribute(PARAM_NB_COMPUTERS, listComputers.size());
		if(listComputers.isEmpty()) {
			model.addAttribute(PARAM_NO_COMPUTERS, true);
		}
		
		model.addAttribute(PARAM_PAGE_MANAGER, pageManager);
		
		return PAGE_LIST_COMPUTER;
	}
	
	@PostMapping("/DeleteComputer")
	public String deleteComputer(@RequestParam(required = true) Map<String, String> params, Model model) {
		
		String[] stListComputersDto = params.get(PARAM_LIST_COMPUTER_TO_DELETE).split(",");
		
		for(String cpuId : stListComputersDto) {
			computerService.deleteComputer(Integer.valueOf(cpuId));
		}
		
		return getListComputer(params, model);
	}
	
	@GetMapping("/AddComputer")
	public String getAddComputer(Model model) {
		
		model.addAttribute(PARAM_LIST_COMPANIES, companyService.getAllCompanies());
		
		return PAGE_ADD_COMPUTER;
	}
	
	@PostMapping("/AddComputer")
	public String postAddComputer(@ModelAttribute("computerDto")ComputerDto computerDto, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			//TODO
			return PAGE_ERROR_500;
		}

		computerService.addComputer(computerDto);
		
		return "redirect:/";
	}
	
	@GetMapping("/EditComputer")
	public String getEditComputer(@RequestParam(value = "computerId", required = true) String stComputerId, Model model) {
		
		int computerId = Integer.valueOf(stComputerId);
		Optional<ComputerDto> computerDto = computerService.getComputerById(computerId);
		
		if(computerDto.isPresent()) {
			model.addAttribute(PARAM_COMPUTER_DTO, computerDto.get());
			model.addAttribute(PARAM_LIST_COMPANIES, companyService.getAllCompanies());
			
			return PAGE_EDIT_COMPUTER;
		}
		else {
			return PAGE_ERROR_404;
		}
	}
	
	@PostMapping("/EditComputer")
	public String postEditComputer(@ModelAttribute("computerDto")ComputerDto computerDto, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			//TODO
			return PAGE_ERROR_500;
		}
		
		computerService.updateComputer(computerDto);
		
		return PAGE_LIST_COMPUTER;
	}
}

package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CompanyDto;
import dto.ComputerDto;
import model.PageManager;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class ListComputer
 */
@WebServlet("/ListComputer")
public class ListComputer extends HttpServlet {

	private static final String VIEW_LIST_COMPUTER = "/WEB-INF/views/dashboard.jsp";
	private static final String PARAM_START_INDEX = "startIndex";
	private static final String PARAM_SEARCH_VALUE = "search";
	private static final String PARAM_SORT_TYPE = "sort";
	private static final String ATT_PAGE_MANAGER = "pageManager";

	private static final long serialVersionUID = 1L;
	private static final ComputerService computerService = new ComputerService();
	private static final CompanyService companyService = new CompanyService();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListComputer() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String stStartIndex = request.getParameter(PARAM_START_INDEX);
		String stSearch = request.getParameter(PARAM_SEARCH_VALUE);
		String stSort = request.getParameter(PARAM_SORT_TYPE);

		List<ComputerDto> listComputers;
		
		int startIndex;
		if(stStartIndex == null | stStartIndex == "") {
			startIndex = 0;
		}
		else {
			startIndex = Integer.valueOf(stStartIndex);
		}

		if(stSearch == null || stSearch.equals("")) {
			listComputers = computerService.getAllComputers();			
		}
		else {
			listComputers = computerService.searchByName(stSearch);
			List<CompanyDto> listCompanies = companyService.searchByName(stSearch);
			for(CompanyDto company : listCompanies) {
				listComputers.addAll(computerService.searchByCompany("" + company.getId()));
			}			
		}
		
		if(stSort != null && !stSort.equals("")) {
			listComputers = computerService.sortComputers(listComputers, stSort);
		}
		
		PageManager<ComputerDto> pageManager = new PageManager<>(listComputers);
		pageManager.setIndex(startIndex);
		
		request.setAttribute(ATT_PAGE_MANAGER, pageManager);
		request.setAttribute(PARAM_SEARCH_VALUE, stSearch);
		request.setAttribute(PARAM_SORT_TYPE, stSort);
		
		this.getServletContext().getRequestDispatcher(VIEW_LIST_COMPUTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

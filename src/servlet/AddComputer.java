package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dto.CompanyDto;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	
	private static final String VIEW_FORM_ADD_COMPUTER = "/WEB-INF/views/addComputer.jsp";
	private static final String VIEW_RETURN = "ListComputer";
	private static final String ATT_LIST_COMPANIES = "companies";
	
	private static final long serialVersionUID = 1L;
	
	private static final CompanyService companyService = new CompanyService();
	private static final ComputerService computerService = new ComputerService();
	private static final Logger logger = Logger.getLogger(AddComputer.class);
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputer() {
		super();
		logger.info("CONSTR");
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.info("INIT");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompanyDto> companies = companyService.getAllCompanies();
		request.setAttribute(ATT_LIST_COMPANIES, companies);
		this.getServletContext().getRequestDispatcher(VIEW_FORM_ADD_COMPUTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		computerService.addComputer(request);
		
		response.sendRedirect(VIEW_RETURN);	
	}

}

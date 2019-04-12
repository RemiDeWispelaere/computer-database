package main.java.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.dto.CompanyDto;
import main.java.dto.ComputerDto;
import main.java.service.CompanyService;
import main.java.service.ComputerService;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	
	private static final String VIEW_EDIT_COMPUTER = "/WEB-INF/views/editComputer.jsp";
	private static final String VIEW_RETURN = "ListComputer";
	private static final String ATT_COMPUTER = "computer";
	private static final String ATT_LIST_COMPANIES = "companies";

	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(EditComputer.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("INIT");
		super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<ComputerDto> computer = computerService.getComputerById(request);
		List<CompanyDto> companies = companyService.getAllCompanies();
		
		request.setAttribute(ATT_COMPUTER, computer.orElse(null));	
		request.setAttribute(ATT_LIST_COMPANIES, companies);
		this.getServletContext().getRequestDispatcher(VIEW_EDIT_COMPUTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		computerService.updateComputer(request);
		
		response.sendRedirect(VIEW_RETURN);
	}

}

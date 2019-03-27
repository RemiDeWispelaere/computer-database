package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CompanyDto;
import dto.ComputerDto;
import service.CompanyService;
import service.ComputerService;

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
	
	private static final ComputerService computerService = new ComputerService();
	private static final CompanyService companyService = new CompanyService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
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

package main.java.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.dao.ComputerDao;
import main.java.model.Computer;
import main.java.service.ComputerService;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	
	private static final String VIEW_RETURN = "ListComputer";
	private static final String PARAM_LIST_COMPUTER = "selection"; 
	
	private static final Logger logger = Logger.getLogger(DeleteComputer.class);

	@Autowired
	private ComputerService computerService;
	
	private static final long serialVersionUID = 1L;
	
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] stListComputersDto = request.getParameter(PARAM_LIST_COMPUTER).split(",");
		System.out.println("------------------------------" + request.getParameter("startIndex"));
		for(String cpuId : stListComputersDto) {
			computerService.deleteComputer(Integer.valueOf(cpuId));
		}
		
		response.sendRedirect(VIEW_RETURN);
	}

}

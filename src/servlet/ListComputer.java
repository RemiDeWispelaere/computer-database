package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.ComputerDto;
import model.PageManager;
import service.ComputerService;

/**
 * Servlet implementation class ListComputer
 */
@WebServlet("/ListComputer")
public class ListComputer extends HttpServlet {
	
	private static final String VIEW_LIST_COMPUTER = "/WEB-INF/views/dashboard.jsp";
	private static final String PARAM_START_INDEX = "startIndex";
	private static final String ATT_PAGE_MANAGER = "pageManager";
	
	private static final long serialVersionUID = 1L;
	private static final ComputerService computerService = new ComputerService();
       
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
		int startIndex;
		if(stStartIndex == null | stStartIndex == "") {
			startIndex = 0;
		}
		else {
			startIndex = Integer.valueOf(stStartIndex);
		}
		
		
		List<ComputerDto> listComputers = computerService.getAllComputers();
		PageManager<ComputerDto> pageManager = new PageManager<>(listComputers);
		pageManager.setIndex(startIndex);
		
		request.setAttribute(ATT_PAGE_MANAGER, pageManager);
		
		this.getServletContext().getRequestDispatcher(VIEW_LIST_COMPUTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

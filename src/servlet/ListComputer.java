package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ComputerDao;
import dao.DAOFactory;
import model.Computer;

/**
 * Servlet implementation class ListComputer
 */
@WebServlet("/ListComputer")
public class ListComputer extends HttpServlet {
	
	private static final String VIEW_LIST_COMPUTER = "/WEB-INF/views/dashboard.jsp";
	private static final String LIST_COMPUTERS = "computers";
	
	private static final long serialVersionUID = 1L;
	private static final ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();
       
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
		List<Computer> listComputers = computerDao.findAll();
		request.setAttribute(LIST_COMPUTERS, listComputers);
		
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

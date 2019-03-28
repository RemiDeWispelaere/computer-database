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
import dto.ComputerDto;
import model.Computer;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	
	private static final String VIEW_RETURN = "ListComputer";
	private static final String PARAM_LIST_COMPUTER = "selection"; 
	
	private static final ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();
	
	private static final long serialVersionUID = 1L;
	
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] stListComputersDto = request.getParameter(PARAM_LIST_COMPUTER).split(",");
		
		for(String cpuId : stListComputersDto) {
			computerDao.delete(new Computer.ComputerBuilder().withId(Integer.valueOf(cpuId)).build());
		}
		
		response.sendRedirect(VIEW_RETURN);
	}

}

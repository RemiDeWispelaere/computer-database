package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ComputerDao;
import dao.DAOFactory;
import model.Computer;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

		String computerName = request.getParameter("computerName");
		String stIntroducedDate = request.getParameter("introduced");
		String stDiscontinuedDate = request.getParameter("discontinued");
		String stCompanyId = request.getParameter("companyId");

		Date introducedDate = null;	
		try{
			introducedDate = new Date(dateFormat.parse(stIntroducedDate).getTime());
		}catch(ParseException e) {
			
		}

		Date discontinuedDate = null;	
		try{
			discontinuedDate = new Date(dateFormat.parse(stDiscontinuedDate).getTime());
		}catch(ParseException e) {
		}

		Long companyId = Long.valueOf(stCompanyId);

		computerDao.add(new Computer(0, computerName, companyId, introducedDate, discontinuedDate));
	}

}

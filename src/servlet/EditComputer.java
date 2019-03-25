package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CompanyDao;
import dao.ComputerDao;
import dao.DAOFactory;
import model.Company;
import model.Computer;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	
	private static final String VIEW_EDIT_COMPUTER = "/WEB-INF/views/editComputer.jsp";
	private static final String VIEW_RETURN = "ListComputer";
	private static final String PARAM_COMPUTER_ID = "computerId";
	private static final String ATT_COMPUTER = "computer";
	private static final String ATT_LIST_COMPANIES = "companies";
	
	private static final ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();
	private static final CompanyDao companyDao = DAOFactory.getInstance().getCompanyDao();
	private static final long serialVersionUID = 1L;
       
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
		int computerId = Integer.valueOf(request.getParameter(PARAM_COMPUTER_ID));
		Optional<Computer> computer = computerDao.findById(computerId);
		List<Company> companies = companyDao.findAll();
		
		request.setAttribute(ATT_COMPUTER, computer.orElse(null));	
		request.setAttribute(ATT_LIST_COMPANIES, companies);
		this.getServletContext().getRequestDispatcher(VIEW_EDIT_COMPUTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int computerId = Integer.valueOf(request.getParameter("computerId"));
		String computerName = request.getParameter("computerName");
		String stIntroducedDate = request.getParameter("introduced");
		String stDiscontinuedDate = request.getParameter("discontinued");
		String stCompanyId = request.getParameter("companyId");

		Date introducedDate = null;	
		try{
			introducedDate = new Date(dateFormat.parse(stIntroducedDate).getTime());
		}catch(ParseException e) {
			System.out.println(e.getMessage());
		}

		Date discontinuedDate = null;	
		try{
			discontinuedDate = new Date(dateFormat.parse(stDiscontinuedDate).getTime());
		}catch(ParseException e) {
			System.out.println(e.getMessage());
		}

		Long companyId = Long.valueOf(stCompanyId);

		Computer computer = new Computer.ComputerBuilder()
				.withId(computerId)
				.withName(computerName)
				.withCompanyId(companyId)
				.withIntroducedDate(introducedDate)
				.withDiscontinuedDate(discontinuedDate)
				.build();
		computerDao.update(computer);
		
		response.sendRedirect(VIEW_RETURN);
	}

}

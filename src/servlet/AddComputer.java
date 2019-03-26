package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.CompanyDao;
import dao.ComputerDao;
import dao.DAOFactory;
import model.Company;
import model.Computer;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	
	private static final String VIEW_FORM_ADD_COMPUTER = "/WEB-INF/views/addComputer.jsp";
	private static final String VIEW_RETURN = "ListComputer";
	private static final String ATT_LIST_COMPANIES = "companies";
	private static final long serialVersionUID = 1L;
	private static final ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();
	private static final CompanyDao companyDao = DAOFactory.getInstance().getCompanyDao();
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
		List<Company> companies = companyDao.findAll();
		request.setAttribute(ATT_LIST_COMPANIES, companies);
		this.getServletContext().getRequestDispatcher(VIEW_FORM_ADD_COMPUTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
				.withName(computerName)
				.withCompanyId(companyId)
				.withIntroducedDate(introducedDate)
				.withDiscontinuedDate(discontinuedDate)
				.build();
		computerDao.add(computer).orElseThrow(RuntimeException::new);
		
		response.sendRedirect(VIEW_RETURN);
		
//		int newCpuId = computerDao.add(computer).orElseThrow(RuntimeException::new);
//		this.getServletContext().getRequestDispatcher("/WEBINF/views/computer" + newCpuId + ".jsp");
	}

}
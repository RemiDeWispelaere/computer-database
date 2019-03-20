package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ComputerDao;
import dao.DAOFactory;
import model.Computer;

@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	
	private Logger logger = LoggerFactory.getLogger(AddComputer.class);
	private ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();
	
	public void init(ServletConfig config) throws ServletException{
		logger.info("initialisation de la servlet AddComputer");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		
		String computerName = request.getParameter("computerName");
		String stIntroducedDate = request.getParameter("introduced");
		String stDiscontinuedDate = request.getParameter("discontinued");
		String stCompanyId = request.getParameter("companyId");
		
		Date introducedDate = null;	
		try{
			introducedDate = new Date(dateFormat.parse(stIntroducedDate).getTime());
		}catch(ParseException e) {
			logger.warn("INTRODUCED DATE INVALIDE");
		}
		
		Date discontinuedDate = null;	
		try{
			discontinuedDate = new Date(dateFormat.parse(stDiscontinuedDate).getTime());
		}catch(ParseException e) {
			logger.warn("DISCONTINUED DATE INVALIDE");
		}
		
		Long companyId = Long.valueOf(stCompanyId);
		
		computerDao.add(new Computer(0, computerName, companyId, introducedDate, discontinuedDate));
	}
}

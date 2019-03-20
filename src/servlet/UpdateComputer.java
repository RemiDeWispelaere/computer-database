package servlet;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ComputerDao;
import dao.DAOFactory;
import model.Computer;

public class UpdateComputer extends HttpServlet {
	
	private Logger logger = LoggerFactory.getLogger(UpdateComputer.class);
	private ComputerDao computerDao = DAOFactory.getInstance().getComputerDao();

	public void init(ServletConfig config) throws ServletException{
		logger.info("initialisation de la servlet UpdateComputer");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		
		int computerId = Integer.valueOf(request.getParameter("id"));
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
		
		computerDao.update(new Computer(computerId, computerName, companyId, introducedDate, discontinuedDate));
	}
}

package amu.m2sir.malodumont.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import amu.m2sir.malodumont.beans.MessageService;
import amu.m2sir.malodumont.cloneTwitter.App;


@WebServlet(
        name = "ServletMessage",
        urlPatterns = "/message"
)
public class ServletMesssage extends HttpServlet{
	MessageService messageService = App.messageService;
	private boolean registration = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3190065119457264110L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		String url;
		Map<String, String[]> parametres = req.getParameterMap();
		if(req.getSession().getAttribute("user") != null)
			url = "/Message.jsp";
		else if(!parametres.isEmpty() && parametres.containsKey("registration")) {
			System.out.println("registration");
			url = "/Registration.jsp";
			registration = true;
		}
		else if(registration)
			url = "/Registration.jsp";
		else 
				url = "/Login.jsp";
		try {
			this.getServletContext().getRequestDispatcher(url).forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

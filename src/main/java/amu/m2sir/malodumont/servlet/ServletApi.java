package amu.m2sir.malodumont.servlet;

import java.io.IOException;
import java.util.Map;

import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import amu.m2sir.malodumont.beans.LikeService;
import amu.m2sir.malodumont.beans.MessageService;
import amu.m2sir.malodumont.beans.UserService;
import amu.m2sir.malodumont.cloneTwitter.App;


@WebServlet(
        name = "ServletApi",
        urlPatterns = "/api"
)
public class ServletApi extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 145458477842157956L;
	MessageService messageService = App.messageService;
	UserService userService = App.userService;
	LikeService likeService = App.likeService;
	
	public void redirectRegistration(HttpServletRequest req, HttpServletResponse resp){
		try {
			this.getServletContext().getRequestDispatcher("/Registration.jsp").forward(req, resp);
		} catch (ServletException | IOException e1) {
			e1.printStackTrace();
		} 
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		Map<String, String[]> parametres = req.getParameterMap();
		JsonArrayBuilder arrayBuilder = null;
		String user = null;
		if(req.getSession().getAttribute("user") != null)
			user = req.getSession().getAttribute("user").toString();
		//System.out.println("Request de : "+user);
		if(!parametres.isEmpty() && parametres.containsKey("action")){
			switch(parametres.get("action")[0]){
				case "list" : arrayBuilder = messageService.getMessages(user); break;
				case "likeurs" : arrayBuilder = likeService.getLikes(); break;
				case "like" : arrayBuilder = likeService.like(Long.valueOf(parametres.get("messageId")[0]),
						req.getSession().getAttribute("user").toString()); break;
				case "add" : arrayBuilder = messageService.addMessage(parametres.get("contenu")[0],user); break;
				case "remove" : arrayBuilder = messageService.deleteMessage(parametres.get("messageId")[0]); break;
				case "registration" : arrayBuilder = userService.registration(parametres.get("user")[0], parametres.get("pwd")[0], req); break;
				case "login" : arrayBuilder = userService.connect(parametres.get("user")[0], parametres.get("pwd")[0], req); break;
				default : break;
			}
			resp.setContentType("application/json); charset=UTF-8");
			try {
				resp.getWriter().write(arrayBuilder.build().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

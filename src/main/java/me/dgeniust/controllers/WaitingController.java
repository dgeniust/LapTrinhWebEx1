package me.dgeniust.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.dgeniust.models.UserModel;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		HttpSession session = req.getSession(false);
		boolean ok = false;
		if (session != null && session.getAttribute("account") != null) {
			ok = true;
		}
		
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				session = req.getSession(true);
				session.setAttribute(cookie.getName(), cookie.getValue());
			}
			
		}
		if (!ok) {
			goHomePage(req, resp);
			return;
		}
			
		PrintWriter out = resp.getWriter();
		
		
	}

	public void goHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("home.jsp").forward(req, resp);

	}
}
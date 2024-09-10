package me.dgeniust.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.dgeniust.dao.UserServiceImpl;
import me.dgeniust.models.UserModel;
import me.dgeniust.services.IUserService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
	public static final String SESSION_USERNAME = "username";
	public static final String COOKIE_REMEMBER = "username";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean isRememberMe = false;
		String remember = req.getParameter("remember");

		if ("on".equals(remember)) {
			isRememberMe = true;
		}
		String errorMsg = "";
		if (username.isEmpty() || password.isEmpty()) {
			errorMsg = "Tài khoản hoặc mật khẩu không được trống";
			req.setAttribute("error", errorMsg);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		IUserService service = new UserServiceImpl();

		UserModel user = service.login(username, password);
		if (user != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute("account", user);
			System.out.println(user.getFullname());
			if (isRememberMe) {
				saveCookie(resp, username);
			}
			System.out.println(username+"has been login to web");
			req.getRequestDispatcher("helloworld.jsp").forward(req, resp);
		} else {
			System.out.println("Login fail");
			errorMsg = "Tài khoản hoặc mật khẩu không đúng";
			req.setAttribute("error", errorMsg); 
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("account") != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					session = req.getSession(true);
					session.setAttribute("username", cookie.getValue());
					resp.sendRedirect(req.getContextPath() + "/waiting");
					return;
				}
			}
		}
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	private void saveCookie(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie(COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}
}
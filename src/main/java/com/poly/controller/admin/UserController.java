package com.poly.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.entity.User;
import com.poly.service.UserService;
import com.poly.utils.XForm;
import com.poly.utils.XNumber;

@WebServlet(urlPatterns = "/admin/users")
public class UserController extends HttpServlet{
	private static final long serialVersionUID = 6059419205172657974L;
	UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = XForm.getInt("page", 1);
		int pagesize = XForm.getInt("pagesize", 8);
		int totalVideo = userService.getTotalUser();
		int totalPage = XNumber.roundUp((double) totalVideo / pagesize);
		List<User> users = userService.findWithPagination(page, pagesize);
		req.setAttribute("users", users);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("currentPage", page);
		req.setAttribute("pagesize", pagesize);
		req.getRequestDispatcher("/views/admin/user.jsp").forward(req, resp);
	}
	

}

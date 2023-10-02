package com.poly.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.common.BaseResponse;
import com.poly.constatnt.Constant;
import com.poly.dtos.UserDto;
import com.poly.entity.User;
import com.poly.service.UserService;
import com.poly.utils.XHttp;
import com.poly.utils.XScope;

@WebServlet(urlPatterns = { "/api/account/sign-in", "/api/account/sign-up", "/api/account/forget-password",
		"/api/account/change-password", "/api/account/sign-out" })
public class AccountApi extends HttpServlet {

	private static final long serialVersionUID = -3078888602052281294L;

	UserService userService = new UserService();
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getMethod();
		if (method.equalsIgnoreCase("POST")) {
			String uri = req.getRequestURI();
			if (uri.contains("sign-in")) {
				this.doSignIn(req, resp);
			} else if (uri.contains("sign-up")) {
				this.doSignUp(req, resp);
			} else if (uri.contains("forget-password")) {
				this.doForgetPassword(req, resp);
			} else if (uri.contains("change-password")) {
				this.doChangePassword(req, resp);
			}
		} else if (method.equalsIgnoreCase("GET")) {
			String uri = req.getRequestURI();
			if (uri.contains("sign-out")) {
				XScope.removeSession(Constant.USER);
				resp.sendRedirect("/asm/home");
			}
		}
	}

	public void doSignIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDto userDto = XHttp.of(req.getReader()).toEntity(UserDto.class);
		String message = userService.singIn(userDto);
		BaseResponse baseResponse = new BaseResponse(message, message.length() == 0);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	public void doSignUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = XHttp.of(req.getReader()).toEntity(User.class);

		String message = userService.doSignup(user);
		boolean isSuccess = message.length() == 0;
		BaseResponse baseResponse = new BaseResponse(isSuccess ? "Success full! Please back to login" : message,
				isSuccess);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	public void doForgetPassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = XHttp.of(req.getReader()).toEntity(User.class);
		String message = userService.forgetPassword(user);
		boolean isSuccess = message.length() == 0;
		BaseResponse baseResponse = new BaseResponse(
				isSuccess ? "Success. Please check your email to get password!" : message, isSuccess);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	public void doChangePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserDto userDto = XHttp.of(req.getReader()).toEntity(UserDto.class);
		User loginUser = XScope.getSession("user");
		String message = userService.changePassword(userDto, loginUser);
		boolean isSuccess = message.length() == 0;
		if (isSuccess) {
			XScope.removeSession("user");
		}
		BaseResponse baseResponse = new BaseResponse(isSuccess ? "Success full! Please login again" : message,
				isSuccess);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

}

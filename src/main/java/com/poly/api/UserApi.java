package com.poly.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.common.BaseResponse;
import com.poly.constatnt.Constant;
import com.poly.dtos.FavoriteUserDto;
import com.poly.dtos.ShareUserDto;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.UserService;
import com.poly.utils.XForm;
import com.poly.utils.XHttp;
import com.poly.utils.XNumber;
import com.poly.utils.XScope;

@WebServlet(urlPatterns = { "/api/admin/favorite-user", "/api/admin/share-user", "/api/admin/user", "/api/update-user",
		"/api/admin/delete-user", "/api/user-data" })
public class UserApi extends HttpServlet {

	private static final long serialVersionUID = 4875178134474428513L;
	ObjectMapper objectMapper = new ObjectMapper();
	UserService userService = new UserService();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (uri.contains("favorite-user")) {
			this.doGetFavoriteUser(req, resp);
		} else if (uri.contains("share-user")) {
			this.doGetShareUser(req, resp);
		} else if (uri.contains("update-user")) {
			this.doUpdateUser(req, resp);
		} else if (uri.contains("delete-user")) {
			this.doDeleteUser(req, resp);
		} else if (uri.contains("admin/user")) {
			this.doGetUser(req, resp);
		} else if(uri.contains("user-data")) {
			this.doGetUserData(req, resp);
		}
	}

	private void doGetUserData(HttpServletRequest req, HttpServletResponse resp) throws StreamWriteException, DatabindException, IOException {
		User us = XScope.getSession(Constant.USER);
		this.objectMapper.writeValue(resp.getOutputStream(), us);
	}

	private void doDeleteUser(HttpServletRequest req, HttpServletResponse resp)
			throws StreamWriteException, DatabindException, IOException {
		String id = XForm.getString("id", null);
		if (id != null) {
			boolean isSuccess = userService.delete(id);
			if (isSuccess) {
				BaseResponse baseResponse = new BaseResponse("Sucess!", true);
				this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
			} else {
				BaseResponse baseResponse = new BaseResponse("Delete Faile!", false);
				this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
			}
		} else {
			BaseResponse baseResponse = new BaseResponse("User not found!", false);
			this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
		}
	}

	private void doUpdateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User us = XHttp.of(req.getReader()).toEntity(User.class);
		User loginUser = XScope.getSession("user");
		String message = userService.updateUser(us, loginUser);
		boolean isSuccess = message.length() ==0;
		BaseResponse baseResponse = new BaseResponse(isSuccess ? "Update success!" : message, message.length() == 0);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	private void doGetUser(HttpServletRequest req, HttpServletResponse resp)
			throws StreamWriteException, DatabindException, IOException {
		String userId = XForm.getString("id", null);
		if (userId != null) {
			User us = userService.findById(userId);
			this.objectMapper.writeValue(resp.getOutputStream(), us);
		} else {
			this.objectMapper.writeValue(resp.getOutputStream(), null);
		}

	}

	private void doGetShareUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String videoid = XForm.getString("id", null);
		if (videoid != null) {
			List<ShareUserDto> datas = userService.findAllShareUser(videoid);
			this.objectMapper.writeValue(resp.getOutputStream(), datas);
		}

	}

	private void doGetFavoriteUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String videoid = XForm.getString("id", null);
		if (videoid != null) {
			List<FavoriteUserDto> datas = userService.findAllFavoriteUser(videoid);
			this.objectMapper.writeValue(resp.getOutputStream(), datas);
		}
	}

}

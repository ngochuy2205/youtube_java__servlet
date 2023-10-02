package com.poly.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.common.BaseResponse;
import com.poly.constatnt.Constant;
import com.poly.dtos.FavoriteUserDto;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.ReportService;
import com.poly.utils.XHttp;
import com.poly.utils.XScope;

@WebServlet(urlPatterns = { "/api/favorite/like", "/api/favorite/unlike"})
public class FavoriteApi extends HttpServlet {

	private static final long serialVersionUID = 5237906382938398411L;
	ObjectMapper objectMapper = new ObjectMapper();
	FavoriteService favoriteService = new FavoriteService();
	ReportService reportService = new ReportService();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User us = XScope.getSession(Constant.USER);

		String uri = req.getRequestURI();
		Video video = XHttp.of(req.getReader()).toEntity(Video.class);
		if (video != null) {
			if (uri.contains("/like")) {
				this.doLike(req, resp, us, video);
			} else if (uri.contains("/unlike")) {
				this.doUnLike(req, resp, us, video);
			} else {
				BaseResponse baseResponse = new BaseResponse("Video not found!", false);
				this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
			}
		}

	}

	public void doLike(HttpServletRequest req, HttpServletResponse resp, User us, Video video)
			throws ServletException, IOException {
		favoriteService.like(us, video);
		BaseResponse baseResponse = new BaseResponse("Success!", true);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	public void doUnLike(HttpServletRequest req, HttpServletResponse resp, User us, Video video)
			throws ServletException, IOException {
		favoriteService.unlike(us, video);
		BaseResponse baseResponse = new BaseResponse("Success!", true);
		this.objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

}

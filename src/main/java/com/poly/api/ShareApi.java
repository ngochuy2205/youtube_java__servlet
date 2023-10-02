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
import com.poly.dtos.ShareDto;
import com.poly.entity.Share;
import com.poly.entity.User;
import com.poly.service.ShareService;
import com.poly.utils.XHttp;
import com.poly.utils.XScope;

@WebServlet(urlPatterns = { "/api/share/user" })
public class ShareApi extends HttpServlet {
	private static final long serialVersionUID = 2130707616229684174L;
	ShareService shareService = new ShareService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ShareDto shareDto = XHttp.of(req.getReader()).toEntity(ShareDto.class);
		User us = XScope.getSession(Constant.USER);
		boolean isSuccess = shareService.share(ShareDto.toShare(shareDto, us));
		String message = isSuccess ? "Shared video!" : "Faile!";
		BaseResponse baseResponse = new BaseResponse(message, isSuccess);
		objectMapper.writeValue(resp.getOutputStream(), baseResponse);

	}

}

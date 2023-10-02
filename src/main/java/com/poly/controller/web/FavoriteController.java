package com.poly.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.constatnt.Constant;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.FavoriteService;
import com.poly.service.VideoService;
import com.poly.utils.XForm;
import com.poly.utils.XNumber;
import com.poly.utils.XScope;

@WebServlet("/favorite")
public class FavoriteController extends HttpServlet {
	private static final long serialVersionUID = 6130741550333467712L;
	private VideoService videoService = new VideoService();
	private FavoriteService favoriteService = new FavoriteService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User us = XScope.getSession(Constant.USER);
		int page = XForm.getInt("page", 1);
		int pagesize = XForm.getInt("pagesize", 8);
		int totalVideo = favoriteService.getTotalFavoriteVideo(us.getId());
		int totalPage = XNumber.roundUp((double) totalVideo / pagesize);
		List<Video> videos = videoService.findFavoriteVideoByUser(us.getId(), page , pagesize);
		req.setAttribute("videos", videos);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("currentPage", page);
		req.setAttribute("pagesize", pagesize);
		req.getRequestDispatcher("/views/web/favorite.jsp").forward(req, resp);
	}

}

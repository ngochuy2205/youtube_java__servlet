package com.poly.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.entity.Video;
import com.poly.service.VideoService;
import com.poly.utils.XForm;
import com.poly.utils.XNumber;

@WebServlet("/admin/videos")
public class VideoController extends HttpServlet {
	private static final long serialVersionUID = 3968414433292121855L;
	private VideoService videoService = new VideoService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = XForm.getInt("page", 1);
		int pagesize = XForm.getInt("pagesize", 8);
		int totalVideo = videoService.getTotalVideo();
		int totalPage = XNumber.roundUp((double) totalVideo / pagesize);
		List<Video> videos = videoService.findWithPagination(page, pagesize);
		req.setAttribute("videos", videos);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("currentPage", page);
		req.setAttribute("pagesize", pagesize);
		req.getRequestDispatcher("/views/admin/videos.jsp").forward(req, resp);
	}
	
}

package com.poly.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.entity.Video;
import com.poly.service.VideoService;

@WebServlet("/list-share-video")
public class Controller extends HttpServlet{
	VideoService videoService = new VideoService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Video> videos = videoService.findWithShare();
		req.setAttribute("videos", videos);
		req.getRequestDispatcher("/views/view.jsp").forward(req, resp);
	}
	
}

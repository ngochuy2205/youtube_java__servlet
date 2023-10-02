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
import com.poly.entity.Video;
import com.poly.service.VideoService;
import com.poly.utils.XForm;
import com.poly.utils.XHttp;

@WebServlet(urlPatterns = { "/api/videos", "/api/admin/videos" , "/api/justwatch"})
public class VideoApi extends HttpServlet {
	private VideoService videoService = new VideoService();
	private static final long serialVersionUID = 65514938066301075L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (uri.contains("/admin/videos")) {
			String method = req.getMethod();
			if (method.equalsIgnoreCase("POST")) {
				this.doCreateVideo(req, resp);
			} else if (method.equalsIgnoreCase("PUT")) {
				this.doUpdateVideo(req, resp);
			} else if (method.equalsIgnoreCase("DELETE")) {
				this.doDeleteVideo(req, resp);
			}
		} else if (uri.contains("/videos")) {
			this.doGetVideos(req, resp);
		}else if(uri.contains("/justwatch")) {
			this.doGetJustWatchVideo(req, resp);
		}
	}

	private void doGetJustWatchVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String ids[] = XHttp.of(req.getReader()).toEntity(String[].class);
		List<Video> videos = videoService.getJustWatchVideo(ids);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(resp.getOutputStream(), videos);
	}

	protected void doGetVideos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String vid = XForm.getString("id", null);
		String listAll = XForm.getString("list", "");
		ObjectMapper objectMapper = new ObjectMapper();
		if (vid != null) {
			Video video = videoService.findById(vid);
			objectMapper.writeValue(resp.getOutputStream(), video);
		} else if (listAll.equals("1")) {
			List<Video> videos = videoService.findAll();
			objectMapper.writeValue(resp.getOutputStream(), videos);
		} else {
			int page = XForm.getInt("page", 1);
			int pageSize = XForm.getInt("pagesize", 10);
			List<Video> list = videoService.findWithPagination(page, pageSize);
			objectMapper.writeValue(resp.getOutputStream(), list);
		}

	}

	protected void doCreateVideo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Video video = XHttp.of(req.getReader()).toEntity(Video.class);
		Video respVideo = videoService.insert(video);
		BaseResponse baseResponse = new BaseResponse(respVideo != null ? "Success" : "VideoId is exist",
				respVideo != null);
		objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	protected void doDeleteVideo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String id = XForm.getString("id", null);
		if (id != null) {
			videoService.delete(id);
		}
		BaseResponse baseResponse = new BaseResponse(id == null ? "Delete faile" : "Delete Success", id == null);
		objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

	protected void doUpdateVideo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Video video = XHttp.of(req.getReader()).toEntity(Video.class);
		Video respVideo = videoService.update(video);
		BaseResponse baseResponse = new BaseResponse(respVideo != null ? "Update Success" : "Update Faile",
				respVideo != null);
		objectMapper.writeValue(resp.getOutputStream(), baseResponse);
	}

}

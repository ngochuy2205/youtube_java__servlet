package com.poly.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entity.Report;
import com.poly.service.ReportService;

@WebServlet(urlPatterns = "/api/admin/report")
public class ReportApi extends HttpServlet {

	private static final long serialVersionUID = 3063964513352567142L;
	ReportService reportService = new ReportService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Report> reports = reportService.statistic();
		objectMapper.writeValue(resp.getOutputStream(), reports);
	}

}

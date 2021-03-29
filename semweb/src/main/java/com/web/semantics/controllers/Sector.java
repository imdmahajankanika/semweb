package com.web.semantics.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.semantics.DAO.StationsSector;

public class Sector extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sector() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = (String) request.getParameter("id");
		String sector = (String) request.getParameter("sector");
		StationsSector sr = new StationsSector(sector, id);

		Map<String, List<String>> rs = sr.getStations();

		Set<String> keys = rs.keySet();
		Iterator<String> i = keys.iterator();
		while (i.hasNext()) {
			String k = (String) i.next();
			request.setAttribute(k, rs.get(k));
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/AllStations.jsp").forward(request, response);
	}

}

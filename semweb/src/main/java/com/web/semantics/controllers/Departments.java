package com.web.semantics.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.semantics.DAO.DepartmentsDao;

public class Departments extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Departments() {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DepartmentsDao departments = new DepartmentsDao();
		Map<String, List<String>> rs = departments.getDepartments();

		request.setAttribute("liste_departments", rs.get("departments"));
		request.setAttribute("code_depts", rs.get("code_dept"));

		this.getServletContext().getRequestDispatcher("/WEB-INF/ByDepartments.jsp").forward(request, response);
	}

}
package com.web.semantics.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Search extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Search(){
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("ErrorMessage", "0");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Search.jsp" ).forward( request, response );
	}

}
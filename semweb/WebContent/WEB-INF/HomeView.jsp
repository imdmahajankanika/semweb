<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.jena.rdf.model.Literal, java.util.List, org.apache.jena.rdf.model.Resource"  %>
<!DOCTYPE html>


<html>
    <head>
        <meta charset="utf-8" />
        <title>Web Semantics project By kanika MAHAJAN</title>
    </head>

    <body>
             <h1> Train stations application </h1>
        <h2>Search your train station by following:</h2>
		<ul>
		
			<% String url = (String) request.getAttribute("web_app_url");
			
			String url_dept = url + "/department";
			String url_all = url + "/stations";
			String url_search = url + "/search";
			
			out.println("<li><a href=" + url_dept + ">Sort by department</a></li>");
			out.println("<li><a href=" + url_all + ">All stations</a></li>");
			out.println("<li><a href=" + url_search + ">Search the nearest stations</a></li>");
			%>
		</ul>
    </body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.jena.rdf.model.Literal, java.util.List, org.apache.jena.rdf.model.Resource, com.web.semantics.model.CreateOntology"  %>
<!DOCTYPE html>


<html prefix="xsd: https://www.w3.org/2001/XMLSchema#">
    <head>
        <meta charset="utf-8" />
        <title>All stations</title>
    </head>

    <body>
        <h1> 
        <%	
        	String id = (String) request.getParameter("id");
        	String sector = (String) request.getParameter("sector");
        	if (id == null){
        		out.println("All stations sort by alphabetic order");
        	}
        	else if (sector.equals("dept")) {
        			List<String> dpts = (List<String>) request.getAttribute("depts");
        			String name_dept = (String) (dpts.get(0));
        			out.println("Stations located in department : " + name_dept);
        	}
        
        
        %> 
        </h1>
        <% 
        	out.println("<h2> Every station comes with its geographic coordinates </h2>");
        %>
        <table style="width:50%">
        <%     		
    		
    		List<String> stations_uri = (List<String>) request.getAttribute("stations_uri");
    		List<String> stations = (List<String>) request.getAttribute("stations");
    		List<String> stations_id = (List<String>) request.getAttribute("stations_id");
    		List<String> latitudes = (List<String>) request.getAttribute("lat");
    		List<String> longitudes = (List<String>) request.getAttribute("lon");
    		List<String> depts = (List<String>) request.getAttribute("depts");
        	List<String> cities = (List<String>) request.getAttribute("cities");
    		
    		CreateOntology o = new CreateOntology();    		
    		
    		
    		
    		
    		if (sector == null){
    			out.println("<tr> <th> Station number </th> <th> Station name </th><th>City </th> <th> Department name </th> <th> Latitude </th> <th> Longitude </th> </tr> ");
        		for( int i = 0; i < stations.size(); i++ ){
        			out.println("<tr about=\"" + stations_uri.get(i) +  "\">");
        			
                	out.println("<td rel=\"" + o.getHasId() + "\" datatype=\"xsd:integer\">"+ stations_id.get(i) + "</td>");
                	out.println("<td property=\"" + o.getHasName() +  "\"> <a href=\"/semweb/station?id=" + stations_id.get(i) + "\">" + stations.get(i) + "</a></td>");
                	out.println("<td property=\"" + o.getPostalCode() +  "\">" + cities.get(i) + "</td>");
                	out.println("<td property=\"" + o.getAddrLocality() +  "\">" +  depts.get(i) + "</td>");
                	out.println("<td property=\"" + o.getHasLatitude() + "\" datatype=\"xsd:float\">" + latitudes.get(i) + "</td>");
                	out.println("<td property=\"" + o.getHasLongitude() + "\" datatype=\"xsd:float\">" + longitudes.get(i) + "</td>");
                	
                	out.println("</tr>");
        		}
        		
    		}
    		else if (sector.equals("dept")){
    			out.println("<tr> <th> Station ID </th> <th> Station name </th> <th>City </th> <th> Latitude </th> <th> Longitude </th> </tr>");
    			for( int i = 0; i < stations.size(); i++ ){
    				out.println("<tr about=\"" + stations_uri.get(i) +  "\">");

    				out.println("<td rel=\"" + o.getHasId() + "\" datatype=\"xsd:integer\">"+ stations_id.get(i) + "</td>");
    				out.println("<td property=\"" + o.getHasName() +  "\"> <a href=\"/semweb/station?id=" + stations_id.get(i) + "\">" + stations.get(i) + "</a></td>");

                	out.println("<td property=\"" + o.getPostalCode() +  "\">" + cities.get(i) + "</td>");
    				out.println("<td property=\"" + o.getHasLatitude() + "\" datatype=\"xsd:float\">" + latitudes.get(i) + "</td>");
                	out.println("<td property=\"" + o.getHasLongitude() + "\" datatype=\"xsd:float\">" + longitudes.get(i) + "</td>");
    				
    				out.println("</tr>");
    			}
    		}
        %>
        </table>
    </body>
</html>    
    
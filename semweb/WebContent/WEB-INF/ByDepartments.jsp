<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.jena.rdf.model.Literal, java.util.List, org.apache.jena.rdf.model.Resource"  %>
<!DOCTYPE html>


<html>
    <head>
        <meta charset="utf-8" />
        <title>Sort by department</title>
    </head>

    <body>
        <h1>Sort by department</h1>
        <h2>Choose the department to access the stations</h2>

        <table style="width:30%">
        	<tr>
        		<th>Department_Id</th>
        		<th>Department_Name</th>
        	</tr>
        	
        		<% 
        			List<Literal> rs = (List<Literal>) request.getAttribute("liste_departments");
    				List<String> cd = (List<String>) request.getAttribute("code_depts");

        	
        			for( int i = 0; i < rs.size(); i++ ){
        				out.println("<tr>");
            			out.println("<td> " + cd.get(i) + "</td>");
            			out.println("<td><a href=/semweb/sector?sector=dept&id=" + cd.get(i) + ">" + rs.get(i) + "</a></td>"); 
            			out.println("</tr>");
        			}
        		%>
        	
        	
        	
        	
        
        </table>
    </body>
</html>

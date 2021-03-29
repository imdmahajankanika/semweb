<%@page import="com.web.semantics.model.CreateOntology"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.web.semantics.model.CreateOntology"  %>
<!DOCTYPE html>


<html prefix="xsd: https://www.w3.org/2001/XMLSchema#">
    <head>
        <meta charset="utf-8" />
        <title>Results</title>
    </head>

    <body>
    <script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBIwzALxUPNbatRBj3Xi1Uhp0fFzwWNBkE&callback=listOfPlaces&libraries=places&v=weekly"
      async
    ></script>
    <script src="https://rawgit.com/yahoo/intl-messageformat/v1.3.0/dist/intl-messageformat-with-locales.min.js"></script>
        <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    
     <script type="text/javascript">
    	
     function listOfPlaces(){
    			
			var latitude= <%=request.getAttribute("lat")%>; 
			var longitude= <%=request.getAttribute("lon")%>; 
			var city="<%=request.getAttribute("stations")%>"; 
				city=city.replace("[", "");
				city=city.replace("]", "").split(',');
				
		      var map = new google.maps.Map(document.getElementById('map'), {
		        zoom: 13,
		        center: new google.maps.LatLng(latitude[0], longitude[0]),
		        mapTypeId: google.maps.MapTypeId.ROADMAP
		      });
		
		      var infowindow = new google.maps.InfoWindow();
		
		      var marker, i;
		
		      for (i = 0; i < latitude.length; i++) {  
		        marker = new google.maps.Marker({
		          position: new google.maps.LatLng(latitude[i], longitude[i]),
		          map: map
		        });
		
		        google.maps.event.addListener(marker, 'click', (function(marker, i) {
		          return function() {
		            infowindow.setContent(city[i]);
		            infowindow.open(map, marker);
		          }
		        })(marker, i));
		      }
		}
    		
		</script>
		
        <h1>Stations close to your position</h1>
        <%
	    	String latitude = request.getParameter("latitude");
	    	String longitude = request.getParameter("longitude");
	    	String max_dist = request.getParameter("max_dist");
	    	String num_results = request.getParameter("num_results");
	    	out.println("<p>");
	    	out.println("Your position : <br/>");
	    	out.println("Latitude : " + latitude);
	    	out.println("Longitude : " + longitude);
	    	out.print("<br/>");
	    	if (!(max_dist.equals(""))){
	    		out.println("You asked to see the stations in a radius of " + max_dist + "kms.");
	    	}
	    	
	    	out.println("</p>");
        %>

        <table style="width:60%">
        <%     		
    		
    		List<String> stations_uri = (List<String>) request.getAttribute("stations_uri");
    		List<String> stations = (List<String>) request.getAttribute("stations");
    		List<String> stations_id = (List<String>) request.getAttribute("stations_id");
    		List<String> latitudes = (List<String>) request.getAttribute("lat");
    		List<String> longitudes = (List<String>) request.getAttribute("lon");
    		List<String> depts = (List<String>) request.getAttribute("depts");
    		List<String> cities = (List<String>) request.getAttribute("cities");
    		List<String> distances = (List<String>) request.getAttribute("distances");
        	
    		CreateOntology o = new CreateOntology();    		
    		
    		if (stations.size() == 0){
    			out.println("<p>");
        		out.println("We found no stations corresponding to your criterias");
        		out.println("</p>");
        	}
    		else{
    			out.println("<tr> <th>Station id </th> <th>Station name </th> <th>City </th> <th>Department name </th> <th>Latitude </th> <th>Longitude </th> <th>Approximate distance </th></tr> ");
    		}
    		for( int i = 0; i < stations.size(); i++ ){
        		out.println("<tr about=\"" + stations_uri.get(i) +  "\">");
        		
               	out.println("<td rel=\"" + o.getHasId() + "\" datatype=\"xsd:integer\">"+ stations_id.get(i) + "</td>");
               	out.println("<td property=\"" + o.getHasName() +  "\"> <a href=\"/semweb/station?id=" + stations_id.get(i) + "\">" + stations.get(i) + "</a></td>");
               	out.println("<td id='city' property=\"" + o.getPostalCode() +  "\">" + cities.get(i) + "</td>");
               	out.println("<td property=\"" + o.getAddrLocality() +  "\">" +  depts.get(i) + "</td>");
               	out.println("<td id='latitude' property=\"" + o.getHasLatitude() + "\" datatype=\"xsd:float\">" + latitudes.get(i) + "</td>");
               	out.println("<td id='longitude' property=\"" + o.getHasLongitude() + "\" datatype=\"xsd:float\">" + longitudes.get(i) + "</td>");
               	out.println("<td property=\"" + o.getDistance() + "\" datatype=\"xsd:float\">" + distances.get(i).substring(0, 6) + "kms</td>");
                	
               	
               	
               	out.println("</tr>");
        	}
        	
        	
        %>
        </table>
        
				<div id="map"></div>
    </body>
    
    <style type="text/css">
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
  height: 60%;
}

/* Optional: Makes the sample page fill the window. */
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
      .custom-map-control-button {
        appearance: button;
        background-color: #fff;
        border: 0;
        border-radius: 2px;
        box-shadow: 0 1px 4px -1px rgba(0, 0, 0, 0.3);
        cursor: pointer;
        margin: 10px;
        padding: 0 0.5em;
        height: 40px;
        font: 400 18px Roboto, Arial, sans-serif;
        overflow: hidden;
      }
      .custom-map-control-button:hover {
        background: #ebebeb;
      }
#description {
  font-family: Roboto;
  font-size: 15px;
  font-weight: 300;
}

#infowindow-content .title {
  font-weight: bold;
}

#infowindow-content {
  display: none;
}

#map #infowindow-content {
  display: inline;
}

.pac-card {
  margin: 10px 10px 0 0;
  border-radius: 2px 0 0 2px;
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  outline: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  background-color: #fff;
  font-family: Roboto;
}

#pac-container {
  padding-bottom: 12px;
  margin-right: 12px;
}

.pac-controls {
  display: inline-block;
  padding: 5px 11px;
}

.pac-controls label {
  font-family: Roboto;
  font-size: 13px;
  font-weight: 300;
}

#pac-input {
  background-color: #fff;
  font-family: Roboto;
  font-size: 15px;
  font-weight: 300;
  margin-left: 12px;
  padding: 0 11px 0 13px;
  text-overflow: ellipsis;
  width: 400px;
}

#pac-input:focus {
  border-color: #4d90fe;
}

#title {
  color: #fff;
  background-color: #4d90fe;
  font-size: 25px;
  font-weight: 500;
  padding: 6px 12px;
}

#target {
  width: 345px;
}
</style>
</html>    
    
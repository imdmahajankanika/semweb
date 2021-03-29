<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.web.semantics.model.CreateOntology"  %>
<!DOCTYPE html>


<html prefix="xsd: https://www.w3.org/2001/XMLSchema#">
    <head>
        <meta charset="utf-8" />
        <title>Station</title>
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
		        zoom: 15,
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
		
    <%
			List<String> stations = (List<String>) request.getAttribute("stations");
			List<String> stations_uri = (List<String>) request.getAttribute("stations_uri");
			List<String> latitudes = (List<String>) request.getAttribute("lat");
    		List<String> longitudes = (List<String>) request.getAttribute("lon");
    		List<String> depts = (List<String>) request.getAttribute("depts");
        	List<String> cities = (List<String>) request.getAttribute("cities");
        	List<String> ohs = (List<String>) request.getAttribute("ohs");
        	CreateOntology o = new CreateOntology();

        	out.println("<body about=\"" + stations_uri.get(0) + "\">");
        	
        	
				out.println("<h1 property=\"" + o.getHasName() + "\">");
				out.println(stations.get(0));
				out.println("</h1>");	
			
				out.println("<h2>");
				out.println("<span property=\"" + o.getPostalCode() + "\">");
				out.println(cities.get(0));
				out.println("</span> - ");
				out.println("<span property=\"" + o.getAddrLocality() + "\">");
				out.println(depts.get(0));
				out.println("</span>");
				out.println("</h2>");
				
				
				out.println("<p>");
				out.println("Coordinates : <br/>");
				out.println("<ul>");
				out.println("<li>Latitude : <span property=\"" + o.getHasLatitude() + "\" datatype=\"xsd:float\">" + latitudes.get(0) + "</span></li>");
				out.println("<li>Longitude : <span property=\"" + o.getHasLongitude() + "\" datatype=\"xsd:float\">" + longitudes.get(0) + "</span></li>");
				out.println("</ul>");
				out.println("</p>");
			
				out.println("<p>");
				
				if (ohs.size() != 0){
					out.println("Opening Hours : ");
					out.println("<ul>");
					for ( int i = 0; i < ohs.size(); i++ ){
						out.println("<li property=\"" + o.getOpenHrs() + "\">");
						out.println(ohs.get(i));
						out.println("</li>");	
					}
					out.println("</ul>");	
				}
				else{
					out.println("We don't have any information about this station's opening hours.");
				}
				out.println("</p>");
			
			
			
			out.println("</body>");
		%>
		<div id="map"></div>
	</body>	
	
	<style type="text/css">
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
  height: 50%;
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
		
		
		
		
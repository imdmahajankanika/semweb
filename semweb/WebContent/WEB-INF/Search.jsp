<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8" />
		<title>Nearest stations</title>
	</head>
	
	<body>
		<h1>Find the nearest stations</h1>
		<%
			String error_message = (String) request.getAttribute("ErrorMessage");
			if (error_message.equals("1")){
				out.println("<p>");
				out.println("Error while processing your request. <br/>");
				out.println("Please enter all the fields are correctly! <br/>");
				out.println("Latitude and longitude should be floating numbers (with \".\", not \",\") or integers. <br/>");
				out.println("The distance and the number of results should be integers. <br/><br/>");
				out.println("</p>");
				out.println("<br/>");
			}
		
		
		%>
		
		
		<form name="searchForm" method="post" action="coordinates">
			<fieldset>
				<legend>Your coordinates</legend>
				<p>Please enter your coordinates</p>
				
				<label for="latitude">Latitude*</label>
				<input type="text" id="latitude" name="latitude" value="48.8566" size="20"/>
				<br/>
				<br/>
				
				<label for="longitude">Longitude*</label>
				<input type="text" id="longitude" name="longitude" value="2.3522" size="20"/>
				<br/>
				<br/>
		
				<label for="max_dist">Maximal distance (km)</label>
				<input type="text" id="max_dist" name="max_dist" value="" size="20"/>
				<br/>
				<br/>
				
				<label for="num_results">Number of results</label>
				<input type="text" id="num_results" name="num_results" value="10" size="20"/>
				<br/>
				<br/>
			
			<input type="submit" value="GO"/>
				<input type="reset" value="Reset"/>
			</fieldset>
		
		</form>
				 <input id="pac-input" class="controls" type="text"
				placeholder="Search Box" onfocusout="getLocation()"/>
				<div id="map"></div>
	<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBIwzALxUPNbatRBj3Xi1Uhp0fFzwWNBkE&callback=initAutocomplete&libraries=places&v=weekly"
      async
    ></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    
	</body>
     <script type="text/javascript">
     
     var form = $('#searchForm');
     form.submit(function()
     {
         $.ajax({
             url: 'coordinates',
             data: form.serialize(),
             type : 'get',
             success: function(data){ 
                 console.log(data);
             }
                 });

         //return false;

     });
     
     var getLocation =  function() {
    	  var address=document.getElementById("pac-input").value;
    	  var geocoder = new google.maps.Geocoder();
    	  geocoder.geocode( { 'address': address}, function(results, status) {

    	  if (status == google.maps.GeocoderStatus.OK) {
    		  document.getElementById("latitude").value = results[0].geometry.location.lat();
    		  document.getElementById("longitude").value = results[0].geometry.location.lng();
    	      } 
    	  }); 
    	}
       

       function initAutocomplete() {
    	   const map = new google.maps.Map(document.getElementById("map"), {
    	     center: { lat: 48.8566, lng: 2.3522 },
    	     zoom: 12,
    	     mapTypeId: "roadmap",
    	   });
    	   // Create the search box and link it to the UI element.
    	   const input = document.getElementById("pac-input");
    	   const searchBox = new google.maps.places.SearchBox(input);
    	   map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
    	   // Bias the SearchBox results towards current map's viewport.
    	   map.addListener("bounds_changed", () => {
    	     searchBox.setBounds(map.getBounds());
    	   });
    	   let markers = [];
    	   // Listen for the event fired when the user selects a prediction and retrieve
    	   // more details for that place.
    	   searchBox.addListener("places_changed", () => {
    	     const places = searchBox.getPlaces();

    	     if (places.length == 0) {
    	       return;
    	     }
    	     // Clear out the old markers.
    	     markers.forEach((marker) => {
    	       marker.setMap(null);
    	     });
    	     markers = [];
    	     // For each place, get the icon, name and location.
    	     const bounds = new google.maps.LatLngBounds();
    	     places.forEach((place) => {
    	       if (!place.geometry || !place.geometry.location) {
    	         console.log("Returned place contains no geometry");
    	         return;
    	       }
    	       const icon = {
    	         url: place.icon,
    	         size: new google.maps.Size(71, 71),
    	         origin: new google.maps.Point(0, 0),
    	         anchor: new google.maps.Point(17, 34),
    	         scaledSize: new google.maps.Size(25, 25),
    	       };
    	       // Create a marker for each place.
    	       markers.push(
    	         new google.maps.Marker({
    	           map,
    	           icon,
    	           title: place.name,
    	           position: place.geometry.location,
    	         })
    	       );

    	       if (place.geometry.viewport) {
    	         // Only geocodes have viewport.
    	         bounds.union(place.geometry.viewport);
    	       } else {
    	         bounds.extend(place.geometry.location);
    	       }
    	     });
    	     map.fitBounds(bounds);
    	   });
    	 }
 
</script>
<style type="text/css">
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
  height:60%;
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
  margin-left: 450px;
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
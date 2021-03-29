package com.web.semantics.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import com.web.semantics.model.CreateOntology;

public class GeoCoordinatesDao {

	private String latitude;
	private String longitude;
	private String num_results;
	private String max_dist;

	public GeoCoordinatesDao(String latitude, String longitude, String max_dist, String num_results) {
		this.latitude = latitude;
		this.longitude = longitude;
		if (num_results.equals("")) {
			this.num_results = "10";
		} else {
			this.num_results = num_results;
		}
		if (max_dist.equals("")) {
			this.max_dist = "50";
		} else {
			this.max_dist = max_dist;
		}
	}

	public Map<String, List<String>> getStations() {

		CreateOntology o = new CreateOntology();

		String datasetURL = o.getDataBase();
		String sparqlEndpoint = datasetURL + "/sparql";
		String sparqlUpdate = datasetURL + "/update";
		String graphStore = datasetURL + "/data";
		RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);

		Query query = QueryFactory.create(" PREFIX math: <http://www.w3.org/2005/xpath-functions/math#>\r\n"
				+ "SELECT ?st ?station ?id ?lat ?lon ?city ?dept ?dist_c\r\n" + "WHERE {\r\n" + "  ?st <"
				+ o.getHasName() + "> ?station.\r\n" + "  ?st <" + o.getHasId() + "> ?id.\r\n" + "  ?st <"
				+ o.getHasLatitude() + "> ?lat.\r\n" + "  ?st <" + o.getHasLongitude() + "> ?lon.\r\n" + "  ?st <"
				+ o.getAddrLocality() + "> ?dept_id.\r\n" + "  ?st <" + o.getPostalCode() + "> ?city_id.\r\n"
				+ "  ?city_id <" + o.getHasName() + "> ?city.\r\n" + "  ?dept_id <" + o.getHasName() + "> ?dept.\r\n"
				+ "  BIND ((?lon - " + this.longitude + ") as ?dlon)\r\n"
				+ "  BIND ((6371 * math:acos(math:sin(?lat*math:pi()/180)*math:sin(" + this.latitude + "*math:pi()/180)"
				+ "+ math:cos(?lat*math:pi()/180)*math:cos(" + this.latitude
				+ "*math:pi()/180)*math:cos(?dlon*math:pi()/180))) AS ?dist_c)\r\n" + "  FILTER (?dist_c < "
				+ this.max_dist + ")\r\n" + "}\r\n" + "ORDER BY ASC(?dist_c)\r\n" + "LIMIT " + this.num_results);

		QueryExecution qExec = conneg.query(query);
		ResultSet rs = qExec.execSelect();

		List<String> stations_uri = new ArrayList<String>();
		List<String> stations = new ArrayList<String>();
		List<String> stations_id = new ArrayList<String>();
		List<String> latitudes = new ArrayList<String>();
		List<String> longitudes = new ArrayList<String>();
		List<String> depts = new ArrayList<String>();
		List<String> cities = new ArrayList<String>();

		List<String> distances = new ArrayList<String>();
		Map<String, List<String>> all = new HashMap<String, List<String>>();

		Integer i = 0;
		while (rs.hasNext()) {
			i = i + 1;

			QuerySolution qs = rs.next();

			String station_uri = (String) qs.getResource("st").getURI();
			String station = (String) qs.getLiteral("station").getString();
			String station_id = (String) qs.getLiteral("id").getString();
			String lat = (String) qs.getLiteral("lat").getString();
			String lon = (String) qs.getLiteral("lon").getString();
			String dept = (String) qs.getLiteral("dept").getString();
			String city = (String) qs.getLiteral("city").getString();
			String dist = (String) qs.getLiteral("dist_c").getString();

			stations_uri.add(station_uri);
			stations.add(station);
			stations_id.add(station_id);
			latitudes.add(lat);
			longitudes.add(lon);
			depts.add(dept);
			cities.add(city);
			distances.add(dist);

		}
		qExec.close();
		conneg.close();

		all.put("stations_uri", stations_uri);
		all.put("stations", stations);
		all.put("stations_id", stations_id);
		all.put("lat", latitudes);
		all.put("lon", longitudes);
		all.put("depts", depts);
		all.put("cities", cities);
		all.put("distances", distances);

		return all;

	}

}

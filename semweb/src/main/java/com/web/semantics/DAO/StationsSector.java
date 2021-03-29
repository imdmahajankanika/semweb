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

public class StationsSector {

	private String sector;

	private String id;

	public StationsSector(String sector, String id) {
		this.sector = sector;
		this.id = id;
	}

	public Map<String, List<String>> getStations() {
		CreateOntology o = new CreateOntology();

		String datasetURL = o.getDataBase();
		String sparqlEndpoint = datasetURL + "/sparql";
		String sparqlUpdate = datasetURL + "/update";
		String graphStore = datasetURL + "/data";
		RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);

		Query query;

		String uri_dept = o.getDepts() + this.id;
		query = QueryFactory.create("SELECT ?st ?station ?id ?lat ?lon ?dept ?city\r\n" + "WHERE {\r\n" + "  ?st <"
				+ o.getHasName() + "> ?station.\r\n" + "  ?st <" + o.getHasId() + "> ?id.\r\n" + "  ?st <"
				+ o.getHasLatitude() + "> ?lat.\r\n" + "  ?st <" + o.getHasLongitude() + "> ?lon.\r\n" + "  ?st <"
				+ o.getAddrLocality() + "> <" + uri_dept + ">.\r\n" + "  ?st <" + o.getAddrLocality()
				+ "> ?id_dept.\r\n" + "  ?id_dept <" + o.getHasName() + "> ?dept. \r\n" + "  ?st <" + o.getPostalCode()
				+ "> ?id_city. \r\n" + "  ?id_city <" + o.getHasName() + "> ?city. \r\n" + "}\r\n"
				+ "ORDER BY ASC(?station) \r\n" + "LIMIT 1000");

		QueryExecution qExec = conneg.query(query);
		ResultSet rs = qExec.execSelect();

		List<String> stations_uri = new ArrayList<String>();
		List<String> stations = new ArrayList<String>();
		List<String> stations_id = new ArrayList<String>();
		List<String> latitudes = new ArrayList<String>();
		List<String> longitudes = new ArrayList<String>();
		List<String> depts = new ArrayList<String>();
		List<String> cities = new ArrayList<String>();
		Map<String, List<String>> all = new HashMap<String, List<String>>();

		while (rs.hasNext()) {

			QuerySolution qs = rs.next();

			String station_uri = (String) qs.getResource("st").getURI();
			String station = (String) qs.getLiteral("station").getString();
			String station_id = (String) qs.getLiteral("id").getString();
			String lat = (String) qs.getLiteral("lat").getString();
			String lon = (String) qs.getLiteral("lon").getString();
			String dept = (String) qs.getLiteral("dept").getString();
			String city = (String) qs.getLiteral("city").getString();

			stations_uri.add(station_uri);
			stations.add(station);
			stations_id.add(station_id);
			latitudes.add(lat);
			longitudes.add(lon);
			depts.add(dept);
			cities.add(city);

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

		return all;
	}

	public String getSecteur() {
		return sector;
	}

	public String getId() {
		return id;
	}

}

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
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import com.web.semantics.model.CreateOntology;

public class StationDao {

	private String id;

	public StationDao(String id) {
		this.id = id;
	}

	public Map<String, List<String>> getInfos() {

		CreateOntology o = new CreateOntology();

		String datasetURL = o.getDataBase();
		String sparqlEndpoint = datasetURL + "/sparql";
		String sparqlUpdate = datasetURL + "/update";
		String graphStore = datasetURL + "/data";
		RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);

		Query query = QueryFactory.create("SELECT ?st ?station ?lat ?lon ?id_dept ?dept ?city ?oh\r\n" + "WHERE {\r\n"
				+ "  ?st <" + o.getHasName() + "> ?station.\r\n" + "  ?st <" + o.getHasId() + "> \"" + this.id
				+ "\".\r\n" + "  ?st <" + o.getHasLatitude() + "> ?lat.\r\n" + "  ?st <" + o.getHasLongitude()
				+ "> ?lon.\r\n" + "  ?st <" + o.getAddrLocality() + "> ?id_dept.\r\n" + "  ?id_dept <" + o.getHasName()
				+ "> ?dept. \r\n" + "  ?st <" + o.getPostalCode() + "> ?id_city. \r\n" + "  ?id_city <" + o.getHasName()
				+ "> ?city. \r\n" + "  OPTIONAL {?st <" + o.getOpenHrs() + "> ?oh. } \r\n" + "}\r\n"
				+ "ORDER BY ASC(?station) \r\n" + "LIMIT 1000");

		QueryExecution qExec = conneg.query(query);
		ResultSet rs = qExec.execSelect();

		List<String> stations_uri = new ArrayList<String>();
		List<String> stations = new ArrayList<String>();
		List<String> latitudes = new ArrayList<String>();
		List<String> longitudes = new ArrayList<String>();
		List<String> id_depts = new ArrayList<String>();
		List<String> depts = new ArrayList<String>();
		List<String> cities = new ArrayList<String>();
		List<String> ohs = new ArrayList<String>();
		Map<String, List<String>> all = new HashMap<String, List<String>>();

		int i = 0;
		while (rs.hasNext()) {

			QuerySolution qs = rs.next();

			if (i == 0) {
				String station_uri = (String) qs.getResource("st").getURI();
				String station = (String) qs.getLiteral("station").getString();
				String lat = (String) qs.getLiteral("lat").getString();
				String lon = (String) qs.getLiteral("lon").getString();
				String dept = (String) qs.getLiteral("dept").getString();
				String uri_id_dept = (String) qs.getResource("id_dept").getURI();
				String id_dept = uri_id_dept.substring(o.getDepts().length());
				String city = (String) qs.getLiteral("city").getString();

				stations_uri.add(station_uri);
				stations.add(station);
				latitudes.add(lat);
				longitudes.add(lon);
				depts.add(dept);
				id_depts.add(id_dept);
				cities.add(city);
			}

			Literal oh = (Literal) qs.getLiteral("oh");
			if (oh != null) {
				ohs.add(oh.getString());
			}

			i = i + 1;
		}
		qExec.close();
		conneg.close();

		all.put("stations_uri", stations_uri);
		all.put("stations", stations);
		all.put("lat", latitudes);
		all.put("lon", longitudes);
		all.put("depts", depts);
		all.put("id_depts", id_depts);
		all.put("cities", cities);
		all.put("ohs", ohs);

		return all;

	}

	public String getId() {
		return id;
	}

}

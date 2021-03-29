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
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import com.web.semantics.model.CreateOntology;

public class DepartmentsDao {

	public DepartmentsDao() {

	}

	public Map<String, List<String>> getDepartments() {

		CreateOntology o = new CreateOntology();

		String datasetURL = o.getDataBase();
		String sparqlEndpoint = datasetURL + "/sparql";
		String sparqlUpdate = datasetURL + "/update";
		String graphStore = datasetURL + "/data";
		RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);

		Query query = QueryFactory.create("SELECT DISTINCT ?name_dept ?code_dept \r\n" + "WHERE {\r\n"
				+ "  ?code_dept <" + o.getHasName() + "> ?name_dept.\r\n" + "  ?station <" + o.getAddrLocality()
				+ "> ?code_dept.\r\n" + "}\r\n" + "ORDER BY ASC(?code_dept)\r\n" + "LIMIT 100");

		QueryExecution qExec = conneg.query(query);
		ResultSet rs = qExec.execSelect();

		List<String> departments = new ArrayList<String>();
		List<String> code_depts = new ArrayList<String>();
		Map<String, List<String>> d = new HashMap<String, List<String>>();

		Integer i = 0;
		while (rs.hasNext()) {
			i = i + 1;

			QuerySolution qs = rs.next();

			Literal departement = (Literal) qs.getLiteral("name_dept");

			Resource code_dept = (Resource) qs.getResource("code_dept");
			String uri_code_dept = code_dept.getURI();
			String num_dept = uri_code_dept.substring(o.getDepts().length());

			departments.add(departement.getString());
			code_depts.add(num_dept);

		}
		qExec.close();
		conneg.close();

		d.put("departments", departments);
		d.put("code_dept", code_depts);

		return d;

	}
}

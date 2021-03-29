package com.web.semantics.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.RDF;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.web.semantics.model.CreateOntology;
import com.web.semantics.util.Utility;

/**
 * @author kanika
 *
 */
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Generating the RDF model and uploading the dataset to Jena Fuseki Data files
	 * downloaded from
	 * "https://ressources.data.sncf.com/explore/?sort=modified&refine.theme=Gares"
	 * 
	 * @param args
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		generateTTL();
		this.getServletContext().getRequestDispatcher("/WEB-INF/Main.jsp").forward(request, response);
	}

	public void generateTTL() throws IOException {

		CreateOntology o = new CreateOntology("main");
		Model model = o.getModel();
		// Reading model resources
		Resource stations = model.getResource(o.getStations());
		Resource depts = model.getResource(o.getDepts());
		Resource cities = model.getResource(o.getCities());
		Resource TrainStations = model.getResource(o.getTrainStations());

		// Reading resource properties
		Property hasName = model.getProperty(o.getHasName());
		Property hasLatitude = model.getProperty(o.getHasLatitude());
		Property hasLongitude = model.getProperty(o.getHasLongitude());
		Property postalCode = model.getProperty(o.getPostalCode());
		Property addressLocality = model.getProperty(o.getAddrLocality());
		Property geoWithin = model.getProperty(o.getGeoWithin());
		Property hasId = model.getProperty(o.getHasId());
		Property open_Hrs = model.getProperty(o.getOpenHrs());

		ClassLoader classLoader = getClass().getClassLoader();
		// Reading data from stations dataset
		CSVParser parser = new CSVParserBuilder().withSeparator(Utility.SPLIT).build();
		CSVReader st_reader = new CSVReaderBuilder(
				new FileReader(classLoader.getResource(Utility.STATION_DATA).getPath())).withCSVParser(parser).build();
		// Converting each row of dataset to RDF triplets
		String[] st_record;
		// Skipping the first row with titles
		st_record = st_reader.readNext();
		while ((st_record = st_reader.readNext()) != null) {

			Resource station_code = model.getResource(stations + st_record[1]);
			Resource city_code = model.getResource(cities + st_record[4] + "_" + st_record[5]);
			Resource dept_code = model.getResource(depts + st_record[7]);

			Literal station_name = model.createLiteral(st_record[17], "fr");
			Literal city_name = model.createLiteral(st_record[6], "fr");
			Literal dept_name = model.createLiteral(st_record[8]);

			Literal identifier = model.createLiteral(st_record[1]);
			Literal latitude = model.createTypedLiteral(st_record[10], XSDDatatype.XSDdouble);
			Literal longitude = model.createTypedLiteral(st_record[9], XSDDatatype.XSDdouble);

			model.add(station_code, RDF.type, TrainStations);
			model.add(station_code, hasName, station_name);
			model.add(station_code, hasLatitude, latitude);
			model.add(station_code, hasLongitude, longitude);
			model.add(city_code, hasName, city_name);
			model.add(dept_code, hasName, dept_name);
			model.add(station_code, postalCode, city_code);
			model.add(station_code, addressLocality, dept_code);
			model.add(station_code, hasId, identifier);
			model.add(city_code, geoWithin, dept_code);
		}

		// Reading data from Timetable dataset
		CSVParser parser2 = new CSVParserBuilder().withSeparator(Utility.SPLIT).build();
		CSVReader tt_reader = new CSVReaderBuilder(
				new FileReader(classLoader.getResource(Utility.TIMETABLE_DATA).getPath())).withCSVParser(parser2)
						.build();
		// Converting each row of dataset to RDF triplets
		String[] tt_record;
		// Skipping the first row with titles
		tt_record = tt_reader.readNext();

		while ((tt_record = tt_reader.readNext()) != null) {

			Resource station_code = model.getResource(stations + tt_record[0]);
			String day = tt_record[2];
			Literal hrs;
			if (tt_record[4].equals("")) {
				hrs = model.createLiteral(day + " - jour normal : " + tt_record[3]);
			} else {
				hrs = model.createLiteral(day + " - jour normal : " + tt_record[3] + ", jour férié : " + tt_record[4]);
			}

			model.add(station_code, open_Hrs, hrs);
		}

		try {
			// FileWriter fw = new
			// FileWriter(classLoader.getResource(Utility.MODELOUTPUT_FILE).getFile());
			// OutputStream out = new
			// FileOutputStream(classLoader.getResource(Utility.MODELOUTPUT_FILE).getFile());
			// RDFDataMgr.write(out, model, Lang.TURTLE);
			// FileWriter out = new
			// FileWriter(classLoader.getResource(Utility.MODELOUTPUT_FILE).getFile());
			File file = new File(classLoader.getResource(".").getFile() + "export.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			try {
				model.write(out, "Turtle");
			} finally {
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String datasetURL = o.getDataBase();
		String sparqlEndpoint = datasetURL + "/sparql";
		String sparqlUpdate = datasetURL + "/update";
		String graphStore = datasetURL + "/data";
		RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint, sparqlUpdate, graphStore);
		conneg.load(model);

		System.out.print("Data uploaded !");
		st_reader.close();

		conneg.close();

	}
}

package com.web.semantics.model;

import org.apache.jena.ontology.OntDocumentManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.web.semantics.util.Utility;

/**
 * @author kanika Defining the ontology vocabulary
 *
 */
public class CreateOntology {

	private String stations;
	private String depts;
	private String cities;
	private String trainStations;
	private String hasName;
	private String hasLatitude;
	private String hasLongitude;
	private String postalCode;
	private String addrLocality;
	private String geoWithin;
	private String hasId;
	private String openHrs;
	private String distance;
	private String dataBase;
	private String namespace_ex;
	private String namespace_schema;
	private Model model;

	/**
	 * Creating RDF Ontology
	 */
	public CreateOntology() {
		this.namespace_ex = Utility.EXAMPLE;
		this.namespace_schema = Utility.SCHEMA;
		this.stations = namespace_ex + "stations/";
		this.depts = namespace_ex + "departments/";
		this.cities = namespace_ex + "cities/";
		this.trainStations = namespace_schema + "TrainStation";
		this.hasName = namespace_schema + "name";
		this.hasLatitude = namespace_schema + "latitude";
		this.hasLongitude = namespace_schema + "longitude";
		this.addrLocality = namespace_schema + "addressLocality";
		this.postalCode = namespace_schema + "postalCode";
		this.geoWithin = namespace_schema + "geoWithin";
		this.hasId = namespace_schema + "identifier";
		this.openHrs = namespace_schema + "openingHours";
		this.distance = namespace_schema + "distance";

		this.dataBase = Utility.DB;
	}

	public CreateOntology(String string) {

		this.model = readModel();
		this.namespace_ex = Utility.EXAMPLE;
		this.namespace_schema = Utility.SCHEMA;
		this.stations = namespace_ex + "stations/";
		this.depts = namespace_ex + "departments/";
		this.cities = namespace_ex + "cities/";
		this.trainStations = namespace_schema + "TrainStation";
		this.hasName = namespace_schema + "name";
		this.hasLatitude = namespace_schema + "latitude";
		this.hasLongitude = namespace_schema + "longitude";
		this.addrLocality = namespace_schema + "addressLocality";
		this.postalCode = namespace_schema + "postalCode";
		this.geoWithin = namespace_schema + "geoWithin";
		this.hasId = namespace_schema + "identifier";
		this.openHrs = namespace_schema + "openingHours";
		this.distance = namespace_schema + "distance";

		this.dataBase = Utility.DB;
	}

	/**
	 * @return the stations
	 */
	public String getStations() {
		return stations;
	}

	/**
	 * @param stations the stations to set
	 */
	public void setStations(String stations) {
		this.stations = stations;
	}

	/**
	 * @return the depts
	 */
	public String getDepts() {
		return depts;
	}

	/**
	 * @param depts the depts to set
	 */
	public void setDepts(String depts) {
		this.depts = depts;
	}

	/**
	 * @return the cities
	 */
	public String getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(String cities) {
		this.cities = cities;
	}

	/**
	 * @return the trainStations
	 */
	public String getTrainStations() {
		return trainStations;
	}

	/**
	 * @param trainStations the trainStations to set
	 */
	public void setTrainStations(String trainStations) {
		this.trainStations = trainStations;
	}

	/**
	 * @return the hasName
	 */
	public String getHasName() {
		return hasName;
	}

	/**
	 * @param hasName the hasName to set
	 */
	public void setHasName(String hasName) {
		this.hasName = hasName;
	}

	/**
	 * @return the hasLatitude
	 */
	public String getHasLatitude() {
		return hasLatitude;
	}

	/**
	 * @param hasLatitude the hasLatitude to set
	 */
	public void setHasLatitude(String hasLatitude) {
		this.hasLatitude = hasLatitude;
	}

	/**
	 * @return the hasLongitude
	 */
	public String getHasLongitude() {
		return hasLongitude;
	}

	/**
	 * @param hasLongitude the hasLongitude to set
	 */
	public void setHasLongitude(String hasLongitude) {
		this.hasLongitude = hasLongitude;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the addrLocality
	 */
	public String getAddrLocality() {
		return addrLocality;
	}

	/**
	 * @param addrLocality the addrLocality to set
	 */
	public void setAddrLocality(String addrLocality) {
		this.addrLocality = addrLocality;
	}

	/**
	 * @return the geoWithin
	 */
	public String getGeoWithin() {
		return geoWithin;
	}

	/**
	 * @param geoWithin the geoWithin to set
	 */
	public void setGeoWithin(String geoWithin) {
		this.geoWithin = geoWithin;
	}

	/**
	 * @return the hasId
	 */
	public String getHasId() {
		return hasId;
	}

	/**
	 * @param hasId the hasId to set
	 */
	public void setHasId(String hasId) {
		this.hasId = hasId;
	}

	/**
	 * @return the openHrs
	 */
	public String getOpenHrs() {
		return openHrs;
	}

	/**
	 * @param openHrs the openHrs to set
	 */
	public void setOpenHrs(String openHrs) {
		this.openHrs = openHrs;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the dataBase
	 */
	public String getDataBase() {
		return dataBase;
	}

	/**
	 * @param dataBase the dataBase to set
	 */
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	/**
	 * Creating the model from ontology file
	 * 
	 * @throws OWLOntologyCreationException
	 */
	public Model readModel() {
		// create an empty model
		Model model = ModelFactory.createOntologyModel();

		OntDocumentManager dm = ((OntModel) model).getDocumentManager();

		dm.setProcessImports(true);
		ClassLoader classLoader = getClass().getClassLoader();
		// Read the Ontology to create an in-memory DOM structure
		model.read(classLoader.getResource(Utility.OWL_FILE).getFile());
		// use the FileManager to find the input file
//		InputStream in = FileManager.get().open(owl_file);
//		if (in == null) {
//			System.out.println("Ontology file: " + owl_file + " not found");
//			return null;
//		}
//
//		// read the RDF/XML file
//		model.read(in, "");
//		try {
//			in.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			return null;
//		}
		return model;
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
	}

}
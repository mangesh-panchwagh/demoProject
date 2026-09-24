package resources;

// enum is special class in java which has collection of methods or constants

public enum APIResources {

	// enum treates all method as one collection
	
	
	AddPlaceAPI("/maps/api/place/add/json"), // enum treats this as method 
	getPlaceAPI("/maps/api/place/get/json"),
	deletePlaceAPI("/maps/api/place/delete/json");

	private String resource;
	
	// constructor
	APIResources(String resource) {		// this variable coming from constructor and assigning to current class resource
		this.resource = resource;
	}
	
	// Method to get resource
	public String getResource() {
		return resource;
	}
	
	
}

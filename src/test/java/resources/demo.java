package resources;

// This class does not have any significance just added for demo purpose, never used anywhere
// just to show enum advantages we have used to show its disadvantages
public class demo {

	String addPlaceAPI = "/maps/api/place/add/json";
	String getPlaceAPI = "/maps/api/place/get/json";
	String deletePlaceAPI = "/maps/api/place/delete/json";
	
	public String getAddPlaceAPI() {
		return addPlaceAPI;
	}
	
	public String getPlaceAPI() {
		return getPlaceAPI;
	}
	
	public String deletePlaceAPI() {
		return deletePlaceAPI;
	}
	
}

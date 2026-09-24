
Feature:Validating Place API's
@AddPlace @Regression
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "POST" http request
	Then the API calls got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	#And verify place_Id created maps to "<address>" using "getPlaceAPI"
	
	
	Examples:
		| name    | language  | address              |
	#	| AAhouse | English   | World cross center   |
		| Mangesh | Marathi   | Malabar hills Mumbai |
		
@DeletePlace @Regression 
Scenario: Verify if Delete Place Functionality is working
	Given DeletePlace Payload 
	When user calls "deletePlaceAPI" with "POST" http request
	Then the API calls got success with status code 200
	And "status" in response body is "OK"
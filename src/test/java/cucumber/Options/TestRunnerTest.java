package cucumber.Options;

//import org.junit.runner.RunWith;

//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

// @RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features",plugin="json:target/jsonReports/cucumber-report.json",glue={"stepDefinitions"})
public class TestRunnerTest extends AbstractTestNGCucumberTests{
	
	
}

package org.coffeecart5340.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue ={
                "org.coffeecart5340.cucumber.steps",
                "org.coffeecart5340.cucumber.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class TestRunnerCucumber extends AbstractTestNGCucumberTests {

}

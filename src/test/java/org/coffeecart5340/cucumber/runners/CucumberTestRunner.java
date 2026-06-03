package org.coffeecart5340.cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.coffeecart5340.cucumber.steps",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@smoke" // запускати тільки сценарії з тегом @smoke
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
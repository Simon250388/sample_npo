package org.simon.npo.cucumber.config;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber") // tell JUnit 5 to use  Cucumber test engine to run features
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.simon.npo.cucumber") // specify path to steps definitions
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/cucumber/features/") // // specify path to feature files
public class CucumberTestConfiguration {}

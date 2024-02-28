package org.simon.npo.cucumber.config

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.Suite

@Suite
@IncludeEngines("cucumber") // tell JUnit 5 to use  Cucumber test engine to run features
@ConfigurationParameter(
    key = Constants.GLUE_PROPERTY_NAME,
    value = "org.simon.npo.cucumber"
) // specify path to steps definitions
@ConfigurationParameter(
    key = Constants.FEATURES_PROPERTY_NAME,
    value = "src/test/resources/cucumber/features/"
) // // specify path to feature files
class CucumberTestConfiguration

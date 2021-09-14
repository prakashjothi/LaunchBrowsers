package org.browsers;

import org.base.BaseClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Browsers extends BaseClass {

	@BeforeMethod
	private void beforeMethod() {

	}

	@Test
	private void chrome() {
		launchBrowser("chrome");

	}

	@Test
	private void firefox() {
		launchBrowser("firefox");

	}
	
	@Test
	private void edge() {
		launchBrowser("edge");
	}

}

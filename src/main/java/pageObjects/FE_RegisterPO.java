package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;

public class FE_RegisterPO extends AbstractPO {
	WebDriver driver;

	public FE_RegisterPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

}

package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;

public class FE_HomePO extends AbstractPO {
	WebDriver driver;

	public FE_HomePO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

}

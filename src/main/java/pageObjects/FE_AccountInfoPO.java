package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;

public class FE_AccountInfoPO extends AbstractPO {
	WebDriver driver;

	public FE_AccountInfoPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

}

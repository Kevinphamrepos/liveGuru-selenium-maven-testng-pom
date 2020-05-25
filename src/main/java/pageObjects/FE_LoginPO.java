package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;

public class FE_LoginPO extends AbstractPO {
	WebDriver driver;

	public FE_LoginPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

}

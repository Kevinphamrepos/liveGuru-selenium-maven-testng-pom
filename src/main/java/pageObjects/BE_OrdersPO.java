package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;

public class BE_OrdersPO extends AbstractPO {
	WebDriver driver;

	public BE_OrdersPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}


}

package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import commons.AbstractPO;
import pageUIs.BE_ManageCustomersPageUI;

public class BE_ManageCustomersPO extends AbstractPO {
	WebDriver driver;

	public BE_ManageCustomersPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public void clickToClosePopup() {
		List <WebElement> popupElements =  driver.findElements(By.xpath(BE_ManageCustomersPageUI.POPUP_WINDOW));
		if (popupElements.size() > 0) {
			Assert.assertTrue(popupElements.get(0).isDisplayed());
			System.out.println("Popup displayed status = " + popupElements.get(0).isDisplayed());
			driver.findElement(By.xpath(BE_ManageCustomersPageUI.POPUP_WINDOW_CLOSE)).click();
		}
		
	}

}


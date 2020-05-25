package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_DashboardPageUI;

public class FE_DashboardPO extends AbstractPO {
	WebDriver driver;

	public FE_DashboardPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}
	
	public String getSuccessMessageText() {
		waitForElementVisibleByXpath(FE_DashboardPageUI.REGISTER_SUCCESS_MGS_TEXT);
		return getTextElement(FE_DashboardPageUI.REGISTER_SUCCESS_MGS_TEXT);
	}


	public void clickToWebsiteLogo() {
		waitForElementVisibleByXpath(FE_DashboardPageUI.WEBSITE_LOGO);
		clickOnElement(FE_DashboardPageUI.WEBSITE_LOGO);
	}

}

package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_CompareWindowPageUI;

public class FE_CompareWindowPO extends AbstractPO {
	WebDriver driver;

	public FE_CompareWindowPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public boolean isCompareWindowHeaderDisplayed() {
		waitForElementVisibleByXpath(FE_CompareWindowPageUI.COMPARE_WINDOW_HEADER_TEXT);
		return isElementDisplayed(FE_CompareWindowPageUI.COMPARE_WINDOW_HEADER_TEXT);
	}

	public boolean isDynamicProductNameDisplayed(String productName) {
		waitForDynamicElementVisibleByXpath(FE_CompareWindowPageUI.DYNAMIC_PRODUCT_NAME, productName);
		return isDynamicElementDisplayed(FE_CompareWindowPageUI.DYNAMIC_PRODUCT_NAME, productName);
	}

	public void clickOnCloseWindowButton() {
		waitForElementClickableByXpath(FE_CompareWindowPageUI.CLOSE_WINDOW_BUTTON);
		clickOnElement(FE_CompareWindowPageUI.CLOSE_WINDOW_BUTTON);
	}

}

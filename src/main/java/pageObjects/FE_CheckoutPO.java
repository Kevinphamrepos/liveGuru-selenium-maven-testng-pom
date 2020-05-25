package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_CheckoutPageUI;

public class FE_CheckoutPO extends AbstractPO {
	WebDriver driver;

	public FE_CheckoutPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}
	public void clickOnDynamicContinueButtonByID(String continuButtonID) {
		waitForDynamicElementClickableByXpath(FE_CheckoutPageUI.DYNAMIC_CONTINUE_BUTTON_BY_ID, continuButtonID);
		clickOnDynamicElement(FE_CheckoutPageUI.DYNAMIC_CONTINUE_BUTTON_BY_ID, continuButtonID);
	}
	public String getOrderNumber() {
		waitForElementVisibleByXpath(FE_CheckoutPageUI.ORDER_NUMBER);
		return getTextElement(FE_CheckoutPageUI.ORDER_NUMBER);
	}
	
}

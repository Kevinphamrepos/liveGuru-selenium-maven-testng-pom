package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.BE_LoginPageUI;

public class BE_LoginPO extends AbstractPO {
	WebDriver driver;

	public BE_LoginPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public void inputToDynamicLoginTextbox(String textboxName, String inputValue) {
		waitForDynamicElementVisibleByXpath(BE_LoginPageUI.DYNAMIC_LOGIN_TEXTBOX, textboxName);
		sendKeysToDynamicElement(BE_LoginPageUI.DYNAMIC_LOGIN_TEXTBOX, inputValue, textboxName);
	}
	
	public void clickOnLoginButtonBE() {
		waitForDynamicElementVisibleByXpath(BE_LoginPageUI.LOGIN_BUTTON);
		clickOnDynamicElement(BE_LoginPageUI.LOGIN_BUTTON);
	}

}

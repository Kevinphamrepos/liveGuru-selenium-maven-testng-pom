package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_WishlistSharingPageUI;

public class FE_WishlistSharingPO extends AbstractPO {
	WebDriver driver;

	public FE_WishlistSharingPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public boolean isWishlistSharingHeaderTextDisplayed() {
		waitForElementVisibleByXpath(FE_WishlistSharingPageUI.WHISHLIST_SHARING_HEADER_TEXT);
		return isElementDisplayed(FE_WishlistSharingPageUI.WHISHLIST_SHARING_HEADER_TEXT);
	}
	
	public void clickOnDynamicActionByTitle(String titleValue) {
		waitForDynamicElementClickableByXpath(FE_WishlistSharingPageUI.DYNAMIC_ACTION_BUTTON, titleValue);
		clickOnDynamicElement(FE_WishlistSharingPageUI.DYNAMIC_ACTION_BUTTON, titleValue);
	}
}

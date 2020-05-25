package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_MyWishlistPageUI;

public class FE_MyWishlistPO extends AbstractPO {
	WebDriver driver;

	public FE_MyWishlistPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public boolean isDynamicMyWishlistSuccessMsgDisplayed(String successMsg) {
		waitForDynamicElementVisibleByXpath(FE_MyWishlistPageUI.DYNAMIC_ADD_TO_WISHLIST_SUCCESS_MSG, successMsg);
		return isDynamicElementDisplayed(FE_MyWishlistPageUI.DYNAMIC_ADD_TO_WISHLIST_SUCCESS_MSG, successMsg);
	}


	public void clickOnDynamicActionByTitle(String titleValue) {
		waitForDynamicElementClickableByXpath(FE_MyWishlistPageUI.DYNAMIC_ACTION_BUTTON, titleValue);
		clickOnDynamicElement(FE_MyWishlistPageUI.DYNAMIC_ACTION_BUTTON, titleValue);
	}

	public int getTotalNumberPresentingProductByName() {
		waitForListElementsVisibleByXpath(FE_MyWishlistPageUI.PRESENT_PRODUCT_NAME);	
		return getSizeListElements(FE_MyWishlistPageUI.PRESENT_PRODUCT_NAME);
		
	}

	public boolean isWishlistEmptyMsgDisplayed() {
		waitForElementVisibleByXpath(FE_MyWishlistPageUI.WISHLIST_EMPTY_MSG);
		return isElementDisplayed(FE_MyWishlistPageUI.WISHLIST_EMPTY_MSG);
	}

	public void clicktoRemoveDynamicItemFromWishlist(String productName) {
		waitForDynamicElementClickableByXpath(FE_MyWishlistPageUI.DYNAMIC_REMOVE_ITEM_BY_PRODUCT_NAME, productName);
		clickOnDynamicElement(FE_MyWishlistPageUI.DYNAMIC_REMOVE_ITEM_BY_PRODUCT_NAME, productName);
	}

	public void clickToAddDynamicProductToCart(String productName) {
		waitForDynamicElementClickableByXpath(FE_MyWishlistPageUI.DYNAMIC_ADDTOCART_ITEM_BY_PRODUCT_NAME, productName);
		clickOnDynamicElement(FE_MyWishlistPageUI.DYNAMIC_ADDTOCART_ITEM_BY_PRODUCT_NAME, productName);
	}
	

}

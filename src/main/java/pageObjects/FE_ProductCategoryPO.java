package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_ProductCategoryPageUI;

public class FE_ProductCategoryPO extends AbstractPO {
	WebDriver driver;

	public FE_ProductCategoryPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public String getDynamicProductHomePrice(String productName) {
		waitForDynamicElementVisibleByXpath(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_HOME_PRICE, productName);
		return getTextDynamicElement(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_HOME_PRICE, productName);
	}

	public void clickOnDynamicAddToCartByProductName(String productName) {
		waitForDynamicElementClickableByXpath(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_ADD_TO_CART, productName);
		clickOnDynamicElement(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_ADD_TO_CART, productName);
	}

	public void clickOnDynamicAddToCompareByProductName(String productName) {
		waitForDynamicElementClickableByXpath(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_ADD_TO_COMPARE, productName);
		clickOnDynamicElement(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_ADD_TO_COMPARE, productName);

	}
	
	public void clickOnDynamicAddToWishlistByProductName(String productName) {
		waitForDynamicElementClickableByXpath(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_ADD_TO_WISHLIST, productName);
		clickOnDynamicElement(FE_ProductCategoryPageUI.DYNAMIC_PRODUCT_ADD_TO_WISHLIST, productName);
	}
	
	
}

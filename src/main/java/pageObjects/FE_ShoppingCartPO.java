package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_ShoppingCartPageUI;

public class FE_ShoppingCartPO extends AbstractPO {
	WebDriver driver;

	public FE_ShoppingCartPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}
	
	public boolean isDynamicAddToCartSuccessMgsDisplayed(String pageMsg) {
		waitForDynamicElementVisibleByXpath(FE_ShoppingCartPageUI.DYNAMIC_SHOPPING_CART_SUCCESS_MSG, pageMsg);
		return isDynamicElementDisplayed(FE_ShoppingCartPageUI.DYNAMIC_SHOPPING_CART_SUCCESS_MSG, pageMsg);
	}

	public void clickOnDynamicShoppingCartAction(String actionName) {
		waitForDynamicElementClickableByXpath(FE_ShoppingCartPageUI.DYNAMIC_SHOPPING_CART_ACTION, actionName);
		clickOnDynamicElement(FE_ShoppingCartPageUI.DYNAMIC_SHOPPING_CART_ACTION, actionName);
	}

	public String getDiscountValue() {
		waitForElementVisibleByXpath(FE_ShoppingCartPageUI.DISCOUNT_VALUE);
		return getTextElement(FE_ShoppingCartPageUI.DISCOUNT_VALUE);
	}
	
	public String getGrandTotalPriceValue() {
		waitForElementVisibleByXpath(FE_ShoppingCartPageUI.GRAND_TOTAL_PRICE_VALUE);
		return getTextElement(FE_ShoppingCartPageUI.GRAND_TOTAL_PRICE_VALUE);
	}
	
	public float getGrandTotalPriceFloatValue() {
		return Float.parseFloat(getGrandTotalPriceValue().replace("$", "").trim());
	}
	
	public void inputToDynamicQtyTextbox(String productName, String inputValue) {
		waitForDynamicElementVisibleByXpath(FE_ShoppingCartPageUI.DYNAMIC_CART_QYT_TEXTBOX, productName);
		sendKeysToDynamicElement(FE_ShoppingCartPageUI.DYNAMIC_CART_QYT_TEXTBOX, inputValue, productName);
	}


	public void clickOnDynamicQtyUpdateButton(String productName) {
		waitForDynamicElementClickableByXpath(FE_ShoppingCartPageUI.DYNAMIC_CART_QYT_UPDATE, productName);
		clickOnDynamicElement(FE_ShoppingCartPageUI.DYNAMIC_CART_QYT_UPDATE, productName);
	}

	public boolean isDynamicQtyErrorMsgDisplayed(String productName) {
		waitForDynamicElementVisibleByXpath(FE_ShoppingCartPageUI.DYNAMIC_CART_QYT_ERROR_MSG, productName);
		return isDynamicElementDisplayed(FE_ShoppingCartPageUI.DYNAMIC_CART_QYT_ERROR_MSG, productName);
	}

	public boolean isEmptyCartMsgDisplayed() {
		waitForElementVisibleByXpath(FE_ShoppingCartPageUI.EMPTY_CART_MSG);
		return isElementDisplayed(FE_ShoppingCartPageUI.EMPTY_CART_MSG);
	}

	public String getDynamicSubPriceValue(String subPriceName) {
		waitForDynamicElementVisibleByXpath(FE_ShoppingCartPageUI.DYNAMIC_SUB_PRICE_VALUE, subPriceName);
		return getTextDynamicElement(FE_ShoppingCartPageUI.DYNAMIC_SUB_PRICE_VALUE, subPriceName);
	}
	
	public float getDynamicSubPriceFloatValue(String subPriceName) {
		return Float.parseFloat(getDynamicSubPriceValue(subPriceName).replace("$", "").trim());
	}
	
	public boolean isFlatRatePriceGeneratedValueDisplayed() {
		waitForElementVisibleByXpath(FE_ShoppingCartPageUI.FLAT_RATE_PRICE_GENERATED_VALUE);
		return isElementDisplayed(FE_ShoppingCartPageUI.FLAT_RATE_PRICE_GENERATED_VALUE);

	}

	
	
}

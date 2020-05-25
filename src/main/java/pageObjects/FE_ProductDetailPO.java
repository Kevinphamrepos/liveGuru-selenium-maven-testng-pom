package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.FE_ProductDetailPageUI;

public class FE_ProductDetailPO extends AbstractPO {
	WebDriver driver;

	public FE_ProductDetailPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public String getProductDetailPrice() {
		waitForDynamicElementVisibleByXpath(FE_ProductDetailPageUI.PRODUCT_DETAIL_PRICE);
		return getTextDynamicElement(FE_ProductDetailPageUI.PRODUCT_DETAIL_PRICE);
	}
	
	public void clickOnDynamicRatingLink(String linkName) {
		waitForDynamicElementClickableByXpath(FE_ProductDetailPageUI.DYNAMIC_PRODUCT_DETAIL_RATING_LINK, linkName);
		clickOnDynamicElement(FE_ProductDetailPageUI.DYNAMIC_PRODUCT_DETAIL_RATING_LINK, linkName);
	}
	
	public boolean isDynamicErrorFequiredFieldMsgDisplayed(String fieldName) {
		waitForDynamicElementVisibleByXpath(FE_ProductDetailPageUI.DYNAMIC_ERROR_REQUIRED_FIELD_MSG, fieldName);
		return isDynamicElementDisplayed(FE_ProductDetailPageUI.DYNAMIC_ERROR_REQUIRED_FIELD_MSG, fieldName);
	}
	
	public boolean isDynamicProductDetailNameDisplayed(String productName) {
		waitForDynamicElementVisibleByXpath(FE_ProductDetailPageUI.DYNAMIC_PRODUCT_DETAIL_NAME, productName);
		return isDynamicElementDisplayed(FE_ProductDetailPageUI.DYNAMIC_PRODUCT_DETAIL_NAME, productName);
	}
	
	public void clickOnProductReviewTab() {
		waitForElementClickableByXpath(FE_ProductDetailPageUI.PRODUCT_DETAIL_REVIEW_TAB);
		clickOnElement(FE_ProductDetailPageUI.PRODUCT_DETAIL_REVIEW_TAB);
	}
	
	public boolean isDynamicReviewContentDisplayed(String reviewContent) {
		waitForDynamicElementVisibleByXpath(FE_ProductDetailPageUI.DYNAMIC_PRODUCT_DETAIL_REVIEW_CONTENT, reviewContent);
		return isDynamicElementDisplayed(FE_ProductDetailPageUI.DYNAMIC_PRODUCT_DETAIL_REVIEW_CONTENT, reviewContent);
	}
	
}

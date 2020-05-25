package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPO;
import pageUIs.BE_PendingReviewsPageUI;

public class BE_PendingReviewsPO extends AbstractPO {
	WebDriver driver;

	public BE_PendingReviewsPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public void clickOnDynamicCheckboxByReviewContent(String randomReviewContent) {
		waitForDynamicElementClickableByXpath(BE_PendingReviewsPageUI.DYNAMIC_CHECKBOX_BY_REVIEW_CONTENT, randomReviewContent);
		clickOnDynamicElement(BE_PendingReviewsPageUI.DYNAMIC_CHECKBOX_BY_REVIEW_CONTENT, randomReviewContent);
	}
	
	public void clickOnDynamicEditLinkByReviewContent(String randomReviewContent) {
		waitForDynamicElementClickableByXpath(BE_PendingReviewsPageUI.DYNAMIC_EDIT_LINK_BY_REVIEW_CONTENT, randomReviewContent);
		clickOnDynamicElement(BE_PendingReviewsPageUI.DYNAMIC_EDIT_LINK_BY_REVIEW_CONTENT, randomReviewContent);
	}

	public FE_HomePO openFrontendHomeLink(String pageUrl) {
		openUrl(pageUrl);
		return PageGeneratorManager.getHomePage(driver);
	}


}

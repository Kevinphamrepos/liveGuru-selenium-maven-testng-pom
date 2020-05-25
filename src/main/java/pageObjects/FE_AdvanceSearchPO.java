package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.AbstractPO;
import pageUIs.FE_AdvanceSearchPageUI;

public class FE_AdvanceSearchPO extends AbstractPO {
	WebDriver driver;

	public FE_AdvanceSearchPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}

	public void getDynamicProductNameAndCurrentPrice() {
		waitForListElementsVisibleByXpath(FE_AdvanceSearchPageUI.SEARCHED_PRODUCT_NAME);
		ArrayList<String> searchedNames = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(FE_AdvanceSearchPageUI.SEARCHED_PRODUCT_NAME));
		
		for (WebElement element : elementList) {
			searchedNames.add(element.getAttribute("title"));
		}
		
		for (String productName : searchedNames) {
			String currentPrice = getTextDynamicElement(FE_AdvanceSearchPageUI.DYNAMIC_SEARCHED_PRODUCT_CURRENT_PRICE, productName, productName);
			System.out.println("Searched Product and Its Current Price is: " + productName + " - " + currentPrice);
		}
	}

	public void clickOnModifySearchLink() {
		waitForElementVisibleByXpath(FE_AdvanceSearchPageUI.MODIFY_SEARCH_LINK);
		clickOnElement(FE_AdvanceSearchPageUI.MODIFY_SEARCH_LINK);
	}

	public void clickOnSearchButton() {
		waitForElementVisibleByXpath(FE_AdvanceSearchPageUI.SEARCH_BUTTON);
		clickOnElement(FE_AdvanceSearchPageUI.SEARCH_BUTTON);
	}

}

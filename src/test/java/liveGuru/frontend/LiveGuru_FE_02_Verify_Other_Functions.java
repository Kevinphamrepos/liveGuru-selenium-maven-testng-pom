package liveGuru.frontend;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import pageObjects.PageGeneratorManager;
import pageObjects.FE_AdvanceSearchPO;
import pageObjects.FE_CompareWindowPO;
import pageObjects.FE_HomePO;
import pageObjects.FE_ProductCategoryPO;
import pageObjects.FE_ProductDetailPO;
import pageObjects.FE_ShoppingCartPO;

public class LiveGuru_FE_02_Verify_Other_Functions extends AbstractTest {
	private WebDriver driver;

	private FE_HomePO homePage;
	private FE_ProductCategoryPO productCategoryPage;
	private FE_ProductDetailPO productDetailPage;
	private FE_ShoppingCartPO shoppingCartPage;
	private FE_CompareWindowPO compareWindow;
	private FE_AdvanceSearchPO advanceSearchPage;
	
	String couponCode, sonyXperiaHomePrice, sonyXperiaDetailPrice;

	@Parameters({"browser", "url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {

		driver = getBrowserDriverAutoUrl(browserName, urlValue);
		homePage = PageGeneratorManager.getHomePage(driver);
		
		couponCode = "GURU50";

	}
	
	@Test
	public void TC_03_VerifyCostOfProduct() { 
		
		homePage.openPageByDynamicHeaderNavLink("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		
		sonyXperiaHomePrice = productCategoryPage.getDynamicProductHomePrice("Sony Xperia");
		
		productCategoryPage.clickOnDynamicProductName("Sony Xperia");
		productDetailPage = PageGeneratorManager.getProductDetailPage(driver);
		sonyXperiaDetailPrice = productDetailPage.getProductDetailPrice();
		
		verifyEquals(sonyXperiaHomePrice, sonyXperiaDetailPrice);
	}
	
	@Test
	public void TC_04_VerifyDiscountCoupon() {
		
		productDetailPage.openPageByDynamicHeaderNavLink("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		
		productCategoryPage.clickOnDynamicAddToCartByProductName("IPhone");
		shoppingCartPage = PageGeneratorManager.getShoppingCartPage(driver);
		verifyTrue(shoppingCartPage.isDynamicAddToCartSuccessMgsDisplayed("IPhone was added to your shopping cart."));
		
		shoppingCartPage.inputToDynamicTextboxTextarea("coupon_code", couponCode);
		shoppingCartPage.clickOnDynamicShoppingCartAction("Apply");
		verifyTrue(shoppingCartPage.isDynamicAddToCartSuccessMgsDisplayed("Coupon code " + '"' + couponCode + '"' + " was applied."));

		verifyEquals(shoppingCartPage.getDiscountValue(), "-$25.00");
		verifyEquals(shoppingCartPage.getGrandTotalPriceValue(), "$475.00"); // Bug here
	}
	
	@Test
	public void TC_05_VerifyCantAddMore500Items() {
		
		shoppingCartPage.openPageByDynamicHeaderNavLink("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		
		productCategoryPage.clickOnDynamicAddToCartByProductName("Sony Xperia");
		shoppingCartPage = PageGeneratorManager.getShoppingCartPage(driver);
		verifyTrue(shoppingCartPage.isDynamicAddToCartSuccessMgsDisplayed("Sony Xperia was added to your shopping cart."));
		
		shoppingCartPage.inputToDynamicQtyTextbox("Sony Xperia", "501");
		shoppingCartPage.clickOnDynamicQtyUpdateButton("Sony Xperia");
		
		verifyTrue(shoppingCartPage.isDynamicQtyErrorMsgDisplayed("Sony Xperia"));
		
		shoppingCartPage.clickOnDynamicShoppingCartAction("Empty Cart");
		verifyTrue(shoppingCartPage.isEmptyCartMsgDisplayed());
		
	}
	
	@Test
	public void TC_06_VerifyBeAbleCompareTwoProducts() {
		
		shoppingCartPage.openPageByDynamicHeaderNavLink("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		
		productCategoryPage.clickOnDynamicAddToCompareByProductName("Sony Xperia");
		verifyTrue(productCategoryPage.isDynamicSuccessMsgDisplayed("The product Sony Xperia has been added to comparison list."));
		
		productCategoryPage.clickOnDynamicAddToCompareByProductName("IPhone");
		verifyTrue(productCategoryPage.isDynamicSuccessMsgDisplayed("The product IPhone has been added to comparison list."));
		
		productCategoryPage.clickOnDynamicButtonByTitle("Compare");
		productCategoryPage.switchToWindowByTitle("Products Comparison List - Magento Commerce");
		
		compareWindow = PageGeneratorManager.getCompareWindow(driver);
		verifyTrue(compareWindow.isCompareWindowHeaderDisplayed());
		verifyTrue(compareWindow.isDynamicProductNameDisplayed("Sony Xperia"));
		verifyTrue(compareWindow.isDynamicProductNameDisplayed("IPhone"));
				
		compareWindow.clickOnCloseWindowButton();
		compareWindow.switchToWindowByTitle("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		verifyEquals(productCategoryPage.getPageTitle(), "Mobile");
		
	}
	
	@Test
	public void TC_08_VerifyCanAddReview() { 
		
		productCategoryPage.openPageByDynamicHeaderNavLink("TV");
		productCategoryPage.clickOnDynamicProductName("Samsung LCD");
		
		productDetailPage = PageGeneratorManager.getProductDetailPage(driver);
		productDetailPage.clickOnDynamicRatingLink("Add Your Review");
		
		productDetailPage.inputToDynamicTextboxTextarea("review_field", "");
		productDetailPage.inputToDynamicTextboxTextarea("summary_field", "");
		productDetailPage.inputToDynamicTextboxTextarea("nickname_field", "");
		productDetailPage.clickOnDynamicButtonByTitle("Submit Review");
		verifyTrue(productDetailPage.isDynamicErrorFequiredFieldMsgDisplayed("review"));
		verifyTrue(productDetailPage.isDynamicErrorFequiredFieldMsgDisplayed("summary"));
		verifyTrue(productDetailPage.isDynamicErrorFequiredFieldMsgDisplayed("nickname"));
		
		productDetailPage.inputToDynamicTextboxTextarea("review_field", "Review content...");
		productDetailPage.inputToDynamicTextboxTextarea("summary_field", "Summary content...");
		productDetailPage.inputToDynamicTextboxTextarea("nickname_field", "Nickname is....");
		productDetailPage.clickOnDynamicButtonByTitle("Submit Review");
		verifyTrue(productDetailPage.isDynamicSuccessMsgDisplayed("Your review has been accepted for moderation."));
	}
	
	@Test
	public void TC_10_VerifySearchFunctionality() {
		
		productDetailPage.openPageByDynamicFooterLink("Advanced Search");
		advanceSearchPage = PageGeneratorManager.getAdvanceSearchPage(driver);
		verifyTrue(advanceSearchPage.isDynamicHeaderTitleDisplayed("Catalog Advanced Search"));
		
		advanceSearchPage.inputToDynamicTextboxTextarea("price", "0");
		advanceSearchPage.inputToDynamicTextboxTextarea("price_to", "150");
		advanceSearchPage.clickOnSearchButton();
		
		advanceSearchPage.getDynamicProductNameAndCurrentPrice();
		
		advanceSearchPage.clickOnModifySearchLink();
	
		advanceSearchPage.inputToDynamicTextboxTextarea("price", "151");
		advanceSearchPage.inputToDynamicTextboxTextarea("price_to", "1000");
		advanceSearchPage.clickOnSearchButton();

		advanceSearchPage.getDynamicProductNameAndCurrentPrice();
		
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass() { 
		closeBrowserAndDriver(driver);
	}

}

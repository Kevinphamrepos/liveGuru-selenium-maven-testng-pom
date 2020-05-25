package liveGuru.frontend;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import pageObjects.FE_AccountInfoPO;
import pageObjects.FE_CheckoutPO;
import pageObjects.FE_DashboardPO;
import pageObjects.FE_HomePO;
import pageObjects.FE_LoginPO;
import pageObjects.FE_MyWishlistPO;
import pageObjects.FE_ProductCategoryPO;
import pageObjects.FE_RegisterPO;
import pageObjects.FE_ShoppingCartPO;
import pageObjects.FE_WishlistSharingPO;
import pageObjects.PageGeneratorManager;

public class LiveGuru_FE_01_Register_And_PurchaseProduct extends AbstractTest {
	private WebDriver driver;

	private FE_HomePO homePage;
	private FE_RegisterPO registerPage;
	private FE_DashboardPO dashboardPage;
	private FE_AccountInfoPO accountInfoPage;
	private FE_ProductCategoryPO productCategoryPage;
	private FE_ShoppingCartPO shoppingCartPage;
	private FE_MyWishlistPO myWishlistPage;
	private FE_WishlistSharingPO wishlistSharingPage;
	private FE_LoginPO loginPage;
	private FE_CheckoutPO checkoutPage;
	
	String randomEmail, password;
	String firstName, middleName, lastName, company, streetAddress1, city, state, country, zipCode, telephone; 
	String orderNumber;
	float flatRateFloatValue, subTotalFloatValue, fixedFlatRateFloatPrice;

	@Parameters({"browser", "url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {

		driver = getBrowserDriverAutoUrl(browserName, urlValue);
		homePage = PageGeneratorManager.getHomePage(driver);
		
		randomEmail = "Kevinpham_" + randomNumber() + "@gmail.com";
		password = "abcxyz123";
		
		firstName = "Kevin";
		middleName = "Mid";
		lastName = "Pham";
		company = "VIP Ltd. Co.";
		streetAddress1 = "123 ABC";
		city = "New York City";
		state = "New York";
		country = "United States";
		zipCode = "543432";
		telephone = "123456789"; 
		
		fixedFlatRateFloatPrice = (float) 5.00;
	}

	@Test
	public void TC_01_RegisterToSystem() {
		
		homePage.clickOnHeaderAccountMenu();
		homePage.openPageByDynamicAccountMenuLink("Register");
		registerPage = PageGeneratorManager.getRegisterPage(driver);
		verifyTrue(registerPage.isDynamicHeaderTitleDisplayed("Create an Account"));
		
		registerPage.inputToDynamicTextboxTextarea("firstname", firstName);
		registerPage.inputToDynamicTextboxTextarea("middlename", middleName);
		registerPage.inputToDynamicTextboxTextarea("lastname", lastName);
		registerPage.inputToDynamicTextboxTextarea("email_address", randomEmail);
		registerPage.inputToDynamicTextboxTextarea("password", password);
		registerPage.inputToDynamicTextboxTextarea("confirmation", password);
		
		registerPage.clickOnDynamicButtonByTitle("Register");
		dashboardPage = PageGeneratorManager.getDashboarPage(driver);
		verifyTrue(dashboardPage.isDynamicHeaderTitleDisplayed("My Dashboard"));
		verifyEquals(dashboardPage.getSuccessMessageText(), "Thank you for registering with Main Website Store.");
	}
	
	@Test
	public void TC_02_VerifyUserInfomation() {
		
		dashboardPage.openPageByDynamicDashboardLeftLink("Account Information");
		accountInfoPage = PageGeneratorManager.getAccountInfoPage(driver);
		
		verifyEquals(accountInfoPage.getDynamicTextboxAttributeValue("value", "firstname"), firstName);
		verifyEquals(accountInfoPage.getDynamicTextboxAttributeValue("value", "middlename"), middleName);
		verifyEquals(accountInfoPage.getDynamicTextboxAttributeValue("value", "lastname"), lastName);
		verifyEquals(accountInfoPage.getDynamicTextboxAttributeValue("value", "email"), randomEmail);
		
		accountInfoPage.clickOnHeaderAccountMenu();
		accountInfoPage.openPageByDynamicAccountMenuLink("Log Out");
		homePage = PageGeneratorManager.getHomePage(driver);
	}
	
	@Test
	public void TC_07_VerifyCanShareWishlistByEmail() {
		
		homePage.clickOnHeaderAccountMenu();
		homePage.openPageByDynamicAccountMenuLink("Log In");
		loginPage = PageGeneratorManager.getLoginFEPage(driver);
		loginPage.inputToDynamicTextboxTextarea("email", randomEmail);
		loginPage.inputToDynamicTextboxTextarea("pass", password);
		loginPage.clickOnDynamicButtonByTitle("Login");
		dashboardPage = PageGeneratorManager.getDashboarPage(driver);
		verifyTrue(dashboardPage.isDynamicHeaderTitleDisplayed("My Dashboard"));
		
		dashboardPage.openPageByDynamicHeaderNavLink("TV");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);

		productCategoryPage.clickOnDynamicAddToWishlistByProductName("LG LCD");
		myWishlistPage = PageGeneratorManager.getMyWishlistPage(driver);
		verifyTrue(myWishlistPage.isDynamicMyWishlistSuccessMsgDisplayed("LG LCD has been added to your wishlist."));
		
		myWishlistPage.clickOnDynamicActionByTitle("Share Wishlist");
		wishlistSharingPage = PageGeneratorManager.getWishlistSharingPage(driver);
		verifyTrue(wishlistSharingPage.isWishlistSharingHeaderTextDisplayed());

		wishlistSharingPage.inputToDynamicTextboxTextarea("email_address", "Kevinpham.sharing@gmail.com");
		wishlistSharingPage.inputToDynamicTextboxTextarea("message", "Sharing Wishlist ^^");
		wishlistSharingPage.clickOnDynamicActionByTitle("Share Wishlist");
		myWishlistPage = PageGeneratorManager.getMyWishlistPage(driver);
		verifyTrue(myWishlistPage.isDynamicMyWishlistSuccessMsgDisplayed("Your Wishlist has been shared."));
		
		myWishlistPage.clicktoRemoveDynamicItemFromWishlist("LG LCD");
		myWishlistPage.acceptAlert();
	}
	
	@Test
	public void TC_09_VerifyBeAblePurchaseProduct() {
		
		myWishlistPage.openPageByDynamicHeaderNavLink("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		productCategoryPage.clickOnDynamicAddToWishlistByProductName("IPhone");
		
		myWishlistPage = PageGeneratorManager.getMyWishlistPage(driver);
		verifyTrue(myWishlistPage.isDynamicMyWishlistSuccessMsgDisplayed("IPhone has been added to your wishlist."));
		myWishlistPage.clickToAddDynamicProductToCart("IPhone");
		shoppingCartPage = PageGeneratorManager.getShoppingCartPage(driver);
		verifyTrue(shoppingCartPage.isDynamicSuccessMsgDisplayed("IPhone was added to your shopping cart."));

		shoppingCartPage.selectItemInDynamicDropdown("country", country);
		shoppingCartPage.selectItemInDynamicDropdown("region_id", state);
		shoppingCartPage.inputToDynamicTextboxTextarea("postcode", zipCode);
		shoppingCartPage.clickOnDynamicShoppingCartAction("Estimate");
		verifyTrue(shoppingCartPage.isFlatRatePriceGeneratedValueDisplayed());

		shoppingCartPage.clickForSelectDynamicRadioButton("s_method_flatrate_flatrate");
		shoppingCartPage.clickOnDynamicButtonByTitle("Update Total");
		
		flatRateFloatValue = shoppingCartPage.getDynamicSubPriceFloatValue("Flat Rate");
		System.out.println("Flat Rate Float Value = " + flatRateFloatValue);
		verifyEquals(flatRateFloatValue, fixedFlatRateFloatPrice);
		
		subTotalFloatValue = shoppingCartPage.getDynamicSubPriceFloatValue("Subtotal");
		System.out.println("SubTotal Float Value = " + subTotalFloatValue);
		System.out.println("Grand Total Float Value = " + (flatRateFloatValue + subTotalFloatValue));
		verifyEquals(shoppingCartPage.getGrandTotalPriceFloatValue(), (flatRateFloatValue + subTotalFloatValue));
		
		shoppingCartPage.clickOnDynamicButtonByTitle("Proceed to Checkout");
		
		checkoutPage = PageGeneratorManager.getCheckoutPage(driver);
		verifyTrue(checkoutPage.isDynamicHeaderTitleDisplayed("Checkout"));
		
		checkoutPage.inputToDynamicTextboxTextarea("billing:firstname", firstName);
		checkoutPage.inputToDynamicTextboxTextarea("billing:lastname", lastName);
		checkoutPage.inputToDynamicTextboxTextarea("billing:street1", streetAddress1);
		checkoutPage.inputToDynamicTextboxTextarea("billing:city", city);
		checkoutPage.selectItemInDynamicDropdown("billing:region_id", state);
		checkoutPage.inputToDynamicTextboxTextarea("billing:postcode", zipCode);
		checkoutPage.selectItemInDynamicDropdown("billing:country_id", country);
		checkoutPage.inputToDynamicTextboxTextarea("billing:telephone", telephone);
		checkoutPage.clickForSelectDynamicRadioButton("billing:use_for_shipping_yes");
		checkoutPage.clickOnDynamicContinueButtonByID("billing");
		
		checkoutPage.clickOnDynamicContinueButtonByID("shipping-method");
		checkoutPage.clickForSelectDynamicRadioButton("p_method_checkmo");
		checkoutPage.clickOnDynamicContinueButtonByID("payment");
		
		checkoutPage.clickOnDynamicButtonByTitle("Place Order");
		verifyTrue(checkoutPage.isDynamicHeaderTitleDisplayed("Your order has been received."));
		
		orderNumber = checkoutPage.getOrderNumber();
		log.info("Order Number is: " + orderNumber);
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass() { 
		closeBrowserAndDriver(driver);
	}

}

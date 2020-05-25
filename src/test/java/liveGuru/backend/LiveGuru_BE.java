package liveGuru.backend;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import pageObjects.PageGeneratorManager;
import pageObjects.FE_HomePO;
import pageObjects.BE_InvoicesPO;
import pageObjects.BE_LoginPO;
import pageObjects.BE_ManageCustomersPO;
import pageObjects.BE_OrdersPO;
import pageObjects.BE_PendingReviewsPO;
import pageObjects.FE_ProductCategoryPO;
import pageObjects.FE_ProductDetailPO;

public class LiveGuru_BE extends AbstractTest {
	private WebDriver driver;
	
	private BE_LoginPO loginBEPage;
	private BE_ManageCustomersPO manageCustomersBEPage;
	private BE_OrdersPO ordersBEPage;
	private BE_PendingReviewsPO pendingReviewsPage;
	private FE_ProductDetailPO productDetailPage;
	private FE_ProductCategoryPO productCategoryPage;
	private FE_HomePO homePage;
	private BE_InvoicesPO invoicesBEPage;
	
	String userName, password, frontendHomeLink, reviewLink;
	String randomReviewContent, randomReviewSummary, randomReviewNickname;
	String sortAscending, sortDescending;
	String rootFolder, downloadPath;
	
	@Parameters({"browser", "url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {
		
		log.info("Browser in using is: " + browserName);
		
		userName = "user01";
		password = "guru99com";
		frontendHomeLink = "http://live.demoguru99.com/index.php/";
		reviewLink = "http://live.demoguru99.com/index.php/review/product/list/id/1/";
		
		randomReviewContent = "My Product Review Content Number_" + randomNumber();
		randomReviewSummary = "My Product Review Summary Number_" + randomNumber();
		randomReviewNickname = "Kevinpham_" + randomNumber();
		
		rootFolder = System.getProperty("user.dir");
		downloadPath = rootFolder + "\\downloadedFiles";
		
		sortAscending = "Ascending";
		sortDescending = "Descending";
		
		driver = getBrowserDriverAutoUrl(browserName, urlValue);
		loginBEPage = PageGeneratorManager.getLoginBEPage(driver);
		
		loginBEPage.inputToDynamicLoginTextbox("username", userName);
		loginBEPage.inputToDynamicLoginTextbox("password", password);
		loginBEPage.clickOnLoginButtonBE();
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		manageCustomersBEPage.clickToClosePopup();
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));
		
	}

	@Test
	public void TC_01_VerifyInvoiceCanBePrinted() {
		
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Sales");
		manageCustomersBEPage.clickOnDynamicMenuInNavBar("Orders");
		ordersBEPage = PageGeneratorManager.getOrdersPage(driver);
		verifyTrue(ordersBEPage.isDynamicBEPageHeaderDisplayed("Orders"));
		
		ordersBEPage.selectItemInDynamicDropdownBE("status", "Canceled");
		ordersBEPage.clickOnDynamicButtonByTitle("Search");
		ordersBEPage.checkToFirstCheckbox();
		
		ordersBEPage.selectItemInActionDropdownBE("Print Invoices");
		ordersBEPage.clickOnDynamicButtonByTitle("Submit");
		ordersBEPage.isDynamicErrorMsgDisplay("There are no printable documents related to selected orders.");
		
		ordersBEPage.selectItemInDynamicDropdownBE("status", "Complete");
		ordersBEPage.clickOnDynamicButtonByTitle("Search");
		ordersBEPage.checkToFirstCheckbox();

		ordersBEPage.selectItemInActionDropdownBE("Print Invoices");
		ordersBEPage.clickOnDynamicButtonByTitle("Submit");
		verifyTrue(manageCustomersBEPage.isFileDownloaded(downloadPath, "invoice"));
		
		// For Next-Testcase
		manageCustomersBEPage.openManageCustomerBEPage();
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));
		
	}
	
	@Test
	public void TC_02_VerifyProductReviewMechanism() { 
		
		manageCustomersBEPage.openUrlInNewTabByJSE(reviewLink);
		productDetailPage = PageGeneratorManager.getProductDetailPage(driver);
		verifyTrue(productDetailPage.isDynamicProductDetailNameDisplayed("Sony Xperia"));
		productDetailPage = PageGeneratorManager.getProductDetailPage(driver);
		productDetailPage.inputToDynamicTextboxTextarea("review_field", randomReviewContent);
		productDetailPage.inputToDynamicTextboxTextarea("summary_field", randomReviewSummary);
		productDetailPage.inputToDynamicTextboxTextarea("nickname_field", randomReviewNickname);
		productDetailPage.clickOnDynamicButtonByTitle("Submit Review");
		productDetailPage.isDynamicSuccessMsgDisplayed("Your review has been accepted for moderation.");
		
		// Switch to BE Login
		productDetailPage.switchToWindowByTitle("Manage Customers / Customers / Magento Admin");
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));
			
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Catalog");
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Reviews and Ratings");
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Customer Reviews");
		manageCustomersBEPage.clickOnDynamicMenuInNavBar("Pending Reviews");
		pendingReviewsPage = PageGeneratorManager.getPendingReviewsPage(driver);
		verifyTrue(pendingReviewsPage.isDynamicBEPageHeaderDisplayed("Pending Reviews"));
		
		pendingReviewsPage.clickOnDynamicHeadingColunm("ID");
		pendingReviewsPage.clickOnDynamicHeadingColunm("ID"); // Click 2 lần để Sort Ascending
		
		pendingReviewsPage.clickOnDynamicCheckboxByReviewContent(randomReviewContent);
		pendingReviewsPage.clickOnDynamicEditLinkByReviewContent(randomReviewContent);
		
		pendingReviewsPage.selectItemInDynamicDropdownBE("status_id", "Approved");
		pendingReviewsPage.clickOnDynamicButtonByTitle("Save Review");
		verifyTrue(pendingReviewsPage.isDynamicSuccessMsgDisplayed("The review has been saved."));
		
		// Switch to FE Home (in New Tab)
		pendingReviewsPage.openUrlInNewTabByJSE(frontendHomeLink);
		homePage = PageGeneratorManager.getHomePage(driver);
		homePage.openPageByDynamicHeaderNavLink("Mobile");
		productCategoryPage = PageGeneratorManager.getProductCategoryPage(driver);
		verifyTrue(productCategoryPage.isDynamicHeaderTitleDisplayed("Mobile"));
		productCategoryPage.clickOnDynamicProductImage("Xperia");
		productDetailPage = PageGeneratorManager.getProductDetailPage(driver);
		productDetailPage.clickOnProductReviewTab();
		productDetailPage.isDynamicReviewContentDisplayed(randomReviewContent);
		
		// Switch to BE Login - For Next-Testcase
		productDetailPage.switchToWindowByTitle("Pending Reviews / Customer Reviews / Reviews and Ratings / Catalog / Magento Admin");
		pendingReviewsPage = PageGeneratorManager.getPendingReviewsPage(driver);
		verifyTrue(pendingReviewsPage.isDynamicBEPageHeaderDisplayed("Pending Reviews")); 
		pendingReviewsPage.openManageCustomerBEPage();
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));
		
	}
	
	@Test
	public void TC_03_VerifySortFunctionality() {
		
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Sales");
		manageCustomersBEPage.clickOnDynamicMenuInNavBar("Invoices");
		invoicesBEPage = PageGeneratorManager.getInvoicesPage(driver);
		verifyTrue(invoicesBEPage.isDynamicBEPageHeaderDisplayed("Invoices"));
		
		invoicesBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		
		// Colunm: Invoice #
		invoicesBEPage.clickToSortDynamicColunm("Invoice #", sortAscending, "title");
		verifyTrue(invoicesBEPage.isDynamicStringColunmSortedAscending("Invoice #"));
		invoicesBEPage.clickToSortDynamicColunm("Invoice #", sortDescending, "title");
		verifyTrue(invoicesBEPage.isDynamicStringColunmSortedDescending("Invoice #"));
				
		// Colunm: Invoice Date	---> Chưa có Solution cho Date Format
		invoicesBEPage.clickToSortDynamicColunm("Invoice Date", sortAscending, "title");
		verifyTrue(invoicesBEPage.isDynamicDatetimeColunmSortedCorrectly("Invoice Date", sortAscending));
		invoicesBEPage.clickToSortDynamicColunm("Invoice Date", sortDescending, "title");
		verifyTrue(invoicesBEPage.isDynamicDatetimeColunmSortedCorrectly("Invoice Date", sortDescending));

		// Colunm: Order #
		invoicesBEPage.clickToSortDynamicColunm("Order #", sortAscending, "title");
		verifyTrue(invoicesBEPage.isDynamicStringColunmSortedAscending("Order #"));
		invoicesBEPage.clickToSortDynamicColunm("Order #", sortDescending, "title");
		verifyTrue(invoicesBEPage.isDynamicStringColunmSortedDescending("Order #"));
		
		// Colunm: Order Date ---> Chưa có Solution cho Date Format
		invoicesBEPage.clickToSortDynamicColunm("Order Date", sortAscending, "title");
		verifyTrue(invoicesBEPage.isDynamicDatetimeColunmSortedCorrectly("Order Date", sortAscending));
		invoicesBEPage.clickToSortDynamicColunm("Order Date", sortDescending, "title");
		verifyTrue(invoicesBEPage.isDynamicDatetimeColunmSortedCorrectly("Order Date", sortDescending));
		
		// Colunm: Bill to Name
		invoicesBEPage.clickToSortDynamicColunm("Bill to Name", sortAscending, "title");
		verifyTrue(invoicesBEPage.isDynamicStringColunmSortedCorrectlyLowerCase("Bill to Name", sortAscending));
		invoicesBEPage.clickToSortDynamicColunm("Bill to Name", sortDescending, "title");
		verifyTrue(invoicesBEPage.isDynamicStringColunmSortedCorrectlyLowerCase("Bill to Name", sortDescending));
		
		// Colunm: Amount 
		invoicesBEPage.clickToSortDynamicColunm("Amount", sortAscending, "title");
		verifyTrue(invoicesBEPage.isDynamicFloatColunmSortedCorrectly("Amount", sortAscending));
		invoicesBEPage.clickToSortDynamicColunm("Amount", sortDescending, "title");
		verifyTrue(invoicesBEPage.isDynamicFloatColunmSortedCorrectly("Amount", sortDescending));
		
		// For Next-Testcase
		invoicesBEPage.openManageCustomerBEPage();
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));

	}
	
	@Test
	public void TC_04_VerifyPaginationFunctionality() {
		
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Sales");
		manageCustomersBEPage.clickOnDynamicMenuInNavBar("Orders");
		ordersBEPage = PageGeneratorManager.getOrdersPage(driver);
		verifyTrue(ordersBEPage.isDynamicBEPageHeaderDisplayed("Orders"));
		
		ordersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		
		ordersBEPage.selectItemInDynamicDropdownBE("limit", "20");
		verifyTrue(ordersBEPage.isNumberItemsDisplayedAsViewNumber("20"));
		
		ordersBEPage.selectItemInDynamicDropdownBE("limit", "30");
		verifyTrue(ordersBEPage.isNumberItemsDisplayedAsViewNumber("30"));
		
		ordersBEPage.selectItemInDynamicDropdownBE("limit", "50");
		verifyTrue(ordersBEPage.isNumberItemsDisplayedAsViewNumber("50"));
		
		ordersBEPage.selectItemInDynamicDropdownBE("limit", "100");
		verifyTrue(ordersBEPage.isNumberItemsDisplayedAsViewNumber("100"));
		
		ordersBEPage.selectItemInDynamicDropdownBE("limit", "200");
		verifyTrue(ordersBEPage.isNumberItemsDisplayedAsViewNumber("200"));
		
		// For Next-Testcase
		ordersBEPage.openManageCustomerBEPage();
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));

	}
	
	@Test
	public void TC_05_VerifySearchFunctionality() {
		
		// ID
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("entity_id[from]", "123");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("entity_id[to]", "125");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicRangeFilterResultDisplayedOnAllPagesAsExpectation("ID", "entity_id[from]", "entity_id[to]"));
		
		// Name
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("name", "Kevin");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation("Name", "Kevin"));
		
		// Name
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("email", "Kevinpham");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation("Email", "Kevinpham"));
		
		// Telephone
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("Telephone", "123456789");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation("Telephone", "123456789"));
		
		// Zip
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("billing_postcode", "543432");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation("ZIP", "543432"));
		
		// Country
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.selectItemInDynamicDropdownBE("billing_country_id", "Vietnam");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation("Country", "Vietnam"));
		
		// State
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		manageCustomersBEPage.inputToDynamicFilterTextboxBE("billing_region", "California");
		manageCustomersBEPage.clickOnDynamicButtonByTitle("Search");
		verifyTrue(manageCustomersBEPage.isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation("State/Province", "California"));
		
		// For Next-Testcase
		manageCustomersBEPage.openManageCustomerBEPage();
		manageCustomersBEPage = PageGeneratorManager.getMamanageCustomersPage(driver);
		verifyTrue(manageCustomersBEPage.isDynamicBEPageHeaderDisplayed("Manage Customers"));
	
	}
	
	@Test
	public void TC_06_VerifySelectCheckboxFunctionality() {
		
		manageCustomersBEPage.hoverOnDynamicMenuInNavBar("Sales");
		manageCustomersBEPage.clickOnDynamicMenuInNavBar("Orders");
		ordersBEPage = PageGeneratorManager.getOrdersPage(driver);
		verifyTrue(ordersBEPage.isDynamicBEPageHeaderDisplayed("Orders"));
		
		ordersBEPage.clickOnDynamicButtonByTitle("Reset Filter");
		ordersBEPage.selectItemInDynamicDropdownBE("limit", "20");
		verifyTrue(ordersBEPage.isNumberItemsDisplayedAsViewNumber("20"));
		
		manageCustomersBEPage.clickOnDynamicSelectVisible("Select Visible");
		verifyEquals(ordersBEPage.getTextNumberItemsSelected(), "20");
		verifyTrue(ordersBEPage.isAllCheckboxOnPageAreChecked());
		
		manageCustomersBEPage.clickOnDynamicSelectVisible("Unselect Visible");
		verifyEquals(ordersBEPage.getTextNumberItemsSelected(), "0");
		verifyTrue(ordersBEPage.isAllCheckboxOnPageAreUnchecked());
		
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass() { 
		closeBrowserAndDriver(driver);
	}

}

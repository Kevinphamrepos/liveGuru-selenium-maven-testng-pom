package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageGeneratorManager {
	
	public static FE_HomePO getHomePage(WebDriver driver) {
		return new FE_HomePO(driver);
	}
	
	public static FE_RegisterPO getRegisterPage(WebDriver driver) {
		return new FE_RegisterPO(driver);
	}
	
	public static FE_DashboardPO getDashboarPage(WebDriver driver) {
		return new FE_DashboardPO(driver);
	}
	
	public static FE_AccountInfoPO getAccountInfoPage(WebDriver driver) {
		return new FE_AccountInfoPO(driver);
	}

	public static FE_ProductCategoryPO getProductCategoryPage(WebDriver driver) {
		return new FE_ProductCategoryPO(driver);
	}

	public static FE_ProductDetailPO getProductDetailPage(WebDriver driver) {
		return new FE_ProductDetailPO(driver);
	}

	public static FE_ShoppingCartPO getShoppingCartPage(WebDriver driver) {
		return new FE_ShoppingCartPO(driver);
	}

	public static FE_CompareWindowPO getCompareWindow(WebDriver driver) {
		return new FE_CompareWindowPO(driver);
	}

	public static FE_MyWishlistPO getMyWishlistPage(WebDriver driver) {
		return new FE_MyWishlistPO(driver);
	}

	public static FE_WishlistSharingPO getWishlistSharingPage(WebDriver driver) {
		return new FE_WishlistSharingPO(driver);
	}

	public static FE_LoginPO getLoginFEPage(WebDriver driver) {
		return new FE_LoginPO(driver);
	}

	public static FE_CheckoutPO getCheckoutPage(WebDriver driver) {
		return new FE_CheckoutPO(driver);
	}

	public static FE_AdvanceSearchPO getAdvanceSearchPage(WebDriver driver) {
		return new FE_AdvanceSearchPO(driver);
		
	}
	
	public static BE_LoginPO getLoginBEPage(WebDriver driver) {
		return new BE_LoginPO(driver);
	}

	public static BE_ManageCustomersPO getMamanageCustomersPage(WebDriver driver) {
		return new BE_ManageCustomersPO(driver);
	}

	public static BE_OrdersPO getOrdersPage(WebDriver driver) {
		return new BE_OrdersPO(driver);
	}

	public static BE_PendingReviewsPO getPendingReviewsPage(WebDriver driver) {
		return new BE_PendingReviewsPO(driver);
	}

	public static BE_InvoicesPO getInvoicesPage(WebDriver driver) {
		return new BE_InvoicesPO(driver);
	}
	

}

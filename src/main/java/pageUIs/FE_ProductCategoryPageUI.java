package pageUIs;


public class FE_ProductCategoryPageUI { 
	
	public static final String DYNAMIC_PRODUCT_HOME_PRICE = "//a[text()='%s']/parent::h2/following-sibling::div//span[@class='price']";
	public static final String DYNAMIC_PRODUCT_ADD_TO_CART = "//h2[@class='product-name']/a[text()='%s']/parent::h2/following-sibling::div[@class='actions']/button[@title='Add to Cart']";
	public static final String DYNAMIC_PRODUCT_ADD_TO_COMPARE = "//h2[@class='product-name']/a[text()='%s']/parent::h2/following-sibling::div[@class='actions']/ul/li/a[text()='Add to Compare']";
	public static final String DYNAMIC_PRODUCT_ADD_TO_WISHLIST = "//h2[@class='product-name']/a[text()='%s']/parent::h2/following-sibling::div[@class='actions']/ul/li/a[text()='Add to Wishlist']";
	
}
package pageUIs;


public class FE_MyWishlistPageUI { 
	
	public static final String DYNAMIC_ADD_TO_WISHLIST_SUCCESS_MSG = "//li[@class='success-msg']//span[contains(text(),'%s')]";
	public static final String WISHLIST_EMPTY_MSG = "//p[@class='wishlist-empty' and text()='You have no items in your wishlist.']";
	public static final String DYNAMIC_ACTION_BUTTON = "//button[@title='%s']";
	public static final String PRESENT_PRODUCT_NAME = "//h3[@class='product-name']/a";
	public static final String DYNAMIC_REMOVE_ITEM_BY_PRODUCT_NAME = "//h3[@class='product-name']//a[contains(text(),'%s')]/ancestor::td/following-sibling::td/a[@title='Remove Item']";
	public static final String DYNAMIC_ADDTOCART_ITEM_BY_PRODUCT_NAME = "//h3[@class='product-name']//a[contains(text(),'%s')]/ancestor::td/following-sibling::td//button[@title='Add to Cart']";
}
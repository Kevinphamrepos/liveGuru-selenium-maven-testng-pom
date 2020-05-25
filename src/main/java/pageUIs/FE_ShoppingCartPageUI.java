package pageUIs;


public class FE_ShoppingCartPageUI { 
	
	public static final String DYNAMIC_SHOPPING_CART_SUCCESS_MSG = "//ul[@class='messages']//span[text()='%s']";
	public static final String DYNAMIC_SHOPPING_CART_ACTION = "//span[text()='%s']";
	public static final String DISCOUNT_VALUE = "//td[contains(text(),'Discount')]/following-sibling::td/span[@class='price']";
	public static final String GRAND_TOTAL_PRICE_VALUE = "//div[@class='cart-totals']//strong[contains(text(),'Grand Total')]/parent::td/following-sibling::td//span[@class='price']"; 
	public static final String DYNAMIC_CART_QYT_TEXTBOX = "//a[text()='%s']/parent::h2/parent::td/following-sibling::td/input[@class='input-text qty']";
	public static final String DYNAMIC_CART_QYT_UPDATE = "//a[text()='%s']/parent::h2/parent::td/following-sibling::td/button[@title='Update']";
	public static final String DYNAMIC_CART_QYT_ERROR_MSG = "//a[text()='Sony Xperia']/parent::h2/following-sibling::p[contains(text(),'* The maximum quantity allowed for purchase is 500.')]";
	public static final String EMPTY_CART_MSG = "//div[@class='cart-empty']//p[text()='You have no items in your shopping cart.']";
	public static final String DYNAMIC_SUB_PRICE_VALUE = "//div[@class='cart-totals']//td[contains(text(),'%s')]/following-sibling::td/span[@class='price']"; // Subtotal / Flat Rate / Discount
	public static final String FLAT_RATE_PRICE_GENERATED_VALUE = "//ul/li//label[contains(text(),'Fixed')]/span[@class='price']";
	
}
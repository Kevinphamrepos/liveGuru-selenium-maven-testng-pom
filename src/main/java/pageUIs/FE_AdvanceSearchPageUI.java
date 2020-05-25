package pageUIs;

public class FE_AdvanceSearchPageUI { 
	
	public static final String SEARCHED_PRODUCT_NAME = "//h2[@class='product-name']/a";
	public static final String DYNAMIC_SEARCHED_PRODUCT_CURRENT_PRICE = "(//h2[@class='product-name']/a[@title='%s']/parent::h2/following-sibling::div[@class='price-box']/span/span[@class='price'] | //h2[@class='product-name']/a[@title='%s']/parent::h2/following-sibling::div[@class='price-box']/p[@class='special-price']/span[@class='price'])";
	public static final String MODIFY_SEARCH_LINK = "//a[text()='Modify your search']";
	public static final String SEARCH_BUTTON = "//div[@class='buttons-set']/button[@title='Search']";
	
	
}
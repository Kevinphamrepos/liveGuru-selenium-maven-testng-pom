package pageUIs;

public class AbstractPageUI { 
	
	// FRONT-END
	public static final String MANAGE_CUSTOMERS_PAGE_LOGO = "//img[@class='logo']";
	public static final String HEADER_ACCOUNT_MENU = "//div[@class='account-cart-wrapper']//span[text()='Account']";
	public static final String DYNAMIC_HEADER_ACCOUNT_MENU_LINK = "//div[@id='header-account']//a[text()='%s']";
	public static final String DYNAMIC_PAGE_HEADER_TITLE = "//h1[text()='%s']";
	public static final String DYNAMIC_TEXTBOX_TEXTAREA = "(//input | //textarea)[@id='%s']";
	public static final String DYNAMIC_BUTTON_BY_TITLE = "//button[@title='%s']";
	public static final String DYNAMIC_DASHBOARD_LEFT_LINK = "//div[@class='block-content']//a[text()='%s']";
	public static final String DYNAMIC_HEADER_NAV_LINK = "//div[@id='header-nav']//a[text()='%s']";
	public static final String DYNAMIC_PRODUCT_NAME = "//h2[@class='product-name']/a[text()='%s']";
	public static final String DYNAMIC_PRODUCT_IMAGE = "//a[@class='product-image' and @title='%s']";
	public static final String DYNAMIC_SUCCESS_MSG = "//li[@class='success-msg']//span[text()='%s']";
	public static final String DYNAMIC_DROPDOWN_BOX = "//div[@class='input-box']/select[@id='%s']";
	public static final String DYNAMIC_RADIO_BUTTON = "//input[@id='%s']";
	public static final String DYNAMIC_FOOTER_LINK = "//div[@class='footer']//a[text()='%s']";
	public static final String TABLE_PAGER_NEXT_ICON = "//td[@class='pager']//a[@title='Next page']";
	public static final String TABLE_PAGER_BAR = "//td[@class='pager']";
	
	// BACK-END
	public static final String DYNAMIC_MENU_NAVI_BAR = "//ul[@id='nav']//span[text()='%s']";
	public static final String DYNAMIC_BE_PAGE_FIXED_HEADER = "//div[@id='page:main-container']//h3[text()='%s']";
	public static final String DYNAMIC_BE_PAGE_FLOATING_HEADER = "//div[@class='content-header-floating']//h3[text()='%s']";
	public static final String DYNAMIC_HEADING_COLUNM = "//tr[@class='headings']//span[text()='%s']";
	public static final String DYNAMIC_FILTER_TEXTBOX = "//tr[@class='filter']//input[@name='%s']";
	public static final String DYNAMIC_DROPDOWN = "//select[@name='%s']";
	public static final String ACTION_DROPDOWN = "//select[@id='sales_order_grid_massaction-select']";
	public static final String DYNAMIC_ERROR_MSG = "//li[@class='error-msg']//span[text()='%s']";
	public static final String VIEWED_ROW_NUMBER = "//div[@class='grid']//tbody//tr";
	public static final String DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME = "//span[text()='%s']/ancestor::th/preceding-sibling::th";
	public static final String DYNAMIC_FILTERED_DATA_BY_COLUMN_INDEX = "//div[@class='grid']//tbody//tr/td[%s]";
	public static final String DYNAMIC_SELECTED_ITEMS = "//strong[@id='sales_order_grid_massaction-count']";
	public static final String DYNAMIC_SELECT_UNSELECT_VISIBLE = "//td/a[text()='%s']";
	public static final String CHECKBOX_ON_PAGE = "//input[@type='checkbox']";
	
	
	
	
}
package pageUIs;

public class BE_InvoicesPageUI { 
	
	public static final String DYNAMIC_HEADER_SORT_ATTRIBUTE_STATUS = "//span[text()='%s']/parent::a";
	
	public static final String HEADER_SORT_STATUS_ASC = "//tr[@class='headings']//a[@title='asc']"; 
	public static final String HEADER_SORT_STATUS_DESC = "//tr[@class='headings']//a[@title='desc']";
	public static final String DYNAMIC_COLUNM_DATA_BY_COLUMN_INDEX = "//div[@class='grid']//tbody//tr/td[%s]";
	public static final String DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME = "//span[text()='%s']/ancestor::th/preceding-sibling::th";
	public static final String COLUMN_INDEX = "//thead/tr/th/span";
	
}
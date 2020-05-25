package pageObjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.AbstractPO;
import pageUIs.BE_InvoicesPageUI;

public class BE_InvoicesPO extends AbstractPO {
	WebDriver driver;

	public BE_InvoicesPO(WebDriver linkDriver) {
		super(linkDriver);
		driver = linkDriver;
	}
	
	public String getDynamicColunmSortStatusByAttribute(String colunmName, String attributeName) {
		waitForDynamicElementVisibleByXpath(BE_InvoicesPageUI.DYNAMIC_HEADER_SORT_ATTRIBUTE_STATUS, colunmName);
		return getDynamicAtributeValue(BE_InvoicesPageUI.DYNAMIC_HEADER_SORT_ATTRIBUTE_STATUS, attributeName, colunmName);
	}
	
	public void clickToSortDynamicColunm(String colunmName, String sortType, String attributeName) {
		clickOnDynamicHeadingColunm(colunmName);
		sleepInSecond(3); 
		
		if (sortType.equalsIgnoreCase("Ascending")) {
			if (getDynamicColunmSortStatusByAttribute(colunmName, attributeName).equals("asc")) {
			} else if (getDynamicColunmSortStatusByAttribute(colunmName, attributeName).equals("desc")) {
				System.out.println("Click to Sort Ascending");
				clickOnDynamicHeadingColunm(colunmName);
				sleepInSecond(3); 
			} else {
				System.out.println("Colunm is not sorted");
			}
			
		} else if (sortType.equalsIgnoreCase("Descending")) {
			if (getDynamicColunmSortStatusByAttribute(colunmName, attributeName).equals("desc")) {
				System.out.println("No need to Click again");
			} else if (getDynamicColunmSortStatusByAttribute(colunmName, attributeName).equals("asc")) {
				System.out.println("Click to Sort Descending");
				clickOnDynamicHeadingColunm(colunmName);
				sleepInSecond(3); 
			} else {
				System.out.println("Colunm is not sorted");
			}
			
		} else {
			System.out.println("Please Provide Corrective SortType");
		}
		
		System.out.println("Final Sorting Status: " + getDynamicColunmSortStatusByAttribute(colunmName, attributeName));
	}
	
	// Phải đổi tên từ Ascending sang Descending vì UI và Code tính ngược chiều nhau 
	public boolean isDynamicStringColunmSortedDescending(String colunmName) {
		int colunmIndex = findDynamicElementsByXpath(BE_InvoicesPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		System.out.println(colunmIndex);
		String dataColunmXpath = castRestParameterByIndex(BE_InvoicesPageUI.DYNAMIC_COLUNM_DATA_BY_COLUMN_INDEX, colunmIndex);
		System.out.println(dataColunmXpath);
		ArrayList<String> arrayList = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}
		
		System.out.println("-------- Data on UI: --------");
		for (String item : arrayList) {
			System.out.println(item);
		}
		
		ArrayList<String> sortingArray = new ArrayList<String>();
		for (String child : arrayList) {
			sortingArray.add(child);
		}
		Collections.sort(sortingArray);
		
		System.out.println("-------- Sorted Data in Code ---------");
		for (String sortedItem : sortingArray) {
			System.out.println(sortedItem);
		}
		
		return sortingArray.equals(arrayList);
		
	}
	
	// Attention: Sort in Code phân biệt Upper-case với Lower Case nhưng UI thì không	
	// Phải đổi tên từ Descending sang Ascending vì UI và Code tính ngược chiều nhau 
	public boolean isDynamicStringColunmSortedAscending(String colunmName) {
		int colunmIndex = findDynamicElementsByXpath(BE_InvoicesPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		System.out.println(colunmIndex);
		String dataColunmXpath = castRestParameterByIndex(BE_InvoicesPageUI.DYNAMIC_COLUNM_DATA_BY_COLUMN_INDEX, colunmIndex);
		System.out.println(dataColunmXpath);
		ArrayList<String> arrayList = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}
		
		System.out.println("-------- Data on UI: --------");
		for (String item : arrayList) {
			System.out.println(item);
		}
		
		ArrayList<String> sortingArray = new ArrayList<String>();
		for (String child : arrayList) {
			sortingArray.add(child);
		}
		
		Collections.sort(sortingArray);
		
		Collections.reverse(sortingArray);
		System.out.println("-------- Sorted Data in Code ---------"); // Descending - Top down to bottom
		for (String sortedItem : sortingArray) {
			System.out.println(sortedItem);
		}
		
		return sortingArray.equals(arrayList);
	}
	
	public boolean isDynamicStringColunmSortedCorrectlyLowerCase(String colunmName, String sortType) {
		int colunmIndex = findDynamicElementsByXpath(BE_InvoicesPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		System.out.println(colunmIndex);
		String dataColunmXpath = castRestParameterByIndex(BE_InvoicesPageUI.DYNAMIC_COLUNM_DATA_BY_COLUMN_INDEX, colunmIndex);
		System.out.println(dataColunmXpath);
		ArrayList<String> originalarrayList = new ArrayList<String>();
		ArrayList<String> arrayListLowerCase = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			originalarrayList.add(element.getText());
			arrayListLowerCase.add(element.getText().toLowerCase());
		}
		
		System.out.println("-------- Original Data on UI: --------");
		for (String item : originalarrayList) {
			System.out.println(item);
		}
		
		System.out.println("-------- LowerCase Data on UI: --------");
		for (String item : arrayListLowerCase) {
			System.out.println(item);
		}
		
		// For Lower Case
		ArrayList<String> sortingArray = new ArrayList<String>();
		for (String child : arrayListLowerCase) {
			sortingArray.add(child);
		}
		
		Collections.sort(sortingArray);
		
		if (sortType.equalsIgnoreCase("Ascending")) {
			Collections.reverse(sortingArray);
			System.out.println("Collection is Sorted Ascending");
		} else if (sortType.equalsIgnoreCase("Descending")) {
			System.out.println("Collection is Sorted Descending");
		} else {
			System.out.println("Please Provide Corrective SortType");
		}
		
		System.out.println("-------- Sorted Data in Code ---------"); // Descending - Top down to bottom
		for (String sortedItem : sortingArray) {
			System.out.println(sortedItem);
		}
		
		return sortingArray.equals(arrayListLowerCase); 
	}
	
	public boolean isDynamicFloatColunmSortedCorrectly(String colunmName, String sortType) {
		int colunmIndex = findDynamicElementsByXpath(BE_InvoicesPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		System.out.println(colunmIndex);
		String dataColunmXpath = castRestParameterByIndex(BE_InvoicesPageUI.DYNAMIC_COLUNM_DATA_BY_COLUMN_INDEX, colunmIndex);
		System.out.println(dataColunmXpath);
		ArrayList<String> originalarrayList = new ArrayList<String>();
		ArrayList<Float> arrayListFloat = new ArrayList<Float>(); 
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			originalarrayList.add(element.getText());
			arrayListFloat.add(Float.parseFloat(element.getText().replaceAll("[$,]", "").trim()));
		}
		
		System.out.println("-------- Original Data on UI: --------");
		for (String item : originalarrayList) {
			System.out.println(item);
		}
		
		System.out.println("-------- Float Data on UI: --------");
		for (Float item : arrayListFloat) {
			System.out.println(item);
		}
		
		// For Float Type
		ArrayList<Float> sortingArray = new ArrayList<Float>();
		for (Float child : arrayListFloat) {
			sortingArray.add(child);
		}
		
		Collections.sort(sortingArray);

		if (sortType.equalsIgnoreCase("Ascending")) {
			Collections.reverse(sortingArray);
			System.out.println("Collection is Sorted Ascending");
		} else if (sortType.equalsIgnoreCase("Descending")) {
			System.out.println("Collection is Sorted Descending");
		} else {
			System.out.println("Please Provide Corrective SortType");
		}
		
		System.out.println("-------- Sorted Data in Code ---------"); // Descending - Top down to bottom
		for (Float sortedItem : sortingArray) {
			System.out.println(sortedItem);
		}
		
		return sortingArray.equals(arrayListFloat);
	}
	
	public boolean isDynamicDatetimeColunmSortedCorrectly(String colunmName, String sortType) {
		int colunmIndex = findDynamicElementsByXpath(BE_InvoicesPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		System.out.println(colunmIndex);
		String dataColunmXpath = castRestParameterByIndex(BE_InvoicesPageUI.DYNAMIC_COLUNM_DATA_BY_COLUMN_INDEX, colunmIndex);
		System.out.println(dataColunmXpath);
		ArrayList<String> originalarrayList = new ArrayList<String>();
		ArrayList<String> arrayListDate = new ArrayList<String>(); 
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			originalarrayList.add(element.getText());
			arrayListDate.add(convertStringDateToSortableDateOldJava(element.getText())); 
		}
		
		System.out.println("-------- Original Data on UI: --------");
		for (String item : originalarrayList) {
			System.out.println(item);
		}
		
		System.out.println("-------- Converted Data on UI: --------");
		for (String item : arrayListDate) {
			System.out.println(item);
		}
		
		// For String Date
		ArrayList<String> sortingArray = new ArrayList<String>();
		for (String child : arrayListDate) {
			sortingArray.add(child);
		}
		
		Collections.sort(sortingArray);
		
		if (sortType.equalsIgnoreCase("Ascending")) {
			Collections.reverse(sortingArray);
			System.out.println("Collection is Sorted Ascending");
		} else if (sortType.equalsIgnoreCase("Descending")) {
			System.out.println("Collection is Sorted Descending");
		} else {
			System.out.println("Please Provide Corrective SortType");
		}
		
		System.out.println("-------- Sorted Data in Code ---------"); 
		for (String sortedItem : sortingArray) {
			System.out.println(sortedItem);
		}
		
		return sortingArray.equals(arrayListDate);
	}

	public String convertStringDateToSortableDateOldJava(String stringDate) {
		DateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		DateFormat outputFormat = new SimpleDateFormat("yyyyMMddaHHmmss");
		try {
			Date parsedDate = inputFormat.parse(stringDate);
			String outputText = outputFormat.format(parsedDate);
			return outputText;
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String convertStringDateToSortableDateJava8(String stringDate) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH);
		DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyyMMddahhmmss", Locale.ENGLISH);
		LocalDateTime parsedDate = LocalDateTime.parse(stringDate, inputFormat);
		String outputText = outputFormat.format(parsedDate);
		return outputText;
	}
}
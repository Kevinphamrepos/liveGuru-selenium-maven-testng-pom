package commons;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pageUIs.AbstractPageUI;

public class AbstractPO {

	WebDriver driver;
	WebElement element;
	List<WebElement> elements;
	WebDriverWait explicitWait;
	Select select;
	Actions action;
	JavascriptExecutor javascriptExecutor;
	By byXpath;
	long shortTimeout = 5;
	long longTimeout = 30;

	public AbstractPO(WebDriver linkDriver) {
		driver = linkDriver;
		javascriptExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, longTimeout);
		action = new Actions(driver);
		
	}
		
	public void openUrl(String urlValue) {
		driver.get(urlValue);
	}
	
	public void openUrlInNewTabByJSE(String urlValue) {
		executeJavascript("window.open('" + urlValue + "','_blank');");
		switchToWindowByUrl(urlValue);
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public String getPageCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public String getPageSource() {
		return driver.getPageSource();
	}
	
	public void backToPreviousPage() {
		driver.navigate().back();
	}
	
	public void forwardToNextPage() {
		driver.navigate().forward();
	}
	
	public void refreshCurrentPage() {
		driver.navigate().refresh();
	}
	
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}
	
	public void dismissAlert() {
		driver.switchTo().alert().dismiss();
	}
	
	public String getTextAlert() {
		return driver.switchTo().alert().getText();
	}
	
	public void sendKeysToAlert(String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	public By byXpath(String elementXpath) {
		return byXpath = By.xpath(elementXpath);
	}
	
	public WebElement findElementByXpath(String elementXpath) {
		return driver.findElement(By.xpath(elementXpath));
	}
	
	public List<WebElement> findElementsByXpath(String elementsXpath) {
		return driver.findElements(By.xpath(elementsXpath));
	}
	
	public void clickOnElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		element.click();
	}
	
	public void sendKeysToElement (String elementXpath, String value) {
		element = driver.findElement(By.xpath(elementXpath));
		element.clear();
		element.sendKeys(value);
	}
	
	public void selectItemInHTMLDropdown(String dropdownXpath, String expectedItem) {
		element = driver.findElement(By.xpath(dropdownXpath));
		select = new Select(element);
		select.selectByVisibleText(expectedItem);
	}
	
	public String getSelectedItemValue(String dropdownXpath) {
		element = driver.findElement(By.xpath(dropdownXpath));
		select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}
	
	public String getItemSelectedText(String dropdownXpath) {
		element = driver.findElement(By.xpath(dropdownXpath));
		return element.getText();
	}
	
	public String getItemSelectedTextByJS(String itemSelectedCss) {
		return (String) javascriptExecutor.executeScript("return document.querySelector('" + itemSelectedCss + "').text");
	}

	public void selectOneItemInCustomDropdown(String dropdownXpath, String allItemsXpath, String expectedItem) {
		element = driver.findElement(By.xpath(dropdownXpath));
		if (element.isDisplayed()) {
			element.click();
		} else {
			javascriptExecutor.executeScript("arguments[0].click();", element);
		}
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));
		elements = driver.findElements(By.xpath(allItemsXpath));
		for (WebElement itemElement : elements) {
			if (itemElement.getText().equals(expectedItem)) {
				if (itemElement.isDisplayed()) {
					itemElement.click();
				} else {
					javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", itemElement);
					javascriptExecutor.executeScript("arguments[0].click();", itemElement);
				}
				break;
			}
		}
	}
	
	public void deSelectOneItemInCustomDropdown(String dropdownXpath, String allItemsXpath, String expectedItem) {
		element = driver.findElement(By.xpath(dropdownXpath));
		if (element.isDisplayed()) {
			element.click();
		} else {
			javascriptExecutor.executeScript("arguments[0].click();", element);
		}
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));
		elements = driver.findElements(By.xpath(allItemsXpath));
		for (WebElement itemElement : elements) {
			if (itemElement.getText().equals(expectedItem)) {
				if (!itemElement.isDisplayed()) { 
					itemElement.click();
				} else {
					javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", itemElement);
					javascriptExecutor.executeScript("arguments[0].click();", itemElement);
				}
				break;
			}
		}
	}
	
	public void selectOneItemInDynamicCustomDropdownWithHiddenSelectTag(String selectTagID, String expectedItem) {
		String showDynamicSelectTagScript = "document.getElementById('%s').style.display=''";
		String hideDynamicSelectTagScript = "document.getElementById('%s').style.display='none'";
		String selectTagXpath = "//select[@id='%s']";
		javascriptExecutor.executeScript(castRestParameter(showDynamicSelectTagScript, selectTagID));
		selectItemInHTMLDropdown(castRestParameter(selectTagXpath, selectTagID), expectedItem);
		javascriptExecutor.executeScript(castRestParameter(hideDynamicSelectTagScript, selectTagID));
	}
	
	public void selectMultiItemInCustomDropdown(
			String dropdownXpath, 
			String allItemsXpath, 
			String[] expectedItems, 
			String itemSelectedXpath) {
		
		element = driver.findElement(By.xpath(dropdownXpath));
		if (element.isDisplayed()) {
			element.click();
		} else {
			javascriptExecutor.executeScript("arguments[0].click();", element);
		}

		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));

		elements = driver.findElements(By.xpath(allItemsXpath));
		for (WebElement itemElement : elements) {
			for (String multiItems : expectedItems) {
				if (itemElement.getText().equals(multiItems)) {
					if (itemElement.isDisplayed()) {
						itemElement.click();
					} else {
						javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", itemElement);
						javascriptExecutor.executeScript("arguments[0].click();", itemElement);
					}
					
					List<WebElement> multiItemSelectedElements = driver.findElements(By.xpath(itemSelectedXpath));
					if (expectedItems.length == multiItemSelectedElements.size()) {
						break;
					}
				}
			}
		}
	}
	
	public void typeItemIntoEditableDropdown(
			String dropdownXpath, 
			String typeboxXpath,
			String expectedItem) {
		driver.findElement(By.xpath(dropdownXpath)).click();
		driver.findElement(By.xpath(typeboxXpath)).clear();
		driver.findElement(By.xpath(typeboxXpath)).sendKeys(expectedItem);
		action.sendKeys(driver.findElement(By.xpath(typeboxXpath)), Keys.ENTER).perform();
	}
	
	public boolean itemSelectedDisplayedStatus(String itemSelectedXpath) {
		element = driver.findElement(By.xpath(itemSelectedXpath));
		if (element.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean multiItemsSelectedDisplayedStatus(
			String dropdownXpath, 
			String itemSelectedXpath,
			String[] expectedItems,
			int totalitems) {
		List<WebElement> finalItemSelectedElements = driver.findElements(By.xpath(itemSelectedXpath));
		int toalItemsSelected = finalItemSelectedElements.size();
		ArrayList<String> itemsSelectedTextArray = new ArrayList<String>();
		for (WebElement itemSelectedElement : finalItemSelectedElements) {
			itemsSelectedTextArray.add(itemSelectedElement.getText());
		}
		String textDisplayedOnDropboxUI = driver.findElement(By.xpath(dropdownXpath)).getText();

		if (toalItemsSelected > 0 && toalItemsSelected <= 3) {
			for (String singleItem : expectedItems) {
				if (textDisplayedOnDropboxUI.contains(singleItem)) {
					return true;
				} else
					return false;
			}
		} else if (toalItemsSelected == totalitems) {
			if (textDisplayedOnDropboxUI.contains("All selected")) {
				return true;
			} else
				return false;
		} else {
			return textDisplayedOnDropboxUI.equals(toalItemsSelected + " of " + totalitems + " selected");
		}
		return false;
	}	
	
	public void verifyByDisplay(String itemSelectedXpath) {
		boolean itemSelectedDisplayedStatus = driver.findElement(By.xpath(itemSelectedXpath)).isDisplayed();
		Assert.assertTrue(itemSelectedDisplayedStatus);
	}

	public void verifyByText(String dropdownXpath, String expectedItem) {
		String itemSelectedText = driver.findElement(By.xpath(dropdownXpath)).getText();
		Assert.assertEquals(itemSelectedText, expectedItem);
	}

	public void verifyByJSText(String itemSelectedCss, String expectedItem) {
		String textGetBYJS = (String) javascriptExecutor.executeScript("return document.querySelector('" + itemSelectedCss + "').text");
		Assert.assertEquals(textGetBYJS, expectedItem);
	}
	
	public String getAtributeValue(String elementXpath, String attributeName) {
		element = driver.findElement(By.xpath(elementXpath));
		return element.getAttribute(attributeName);
	}
	
	public String getTextElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		return element.getText();
	}
	
	public int countElementsSize(String elementsXpath) {
		elements = driver.findElements(By.xpath(elementsXpath));
		return elements.size();
	}
	
	public void checkACheckbox(String checkboxXpath) {
		element = driver.findElement(By.xpath(checkboxXpath));
		if (!element.isSelected()) {
			element.click();
		}
	}
		
	public void uncheckACheckbox(String checkboxXpath) {
		element = driver.findElement(By.xpath(checkboxXpath));
		if (element.isSelected()) { 
			element.click();
		}
	}
	
	public boolean isElementDisplayed(String elementXpath) {
		overrideImplicitTimeout(shortTimeout);
		try {
			element = driver.findElement(By.xpath(elementXpath));
			overrideImplicitTimeout(longTimeout);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			overrideImplicitTimeout(longTimeout);
			return false;
		}
	}
	
	public boolean isElementSelected(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		return element.isSelected();
	}	
	
	public boolean isElementEnabled(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		return element.isEnabled();
	}	
	
	public void switchToWindowByID(String parentWindowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String windowID : allWindowIDs) {
			if (!windowID.equals(parentWindowID)) {
				driver.switchTo().window(windowID);
				break;
			}
		}
	}
	
	public void switchToWindowByTitle(String expectedTitle) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String windowID : allWindowIDs) {
			driver.switchTo().window(windowID);
			String currentWindowTitle = driver.getTitle();
			if (currentWindowTitle.equals(expectedTitle)) {
				break;
			}
		}
	}

	public void switchToWindowByUrl(String expectedUrl) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String windowID : allWindowIDs) {
			driver.switchTo().window(windowID);
			String currentWindowUrl = driver.getCurrentUrl();
			if (currentWindowUrl.equals(expectedUrl)) {
				break;
			}
		}
	}

	public boolean closeAllWindowsWithoutParent(String parentWindowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		System.out.println("All Windows are running: " + allWindowIDs.size());
		for (String runwindowID : allWindowIDs) {
			if (!runwindowID.equals(parentWindowID)) {
				driver.switchTo().window(runwindowID);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindowID);
		if (driver.getWindowHandles().size() == 1) 
			return true;
		else
			return false;
	}	
	
	public void switchToFrame(String frameXpath) {
		WebElement frameElement = driver.findElement(By.xpath(frameXpath));
		driver.switchTo().frame(frameElement);
	}
	
	public void switchToParentPage() {
		driver.switchTo().defaultContent();
	}
	
	public void bypassPopup(String popupXpath, String closePopupXpath) {
		elements =  driver.findElements(By.xpath(popupXpath));
		if (elements.size() > 0) {
			explicitWait.until(ExpectedConditions.visibilityOf(elements.get(0)));
			driver.findElement(By.xpath(closePopupXpath)).click();
		}
	}
	
	public void doubleClickOnElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		action.doubleClick(element).perform();
	}
	
	public void hoverMouseOnElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		action.moveToElement(element).perform();
	}
	
	public void rightClickOnElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		action.contextClick(element).perform();
	}
	
	public void dragAndDrop(String sourceXpath, String targetXpath) {
		WebElement sourceElement = driver.findElement(By.xpath(sourceXpath));
		WebElement targetElement = driver.findElement(By.xpath(targetXpath));
		action.dragAndDrop(sourceElement, targetElement).perform();
	}
	
	public void sendKeyboardToElement(String elementXpath, Keys key) {
		element = driver.findElement(By.xpath(elementXpath));
		action.sendKeys(element, key).perform();
	}
	
	public void dragAndDropInHTML5Offset(String sourceXpath, String targetXpath) throws AWTException {

		WebElement sourceElement = driver.findElement(By.xpath(sourceXpath));
		WebElement targetElement = driver.findElement(By.xpath(targetXpath));

		Robot robot = new Robot();
		robot.setAutoDelay(500);

		Dimension sourceSize = sourceElement.getSize();
		Dimension targetSize = targetElement.getSize();

		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = sourceElement.getLocation();
		Point targetLocation = targetElement.getLocation();

		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		robot.mouseMove(targetLocation.x, targetLocation.y);

		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}	
	
	public void uploadSingleFileBySendKeys(
			String addFileXpath,
			String startUploadXpath,
			String filePath) {
		driver.findElement(By.xpath(addFileXpath)).sendKeys(filePath);
		driver.findElement(By.xpath(startUploadXpath)).click();
	}
		
	public void uploadMultiFilesBySendKeys(
			String addFilesXpath,
			String startUploadXpath,
			String[] filesPath) {
		
		WebElement addFilesButton = driver.findElement(By.xpath(addFilesXpath));
		for (String filePath : filesPath) {
			addFilesButton.sendKeys(filePath);
			addFilesButton = driver.findElement(By.xpath(addFilesXpath));
		}
				
		List <WebElement> startButtonElements = driver.findElements(By.xpath(startUploadXpath));
		for (WebElement startElement : startButtonElements) {
			startElement.click();
		}
	}
	
	public void uploadFileByRobot(
			String addFileXpath,
			String startUploadXpath,
			String filePath) {

		driver.findElement(By.xpath(addFileXpath)).click();
		
		StringSelection selectfile = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selectfile, null);

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		driver.findElement(By.xpath(startUploadXpath)).click();
	}
	
	public void uploadFilesByAutoIT(
			String addFilesXpath,
			String startUploadXpath,
			String filePath) {
		
		WebElement addFilesButton = driver.findElement(By.xpath(addFilesXpath));
		String projectDir = System.getProperty("user.dir");
		String autoITForChromePath = projectDir + "\\resources\\AutoITForChrome.exe";
		String autoITForFirefoxPath = projectDir + "\\resources\\AutoITForFirefox.exe";
		
		addFilesButton.click();
		
		if (driver.toString().contains("Firefox")) {
			try {
				Runtime.getRuntime().exec(new String[] {autoITForFirefoxPath, filePath});
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Runtime.getRuntime().exec(new String[] {autoITForChromePath, filePath});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		driver.findElement(By.xpath(startUploadXpath)).click();
	}
	
	public Object executeJavascript(String javaSript) {
		return javascriptExecutor.executeScript(javaSript);
	}
	
	public void scrollToBottomPage() {
		javascriptExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	
	public void scrollToElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void highlightElement(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		String originalStyle = element.getAttribute("style");
		javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 3px solid red; border-style: dashed;");
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		javascriptExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}
	
	public void removeElementAttribute(String elementXpath, String attributeName) {
		element = driver.findElement(By.xpath(elementXpath));
		javascriptExecutor.executeScript("arguments[0].removeAttribute('" + attributeName + "');", element);
	}
	
	public boolean checkAnyImageLoaded(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		boolean status = (boolean) javascriptExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWith !=\"underfined\" && arguments[0].naturalWith > 0", element);
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean verifyTextInInnerText(String textExpected) {
		String textActual = (String) javascriptExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		System.out.println("Text actual = " + textActual);
		return textActual.equals(textExpected);
	}

	public void navigateToUrlByJSE(String url) {
		javascriptExecutor.executeScript("window.location = '" + url + "'");
	}

	public void clickOnElementByJSE(String elementXpath) {
		element = driver.findElement(By.xpath(elementXpath));
		javascriptExecutor.executeScript("arguments[0].click();", element);
	}

	public void sendKeysToElementByJSE(String elementXpath, String value) {
		element = driver.findElement(By.xpath(elementXpath));
		javascriptExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}
	
	public void waitForElementVisibleByXpath(String elementXpath) {
		byXpath = By.xpath(elementXpath);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath));
	}
	
	public void waitForAllElementVisibleByXpath(String elementsXpath) {
		byXpath = By.xpath(elementsXpath);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byXpath));
	}
	
	public void waitForElementVisibleByElement(WebElement element) {
		explicitWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementInvisibleByXpath(String elementXpath) {
		byXpath = By.xpath(elementXpath);
		overrideImplicitTimeout(shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byXpath));
		overrideImplicitTimeout(longTimeout);
	}
	
	public void waitForElementPresentleByXpath(String elementXpath) {
		byXpath = By.xpath(elementXpath);
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(byXpath));
	}
	
	public void waitForElementClickableByXpath(String elementXpath) {
		byXpath = By.xpath(elementXpath);
		explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath));
	}
	
	public void waitForElementClickable(WebElement element) {
		explicitWait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForAlertPresentByXpath() {
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public int randomNumber() {
		Random number = new Random();
		return number.nextInt(10000);	
	}
	
	public void sleepInSecond(long numberInSecond) {
		try {
			Thread.sleep(numberInSecond * 1000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	public String byPassAuthenAlert(String url, String username, String password) {
		String[] splitUrl = url.split("//");
		url = splitUrl[0] + "//" + username + ":" + password + "@" + splitUrl[1];
		return url;
	}

	public String readFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			reader.close();
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	public void overrideImplicitTimeout(long newTimeout) {
		driver.manage().timeouts().implicitlyWait(newTimeout, TimeUnit.SECONDS);
	}
	
	public String castRestParameter (String elementXpath, String... dynamicValues) {
		elementXpath = String.format(elementXpath, (Object[]) dynamicValues);
		return elementXpath;
	}
	
	public String castRestParameterByIndex (String elementXpath, int dynamicValues) {
		elementXpath = String.format(elementXpath, dynamicValues);
		return elementXpath;
	}
	
	public void clickOnDynamicElement(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		element.click();
	}
	
	public void clickOnDynamicElementByJSE(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		clickOnElementByJSE(elementXpath);
	}
	
	public void sendKeysToDynamicElement(String elementXpath, String inputValue, String... dynamicValues ) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		element.clear();
		element.sendKeys(inputValue);
	}
	
	public String getTextDynamicElement(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		return element.getText();
	}
	
	public boolean isDynamicElementDisplayed(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		overrideImplicitTimeout(shortTimeout);
		try {
			element = driver.findElement(By.xpath(elementXpath));
			overrideImplicitTimeout(longTimeout);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			overrideImplicitTimeout(longTimeout);
			return false;
		}
	}
	
	public boolean isDynamicElementEnabled(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		overrideImplicitTimeout(shortTimeout);
		try {
			element = driver.findElement(By.xpath(elementXpath));
			overrideImplicitTimeout(longTimeout);
			return element.isEnabled();
		} catch (NoSuchElementException e) {
			overrideImplicitTimeout(longTimeout);
			return false;
		}
	}
	
	public void waitForDynamicElementVisibleByXpath(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		byXpath = By.xpath(elementXpath);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath));
	}
	
	public void waitForDynamicElementInvisibleByXpath(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		byXpath = By.xpath(elementXpath);
		overrideImplicitTimeout(shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byXpath));
		overrideImplicitTimeout(longTimeout);
	}
	
	public void waitForDynamicElementClickableByXpath(String elementXpath, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		byXpath = By.xpath(elementXpath);
		explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath));
	}

	public List<WebElement> findDynamicElementsByXpath(String elementsXpath, String... dynamicValues) {
		elementsXpath = castRestParameter(elementsXpath, dynamicValues);
		return driver.findElements(By.xpath(elementsXpath));
	}
	
	public void selectItemInDynamicHTMLDropdown(String dropdownXpath, String expectedItem, String...dynamicValues) {
		dropdownXpath = castRestParameter(dropdownXpath, dynamicValues);
		element = driver.findElement(By.xpath(dropdownXpath));
		select = new Select(element);
		select.selectByVisibleText(expectedItem);
	}
	
	public void deSelectItemInDynamicHTMLDropdown(String dropdownXpath, String expectedItem, String...dynamicValues) {
		dropdownXpath = castRestParameter(dropdownXpath, dynamicValues);
		element = driver.findElement(By.xpath(dropdownXpath));
		select = new Select(element);
		select.deselectByVisibleText(expectedItem);
	}
	
	public void selectOptionByIndexInDynamicHTMLDropdown(String dropdownXpath, int optionIndex, String...dynamicValues) {
		dropdownXpath = castRestParameter(dropdownXpath, dynamicValues);
		element = driver.findElement(By.xpath(dropdownXpath));
		select = new Select(element);
		select.selectByIndex(optionIndex);
	}
	
	public boolean isDynamicElementSelected(String dynamicXpath, String...dynamicValues) {
		String elementXpath = castRestParameter(dynamicXpath, dynamicValues);
		waitForElementVisibleByXpath(elementXpath);
		element = driver.findElement(By.xpath(elementXpath));
		return element.isSelected();
	}
	
	public void removeDynamicElementAttribute(String elementXpath, String attributeName, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		javascriptExecutor.executeScript("arguments[0].removeAttribute('" + attributeName + "');", element);
	}
	

	public void clickOnHeaderAccountMenu() {
		waitForElementVisibleByXpath(AbstractPageUI.HEADER_ACCOUNT_MENU);
		clickOnElement(AbstractPageUI.HEADER_ACCOUNT_MENU);
	}
	
	public void openPageByDynamicAccountMenuLink(String linkText) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_HEADER_ACCOUNT_MENU_LINK, linkText);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_HEADER_ACCOUNT_MENU_LINK, linkText);
	}
	
	public void inputToDynamicTextboxTextarea(String textboxID, String inputValue) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_TEXTBOX_TEXTAREA, textboxID);
		sendKeysToDynamicElement(AbstractPageUI.DYNAMIC_TEXTBOX_TEXTAREA, inputValue, textboxID);
	}
	
	public boolean isDynamicHeaderTitleDisplayed(String textValue) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_PAGE_HEADER_TITLE, textValue);
		return isDynamicElementDisplayed(AbstractPageUI.DYNAMIC_PAGE_HEADER_TITLE, textValue);
	}
	
	public boolean isDynamicSuccessMsgDisplayed(String successMsg) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_SUCCESS_MSG, successMsg);
		return isDynamicElementDisplayed(AbstractPageUI.DYNAMIC_SUCCESS_MSG, successMsg);
	}
	
	public void clickOnDynamicButtonByTitle(String buttonTitle) {
		waitForDynamicElementClickableByXpath(AbstractPageUI.DYNAMIC_BUTTON_BY_TITLE, buttonTitle);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_BUTTON_BY_TITLE, buttonTitle);
		sleepInSecond(2);
	}
	
	public void openPageByDynamicDashboardLeftLink(String linkText) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_DASHBOARD_LEFT_LINK, linkText);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_DASHBOARD_LEFT_LINK, linkText);
	}
	
	public String getDynamicAtributeValue(String elementXpath, String attributeName, String... dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		return element.getAttribute(attributeName);
	}

	public String getDynamicTextboxAttributeValue(String attributeName, String... textboxID) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_TEXTBOX_TEXTAREA, textboxID);
		return getDynamicAtributeValue(AbstractPageUI.DYNAMIC_TEXTBOX_TEXTAREA, attributeName, textboxID);
	}

	public void openPageByDynamicHeaderNavLink(String linkText) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_HEADER_NAV_LINK, linkText);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_HEADER_NAV_LINK, linkText);
	}
	
	public void clickOnDynamicProductName(String productName) {
		waitForDynamicElementClickableByXpath(AbstractPageUI.DYNAMIC_PRODUCT_NAME, productName);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_PRODUCT_NAME, productName);
	}
	
	public void clickOnDynamicProductImage(String productName) {
		waitForDynamicElementClickableByXpath(AbstractPageUI.DYNAMIC_PRODUCT_IMAGE, productName);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_PRODUCT_IMAGE, productName);
	}
	
	public void selectItemInDynamicDropdown(String dropdownID, String expectedItem) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_DROPDOWN_BOX, dropdownID);
		selectItemInDynamicHTMLDropdown(AbstractPageUI.DYNAMIC_DROPDOWN_BOX, expectedItem, dropdownID);
	}

	public void clickForSelectDynamicRadioButton(String radioButtonID) {
		waitForDynamicElementClickableByXpath(AbstractPageUI.DYNAMIC_RADIO_BUTTON, radioButtonID);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_RADIO_BUTTON, radioButtonID);
	}

	public void getArrayListTextOfElements(WebDriver driver, String listElementXpath) {
		ArrayList<String> arrayListText = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(listElementXpath));
		for (WebElement element : elementList) {
			arrayListText.add(element.getText());
		}
	}

	public int getSizeListElements(String listElementsXpath) {
		List<WebElement> elementList = driver.findElements(By.xpath(listElementsXpath)) ;
		return elementList.size();
	}
	
	public void waitForListElementsVisibleByXpath(String elementsXpath) {
		byXpath = byXpath(elementsXpath);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byXpath));
	}

	public void openPageByDynamicFooterLink(String linkText) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_FOOTER_LINK, linkText);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_FOOTER_LINK, linkText);
	}
	
	public void openManageCustomerBEPage() { 
		waitForElementVisibleByXpath(AbstractPageUI.MANAGE_CUSTOMERS_PAGE_LOGO);
		clickOnElement(AbstractPageUI.MANAGE_CUSTOMERS_PAGE_LOGO);
	}
	
	public void hoverOnDynamicElement(String elementXpath, String...dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		action.moveToElement(element).perform();
	}
	
	public void clickAndHoldOnDynamicElement(String elementXpath, String...dynamicValues) {
		elementXpath = castRestParameter(elementXpath, dynamicValues);
		element = driver.findElement(By.xpath(elementXpath));
		action.moveToElement(element).clickAndHold().build().perform();
	}
	
	public void hoverOnDynamicMenuInNavBar(String menuText) { 
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_MENU_NAVI_BAR, menuText);
		hoverOnDynamicElement(AbstractPageUI.DYNAMIC_MENU_NAVI_BAR, menuText);
	}
	
	public void clickOnDynamicMenuInNavBar(String menuText) { 
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_MENU_NAVI_BAR, menuText);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_MENU_NAVI_BAR, menuText);
	}

	public boolean isDynamicBEPageHeaderDisplayed(String headerText) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_BE_PAGE_FIXED_HEADER, headerText);
		return isDynamicElementDisplayed(AbstractPageUI.DYNAMIC_BE_PAGE_FIXED_HEADER, headerText);
	}
	
	public void clickOnDynamicHeadingColunm(String colunmName) { 
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_HEADING_COLUNM, colunmName);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_HEADING_COLUNM, colunmName);
	}

	public void selectItemInDynamicDropdownBE(String dropboxName, String itemName) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_DROPDOWN, dropboxName);
		selectItemInDynamicHTMLDropdown(AbstractPageUI.DYNAMIC_DROPDOWN, itemName, dropboxName);
		sleepInSecond(2); // Sleep để DOM kịp load các elements
	}
	
	public void selectItemInActionDropdownBE(String itemName) {
		waitForElementVisibleByXpath(AbstractPageUI.ACTION_DROPDOWN);
		selectItemInHTMLDropdown(AbstractPageUI.ACTION_DROPDOWN, itemName);
	}
	
	public boolean isDynamicErrorMsgDisplay(String errorMsg) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_ERROR_MSG, errorMsg);
		return isDynamicElementDisplayed(AbstractPageUI.DYNAMIC_ERROR_MSG, errorMsg);
	}

	public boolean isNumberItemsDisplayedAsViewNumber(String viewNumber) {
		waitForListElementsVisibleByXpath(AbstractPageUI.VIEWED_ROW_NUMBER);
		int displayedRow = findElementsByXpath(AbstractPageUI.VIEWED_ROW_NUMBER).size();
		String displayedNumber = Integer.toString(displayedRow);
		System.out.println("ViewNumber: " + viewNumber + " - DisplayedNumber: " + displayedNumber);
		return displayedNumber.equals(viewNumber);
	}
	
	public void inputToDynamicFilterTextboxBE(String textboxName, String inputValue) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_FILTER_TEXTBOX, textboxName);
		sendKeysToDynamicElement(AbstractPageUI.DYNAMIC_FILTER_TEXTBOX, inputValue, textboxName);
	}
	
	public String getTextFromDynamicFilterTextboxBE(String textboxName) {
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_FILTER_TEXTBOX, textboxName);
		return getTextDynamicElement(AbstractPageUI.DYNAMIC_FILTER_TEXTBOX, textboxName);
	}
	
	public boolean isDynamicStringFilterResultDisplayedOnPageAsExpectation(String colunmName, String filterValue) {
		int colunmIndex = findDynamicElementsByXpath(AbstractPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		String dataColunmXpath = castRestParameterByIndex(AbstractPageUI.DYNAMIC_FILTERED_DATA_BY_COLUMN_INDEX, colunmIndex);
		boolean result = true;
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			String displayedValue = element.getText();
			if (!StringUtils.containsIgnoreCase(displayedValue, filterValue)) {
				result =  false;
				break;
			}
		}
		return result;	
	}
	
	public boolean isDynamicStringFilterResultDisplayedOnAllPagesAsExpectation(String colunmName, String filterValue) {
		boolean result = true;
		selectItemInDynamicDropdownBE("limit", "200");
		int toTalResultPage = getTotalResultPageNumber();
		if (toTalResultPage <=1) {
			result = isDynamicStringFilterResultDisplayedOnPageAsExpectation(colunmName, filterValue);
		} else {
			for (int page = 1; page <= toTalResultPage; page++) {
				goToPageByPageNumber(page);
				sleepInSecond(5);
				result = isDynamicStringFilterResultDisplayedOnPageAsExpectation(colunmName, filterValue);
				}
		} 
		return result;
	}
	
	public void goToPageByPageNumber(int pageNumber) {
		WebElement pageTextbox = driver.findElement(By.xpath("//input[@name='page']"));
		waitForElementVisibleByElement(pageTextbox);
		pageTextbox.clear();
		pageTextbox.sendKeys(Integer.toString(pageNumber));
		pageTextbox.sendKeys(Keys.ENTER);
	}
	
	public boolean isDynamicRangeFilterResultDisplayedOnPageAsExpectation(String colunmName, String fromTextboxName, String toTextboxName) {
		int colunmIndex = findDynamicElementsByXpath(AbstractPageUI.DYNAMIC_NUMBER_PRECEDING_BY_COLUMN_NAME, colunmName).size() + 1;
		String dataColunmXpath = castRestParameterByIndex(AbstractPageUI.DYNAMIC_FILTERED_DATA_BY_COLUMN_INDEX, colunmIndex);
		
		boolean result = true;
		
		int fromNumber = NumberUtils.toInt(getTextFromDynamicFilterTextboxBE(fromTextboxName), 0);
		int toNumber = NumberUtils.toInt(getTextFromDynamicFilterTextboxBE(toTextboxName), Integer.MAX_VALUE); 
		
		List<WebElement> elementList = driver.findElements(By.xpath(dataColunmXpath));
		for (WebElement element : elementList) {
			int displayedValue = NumberUtils.toInt(element.getText());
			if (displayedValue < fromNumber || displayedValue > toNumber) {
				System.out.println("Break Value = " + displayedValue);
				result =  false;
				break;
			}
		}
		return result;	
	}
	
	public boolean isDynamicRangeFilterResultDisplayedOnAllPagesAsExpectation(String colunmName, String fromTextboxName, String toTextboxName) {
		boolean result = true;
		selectItemInDynamicDropdownBE("limit", "200");
		int toTalResultPage = getTotalResultPageNumber();
		if (toTalResultPage <=1) {
			result = isDynamicRangeFilterResultDisplayedOnPageAsExpectation(colunmName, fromTextboxName, toTextboxName);
		} else {
			for (int page = 1; page < toTalResultPage; page++) {
				result = isDynamicRangeFilterResultDisplayedOnPageAsExpectation(colunmName, fromTextboxName, toTextboxName);
				clickOnNextIcon();
				}
		} 
		return result;
	}
	
	public boolean isAllCheckboxOnPageAreChecked() {
		boolean result = true;
		List<WebElement> elementList = driver.findElements(By.xpath(AbstractPageUI.CHECKBOX_ON_PAGE));
		for (WebElement element : elementList) {
			if (!element.isSelected()) {
				result =  false;
				break;
			}
		}
		return result;	
	}
	
	public boolean isAllCheckboxOnPageAreUnchecked() {
		boolean result = true; 
		List<WebElement> elementList = driver.findElements(By.xpath(AbstractPageUI.CHECKBOX_ON_PAGE));
		for (WebElement element : elementList) {
			if (element.isSelected()) {
				result =  false; 
				break;
			}
		}
		return result;	
	}
	
	public boolean isAllCheckboxOnAllPagesAreChecked() {
		boolean result = true;
		selectItemInDynamicDropdownBE("limit", "200");
		int toTalResultPage = getTotalResultPageNumber();
		if (toTalResultPage <=1) {
			result = isAllCheckboxOnAllPagesAreChecked();
		} else {
			for (int page = 1; page < toTalResultPage; page++) {
				result = isAllCheckboxOnAllPagesAreChecked();
				clickOnNextIcon();
				}
		} 
		return result;
	}
	
	public void clickOnNextIcon() {
		waitForElementVisibleByXpath(AbstractPageUI.TABLE_PAGER_NEXT_ICON);
		clickOnElement(AbstractPageUI.TABLE_PAGER_NEXT_ICON);
		sleepInSecond(2); 
	}
	
	public int getTotalResultPageNumber() {
		waitForElementVisibleByXpath(AbstractPageUI.TABLE_PAGER_BAR);
		String originalText = getTextElement(AbstractPageUI.TABLE_PAGER_BAR);
		originalText = originalText.replaceAll("\\s+","");
		String[] mystringarray = originalText.split("\\|");
		int totalPage = NumberUtils.toInt(mystringarray[0].replaceAll("\\D+",""));
		System.out.println("Total Result Page = " + totalPage);
		return totalPage;
	}
	
	public void clickOnDynamicSelectVisible(String linkText) { 
		waitForDynamicElementVisibleByXpath(AbstractPageUI.DYNAMIC_SELECT_UNSELECT_VISIBLE, linkText);
		clickOnDynamicElement(AbstractPageUI.DYNAMIC_SELECT_UNSELECT_VISIBLE, linkText);
		sleepInSecond(3);
	}
	
	public String getTextNumberItemsSelected() {
		waitForElementVisibleByXpath(AbstractPageUI.DYNAMIC_SELECTED_ITEMS);
		return getTextElement(AbstractPageUI.DYNAMIC_SELECTED_ITEMS);
	}
	
	public void checkToFirstCheckbox() {
		waitForListElementsVisibleByXpath(AbstractPageUI.CHECKBOX_ON_PAGE);
		List<WebElement> elementList = driver.findElements(By.xpath(AbstractPageUI.CHECKBOX_ON_PAGE));
		elementList.get(0).click();
	}

	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		for (int i = 0; i < dir_contents.length; i++) {
			if (StringUtils.containsIgnoreCase(dir_contents[i].getName(), fileName))
				return flag = true;
		}
		return flag;
	}
	
	public boolean isSortedStringAscending(WebDriver driver, String locator) {
		ArrayList<String> arrayList = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}
		ArrayList<String> sortingArray = new ArrayList<String>();
		for (String child : arrayList) {
			sortingArray.add(child);
		}
		Collections.sort(sortingArray);
		return sortingArray.equals(arrayList);
	}

	public boolean isSortedStringDescending(WebDriver driver, String locator) {
		ArrayList<String> arrayList = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}
		ArrayList<String> sortingArray = new ArrayList<String>();
		for (String child : arrayList) {
			sortingArray.add(child);
		}
		Collections.sort(sortingArray);
		Collections.reverse(sortingArray);
		return sortingArray.equals(arrayList);
	}

	public boolean isSortedFloatAscending(WebDriver driver, String locator) {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}
		ArrayList<Float> sortingArray = new ArrayList<Float>();
		for (Float child : arrayList) {
			sortingArray.add(child);
		}
		Collections.sort(sortingArray);
		return sortingArray.equals(arrayList);
	}

	public boolean isSortedStringAscendingJDK8(WebDriver driver, String locator) {
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		List<String> items = elementList.stream().map(n -> n.getText()).collect(Collectors.toList());
		List<String> sortedList = items;
		Collections.sort(sortedList);
		return items.equals(sortedList);
	}
	
	public boolean isSortedStringDescendingJDK8(WebDriver driver, String locator) {
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		List<String> itemList = elementList.stream().map(n -> n.getText()).collect(Collectors.toList());
		List<String> sortingList = itemList;
		Collections.sort(sortingList);
		Collections.reverse(sortingList);
		return sortingList.equals(itemList);
	}
}

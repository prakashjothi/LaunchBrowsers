package org.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public static Actions a;
	public static Select s;
	public static JavascriptExecutor js;
	public static TakesScreenshot tk;
	public static WebDriverWait w;

	// 1. Launch the browser
	public static WebDriver launchBrowser(String browserName) {

		switch (browserName) {

		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		default:
			System.out.println("Invalid browser name");
			break;
		}
		return driver;

	}

	// 2. Launch the URL
	public static void launchURL(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	// 3 Find Element by id, name and xpath
	public static WebElement findElement(String locatorType, String locatorvalue) {
		WebElement e = null;
		switch (locatorType) {
		case "id":
			e = driver.findElement(By.id(locatorvalue));
			break;

		case "name":
			e = driver.findElement(By.name(locatorvalue));
			break;
		case "xpath":
			e = driver.findElement(By.xpath(locatorvalue));
			break;
		default:
			break;
		}
		return e;

	}

	// 3a. Find Element by Xpath
	public static WebElement findElementByXpath(String locatorvalue) {
		WebElement e = driver.findElement(By.xpath(locatorvalue));
		return e;
	}

	// 4. Find Elements
	public static List<WebElement> findElements(String locatorType, String locatorName) {
		List<WebElement> e = null;
		switch (locatorType) {
		case "id":
			e = driver.findElements(By.id(locatorName));
			break;

		case "name":
			e = driver.findElements(By.name(locatorName));
			break;
		case "xpath":
			e = driver.findElements(By.xpath(locatorName));
			break;
		default:
			break;
		}
		return e;

	}

	// 5. Send keys to the element
	public static void fillText(WebElement e, String text) {
		e.clear();
		e.sendKeys(text);
	}

	// 6. Click the element
	public static void elementClick(WebElement e) {
		e.click();
	}

	// 7. Get current URL
	public static String getCurrentURL(WebDriver d) {
		String currentUrl = d.getCurrentUrl();
		return currentUrl;

	}

	// 8. Implicit wait for specified seconds
	public static void implicitWait(long sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}

	// 9. Get Text from the element
	public static String getText(WebElement e) {
		String text = e.getText();
		return text;

	}

	// 10. Get Attribute (value or innerText)
	public static String getAttribute(WebElement e, String type) {

		String attribute = null;
		if (type.equals("value")) {
			attribute = e.getAttribute(type);
		} else if (type.equals("innerText")) {
			attribute = e.getAttribute(type);
		}
		return attribute;

	}

	// 11. Mouse over to the element
	public static void mouseOver(WebElement e) {
		a = new Actions(driver);
		a.moveToElement(e).perform();
	}

	// 12. Right click the element
	public static void rightClick(WebElement e) {
		a = new Actions(driver);
		a.contextClick(e).perform();
	}

	// 13. Double click the element
	public static void doubleClick(WebElement e) {
		a = new Actions(driver);
		a.doubleClick(e).perform();
	}

	// 14. Drag and drop an element
	public static void dragAndDrop(WebElement src, WebElement dest) {
		a = new Actions(driver);
		a.dragAndDrop(src, dest).perform();
	}

	// 15. Deal with Alert
	public static void dealAlert() {
		driver.switchTo().alert().accept();
	}

	// 16. Get text from Alert and deal with it
	public static String getTextAlert() {

		String text = driver.switchTo().alert().getText();
		dealAlert();
		return text;

	}

	// 17. Fill text in Alert box and deal with it
	public static void fillTextAlert(String text) {
		driver.switchTo().alert().sendKeys(text);
		dealAlert();
	}

	// 18. Drop down select by index
	public static void dropDownSelectByIndex(WebElement e, int index) {
		s = new Select(e);
		s.selectByIndex(index);
	}

	// 19. Drop down select value or visible Text
	public static void dropDownSelect(WebElement e, String type, String value) {
		s = new Select(e);
		if (type.equals("value")) {
			s.selectByValue(value);

		} else if (type.equals("visibleText")) {
			s.selectByVisibleText(value);

		}
	}

	// 20. Get text from dropdown
	public static String getTextFromDropDown(WebElement e, int index) {
		s = new Select(e);
		List<WebElement> options = s.getOptions();
		String text = options.get(index).getText();
		return text;
	}

	// 21. Fill text using JavaScript
	public static void fillTextByJS(WebElement e, String text) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value','" + text + "')", e);
	}

	// 22. Get text using JavaScript
	public static String getTextByJS(WebElement e) {
		js = (JavascriptExecutor) driver;
		String text = js.executeScript("return arguments[0].getAttribute('value')", e).toString();
		return text;
	}

	// 23. Click Element using JavaScript
	public static void clickByJS(WebElement e) {
		js = (JavascriptExecutor) driver;
		js.executeAsyncScript("arguments[0].click()", e);
	}

	// 24. Scroll down using JavaScript
	public static void scrollDownByJS(WebElement e) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", e);
	}

	// 25. Scroll up using JavaScript
	public static void scrollUpByJS(WebElement e) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", e);

	}

	// 26. Take screenshot specifying filename and store it in a Screenshots folder
	public static void screenShot(String fileName) throws IOException {
		tk = (TakesScreenshot) driver;
		File src = tk.getScreenshotAs(OutputType.FILE);
		File des = new File(
				"C:\\Users\\jprne\\eclipse-workspace\\prakash\\MavenProjectTest\\Screenshots\\" + fileName + ".png");
		FileUtils.copyFile(src, des);
	}

	// 27. Take screenshot without specifying filename and store in Screenshots
	// folder
	public static void screenShot() throws IOException {
		tk = (TakesScreenshot) driver;
		long time = System.currentTimeMillis();
		File src = tk.getScreenshotAs(OutputType.FILE);
		File des = new File(
				"C:\\Users\\jprne\\eclipse-workspace\\prakash\\MavenProjectTest\\Screenshots\\" + time + ".png");
		FileUtils.copyFile(src, des);
	}

	// 27. Switch to the window
	public static void switchToWindow(int index) {
		Set<String> handles = driver.getWindowHandles();
		List<String> li = new ArrayList<String>();
		li.addAll(handles);
		driver.switchTo().window(li.get(index));

	}

	// 28. Quit the browser
	public static void closeBrowser() {
		driver.quit();

	}

	// 29. Explicit wait until textbox is visible
	public static WebElement waitUntilVisible(WebElement e) {
		w = new WebDriverWait(driver, 20);
		return w.until(ExpectedConditions.visibilityOf(e));

	}

	// 30. Explicit wait until button is clickable
	public static WebElement waitUntilClickable(WebElement e) {
		w = new WebDriverWait(driver, 20);
		return w.until(ExpectedConditions.elementToBeClickable(e));
	}

	// 31. Explicit wait until URL contains a specified text
	public static Boolean waitUntilURLContains(String text) {

		w = new WebDriverWait(driver, 50);
		return w.until(ExpectedConditions.urlContains(text));
	}

	// 32. Read a cell value from an Excel file
	public static String readExcel(String fileName, String sheetName, int row, int cell) throws IOException {
		File f = new File(
				"C:\\Users\\jprne\\eclipse-workspace\\prakash\\MavenProjectTest\\src\\test\\resources\\Excel\\"
						+ fileName + ".xlsx");

		FileInputStream st = new FileInputStream(f);
		Workbook w = new XSSFWorkbook(st);
		Sheet sheet = w.getSheet(sheetName);
		String value = "";

		Row rr = sheet.getRow(row);

		Cell cc = rr.getCell(cell);
		int cellType = cc.getCellType();

		if (cellType == 1) {
			value = cc.getStringCellValue();
		} else {
			if (DateUtil.isCellDateFormatted(cc)) {
				Date date = cc.getDateCellValue();
				SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
				value = sf.format(date);

			} else {
				double num = cc.getNumericCellValue();
				long ln = (long) num;
				value = String.valueOf(ln);

			}
		}

		return value;
	}

	// 33. Read Excel row into a list
	public static List<String> readExcelIntoList(String fileName, String sheetName, int row) throws IOException {
		File f = new File(
				"C:\\Users\\jprne\\eclipse-workspace\\prakash\\MavenProjectTest\\src\\test\\resources\\Excel\\"
						+ fileName + ".xlsx");

		FileInputStream st = new FileInputStream(f);
		Workbook w = new XSSFWorkbook(st);
		Sheet sheet = w.getSheet(sheetName);
		List<String> li = new ArrayList<String>();

		Row rr = sheet.getRow(row);

		for (int i = 0; i < rr.getPhysicalNumberOfCells(); i++) {

			Cell cc = rr.getCell(i);
			int cellType = cc.getCellType();

			if (cellType == 1) {
				li.add(cc.getStringCellValue());
			} else {
				if (DateUtil.isCellDateFormatted(cc)) {
					Date date = cc.getDateCellValue();
					SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
					li.add(sf.format(date));

				} else {
					double num = cc.getNumericCellValue();
					long ln = (long) num;
					li.add(String.valueOf(ln));

				}
			}
		}
		return li;

	}

	// 34. Read Excel into an two dimensional array
	public Object[][] readExcelInto2DArray(String fileName) throws IOException {

		File f = new File(
				"C:\\Users\\jprne\\eclipse-workspace\\prakash\\MavenProjectTest\\src\\test\\resources\\Excel\\"
						+ fileName + ".xlsx");
		FileInputStream st = new FileInputStream(f);
		Workbook w = new XSSFWorkbook(st);
		Sheet sheet = w.getSheet("Sheet1");
		String value = "";

		Row row = sheet.getRow(0);

		int rowCount = sheet.getPhysicalNumberOfRows();
		int cellCount = row.getPhysicalNumberOfCells();
		Object obj[][] = new Object[rowCount - 1][cellCount];

		for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
			Row rr = sheet.getRow(i + 1);

			for (int j = 0; j < rr.getPhysicalNumberOfCells(); j++) {
				Cell cc = rr.getCell(j);
				int cellType = cc.getCellType();

				if (cellType == 1) {
					value = cc.getStringCellValue();
				} else {
					if (DateUtil.isCellDateFormatted(cc)) {
						Date date = cc.getDateCellValue();
						SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
						value = sf.format(date);

					} else {
						double num = cc.getNumericCellValue();
						long ln = (long) num;
						value = String.valueOf(ln);

					}
				}
				obj[i][j] = value;
			}

		}
		return obj;

	}

}

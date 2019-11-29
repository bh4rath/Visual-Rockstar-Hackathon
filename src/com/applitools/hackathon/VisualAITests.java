package com.applitools.hackathon;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//import utilities.ConfigFileReader;

public class VisualAITests {
	protected static BatchInfo batch;
	static WebDriver driver;
	protected static EyesRunner runner;
	protected static Eyes eyes;

	By usernameField = By.id("username");
	By passwordField = By.id("password");
	By loginButton = By.id("log-in");
	By remembermeCheckbox = By.xpath("//input[@type='checkbox']");

	@BeforeMethod
	public static void setUp() throws IOException {
		//ConfigFileReader configFileReader = new ConfigFileReader();
		System.setProperty("webdriver.chrome.driver", ".\\Properties\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://demo.applitools.com/hackathonV2.html");
		//driver.manage().window().maximize();
		runner = new ClassicRunner();
		eyes = new Eyes(runner);
		eyes.setApiKey("i599XoqIT0d84drhuiXAMQfRjFZ108pODyt9Rd2ok2C9V4110");
		
	}

	@Test(priority = 1)
	public void loginPageUIElementsTest() {
		eyes.open(driver, "Demo App", "Login Page Visual UI Elements Test", new RectangleSize(1366,784));
		eyes.checkWindow("Login Page");
		eyes.closeAsync();
	}

	@Test(priority = 2)
	public void loginPageDataDrivenTest() {
		WebElement UsernameField = driver.findElement(usernameField);
		WebElement PasswordField = driver.findElement(passwordField);
		WebElement LoginButton = driver.findElement(loginButton);

		eyes.open(driver, "Demo App", "Login Page Data-Driven Tests", new RectangleSize(1366,784));

		eyes.setBatch(batch);

		// ** Check whether Visual error if you don’t enter the user name
		// and password and click the login button
		LoginButton.click();
		eyes.checkWindow("No username, no password");

		// ** Check whether error message is displayed if you only enter the user name
		// and click the login button
		UsernameField.sendKeys("tester");
		LoginButton.click();
		eyes.checkWindow("No password");

		// ** Check whether error message is displayed if you only enter the password
		// and click the login button
		UsernameField.clear();
		PasswordField.sendKeys("tester");
		LoginButton.click();
		eyes.checkWindow("No username");

		// ** Check whether login is successful if you enter the user name, password
		// and click the login button
		UsernameField.sendKeys("tester");
		PasswordField.sendKeys("tester");
		LoginButton.click();
		eyes.setForceFullPageScreenshot(true);
		eyes.checkWindow("Valid login");
		eyes.closeAsync();
	}

	@Test(priority = 3)
	public void tableSortTest() {
		WebElement UsernameField = driver.findElement(usernameField);
		WebElement PasswordField = driver.findElement(passwordField);
		WebElement LoginButton = driver.findElement(loginButton);

		UsernameField.sendKeys("tester");
		PasswordField.sendKeys("tester");
		LoginButton.click();
		WebElement AmountHeader = driver
				.findElement(By.xpath("//table[@id='transactionsTable']//thead//th[contains(text(),'Amount')]"));
		AmountHeader.click();
		eyes.open(driver, "Demo App", "Table Sort Test", new RectangleSize(1366,784));
		eyes.setForceFullPageScreenshot(true);
		eyes.checkWindow("Table Page");
		eyes.closeAsync();
	}

	@Test(priority = 4)
	public void canvasChartTest() {
		WebElement UsernameField = driver.findElement(usernameField);
		WebElement PasswordField = driver.findElement(passwordField);
		WebElement LoginButton = driver.findElement(loginButton);

		UsernameField.sendKeys("tester");
		PasswordField.sendKeys("tester");
		LoginButton.click();
		WebElement CompareExpenses = driver.findElement(By.id("showExpensesChart"));
		CompareExpenses.click();
		eyes.open(driver, "Demo App", "Canvas Chart Test", new RectangleSize(1366,784));
		eyes.checkWindow("For year 2017 and 2018");
		eyes.checkWindow("For year 2019");
		eyes.closeAsync();
	}

	@Test(priority = 5)
	public void dynamicContentTest() throws IOException {
		WebElement UsernameField = driver.findElement(usernameField);
		WebElement PasswordField = driver.findElement(passwordField);
		WebElement LoginButton = driver.findElement(loginButton);
		
		//driver.get("https://demo.applitools.com/hackathonApp.html?showAd=true");
		
		driver.get("https://demo.applitools.com/hackathonV2.html?showAd=true");
		
		
		UsernameField.sendKeys("tester");
		PasswordField.sendKeys("tester");
		LoginButton.click();
		eyes.open(driver, "Demo App", "Dynamic Content Test", new RectangleSize(1366,784));
		eyes.setMatchLevel(MatchLevel.LAYOUT);
		eyes.checkWindow("Dynamic Content");
		eyes.closeAsync();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		eyes.abortIfNotClosed();
		TestResultsSummary allTestResults = runner.getAllTestResults();
		System.out.println(allTestResults);
	}
}
package testScripts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import commonUtils.Utility;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtentReportTest {
	WebDriver driver;
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	
	@BeforeTest
	public void setupExtent() {
		reports= new ExtentReports();
		spark =new ExtentSparkReporter("target\\SparkReport.html");
		reports.attachReporter(spark);
	}
	
	@BeforeMethod
	public void setup() throws IOException
	{
		String url;
		WebDriverManager.edgedriver().setup();
	 driver=new EdgeDriver();
	driver.manage().window().maximize();
	}
	@Test
	public void javaSearchTest() {
//		  System.setProperty("webdriver.chrome.driver", "E:\\WebDrivers\\chromedriver.exe");
		 	extentTest=reports.createTest("JavaSearch");
			driver.get("https://www.google.com");
			WebElement searchbox =driver.findElement(By.name("q"));
			searchbox.sendKeys("Java  Tutorial");
			searchbox.submit();
			Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Se");

	  }
	@Test
	public void seleniumSearchTest() {
//		  System.setProperty("webdriver.chrome.driver", "E:\\WebDrivers\\chromedriver.exe");
		extentTest=reports.createTest("SeleniumSearch");
			driver.get("https://www.google.com");
			WebElement searchbox =driver.findElement(By.name("q"));
			searchbox.sendKeys("Selenium  Tutorial");
			searchbox.submit();

	  }
	@AfterMethod
	public void Teardown(ITestResult result) {
		if(ITestResult.FAILURE== result.getStatus()) {
			extentTest.log(Status.FAIL, result.getThrowable().getMessage());
			String strPath=Utility.getScreenshotPath(driver);
			extentTest.addScreenCaptureFromPath(strPath);
		}
		driver.close();
	}
	@AfterTest
	public void finishExtent() {
		reports.flush();
	}
}

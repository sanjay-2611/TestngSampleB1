package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleOneTest {
	WebDriver driver;
	Properties prop;
	
	@BeforeTest
	public void setup() throws IOException
	{
		String url;
		WebDriverManager.edgedriver().setup();
	 driver=new EdgeDriver();
	driver.manage().window().maximize();
	String path=System.getProperty("user.dir")+"\\src\\test\\resources\\ConfigFiles\\config.properties";
	 prop=new Properties();
	FileInputStream obtained = new FileInputStream(path);
	prop.load(obtained);

	}
	
	
  @Test(alwaysRun= true, dependsOnMethods="seleniumSearchTest")
  public void javaSearchTest() {
//	  System.setProperty("webdriver.chrome.driver", "E:\\WebDrivers\\chromedriver.exe");
	 
		driver.get(prop.getProperty("url"));
		WebElement searchbox =driver.findElement(By.name("q"));
		searchbox.sendKeys("Java  Tutorial");
		searchbox.submit();

  }
 
  @Test
  public void seleniumSearchTest() {
//	  System.setProperty("webdriver.chrome.driver", "E:\\WebDrivers\\chromedriver.exe");
		
	  	driver.get(prop.getProperty("url"));
		WebElement searchbox =driver.findElement(By.name("q"));
		SoftAssert softAssert= new SoftAssert();
		softAssert.assertEquals(driver.getTitle(), "Google Page");
		searchbox.sendKeys("Selenium Tutorial");		
		searchbox.submit();
		softAssert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search Page");
//		softAssert.assertAll();
		
//		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
//		driver.close();
  }
  @Test//(enabled=false)
  public void cucumberSearchTest() {
//	  System.setProperty("webdriver.chrome.driver", "E:\\WebDrivers\\chromedriver.exe");
	 
	  	driver.get(prop.getProperty("url"));
		WebElement searchbox =driver.findElement(By.name("q"));
		searchbox.sendKeys("Cucumber  Tutorial");
		searchbox.submit();
  }
  @AfterTest
  public void Teardown()
  {
	  driver.close();
  }
}

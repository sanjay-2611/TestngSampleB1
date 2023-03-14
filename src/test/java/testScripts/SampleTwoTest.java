package testScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleTwoTest {
		// TODO Auto-generated method stub
		  @Test(retryAnalyzer= RetrySampleTest.class)
		  public void javaSearchTest() {
//			  System.setProperty("webdriver.chrome.driver", "E:\\WebDrivers\\chromedriver.exe");
			  WebDriverManager.edgedriver().setup();
				WebDriver driver=new EdgeDriver();
				driver.manage().window().maximize();
				driver.get("https://www.google.com");
				WebElement searchbox =driver.findElement(By.name("q"));
				searchbox.sendKeys("Selenium  Tutorial");
				searchbox.submit();
				Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Search");
				//searchbox.sendKeys(Keys.ENTER);
//				System.out.println("Page Title : " + driver.getTitle());
//				driver.navigate().back();
//				System.out.println("Current url : " + driver.getCurrentUrl());
//				driver.navigate().forward();
//				driver.navigate().to("https://www.zucisystems.com");
//				driver.navigate().refresh();
				driver.close();
		  }
		 
	}



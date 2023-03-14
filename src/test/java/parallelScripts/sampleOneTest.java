package parallelScripts;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class sampleOneTest {
//	@Parameters("browser")
//	@BeforeMethod
//	public void start()
//	{
//		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver=new ChromeDriver();
//		driver.manage().window().maximize();
//	}
  @Test
  public void testOne() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestOne from sampleOne"+id);
  }
  @Test
  public void testTwo() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestTwo from sampleOne"+id);
  }
  @Test
  public void testThree() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestThree from sampleOne"+id);
  }
  @Test(threadPoolSize=4, invocationCount=8, timeOut=10000)
  public void testFour() {
	  long id= Thread.currentThread().getId();
	  System.out.println("Testfour from sampleOne"+id);
  }
}

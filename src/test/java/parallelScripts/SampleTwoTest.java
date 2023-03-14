package parallelScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleTwoTest {
//	WebDriver driver;
//	@BeforeMethod
//	public void start()
//	{
//		
//		WebDriverManager.chromedriver().setup();
//		 driver=new ChromeDriver();
//		driver.manage().window().maximize();
//	}
  @Test
  public void testOne() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestOne from sampleTwo"+ id);
  }
  @Test
  public void testTwo() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestTwo from sampleTwo"+ id);
  }
  @Test
  public void testThree() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestThree from sampleTwo"+ id);
  }
  @Test
  public void testFour() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestFour from sampleTwo"+ id);
  }
//  @AfterMethod
//  public void close()
//  {driver.close();}
}


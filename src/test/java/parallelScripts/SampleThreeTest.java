package parallelScripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleThreeTest {
	WebDriver driver;
	@Parameters("browser")
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
	  System.out.println("TestOne from sampleThree"+id);
  }
  @Test
  public void testTwo() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestTwo from sampleThree"+id);
  }
  @Test
  public void testThree() {
	  long id= Thread.currentThread().getId();
	  System.out.println("TestThree from sampleThree"+id);
  }
  @Test
  public void testFour() {
	  long id= Thread.currentThread().getId();
	  System.out.println("Testfour from sampleThree"+id);
  }
//  @AfterMethod
  public void close()
  {
	  driver.close();
  }
}

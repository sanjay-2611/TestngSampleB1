package testScripts;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
public class danubeStore {
	WebDriver driver;
	Properties prop;
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeTest
	public void setup() throws Exception {
		WebDriverManager.edgedriver().setup();
		driver=new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();
//		Dimension newDimension = new Dimension(800, 600);
//		driver.manage().window().setSize(newDimension);
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\ConfigFiles\\config.properties";
		 prop=new Properties();
		FileInputStream obtained = new FileInputStream(path);
		prop.load(obtained);
		driver.get(prop.getProperty("url"));
//		Robot robot = new Robot();
//		for (int i = 0; i < 2; i++) {
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_SUBTRACT);
//			robot.keyRelease(KeyEvent.VK_SUBTRACT);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//		}
		
	}
	
	@Test
	public void register() throws InterruptedException
	{
	
		driver.findElement(By.id("signup")).click();
		driver.findElement(By.id("s-name")).sendKeys(prop.getProperty("name"));
		driver.findElement(By.id("s-surname")).sendKeys(prop.getProperty("surname"));
		driver.findElement(By.id("s-email")).sendKeys(prop.getProperty("email"));
		driver.findElement(By.id("s-password2")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.id("s-company")).sendKeys(prop.getProperty("company"));
		driver.findElement(By.id("myself")).click();
		driver.findElement(By.id("marketing-agreement")).click();
		driver.findElement(By.id("privacy-policy")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Register')]")).click();		
	}
	
	@Test
	public void search() throws InterruptedException {
//		driver.get(prop.getProperty("url"));
		driver.findElement(By.name("searchbar")).sendKeys(prop.getProperty("search"));
		driver.findElement(By.className("preview")).click();
		driver.findElement(By.xpath("//button[text()='Add to cart']")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("s-name")).sendKeys(prop.getProperty("name"));
		driver.findElement(By.id("s-surname")).sendKeys(prop.getProperty("surname"));
		driver.findElement(By.id("s-address")).sendKeys(prop.getProperty("address"));
		driver.findElement(By.id("s-zipcode")).sendKeys(prop.getProperty("zipcode"));
		driver.findElement(By.id("s-city")).sendKeys(prop.getProperty("city"));
		driver.findElement(By.id("s-company")).sendKeys(prop.getProperty("company"));
		driver.findElement(By.id("single")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'Buy')]")).click();
		Thread.sleep(2000);
	}
	@AfterTest
	public void close() {
		driver.close();
	}
	

}

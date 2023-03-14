package testScripts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPageTest {
	WebDriver driver;
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeTest
	public void setupExtent() {
		reports= new ExtentReports();
		spark =new ExtentSparkReporter("target\\LoginPageTestReport.html");
		reports.attachReporter(spark);
	}
	@BeforeMethod
	public void setup() throws IOException
	{
	//	String url;
		WebDriverManager.edgedriver().setup();
	 driver=new EdgeDriver();
	driver.manage().window().maximize();
	driver.get("http://the-internet.herokuapp.com/login");

	}
  @Test(dataProvider="loginData")
  public void ValidLoginTest(String strUser, String strPwd) throws InvalidFormatException, IOException, SAXException, ParserConfigurationException {
	  extentTest=reports.createTest("Login");
		driver.findElement(By.xpath(readXmlData("strUser"))).sendKeys(strUser);
		driver.findElement(By.id(readXmlData("strPwd"))).sendKeys(strPwd);
		driver.findElement(By.cssSelector(readXmlData("loginBtn"))).click();	
		boolean isDisp = driver.findElement(By.cssSelector(readData("successMsg"))).isDisplayed();
		Assert.assertTrue(isDisp);
		}
  @DataProvider(name="loginData")
  public Object[][] getData() throws CsvValidationException, IOException{
	  String path=System.getProperty("user.dir")+"//src//test//resources//testData//loginData.csv";
	  String[] cols;
	  CSVReader reader = new CSVReader(new FileReader(path));
	  ArrayList<Object> dataList=new ArrayList<Object>();
	  while((cols=reader.readNext())!=null)
	  {
		  Object[] record= {cols[0], cols[1]};
		  dataList.add(record);
	  }
	  return dataList.toArray(new Object[dataList.size()][]);
	  
  }
  
  public String readData(String objName) throws InvalidFormatException, IOException {
	  String objPath="";
	  String path=System.getProperty("user.dir")+"//src//test//resources//testData//excelData.xlsx";
	  XSSFWorkbook workbook= new XSSFWorkbook(new File(path));
	  XSSFSheet sheet=workbook.getSheet("login");
	  int numRows=sheet.getLastRowNum();
	  for(int i=0; i<=numRows; i++)
	  {
		  XSSFRow row=sheet.getRow(i);
		  if(row.getCell(0).getStringCellValue().equalsIgnoreCase(objName))
			  objPath=row.getCell(1).getStringCellValue();
	  }
	  return objPath;

  }
  
  public String readXmlData(String tagname) throws SAXException, IOException, ParserConfigurationException {
	  String path=System.getProperty("user.dir")+"//src//test//resources//testData//XmlObjRepo.xml";
	  File file= new File(path);
	  DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
	  DocumentBuilder build=factory.newDocumentBuilder();
	  Document document= build.parse(file);
	  NodeList List= document.getElementsByTagName("ObjRepo");
	  Node node1=List.item(0);
	  Element elem=(Element)node1;
	  return elem.getElementsByTagName(tagname).item(0).getTextContent();
	  
	  
  }
  @AfterMethod
  
	  public void Teardown(ITestResult result) {
			if(ITestResult.FAILURE== result.getStatus()) {
				extentTest.log(Status.FAIL, result.getThrowable().getMessage());
			}
	  driver.quit();
  }
  @AfterTest
	public void finishExtent() {
		reports.flush();
	}
}

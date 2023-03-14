package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

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
import org.testng.ITestResult;
import org.testng.annotations.*;
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

public class danubeStorefile {
	WebDriver driver;
	Properties prop;
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeClass
	public void open()
	{
		reports= new ExtentReports();
		spark =new ExtentSparkReporter("target\\danubeSparkReport.html");
		reports.attachReporter(spark);
	}
	@BeforeTest
	public void setup() throws IOException
	{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\ConfigFiles\\config.properties";
		 prop=new Properties();
		FileInputStream obtained = new FileInputStream(path);
		prop.load(obtained);
		driver.get(prop.getProperty("url"));
		driver.get("https://danube-webshop.herokuapp.com");
			
		
	}
	
	@Test(dataProvider="SIGNUP", priority=1)
	public void Signup(String name, String surname, String email, String password, String company) throws InterruptedException, InvalidFormatException, IOException
	{
		extentTest= reports.createTest("Signup");
	
		driver.findElement(By.id(readData("Signup"))).click();
		driver.findElement(By.id(readData("Name"))).sendKeys(name);
		driver.findElement(By.id(readData("Surname"))).sendKeys(surname);
		driver.findElement(By.id(readData("Email"))).sendKeys(email);
		driver.findElement(By.id(readData("Password"))).sendKeys(password);
		driver.findElement(By.id(readData("Company"))).sendKeys(company);
		driver.findElement(By.id(readData("Myself"))).click();
		driver.findElement(By.id(readData("Agreement"))).click();
		driver.findElement(By.id(readData("Policy"))).click();
		driver.findElement(By.xpath(readData("Register"))).click();		
	}
	@Test(priority=2)
	public void PlaceOrder() throws InterruptedException, SAXException, IOException, ParserConfigurationException {
		extentTest= reports.createTest("Place order");
		driver.findElement(By.name(readXmlData("search"))).sendKeys(prop.getProperty("search"));
		driver.findElement(By.className(readXmlData("searchbtn"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(readXmlData("addtocart"))).click();
		driver.findElement(By.xpath(readXmlData("checkout"))).click();
		Thread.sleep(3000);
		driver.findElement(By.id(readXmlData("name"))).sendKeys(prop.getProperty("name"));
		driver.findElement(By.id(readXmlData("surname"))).sendKeys(prop.getProperty("surname"));
		driver.findElement(By.id(readXmlData("address"))).sendKeys(prop.getProperty("address"));
		driver.findElement(By.id(readXmlData("zipcode"))).sendKeys(prop.getProperty("zipcode"));
		driver.findElement(By.id(readXmlData("city"))).sendKeys(prop.getProperty("city"));
		driver.findElement(By.id(readXmlData("company"))).sendKeys(prop.getProperty("company"));
		driver.findElement(By.id(readXmlData("single"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(readXmlData("buy"))).click();
		Thread.sleep(2000);
	}
	
	public String readData(String objName) throws InvalidFormatException, IOException {
		  String objPath="";
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danubeData.xlsx";
		  XSSFWorkbook workbook= new XSSFWorkbook(new File(path));
		  XSSFSheet sheet=workbook.getSheet("sheet1");
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
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danubeXML.xml";
		  File file= new File(path);
		  DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		  DocumentBuilder build=factory.newDocumentBuilder();
		  Document document= build.parse(file);
		  NodeList List= document.getElementsByTagName("Danube");
		  Node node1=List.item(0);
		  Element elem=(Element)node1;
		  return elem.getElementsByTagName(tagname).item(0).getTextContent();
	  
	  }
	
	 @DataProvider(name="SIGNUP")
	  public Object[][] getData() throws CsvValidationException, IOException{
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danube.csv";
		  String[] cols;
		  CSVReader reader = new CSVReader(new FileReader(path));
		  ArrayList<Object> dataList=new ArrayList<Object>();
		  while((cols=reader.readNext())!=null)
		  {
			  Object[] record= {cols[0], cols[1], cols[2], cols[3], cols[4]};
			  dataList.add(record);
		  }
		  return dataList.toArray(new Object[dataList.size()][]);
		  
	  }
	 
	 @AfterMethod
	 public void close(ITestResult result)
	 {
		 if(ITestResult.FAILURE== result.getStatus()) {
				extentTest.log(Status.FAIL, result.getThrowable().getMessage());
			}
		
		 
		 
	 }
	 @AfterTest
	 public void tear()
	 {
		 driver.close();
		 reports.flush();
		
	 }
}

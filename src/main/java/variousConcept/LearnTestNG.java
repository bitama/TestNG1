package variousConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.bytebuddy.utility.privilege.GetSystemPropertyAction;

public class LearnTestNG {
	WebDriver driver;
	String url;
	String browser;
	String userName="demo@techfios.com";
	String password="abc123";
	String dashboardHeader="Dashboard";
	public void getProperty() {
		try {
			InputStream file = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(file);
			browser=prop.getProperty("browser");
			url=prop.getProperty("url");
			} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@BeforeMethod
	public void  init() {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
			 driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("Firefox")){
			System.setProperty("webdriver.gecko.driver","./drivers/geckodriver.exe");
			driver=new FirefoxDriver();
			
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.get(url);
	}
	@Test
	public void loginTest() {
		By USERNAME_LOCATOR = By.xpath("//*[@id='username']");
		By PASSWORD_LOCATOR = By.xpath("//*[@id='password']");
		By LOGIN_LOCATOR=By.xpath("//*[@name='login']");
		By DASHBOARD_HEADER_LOCATOR=By.xpath("//h2[normalize-space()='Dashboard']");
		
		driver.findElement(USERNAME_LOCATOR).sendKeys(userName);
		driver.findElement(PASSWORD_LOCATOR).sendKeys(password);
		driver.findElement(LOGIN_LOCATOR).click();
		driver.findElement(DASHBOARD_HEADER_LOCATOR);
		
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_LOCATOR).getText(),dashboardHeader,"You got the Wrong page");
		
		
	}
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	
		
	}
}

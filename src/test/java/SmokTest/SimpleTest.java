package SmokTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.internal.IContainer;

import java.lang.reflect.Method;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SimpleTest {
    private WebDriver driver;
    public ExtentSparkReporter htmlreporter;
    public ExtentReports extent;
    public ExtentTest test;

    /*ExtentReports extent = new ExtentReports();
    ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
    extent.attachReporter(spark);*/

    @Parameters({"browserName","url","env"})
    @BeforeTest
    public void setup(@NotNull String browserName, String url, String env){
        //System.setProperty("webdriver.chrome.driver","chromedriver");
        System.out.println("The browser Name is : "+browserName);
        if(browserName.equalsIgnoreCase("Chrome")){
           // System.setProperty("webdriver.chrome.driver","chromedriver");
            driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("Firefox")) {
            //System.setProperty("webdriver.gecko.driver", "geckodriver");
            driver = new FirefoxDriver();
        }
        else if(browserName.equalsIgnoreCase("safari")) {
            //System.setProperty("webdriver.gecko.driver", "geckodriver");
            driver = new SafariDriver();
        }
         driver.manage().window().maximize();
         driver.get(url);
         //report method
         htmlreporter = new ExtentSparkReporter("MyReport2.html");
         htmlreporter.config().setDocumentTitle("Automation Report");
         htmlreporter.config().setReportName("Functional report");
         htmlreporter.config().setTheme(Theme.STANDARD);
         extent = new ExtentReports();
         extent.attachReporter(htmlreporter);
         extent.setSystemInfo("Host Name","Localhost");
         extent.setSystemInfo("Tester Name","Jamel");
         extent.setSystemInfo("Browser Name",browserName);
         test =extent.createTest(browserName);
        test.log(Status.PASS,"Browser luanched is : "+browserName);
    }
   // @Order(1)
    @Test(priority = 1)
    public void Login(){
        //test report implement
        test =extent.createTest("Login");

        //Accept Cookis
        driver.findElement(By.xpath(("//button[@id=\"didomi-notice-agree-button\"]"))).click();
        //connection
        //driver.findElement(By.xpath("//i[@class='pv-QuickAccess--account u-Hidden-mobile pv-Icon pv-Icon--compte']")).click();

    }
    //@Order(2)
    @Test(priority = 2)
    public void Connection() throws InterruptedException {
        //test report implement
        test =extent.createTest("Connection");
        //button cnx
        driver.findElement(By.xpath("//i[@class='pv-QuickAccess--account u-Hidden-mobile pv-Icon pv-Icon--compte']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//id")).click();
    }

    @AfterMethod
    public void tearDown(ITestResult result){
       if(result.getStatus() == ITestResult.FAILURE){
           test.log(Status.FAIL,"Test case failed is : "+result.getName());
           test.log(Status.FAIL,"Test case failed is : "+result.getThrowable());
            }
       else if(result.getStatus()==ITestResult.SKIP){
           test.log(Status.SKIP,"Test case Skipped is : "+result.getName());
       }
       else if(result.getStatus()==ITestResult.SUCCESS){
           test.log(Status.PASS,"Test case Passed is : "+result.getName());
           test.log(Status.INFO,result.getInstanceName());

       }
    }

    @AfterTest
    public void cleanUp(){
        driver.quit();
        extent.flush();
    }
}

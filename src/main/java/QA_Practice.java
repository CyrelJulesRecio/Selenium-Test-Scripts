import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.File;

public class QA_Practice {
    WebDriver driver;
    JavascriptExecutor  js ;

    String AR ="admin@admin.com, you have successfully logged in!";
    @Test(priority = 1)
    public void main(){
        System.setProperty("webdriver.gecko.driver","C:\\Users\\cj\\Desktop\\Selenium jars\\drivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        js =  (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.get("https://localhost/QA-PRACTICE/qa-practice.netlify.app/index.html");
    }
    @Test(priority = 2)
    public void Login(){
        driver.findElement(By.xpath("//*[@id=\"forms\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"login\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("admin@admin.com");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("admin123");
        driver.findElement(By.xpath("//*[@id=\"submitLoginBtn\"]")).click();
        String ER = driver.findElement(By.xpath("//*[@id=\"message\"]")).getText();
        Assert.assertEquals(AR,ER);
    }

    @Test(priority = 3)
    public void RecoverPass() throws AWTException, InterruptedException {
        driver.get("https://localhost/QA-PRACTICE/qa-practice.netlify.app/index-2.html");
        driver.findElement(By.xpath("//*[@id=\"forms\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"recover-password\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("test1");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        String Actual = "An email with the new password has been sent to test1. Please verify your inbox!";
        String Expected = driver.findElement(By.xpath("//*[@id=\"message\"]")).getText();
        Assert.assertEquals(Actual,Expected);
        driver.getTitle();
    }

    @Test(priority = 4)
    public void Scroll(){
        driver.get("https://localhost/QA-PRACTICE/qa-practice.netlify.app/index-2.html");
        js.executeScript("window.scrollBy(0,250)","");
        driver.findElement(By.xpath("//*[@id=\"actions\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"scrolling\"]")).click();
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        js.executeScript("window.scrollBy(0,-document.body.scrollHeight)");
    }
    @Test(priority = 7)
    public void dataReadTest() throws IOException {
        File src = new File("C:\\Users\\cj\\Desktop\\Developer\\Credentials.xlsx");
        FileInputStream fis = new FileInputStream(src);
        XSSFWorkbook xsf = new XSSFWorkbook(fis);
        XSSFSheet sheet = xsf.getSheetAt(0);
        String entry1 = sheet.getRow(0).getCell(0).getStringCellValue();
        String entry2 = sheet.getRow(0).getCell(1).getStringCellValue();
        System.out.println("username = " + entry1);
        System.out.println("password = " + entry2);
        xsf.close();
    }
//    @Test(priority =6)
//    public void Last(){
//
//        driver.get("facebook.com");
//
//
//    }
    @AfterTest
    public void Quit_browser(){
        driver.quit();
    }
}


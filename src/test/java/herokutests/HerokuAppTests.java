package herokutests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class HerokuAppTests {
    private static WebDriver driver;

    @BeforeMethod
    @BeforeSuite
    public static void setup(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vaibhav.bajpai\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/");
        driver.manage().window().maximize();
    }

    @Test(description = "A/B Testing")
    public void abtesting(){
        driver.findElement(By.cssSelector("a[href=\"/abtest\"]")).click();
        String element = driver.getTitle();
        AssertJUnit.assertEquals(element, "The Internet");
        driver.navigate().back();
    }

    @Test(description = "Add/Remove Testing")
    public void addremovetesting() {
        driver.findElement(By.cssSelector("a[href=\"/add_remove_elements/\"]")).click();
        driver.findElement(By.cssSelector("#content > div > button")).click();
        boolean deletebtn = driver.findElement(By.cssSelector("#elements > button")).isDisplayed();
        Assert.assertEquals(deletebtn, true);
        driver.findElement(By.cssSelector("#elements > button")).click();
//        boolean deletebtnappearance = driver.findElement(By.cssSelector("#elements > button")).isDisplayed();
//        Assert.assertEquals(deletebtnappearance, false);
//        driver.navigate().back();
    }

}

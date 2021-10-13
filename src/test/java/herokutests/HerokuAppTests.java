package herokutests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;


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
        try {
            boolean deletebtnappearance = driver.findElement(By.cssSelector("#elements > button")).isEnabled();
            Assert.assertEquals(deletebtnappearance, false);
        } catch (Exception ex) {
        }
        driver.navigate().back();
    }
    @Test(description = "Basic Auth")
    public void basicauthtest() {
        driver.findElement(By.cssSelector("a[href=\"/basic_auth\"]")).click();
        //           http://username:pwd@url
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        String text = driver.findElement(By.cssSelector("p")).getText();
        Assert.assertEquals(text, "Congratulations! You must have the proper credentials.");
        driver.navigate().back();
    }

    @Test(description = "Image broken test")
    public void imagebrokentest(){
        int iBrokenImageCount=0;
        String status = null;
        try
        {
            iBrokenImageCount = 0;
            List<WebElement> image_list = driver.findElements(By.tagName("img"));
            /* Print the total number of images on the page */
            System.out.println("The page under test has " + image_list.size() + " images");
            for (WebElement img : image_list)
            {
                if (img != null)
                {
                    if (img.getAttribute("naturalWidth").equals("0"))
                    {
                        System.out.println(img.getAttribute("outerHTML") + " is broken.");
                        iBrokenImageCount++;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            status = "failed";
            System.out.println(e.getMessage());
        }
        status = "passed";
        System.out.println("The page " + driver.getCurrentUrl() + " has " + iBrokenImageCount + " broken images");
        driver.navigate().back();
    }

    @Test(description = "Checkbox test")
    public void checkboxtest(){
        driver.findElement(By.cssSelector("a[href=\"/checkboxes\"]")).click();
        driver.findElement(By.cssSelector("#checkboxes > input[type=checkbox]:nth-child(1)")).click();
        boolean ischecked = driver.findElement(By.cssSelector("#checkboxes > input[type=checkbox]:nth-child(1)")).isSelected();
        Assert.assertEquals(ischecked, true);
        driver.findElement(By.cssSelector("#checkboxes > input[type=checkbox]:nth-child(3)")).click();
        boolean ischecked2 = driver.findElement(By.cssSelector("#checkboxes > input[type=checkbox]:nth-child(3)")).isSelected();
        Assert.assertEquals(ischecked2, false);
        driver.navigate().back();
    }

    @Test(description = "Context menu test")
    public void contextmenutest(){
        driver.findElement(By.cssSelector("a[href=\"/context_menu\"]")).click();
        Actions actions = new Actions(driver);
        WebElement elementLocator = driver.findElement(By.cssSelector("#hot-spot"));
        actions.contextClick(elementLocator).perform();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        driver.navigate().back();
    }

    @Test(description = "Digest Authentication test")
    public void digestauthenticationtest(){
        driver.findElement(By.cssSelector("a[href=\"/digest_auth\"]")).click();
        driver.get("http://admin:admin@the-internet.herokuapp.com/digest_auth");
        String text = driver.findElement(By.cssSelector("p")).getText();
        Assert.assertEquals(text, "Congratulations! You must have the proper credentials.");
        driver.navigate().back();
    }

    @Test(description = "DragNdDrop")
    public void dragnddrop() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/drag_and_drop\"]")).click();
        WebElement elementA = driver.findElement(By.cssSelector("#column-a"));
        WebElement elementB = driver.findElement(By.cssSelector("#column-b"));
        int x=elementB.getLocation().getX();
        int y=elementB.getLocation().getY();
        Actions actions = new Actions(driver);
        actions.dragAndDrop(elementA, elementB).build().perform();
        driver.navigate().back();
    }

    @Test(description = "Dropdown test")
    public void dropdowntest(){
        driver.findElement(By.cssSelector("a[href=\"/dropdown\"]")).click();
        WebElement dropdown = driver.findElement(By.id("dropdown"));
        Select sc = new Select(dropdown);
        sc.selectByValue("2");
//        String isSelected = driver.findElement(By.id("dropdown")).getText();
//        System.out.println(isSelected);
        driver.navigate().back();
    }

    @Test(description = "Dynamic Controls test")
    public void dynmaicControlTest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/dynamic_controls\"]")).click();
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        Thread.sleep(5000);
        String text = driver.findElement(By.cssSelector("#message")).getText();
        Assert.assertEquals(text, "It's gone!");
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();
        Thread.sleep(5000);
        boolean isdisplayed = driver.findElement(By.cssSelector("#checkbox")).isDisplayed();
        Assert.assertEquals(isdisplayed, true);

        // Enable test
        driver.findElement(By.cssSelector("#input-example > button")).click();
        Thread.sleep(5000);
        boolean isenable = driver.findElement(By.cssSelector("#input-example > input[type=text]")).isEnabled();
        Assert.assertEquals(isenable, true);
        driver.findElement(By.cssSelector("#input-example > input[type=text]")).sendKeys("This is Vaibhav");
        driver.findElement(By.cssSelector("#input-example > button")).click();
        boolean isdisable = driver.findElement(By.cssSelector("#input-example > input[type=text]")).isEnabled();
        Assert.assertEquals(isdisable, true);
        driver.navigate().back();
    }
}
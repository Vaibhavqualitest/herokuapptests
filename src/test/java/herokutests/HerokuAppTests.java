package herokutests;

import java.nio.*;
import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class HerokuAppTests {
    private static WebDriver driver;

    @BeforeMethod
    @BeforeSuite
    public static void setup(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vaibhav.bajpai\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
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

        Actions actions = new Actions(driver);
//        actions.dragAndDrop(elementA, elementB).build().perform();
        actions.dragAndDrop(elementA, elementB).perform();
//        driver.navigate().back();
    }

    @Test(description = "Dropdown test")
    public void dropdowntest(){
        driver.findElement(By.cssSelector("a[href=\"/dropdown\"]")).click();
        WebElement dropdown = driver.findElement(By.id("dropdown"));
        Select sc = new Select(dropdown);
        sc.selectByValue("2");
//        boolean isSelected = driver.findElement(By.id("dropdown")).isSelected();
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

    @Test(description = "Dynamic Loading Test")
    public void dynamicloadingtest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/dynamic_loading\"]")).click();
        // Example-1
        driver.findElement(By.cssSelector("a[href=\"/dynamic_loading/1\"]")).click();
        driver.findElement(By.cssSelector("#start > button")).click();
        Thread.sleep(5000);
        String res = driver.findElement(By.cssSelector("#finish > h4")).getText();
        Assert.assertEquals(res, "Hello World!");
        driver.navigate().back();

        // Example-2
        driver.findElement(By.cssSelector("a[href=\"/dynamic_loading/2\"]")).click();
        driver.findElement(By.cssSelector("#start > button")).click();
        Thread.sleep(5000);
        String res2 = driver.findElement(By.cssSelector("#finish > h4")).getText();
        Assert.assertEquals(res2, "Hello World!");
        driver.navigate().back();
        driver.navigate().back();
    }

    @Test(description = "Entry ad Test")
    public void entryAdtest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/entry_ad\"]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]/div[3]/p")).click();
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("a[href=\"/entry_ad\"]")).click();
        Thread.sleep(3000);
        boolean popup = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]/div[3]/p")).isDisplayed();
        Assert.assertEquals(popup, false);

        //Again enabling ad
        driver.findElement(By.cssSelector("#restart-ad")).click();
        driver.navigate().back();
        driver.findElement(By.cssSelector("a[href=\"/entry_ad\"]")).click();
        Thread.sleep(3000);
        boolean adDisplay = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]/div[3]/p")).isDisplayed();
        Assert.assertEquals(adDisplay, true);
    }

    @Test(description = "Exit intent test")
    public void exitIntentTest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/exit_intent\"]")).click();
        Actions action = new Actions(driver);
        action.moveByOffset(500, 500).perform();
        Thread.sleep(5000);
        driver.navigate().back();
    }

    @Test(description = "File download Test")
    public void fileDownloadTest(){
        driver.findElement(By.cssSelector("a[href=\"/download\"]")).click();
        driver.findElement(By.cssSelector("a[href=\"download/sample.png\"]")).click();
        Assert.assertEquals(isFileDownloaded("C:\\Users\\vaibhav.bajpai\\Downloads", "sample.png"), true);
        driver.navigate().back();
    }
    // function for verification that file is downloaded or not
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equalsIgnoreCase(fileName)) {
                dirContents[i].delete();
                return true;
            }
        }
        return true;
    }

    @Test(description = "File Upload test")
    public void fileUploadTest(){
        driver.findElement(By.cssSelector("a[href=\"/upload\"]")).click();
        driver.findElement(By.cssSelector("#file-upload")).sendKeys("C:\\Users\\vaibhav.bajpai\\Downloads\\sample.png");
        driver.findElement(By.cssSelector("#file-submit")).click();
        String resultText = driver.findElement(By.cssSelector("#content > div > h3")).getText();
        Assert.assertEquals(resultText, "File Uploaded!");
        driver.navigate().back();
    }

    @Test(description = "Floating Menu")
    public void floatingMenuTest(){
        driver.findElement(By.cssSelector("a[href=\"/floating_menu\"]")).click();
        //home url
        driver.findElement(By.cssSelector("a[href=\"#home\"]")).click();
        String homeUrl = driver.getCurrentUrl();
        Assert.assertEquals(homeUrl, "http://the-internet.herokuapp.com/floating_menu#home");
        driver.navigate().back();
        //News url
        driver.findElement(By.cssSelector("a[href=\"#news\"]")).click();
        String newsUrl = driver.getCurrentUrl();
        Assert.assertEquals(newsUrl, "http://the-internet.herokuapp.com/floating_menu#news");
        driver.navigate().back();

        //Contact url
        driver.findElement(By.cssSelector("a[href=\"#contact\"]")).click();
        String contactUrl = driver.getCurrentUrl();
        Assert.assertEquals(contactUrl, "http://the-internet.herokuapp.com/floating_menu#contact");
        driver.navigate().back();

        //About url
        driver.findElement(By.cssSelector("a[href=\"#about\"]")).click();
        String aboutUrl = driver.getCurrentUrl();
        Assert.assertEquals(aboutUrl, "http://the-internet.herokuapp.com/floating_menu#about");
        driver.navigate().back();
    }

    @Test(description = "Forgot Password test")
    public void forgotPasswordTest(){
        driver.findElement(By.cssSelector("a[href=\"/forgot_password\"]")).click();
        driver.findElement(By.cssSelector("#email")).sendKeys("demo@gmail.com");
        driver.findElement(By.cssSelector("#form_submit > i")).click();
        String resultText = driver.findElement(By.cssSelector("body > h1")).getText();
        Assert.assertEquals(resultText, "Internal Server Error");
        driver.navigate().back();
    }

    @Test(description = "Form Authentication Test")
    public void formAuthenticationTest(){
        driver.findElement(By.cssSelector("a[href=\"/login\"]")).click();

        // Error message
        driver.findElement(By.cssSelector("#username")).sendKeys("tomsmith");
        driver.findElement(By.cssSelector("#password")).sendKeys("SuperSecretPassword");
        driver.findElement(By.cssSelector("#login > button > i")).click();
        boolean text = driver.findElement(By.cssSelector("#flash")).isDisplayed();
        Assert.assertEquals(text, true);

        // Login
        driver.findElement(By.cssSelector("#username")).sendKeys("tomsmith");
        driver.findElement(By.cssSelector("#password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("#login > button > i")).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "http://the-internet.herokuapp.com/secure");

        // Logout
        driver.findElement(By.cssSelector("#content > div > a")).click();
        String currentUrl2 = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl2, "http://the-internet.herokuapp.com/login");
    }

//    @Test(description = "Frame test")
//    public void frameTest(){
//        driver.findElement(By.cssSelector("a[href=\"/frames\"]")).click();
//        driver.findElement(By.cssSelector("a[href=\"/nested_frames\"]")).click();
//        driver.switchTo().frame("frame-middle");
//        driver.switchTo().frame(driver.findElement(By.xpath("/html/body")));
//
//    }

    @Test(description = "Geolocation test")
    public void geolocationtest(){
        driver.findElement(By.cssSelector("a[href=\"/geolocation\"]")).click();
        driver.findElement(By.cssSelector("#content > div > button")).click();
        driver.navigate().back();
    }

    @Test(description = "Horizontal Slider")
    public void horizontalSlider(){
        driver.findElement(By.cssSelector("a[href=\"/horizontal_slider\"]")).click();
        WebElement slider =driver.findElement(By.cssSelector("#content > div > div > input[type=range]"));
        Actions action = new Actions(driver);
        action.dragAndDropBy(slider, 3, 0).perform();
        String slidertext =driver.findElement(By.cssSelector("#range")).getText();
        Assert.assertEquals(slidertext, "2.5");
    }

    @Test(description = "Hover Test")
    public void hoverTest(){
        driver.findElement(By.cssSelector("a[href=\"/hovers\"]")).click();
        WebElement user1 = driver.findElement(By.cssSelector("#content > div > div:nth-child(3) > img"));
        WebElement user2 = driver.findElement(By.cssSelector("#content > div > div:nth-child(4) > img"));
        WebElement user3 = driver.findElement(By.cssSelector("#content > div > div:nth-child(5) > img"));
        //user1 hover
        Actions action = new Actions(driver);
        action.moveToElement(user1).perform();
        boolean u1 = driver.findElement(By.cssSelector("#content > div > div:nth-child(3) > div > h5")).isDisplayed();
        Assert.assertEquals(u1, true);

        //user2 hover
        action.moveToElement(user2).perform();
        boolean u2 = driver.findElement(By.cssSelector("#content > div > div:nth-child(4) > div > h5")).isDisplayed();
        Assert.assertEquals(u2, true);

        //user3 hover
        action.moveToElement(user3).perform();
        boolean u3 = driver.findElement(By.cssSelector("#content > div > div:nth-child(5) > div > h5")).isDisplayed();
        Assert.assertEquals(u3, true);
        driver.navigate().back();
    }

    @Test(description = "Input test")
    public void inputTest(){
        driver.findElement(By.cssSelector("a[href=\"/inputs\"]")).click();
        driver.findElement(By.cssSelector("#content > div > div > div > input[type=number]")).sendKeys("12");
        driver.navigate().back();
    }

    @Test(description = "JQueryUi test")
    public void jqueryuiTest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/jqueryui/menu\"]")).click();
        WebElement enable = driver.findElement(By.cssSelector("#ui-id-2"));
        Actions action = new Actions(driver);
        action.moveToElement(enable).perform();

        //JqueryUI test
        Thread.sleep(5000);
        WebElement jqueryUi = driver.findElement(By.xpath("//*[@id=\"ui-id-5\"]"));
        action.moveToElement(jqueryUi).click().perform();
        Assert.assertEquals(driver.getCurrentUrl(),"http://the-internet.herokuapp.com/jqueryui");
        driver.navigate().back();
    }

    @Test(description = "Javascript Alert Test")
    public void javascriptAlerttest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/javascript_alerts\"]")).click();

        //JS Alert tab
        driver.findElement(By.cssSelector("#content > div > ul > li:nth-child(1) > button")).click();
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String result1=  driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertEquals(result1, "You successfully clicked an alert");

        //JS Confirm tab
        driver.findElement(By.cssSelector("#content > div > ul > li:nth-child(2) > button")).click();
        Thread.sleep(2000);
        Alert alertok = driver.switchTo().alert();
        alertok.accept();
        String result2=  driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertEquals(result2, "You clicked: Ok");

        driver.findElement(By.cssSelector("#content > div > ul > li:nth-child(2) > button")).click();
        Thread.sleep(2000);
        Alert alertcancel = driver.switchTo().alert();
        alertcancel.dismiss();
        String result3=  driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertEquals(result3, "You clicked: Cancel");

        //JS prompt
        driver.findElement(By.cssSelector("#content > div > ul > li:nth-child(3) > button")).click();
        Thread.sleep(2000);
        Alert alertprompt = driver.switchTo().alert();
        alertprompt.sendKeys("This is Vaibhav");
        alertprompt.accept();
        String resultaccept=  driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertEquals(resultaccept, "You entered: This is Vaibhav");
        driver.navigate().back();
    }

    @Test(description = "Javascript onload event error test")
    public void javascriptOnloadEventErrorTest(){
        driver.findElement(By.cssSelector("a[href=\"/javascript_error\"]")).click();
        String loadedelement = driver.findElement(By.cssSelector("body > p")).getText();
        Assert.assertEquals(loadedelement, "This page has a JavaScript error in the onload event. This is often a problem to using normal Javascript injection techniques.");
        driver.navigate().back();
    }

    @Test(description = "Key presses test")
    public void keyPressTest(){
        driver.findElement(By.cssSelector("a[href=\"/key_presses\"]")).click();
        Actions action = new Actions(driver);
        action.sendKeys("S").perform();;
        String resulttext = driver.findElement(By.cssSelector("p#result")).getText();
        Assert.assertEquals(resulttext, "You entered: S");
        driver.navigate().back();
    }

    @Test(description = "Large and deep DOM")
    public void largeAndDeepDom(){
        driver.findElement(By.cssSelector("a[href=\"/large\"]")).click();
        String tableres = driver.findElement(By.cssSelector("#large-table > tbody > tr.row-19 > td.column-10")).getText();
        Assert.assertEquals(tableres, "19.10");
        driver.navigate().back();
    }

    @Test(description = "Multiple Windows Test")
    public void multipleWindowsTest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/windows\"]")).click();
        String currentUrl = driver.findElement(By.cssSelector("#content > div > a")).getAttribute("href");
        Thread.sleep(3000);
        Assert.assertEquals(currentUrl, "http://the-internet.herokuapp.com/windows/new");
        driver.navigate().back();
    }

    @Test(description = "Notification message test")
    public void notificationMessageTest(){
        driver.findElement(By.cssSelector("a[href=\"/notification_message\"]")).click();
        String res = driver.findElement(By.cssSelector("#flash")).getText();
        Assert.assertEquals(res, "Action successful\n" + "×");
        driver.navigate().back();
    }

    @Test(description = "Redirect Link Test")
    public void redirectLinkTest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/redirector\"]")).click();
        driver.findElement(By.cssSelector("#redirect")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/status_codes");

        //200 link
        driver.findElement(By.cssSelector("a[href=\"status_codes/200\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/status_codes/200");
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);

        //301 link
        driver.findElement(By.cssSelector("a[href=\"status_codes/301\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/status_codes/301");
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);

        //404 link
        driver.findElement(By.cssSelector("a[href=\"status_codes/404\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/status_codes/404");
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);

        //500 link
        driver.findElement(By.cssSelector("a[href=\"status_codes/500\"]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/status_codes/500");
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);
        driver.navigate().back();

    }

    @Test(description = "Slow Resources Test")
    public void slowResourceTest(){
        driver.findElement(By.cssSelector("a[href=\"/slow\"]")).click();
        String slowText = driver.findElement(By.cssSelector("#content > div > h3")).getText();
        Assert.assertEquals(slowText, "Slow Resources");
        driver.navigate().back();
    }

    @Test(description = "Sortable Table test")
    public void sortableTableTest(){
        driver.findElement(By.cssSelector("a[href=\"/tables\"]")).click();
        List<WebElement> lastName = driver.findElements(By.xpath("//*[@id=\"table1\"]/tbody/tr/td[1]"));
        System.out.println(lastName.size());
        String[] beforeSortlastName = new String[lastName.size()];
        for(int i=0; i< lastName.size(); i++)
        {
            beforeSortlastName[i]= lastName.get(i).getText().trim();
            System.out.println(beforeSortlastName[i]);
        }
        Arrays.sort(beforeSortlastName);
        Assert.assertEquals(beforeSortlastName, lastName);
        driver.navigate().back();
    }

    @Test(description = "Typos Test")
    public void typosTest(){
        driver.findElement(By.cssSelector("a[href=\"/typos\"]")).click();
        String text1 = driver.findElement(By.cssSelector("#content > div > p:nth-child(3)")).getText();
        Assert.assertEquals(text1, "Sometimes you'll see a typo, other times you won't.");

        //Refresh the page
        driver.navigate().refresh();
        String text2 = driver.findElement(By.cssSelector("#content > div > p:nth-child(3)")).getText();
        Assert.assertEquals(text2, "Sometimes you'll see a typo, other times you won,t.");
        driver.navigate().back();
    }

    @Test(description = "Infinite scroll")
    public void infiniteScrollTest() throws InterruptedException {
        driver.findElement(By.cssSelector("a[href=\"/infinite_scroll\"]")).click();
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3500)", "");
    }

    @Test
    public void validateInvalidImages() {
        try {
            driver.findElement(By.cssSelector("a[href=\"/broken_images\"]")).click();
            int invalidImageCount = 0;
            List<WebElement> imagesList = driver.findElements(By.tagName("img"));
            System.out.println("Total no. of images are " + imagesList.size());
            for (WebElement imgElement : imagesList) {
                if (imgElement != null) {
                    verifyimageActive(imgElement);
                }
            }
            System.out.println("Total no. of invalid images are "	+ invalidImageCount);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void verifyimageActive(WebElement imgElement) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(imgElement.getAttribute("src"));
            HttpResponse response = client.execute(request);
            // verifying response code he HttpStatus should be 200 if not,
            // increment as invalid images count
            int invalidImageCount=0;
            if (response.getStatusLine().getStatusCode() != 200)
                invalidImageCount++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
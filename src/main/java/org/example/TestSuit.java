package org.example;

import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.sql.Timestamp;
import java.time.Duration;


public class TestSuit {
    protected static WebDriver driver = new ChromeDriver();
   // static Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public static void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static void sendText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static void register1() {
        clickOnElement(By.className("ico-register"));
        driver.findElement(By.id("FirstName")).sendKeys("TestFirstName");
        driver.findElement(By.id("LastName")).sendKeys("TestLastName");
        driver.findElement(By.name("Email")).sendKeys("TestEmail@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Test1234");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("Test1234");
        clickOnElement(By.id("register-button"));
    }

    @AfterMethod
    public static void closeBrowser() {
        driver.close();
    }

    @BeforeMethod
    public static void openBrowser() {
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
    }

    @Test
    public static void toVerifyregisterationisSuccessfull() {
        String expectedResult = "Your Register comleted";
        //river.findElement(By.className("ico-register")).click();
        register1();//method calling
        //catch message
        String message =getTextFromElement(By.xpath("//div[@class=\"result\"]"));
        //print message
        System.out.println(message);
        //Assertion
        Assert.assertEquals(expectedResult, message, "Registered successful");
    }

    @Test
    public static void toVerifyOnlyRegisteredUserCanSendMail() {
        String expectedResult = "Only Registered user can SendMail";
        //select product
        clickOnElement(By.xpath("(//a[@href='/build-your-own-computer'])[2]"));
        //click on send email
        clickOnElement(By.xpath("(//button[@type=\"button\"])[4]"));
        // type friend's email
        sendText(By.className("friend-email"), "friends123@gmail.com");
        // type your email
        sendText(By.className("your-email"), "TestEmail@gmail.com");
        // click on send email
        clickOnElement(By.name("send-email"));
        //catch mesage
        String s = getTextFromElement(By.xpath("/html/body/div[6]/div[3]/div/div/div/div[2]/form/div[1]/ul/li"));
        System.out.println(s);
        Assert.assertEquals(expectedResult, s, "User can send Mail");
    }

    // 3]verify cart
    @Test
    public static void verifySameProductAddedIncart() {

        String expectedResult = "Item has been added";
        //click on electronics
        clickOnElement(By.xpath("(//a[@title='Show products in category Electronics'])[1]"));
        //click on product
        clickOnElement(By.xpath("(//a[@title='Show products in category Camera & photo'])[1]"));
//click on add to cart butoon
        clickOnElement(By.xpath("(//button[@class='button-2 product-box-add-to-cart-button'])[2]"));
        //click on shopping cart
        clickOnElement(By.className("cart-label"));

        // get name of product added in cart to match with selected product
        String name1 = getTextFromElement(By.className("product-name"));
        //Print message
        System.out.println(name1);
        //click on cart
        driver.findElement(By.className("cart-label")).click();
        //catch name of product added in cart
        String name = driver.findElement(By.className("product-name")).getText();
        //print name, if both are same then added product same in the cart
        System.out.println(name);
        Assert.assertEquals(expectedResult, name, "Item added");
    }

    //4] community poll
    @Test
    public static void communitypoll() {
        String expectedResult = "Only registered user can vote";
        //select vote
        clickOnElement(By.id("pollanswers-2"));
        //click on button
        clickOnElement(By.xpath("//button[@id='vote-poll-1']"));
        //stop for while
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='poll-vote-error']"))).getText();
        //display text
        System.out.println(msg);
        //Assertion
        Assert.assertEquals(msg, expectedResult, "Registered user can vote");
    }

    //5] compare product
    @Test
    public static void toVerifyUserCanCompareProduct() {
        String s = "You have no product to compare ";
        clickOnElement(By.xpath("(//button[@class='button-2 add-to-compare-list-button'])[3]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"bar-notification\"]/div/p/a")));
        //click on Add to compare list on $25 Virtual Gift Card
        driver.findElement(By.xpath("//img[@src='https://demo.nopcommerce.com/Themes/DefaultClean/Content/images/logo.png']")).click();
        clickOnElement(By.xpath("(//button[@class='button-2 add-to-compare-list-button'])[4]"));
        //click on product comparison list
        clickOnElement(By.xpath("//*[@id=\"bar-notification\"]/div/p/a"));
        //get text
        String name1 = driver.findElement(By.partialLinkText("$25 Virtual Gift Card")).getText();
        System.out.println("First Product is: " + name1);
        String name2 = driver.findElement(By.partialLinkText("HTC One M8 Android L 5.0 Lollipop")).getText();
        //get text
        System.out.println("Second Product is: " + name2);
        //click on clear list
        clickOnElement(By.className("clear-list"));
        //get text
        String message = getTextFromElement(By.className("no-data"));
        System.out.println(message);
        Assert.assertEquals(message, s, "Product name is Different");
    }
    //6] user vote
    @Test
    public static void registeredUserShouldBeAbleToVote() {
        String expectedresult = " Vote has been sent";
        register1();//mathod calling
        clickOnElement(By.className("ico-login"));
        //enter email
        sendText(By.id("Email"), "TestEmail@gmail.com");
        //add passwordd
        sendText(By.id("Password"), "Test1234");
        //click on login button
        clickOnElement(By.xpath("//button[@class=\"button-1 login-button\"]"));
        clickOnElement(By.id("pollanswers-4"));
        //click on vote
        clickOnElement(By.id("vote-poll-1"));
        String actualResult = getTextFromElement(By.className("poll-total-votes"));
        System.out.println(actualResult);;
        Assert.assertEquals(expectedresult,actualResult,"Message is not correct");
    }

    //7] refer product to friend
    @Test
    public static void referProductToFriend() {
        String expectedmsg = "Refere a product to friend";
        register1();//method calling
        //ckick on log in
        clickOnElement(By.className("ico-login"));
        //enter email
        sendText(By.id("Email"), "TestEmail@gmail.com");
        //add passwordd
        sendText(By.id("Password"), "Test1234");
        //click on log in
        clickOnElement(By.xpath("//button[@class=\"button-1 login-button\"]"));
        clickOnElement(By.xpath("(//a[@href='/build-your-own-computer'])[2]"));
        //click on email
        driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
        //type friend email id
        sendText(By.id("FriendEmail"), "abc@gmail.com");
        //type your mail
        // sendText(By.className("your-email"), "TestEmail@gmail.com");
        //click on send mail
        clickOnElement(By.name("send-email"));        //get msg
        String msg1 = getTextFromElement(By.className("result"));
        System.out.println(msg1);
        Assert.assertEquals(msg1, expectedmsg, "Product has been referred");

    }
}
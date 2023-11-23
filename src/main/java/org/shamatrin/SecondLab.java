package org.shamatrin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.time.Duration;

public class SecondLab {
    public ChromeDriver chromeDriver;
    String baseUrl = "https://www.olx.ua/uk/";

    @BeforeClass(alwaysRun = true)
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }
    @BeforeMethod
    public void preconditions()
    {
        chromeDriver.get(baseUrl);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown()
    {
        chromeDriver.quit();
    }
    @Test
    public void authClick()
    {
        WebElement catalog = chromeDriver.findElement(By.xpath("//div[@class=\"css-zs6l2q\"]"));
        catalog.click();
    }
    @Test
    public void findItem() {
        WebElement searchInput = chromeDriver.findElement(By.id("search"));
        if (searchInput != null) {
            if (searchInput.isDisplayed()) {
                searchInput.click();
                WebElement searchText = chromeDriver.findElement(By.xpath("//input[@id=\"search\"]"));
                searchText.sendKeys("Pixel 6a");
                WebElement searchButton = chromeDriver.findElement(By.className("css-coyrj2"));
                searchButton.click();
            }
        } else {
            System.out.println("Can't find search field on the page.");
        }
    }

    public void SetProperty ()
    {
        System.setProperty("webdriver.chrome.driver", "D:\\chromeDriver\\chromedriver.exe");
        chromeDriver = new ChromeDriver();
    }

    public void main (String[]args){
        SetProperty();
    }

}
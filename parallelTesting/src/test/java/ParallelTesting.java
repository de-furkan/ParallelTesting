import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParallelTesting {


    //> Artık her test senaryosunun kendi WebDriver örneği var
    //> Artık WebDriver'mız "Thread-Safe"
    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) {

        //> Switch deyimini burda kullanmak en iyisidir çünkü yalnızca tek bir değer bekler ("browser")
        switch (browser.toLowerCase()) {

            case "chrome":
                driver.set(new ChromeDriver());
                break;

            case "firefox":
                driver.set(new FirefoxDriver());
                break;

            case "edge":
                driver.set(new EdgeDriver());
                break;

            case "safari":
                driver.set(new SafariDriver());
                break;

            case "explorer":
                driver.set(new InternetExplorerDriver());
                break;

            default:
                throw new RuntimeException("Driver does not exist...");
        }

        //> Burada yine pencere boyutunu genişletiyoruz
        driver.get().manage().window().maximize();
    }

    @Test(priority = 1)
    public void TC01_assertGoogleAnaSayfaUrl() throws InterruptedException {

        driver.get().get("https://www.google.com");

        Thread.sleep(2000);

        Assert.assertEquals(driver.get().getCurrentUrl(), "https://www.google.com/");
    }

    @Test(priority = 2)
    public void TC02_assertAmazonAnaSayfaUrl() throws InterruptedException {

        driver.get().get("https://www.amazon.com");

        Thread.sleep(2000);

        Assert.assertEquals(driver.get().getCurrentUrl(), "https://www.amazon.com/");
    }

    @Test(priority = 3)
    public void TC03_assertYouTubeAnaSayfaUrl() throws InterruptedException {

        driver.get().get("https://www.youtube.com");

        Thread.sleep(2000);

        Assert.assertEquals(driver.get().getCurrentUrl(), "https://www.youtube.com/");
    }

    @Test(priority = 4)
    public void TC04_assertHerokuAnaSayfaUrl() throws InterruptedException {

        driver.get().get("https://the-internet.herokuapp.com");

        Thread.sleep(2000);

        Assert.assertEquals(driver.get().getCurrentUrl(), "https://the-internet.herokuapp.com/");
    }

    @AfterMethod
    public void tearDown() {

        driver.get().quit();
    }
}

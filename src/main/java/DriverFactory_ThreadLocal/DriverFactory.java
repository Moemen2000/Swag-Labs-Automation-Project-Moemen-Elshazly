package DriverFactory_ThreadLocal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setupDriverInitialization(String driver) {
        switch (driver.toLowerCase()) {
            case "chrome":
                ChromeOptions options1 = new ChromeOptions();
                options1.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(options1));
                break;
            case "firefox":
                FirefoxOptions options2 = new FirefoxOptions();
                options2.addArguments("--start-maximized");
                driverThreadLocal.set(new FirefoxDriver(options2));
                break;
            default:
                EdgeOptions options3 = new EdgeOptions();
                options3.addArguments("--start-maximized");
                driverThreadLocal.set(new EdgeDriver(options3));
        }
    }

    // getDriver instead of driver
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quiteDriver() {
        getDriver().quit();
        driverThreadLocal.remove();
    }

}

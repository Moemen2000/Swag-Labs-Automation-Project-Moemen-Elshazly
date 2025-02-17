package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utility {

    private static final String SCREENSHOTS_PATH = "test-outputs/ScreenShots";

    public static void click_On_Element(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public static void send_Data_To_Element(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }

    public static String get_Text_From_Element(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    public static String get_Attribute_From_Element(WebDriver driver, By locator, String attribute) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getDomAttribute(attribute);
    }

    public static WebDriverWait general_Wait_Of_Element(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void clear_Text_From_Element(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).clear();
    }

    public static void select_From_DropdownList_By_Index(WebDriver driver, By locator, int index) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Select(driver.findElement(locator)).selectByIndex(index);
    }

    public static void select_From_DropdownList_By_VisibleText(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Select(driver.findElement(locator)).selectByVisibleText(text);
    }

    public static void double_Click_On_Element(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Actions(driver).doubleClick(driver.findElement(locator)).perform();
    }

    public static void drag_And_Drop_Between_Two_Elements(WebDriver driver, By source, By destination) {
        new WebDriverWait(driver, Duration.ofSeconds(5));
        new Actions(driver).dragAndDrop(driver.findElement(source), driver.findElement(destination));
    }

    public static void accept_Alert(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.switchTo().alert().accept();
    }

    public static void dismiss_Alert(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.switchTo().alert().dismiss();
    }

    public static void sendKeys_To_Alert(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.switchTo().alert().sendKeys(text);
    }

    public static void click_ENTER_From_Keyboard(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(5));
        new Actions(driver).keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();
    }

    public static void click_TAB_From_Keyboard(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(5));
        new Actions(driver).keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
    }

    public static String get_Text_From_Alert(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.switchTo().alert().getText();
    }

    public static void scrolling_Using_Js(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollItoView();", driver.findElement(locator));
    }

    public static String get_TimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-hh-mm-ssa").format(new Date());
    }

    public static void taking_Screenshot_For_Driver(WebDriver driver, String screenName) {
        try {
            // Capture the ScreenShot
            File screenshot_Src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Sava the file as a Picture(.png) and add the Screen name
            // get_TimeStamp is a function to add date and time to ScreenShot name
            File screenshot_Dest = new File(SCREENSHOTS_PATH +"\\"+ screenName + "-" + get_TimeStamp() + ".png");
            FileUtils.copyFile(screenshot_Src, screenshot_Dest);

            // Attach the Screenshot to Allure
            Allure.addAttachment(screenName, Files.newInputStream(Path.of(screenshot_Dest.getPath())));
        } catch (Exception exception) {
            LogUtils.error_Log(exception.getMessage());
        }
    }

    public static void switch_To_Another_Tab(WebDriver driver) {
        Set<String> windowHandles = driver.getWindowHandles();
        String currentHandle = driver.getWindowHandle();

        // Switch to the second tab
        for (String handle : windowHandles) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
            }
        }
    }

    public static void upload_Files_Using_RobotClass(WebDriver driver, By locator, String filePath) {
        try {
            // Click on WebElement
            click_On_Element(driver, locator);

            // Take the path copy (Ctrl+c)
            StringSelection stringSelection = new StringSelection(filePath);

            // put the path into Clipboard
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(stringSelection, null);

            // Create Robot
            Robot robot = new Robot();
            robot.delay(3000);

            // Click (Enter) on the window that will be appeared
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(3000);

            // Paste the path (Ctrl+v)
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(3000);

            // Click (Enter)
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception exception) {
            LogUtils.error_Log(exception.getMessage());
        }
    }

    // Helper method to compare images
    public static boolean compare_Images(BufferedImage actualImage, BufferedImage expectedImage) {

        // Check if the dimensions are the same
        if (actualImage.getWidth() != expectedImage.getWidth() || actualImage.getHeight() != expectedImage.getHeight()) {
            return false;
        }

        // Compare each pixel
        for (int i = 0; i < actualImage.getHeight(); i++) {
            for (int a = 0; a < actualImage.getWidth(); a++) {
                if (actualImage.getRGB(a, i) != expectedImage.getRGB(a, i)) {
                    return false; // Pixels are different
                }
            }
        }
        return true;
    }

    private static boolean imagesMatching ;
    public static boolean imageComparison(WebDriver driver, By profilePhotoLocator, String uploadedImagePath) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(profilePhotoLocator));

            // Get the image URL from the `src` attribute of the <img> tag
            String currentImageUrl = driver.findElement(profilePhotoLocator).getAttribute("src");

            // Load the Actual image from the website
            BufferedImage actualImage = ImageIO.read(new URL(currentImageUrl));

            // Load the Expected image from your local file system
            BufferedImage expectedImage = ImageIO.read(new File(uploadedImagePath));
            imagesMatching = compare_Images(actualImage, expectedImage);

        }
        catch (Exception exception)
        {
            LogUtils.error_Log(exception.getMessage());
        }
        return imagesMatching ;
    }

    public static void taking_Full_Screenshot(WebDriver driver, String screenshotName){
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .save(SCREENSHOTS_PATH);
            String screenshot_Location = SCREENSHOTS_PATH +"\\"+ screenshotName + ".png";

            Allure.addAttachment(screenshotName,Files.newInputStream(Paths.get(screenshot_Location)));
        }
        catch (Exception exception)
        {
            LogUtils.error_Log(exception.getMessage());
        }

    }
    // لتوليد رقم عشوائي بين 1 والحد الأعلى المُعطى
    // ال Bound أو الحدود مش معانا
    public static int generateRandomNumber(int upperBound)  //0 >> upper-1
    {
        return new Random().nextInt(upperBound) + 1;
    }

    // الرقم اللي اختير عشوائي ممكن يكون متكرر عادي عشان كده هنستخدم set
    public static Set<Integer> generateUniqueNumber(int numberOfProductsNeeded, int totalNumberOfProducts) //5 >> 50
    {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded) {
            int randomNumber = generateRandomNumber(totalNumberOfProducts);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
    }

}

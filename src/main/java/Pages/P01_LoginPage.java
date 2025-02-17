package Pages;

import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class P01_LoginPage {
    private WebDriver driver;

    private final By userNameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");


    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean assertLoginTcCondition(String expectedValue) {
        Utility.general_Wait_Of_Element(driver);
        return driver.getCurrentUrl().equals(expectedValue);
    }

    public String assertForReadingMessageCondition() {
        Utility.general_Wait_Of_Element(driver).until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return Utility.get_Text_From_Element(driver,errorMessage);
    }

    public boolean assertForDisplayedMessageCondition() {
        try {
            Utility.general_Wait_Of_Element(driver);
            return driver.findElement(errorMessage).isDisplayed();
        }
        catch (Exception exception)
        {
            LogUtils.info_Log(exception.getMessage());
            return false;
        }
    }

    public String assertForGetAttribute(String attribute){
        return Utility.get_Attribute_From_Element(driver,passwordField,attribute);
    }

    public P01_LoginPage enterUsername(String usernameText) {
        Utility.send_Data_To_Element(driver, userNameField, usernameText);
        return this;
    }

    public P01_LoginPage enterPassword(String passwordText) {
        Utility.send_Data_To_Element(driver, passwordField, passwordText);
        return this;
    }

    public P02_ProductsPage clickLogin() {
        Utility.click_On_Element(driver, loginButton);
        return new P02_ProductsPage(driver);
    }

    public P02_ProductsPage clickENTER(){
        Utility.click_ENTER_From_Keyboard(driver);
        return new P02_ProductsPage(driver);
    }

}
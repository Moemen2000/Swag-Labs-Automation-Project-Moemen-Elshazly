package Tests;


import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestListenerClass;
import Pages.P01_LoginPage;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static DriverFactory_ThreadLocal.DriverFactory.*;
import static Utilities.DataUtils.get_Data_From_JsonFile;
import static Utilities.Utility.general_Wait_Of_Element;

@Listeners({IInvokedMethodListenerClass.class,ITestListenerClass.class})
public class TCs_LoginPage {
    private final String VALID_USERNAME = get_Data_From_JsonFile("Environments","VALID_USERNAME");
    private final String VALID_PASSWORD = get_Data_From_JsonFile("Environments","VALID_PASSWORD");
    private final String INVALID_USERNAME = get_Data_From_JsonFile("Environments","INVALID_USERNAME");
    private final String INVALID_PASSWORD = get_Data_From_JsonFile("Environments","INVALID_PASSWORD");

    @BeforeMethod
    public void setupDriver() {
        setupDriverInitialization(get_Data_From_JsonFile("Environments","DRIVER"));
        getDriver().get(get_Data_From_JsonFile("Environments","BASE_URL"));
        general_Wait_Of_Element(getDriver());
    }


    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.CRITICAL)
    public void validLogin_TC001(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTcCondition(get_Data_From_JsonFile("Environments","PRODUCTS_PAGE")));
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void inValidLogin_TC002(){
        new P01_LoginPage(getDriver())
                .enterUsername(INVALID_USERNAME)
                .enterPassword(INVALID_PASSWORD)
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidUsernameAndValidPassword_TC003(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(INVALID_PASSWORD)
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void loginWithValidUsernameAndInvalidPassword_TC004(){
        new P01_LoginPage(getDriver())
                .enterUsername(INVALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.CRITICAL)
    public void loginWithLockedOutUsername_TC005(){
        new P01_LoginPage(getDriver())
                .enterUsername(get_Data_From_JsonFile("Environments","LockedOut_Username"))
                .enterPassword(VALID_PASSWORD)
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyUsernameAndPassword_TC006(){
        new P01_LoginPage(getDriver())
                .enterUsername("")
                .enterPassword("")
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username is required");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyPassword_TC007(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword("")
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Password is required");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void verifyPasswordFieldIsSecureAndMasked_TC008(){
        new P01_LoginPage(getDriver())
                .enterPassword(VALID_PASSWORD);
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForGetAttribute("type"),"password");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void loginWithUpperUsernameAndPassword_TC009(){
        new P01_LoginPage(getDriver())
                .enterUsername("STANDARD_USER")
                .enterPassword("SECRET_SAUCE")
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.MINOR)
    public void pressingEnterAfterEnteringUsernameAndPassword_TC010(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickENTER();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTcCondition(get_Data_From_JsonFile("Environments","PRODUCTS_PAGE")));
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.CRITICAL)
    public void userRemainsLoggedInAfterRefreshProductsPage_TC011(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickENTER()
                .refreshPage();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTcCondition(get_Data_From_JsonFile("Environments","PRODUCTS_PAGE")));
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.MINOR)
    public void errorMessageDisappearsAfterRefreshingLoginPage_TC012(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(INVALID_PASSWORD )
                .clickENTER()
                .refreshPage();
        Assert.assertFalse(new P01_LoginPage(getDriver()).assertForDisplayedMessageCondition());
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.NORMAL)
    public void loginWithSpacesBeforeCredentials_TC013() {
        new P01_LoginPage(getDriver())
                .enterUsername(" standard_user")
                .enterPassword(VALID_PASSWORD)
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(), "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.CRITICAL)
    public void loginWith_SQLInjection_Attack_TC014(){
        new P01_LoginPage(getDriver())
                .enterUsername("admin' --")
                .enterPassword("anything")
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    @Owner("Moemen_ElShazly")   @Severity(SeverityLevel.CRITICAL)
    public void  loginWith_XSS_Attack_TC015(){
        new P01_LoginPage(getDriver())
                .enterUsername("<script>alert(\"Hacked\")</script>")
                .enterPassword("<script>alert(\"Hacked\")</script>")
                .clickLogin();
        Assert.assertEquals(new P01_LoginPage(getDriver()).assertForReadingMessageCondition(),"Epic sadface: Username and password do not match any user in this service");
    }



    @AfterMethod
    public void exitDriver() {
        quiteDriver();
    }


}

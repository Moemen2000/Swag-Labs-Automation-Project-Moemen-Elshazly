package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static DriverFactory_ThreadLocal.DriverFactory.*;
import static Utilities.DataUtils.get_Data_From_JsonFile;
import static Utilities.Utility.general_Wait_Of_Element;

@Listeners({IInvokedMethodListenerClass.class, ITestListenerClass.class})
public class TCs_CartIconPage {

    private final String VALID_USERNAME = get_Data_From_JsonFile("Environments","VALID_USERNAME");
    private final String VALID_PASSWORD = get_Data_From_JsonFile("Environments","VALID_PASSWORD");

    @BeforeMethod
    public void setupDriver() {
        setupDriverInitialization(get_Data_From_JsonFile("Environments","DRIVER"));
        getDriver().get(get_Data_From_JsonFile("Environments","BASE_URL"));
        general_Wait_Of_Element(getDriver());
    }

    @Test
    public void checkingNumberOfSelectedProducts_TC016() {
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .addAllProductsToCart();
        Assert.assertTrue(new P02_ProductsPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @AfterMethod
    public void exitDriver() {
        quiteDriver();
    }

}

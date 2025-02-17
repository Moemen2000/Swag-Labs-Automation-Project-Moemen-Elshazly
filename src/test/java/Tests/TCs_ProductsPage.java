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
public class TCs_ProductsPage {
    private final String VALID_USERNAME = get_Data_From_JsonFile("Environments","VALID_USERNAME");
    private final String VALID_PASSWORD = get_Data_From_JsonFile("Environments","VALID_PASSWORD");

    @BeforeMethod
    public void setupDriver() {
        setupDriverInitialization(get_Data_From_JsonFile("Environments","DRIVER"));
        getDriver().get(get_Data_From_JsonFile("Environments","BASE_URL"));
        general_Wait_Of_Element(getDriver());
    }

    @Test
    public void checkingNumberOfSelectedProducts_TC016(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .addAllProductsToCart();
        Assert.assertTrue(new P02_ProductsPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void addingRandomProductsToCart_TC017(){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .addRandomProducts(3,6);
        Assert.assertTrue(new P02_ProductsPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartIcon_TC018 (){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .clickOnCartIcon();
        Assert.assertTrue(new P02_ProductsPage(getDriver()).assertCurrentPageCondition(get_Data_From_JsonFile("Environments","CART_ICON_PAGE")));
    }

    @Test
    public void verifyProductSortingByName_TC019 (){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .clickOnDropdownAndSelectOption(get_Data_From_JsonFile("Environments","VisibleText2"));
        Assert.assertTrue(new P02_ProductsPage(getDriver()).ComparingProductSortingByName(get_Data_From_JsonFile("Environments","VisibleText2")));
    }

    @Test
    public void verifyProductSortingByPrice_TC020 (){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .clickOnDropdownAndSelectOption(get_Data_From_JsonFile("Environments","VisibleText3"));
        Assert.assertTrue(new P02_ProductsPage(getDriver()).ComparingProductSortingByPrice(get_Data_From_JsonFile("Environments","VisibleText3")));
    }

    @Test
    public void verifyOpenSideMenu_TC021 (){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .clickOnSideMenuButton();
        Assert.assertTrue(new P02_ProductsPage(getDriver()).verifySideMenuIsDisplayed());
    }

    @Test
    public void verifyCloseSideMenu_TC021 (){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .clickOnSideMenuButton()
                .clickOnCloseSideMenu();
        Assert.assertFalse(new P02_ProductsPage(getDriver()).verifySideMenuIsDisplayed());
    }

    @Test
    public void verifySuccessfulLogout_TC022 (){
        new P01_LoginPage(getDriver())
                .enterUsername(VALID_USERNAME)
                .enterPassword(VALID_PASSWORD)
                .clickLogin()
                .clickOnSideMenuButton()
                .clickOnLogOutButton();
        Assert.assertTrue(new P02_ProductsPage(getDriver()).assertCurrentPageCondition(get_Data_From_JsonFile("Environments","BASE_URL")));
    }


    @AfterMethod
    public void exitDriver() {
        quiteDriver();
    }

}

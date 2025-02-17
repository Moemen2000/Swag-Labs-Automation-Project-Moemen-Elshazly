package Pages;

import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class P02_ProductsPage {
    private final WebDriver driver;

    public P02_ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By addToCartButtonForAllProducts = By.xpath("(//button[@class])");
    private final By numberOfProductsOnCartIcon = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    private final By numberOfSelectedProductsOnly = By.xpath("//button[text()='REMOVE']");
    private final By cartIcon = By.xpath("//a[contains(@class,'shopping_cart_link')]");
    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");
    private final By dropdownSorting = By.className("product_sort_container");
    private final By productsNameDynamicLocator = By.xpath("(//div[@class='inventory_item_name'])");
    private final By productsPriceDynamicLocator = By.xpath("(//div[@class='inventory_item_price'])");
    private final By openSideMenuButton = By.className("bm-burger-button");
    private final By sideMenu = By.className("bm-menu-wrap");
    private final By closeSideMenuButton = By.cssSelector("div.bm-cross-button > button");
    private final By logOutButton = By.id("logout_sidebar_link");


    public P02_ProductsPage refreshPage(){
        driver.navigate().refresh();
        return new P02_ProductsPage(driver);
    }

    public P02_ProductsPage addAllProductsToCart(){
        List<WebElement> allProducts = driver.findElements(addToCartButtonForAllProducts); // Selected and Unselected Products (All of them)
        LogUtils.info_Log("Number of all products is : " + allProducts.size());
        for (int i=1 ; i<= allProducts.size() ; i++)
        {
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])["+ i + "]"); //Dynamic Locator
            Utility.click_On_Element(driver,addToCartButtonForAllProducts);
        }
        return new P02_ProductsPage(driver);
    }

    public String getNumberOfProductsOnCardIcon(){
        try {
            return Utility.get_Text_From_Element(driver,numberOfProductsOnCartIcon);
        }
        catch (Exception exception)
        {
            LogUtils.error_Log(exception.getMessage());
            return "0";
        }
    }

    public String getNumberOfSelectedProducts(){
        try {
            List<WebElement> onlySelectedProducts = driver.findElements(numberOfSelectedProductsOnly); // Selected Products Only
            LogUtils.info_Log("Number of selected products is : " + onlySelectedProducts.size());
            return String.valueOf(onlySelectedProducts.size());
        }
        catch (Exception exception)
        {
            LogUtils.error_Log(exception.getMessage());
            return "0";
        }
    }

    // إضافة عدد عشوائي من المنتجات إلى سلة التسوق
    //numberOfProductsNeeded: عدد المنتجات المطلوب إضافتها عشوائيًا
    //totalNumberOfProducts: العدد الكلي للمنتجات المتاحة
    public P02_ProductsPage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumber(numberOfProductsNeeded, totalNumberOfProducts);

        for (int random : randomNumbers) {
            LogUtils.info_Log("randomNumber " + random);
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]"); //dynamic Locator

            Utility.click_On_Element(driver, addToCartButtonForAllProducts);
        }
        return this;
    }

    public boolean comparingNumberOfSelectedProductsWithCart(){
        return getNumberOfSelectedProducts().equals(getNumberOfProductsOnCardIcon());
    }

    public P03_CartIconPage clickOnCartIcon(){
        Utility.click_On_Element(driver,cartIcon);
        return new P03_CartIconPage(driver);
    }

    public boolean assertCurrentPageCondition(String expectedURL) {
        Utility.general_Wait_Of_Element(driver).until(ExpectedConditions.urlToBe(expectedURL));
        return driver.getCurrentUrl().equals(expectedURL);
    }

    private float totalPrice = 0;
    public String getTotalPriceOfSelectedProducts(){
        try
        {
            List<WebElement> priceOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for (int i = 1; i<= priceOfSelectedProducts.size(); i++) {
                LogUtils.info_Log("price of selected products " + priceOfSelectedProducts);
                By pricesOfSelectedProductsLocator = By.xpath("(//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");

                String x = Utility.get_Text_From_Element(driver,pricesOfSelectedProductsLocator);
                float y = Float.parseFloat(x.replace("$",""));
                totalPrice += y;
            }
            LogUtils.info_Log("Total Price is " + totalPrice);
            return String.valueOf(totalPrice);
        }
        catch (Exception exception)
        {
            LogUtils.error_Log(exception.getMessage());
            return "0";
        }
    }

    public P02_ProductsPage clickOnDropdownAndSelectOption(String visibleText){
        new Select(driver.findElement(dropdownSorting)).selectByVisibleText(visibleText);
        return this;
    }

    public boolean ComparingProductSortingByName(String visibleText) {
        // Get all product names
        List<WebElement> productsNamesFromPage =driver.findElements(productsNameDynamicLocator);
        Utility.general_Wait_Of_Element(driver).until(ExpectedConditions.presenceOfAllElementsLocatedBy(productsNameDynamicLocator));
        List<String> productNames = new ArrayList<>();

        for (int i=1;i<= productsNamesFromPage.size();i++) {
            By productNameForOnei = By.xpath("(//div[@class='inventory_item_name'])[" +i+ "]");
            productNames.add(driver.findElement(productNameForOnei).getText());
        }

        // Create a sorted copy of the product names
        List<String> sortedProductNames = new ArrayList<>(productNames);

        switch (visibleText.toLowerCase())
        {
            case "name (a to z)":
                Collections.sort(sortedProductNames);
                LogUtils.info_Log("Sorted Product names From A to Z "+sortedProductNames);
                break;
            case "name (z to a)":
                sortedProductNames.sort(Collections.reverseOrder());
                LogUtils.info_Log("Sorted Product names From Z to A "+sortedProductNames);
                break;
        }

        if (productNames.equals(sortedProductNames)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ComparingProductSortingByPrice(String visibleText) {
        // Get all product Prices
        List<WebElement> productsPricesFromPage =driver.findElements(productsPriceDynamicLocator);
        Utility.general_Wait_Of_Element(driver).until(ExpectedConditions.presenceOfAllElementsLocatedBy(productsPriceDynamicLocator));
        List<Double> productPrices = new ArrayList<>();

        for (int i=1;i<= productsPricesFromPage.size();i++) {
            By productPriceForOnei = By.xpath("(//div[@class='inventory_item_price'])[" +i+ "]");
            String x = Utility.get_Text_From_Element(driver,productPriceForOnei);
            Double y = Double.parseDouble(x.replace("$",""));
            productPrices.add(y);
        }

        // Create a sorted copy of the product Prices
        List<Double> sortedProductPrices = new ArrayList<>(productPrices);

        switch (visibleText.toLowerCase())
        {
            case "price (low to high)":
                Collections.sort(sortedProductPrices);
                LogUtils.info_Log("Sorted Product Price From (low to high) "+sortedProductPrices);
                break;
            case "price (high to low)":
                sortedProductPrices.sort(Collections.reverseOrder());
                LogUtils.info_Log("Sorted Product Price From (high to low) "+sortedProductPrices);
                break;
        }

        if (productPrices.equals(sortedProductPrices)) {
            return true;
        } else {
            return false;
        }
    }

    public P02_ProductsPage clickOnSideMenuButton (){
        Utility.click_On_Element(driver, openSideMenuButton);
        return this;
    }

    public P02_ProductsPage clickOnCloseSideMenu(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(closeSideMenuButton));
        return this;
    }

    public boolean verifySideMenuIsDisplayed(){
        try
        {
            Utility.click_On_Element(driver,sideMenu);
            return driver.findElement(sideMenu).isDisplayed();
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public P02_ProductsPage clickOnLogOutButton (){
        Utility.click_On_Element(driver, logOutButton);
        return this;
    }


}

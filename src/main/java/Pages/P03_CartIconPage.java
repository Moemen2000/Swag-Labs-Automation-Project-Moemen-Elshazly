package Pages;

import Utilities.LogUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartIconPage {
    private final WebDriver driver;

    public P03_CartIconPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price']");


    private float totalPrice = 0;

    public String totalPriceInCartPage() {
        try
        {
            List<WebElement> priceOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for (int i = 1; i <= priceOfSelectedProducts.size(); i++) {
                LogUtils.info_Log("price of selected products " + priceOfSelectedProducts);
                By pricesOfSelectedProductsLocator = By.xpath("(//button[.='REMOVE']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");

                String x = Utility.get_Text_From_Element(driver, pricesOfSelectedProductsLocator);
                float y = Float.parseFloat(x.replace("$", ""));
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

    public boolean comparingPriceCondition(String price){
        return totalPriceInCartPage().equals(price);
    }
}

package com.workfusion.assign.pages;

import com.workfusion.automation.rpa.driver.DriverWrapper;
import org.openqa.selenium.By;

public class MainPage extends BasePage{

    String addressExp = "//*[@id=\"header\"]/div[2]/div/div[1]/div[3]/ul/li[4]/a";

    public MainPage(DriverWrapper driver) {
        super(driver);
    }

    public StoresPage switchToStores() {
        String storesUrl = driver.findElement(By.xpath(addressExp)).getAttribute("href");
        driver.getDriver().get(storesUrl);
        //driver.findElement(By.xpath(addressExp)).click();
        return new StoresPage(driver);
    }

}

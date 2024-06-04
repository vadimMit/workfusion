package com.workfusion.assign.pages;

import com.google.gson.annotations.SerializedName;
import com.workfusion.assign.dto.LocationDto;
import com.workfusion.automation.rpa.driver.DriverWrapper;
import com.workfusion.rpa.helpers.RPA;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.workfusion.rpa.helpers.RPA.downloadFileOnAgent;

public class StoresPage extends BasePage  {
    private static final Logger log = LoggerFactory.getLogger(StoresPage.class);

    public StoresPage(DriverWrapper driver) {
        super(driver);
    }

    public List<Set<String>> getLocations() {
        List<WebElement> statesEles = driver.findElements(By.xpath("//*[@id=\"js-locator\"]/div[3]/div[3]/div/div/section/div/ul"));
        List<String> storesInStatesLinks = new ArrayList<>();
        int totalStores = 0;
        for (int i = 1; i <= statesEles.size(); i++) {
            String expressionRef = String.format("//*[@id=\"js-locator\"]/div[3]/div[3]/div/div/section/div/ul/li[%d]/a", i);
            String expressionStoresCount = String.format("//*[@id=\"js-locator\"]/div[3]/div[3]/div/div/section/div/ul/li[%d]/a/span", i);
            storesInStatesLinks.add(driver.findElement(By.xpath(expressionRef)).getAttribute("href"));
            String storesInState = driver.findElement(By.xpath(expressionStoresCount)).getAttribute("data-count");
            totalStores += Integer.parseInt(storesInState.replace("(", "").replace(")", ""));
            if (totalStores >= 10) {
                break;
            }
        }
        List<String> storesLinks = getStoresLinks(storesInStatesLinks);
        return getLocationsInfo(storesLinks);
    }

    private List<Set<String>> getLocationsInfo(List<String> storesLinks) {
        String city = "";
        String state = "";
        String locationName = "";
        String address = "";
        String phone = "";
        String linkToDirection = "";
        Integer id = 1;
        List<LocationDto> locationDtos = new ArrayList<>();
        List<Set<String>> tabs = new ArrayList<>();
        tabs.add(driver.getWindowHandles());
        int totalTabs = tabs.size();
        int newTabIndex = totalTabs - 1;
        int totalStores = 0;
        driver.switchToWindow(tabs.toArray()[newTabIndex].toString());
        for (String storesLink : storesLinks) {
            driver.getDriver().get(storesLink);
            tabs.add(driver.getWindowHandles());
            try {
                List<WebElement> storesEles = driver.findElements(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li"));
                for (int i = 1; i <= storesEles.size(); i++) {
                    locationName = driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/a[1]")).getText();
                    city = driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/div[2]/address/div[2]/span[1]/text()")).getText();
                    state = driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/div[2]/address/div[2]/abbr")).getAttribute("title");
                    address = driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/div[2]/address/div[1]/span")).getText() + " " + driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/div[2]/address/div[2]/span[2]")).getText();
                    phone = driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/div[3]/span[1]/span")).getText();
                    linkToDirection = driver.findElement(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li[1]/div/a[2]")).getAttribute("href");
                    LocationDto locationDto = new LocationDto(locationName, city, state, address, phone, linkToDirection, id);
                    id++;
                    locationDtos.add(locationDto);
                    totalStores++;
                    if (totalStores == 10) {
                        break;
                    }
                }
            } catch (Exception ex) {
                try {
                    locationName = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[2]/div[1]/h1/span[2]")).getText();
                } catch (Exception exc) {
                    locationName = "No name of store on site";
                }
                try {
                    address = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[3]/div[1]/div[2]/div/address/div/span[1]")).getText() + " " + driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[3]/div[1]/div[2]/div/address/div/span[3]")).getText();
                } catch (Exception exc) {
                    address = "No address of store on site";
                }
                try {
                    city = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[3]/div[1]/div[2]/div/address/div/span[2]")).getText().replace(",", "");
                } catch (Exception exc) {
                    city = "No city of store on site";
                }
                try {
                    state = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[3]/div[1]/div[2]/div/address/div/abbr")).getAttribute("title");
                } catch (Exception exc) {
                    state = "No state of store on site";
                }
                try {
                    phone = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[3]/div[1]/div[3]/div/span[1]/span")).getText();
                } catch (Exception exc) {
                    phone = "No state of store on site";
                }
                try {
                    linkToDirection = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[4]/div[3]/div[1]/a")).getAttribute("href");
                } catch (Exception exc) {
                    linkToDirection = "No direction of store on site";
                }

                LocationDto locationDto = new LocationDto(locationName, city, state, address, phone, linkToDirection, id);
                id++;
                locationDtos.add(locationDto);
                totalStores++;
                if (totalStores == 10) {
                    break;
                }
            }
        }
        return tabs;
    }

    private List<String> getStoresLinks(List<String> storesInStatesLinks) {
        List<String> storesLinks = new ArrayList<>();
        Set<String> tabs = driver.getWindowHandles();
        int totalTabs = tabs.size();
        int newTabIndex = totalTabs - 1;
        int totalStores = 0;
        driver.switchToWindow(tabs.toArray()[newTabIndex].toString());
        for (int i = 0; i < storesInStatesLinks.size(); i++) {
            driver.getDriver().get(storesInStatesLinks.get(i));
            List<WebElement> storesEles = driver.findElements(By.xpath("//*[@id=\"main\"]/div/section/div/ul/li"));

            for (int j = 1; j <= storesEles.size(); j++) {
                String expressionRef = String.format("//*[@id=\"main\"]/div/section/div/ul/li[%d]/a", j);
                String expressionStoresCount = String.format("//*[@id=\"main\"]/div/section/div/ul/li[%d]/a/span", j);
                storesLinks.add(driver.findElement(By.xpath(expressionRef)).getAttribute("href"));
                String storesInCity = driver.findElement(By.xpath(expressionStoresCount)).getAttribute("data-count");
                totalStores += Integer.parseInt(storesInCity.replace("(", "").replace(")", ""));
                if (totalStores >= 10) {
                    break;
                }
            }
            if (totalStores >= 10) {
                break;
            }
        }

        return storesLinks;
    }


}
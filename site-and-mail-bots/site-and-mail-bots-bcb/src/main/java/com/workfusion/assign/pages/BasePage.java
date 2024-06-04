package com.workfusion.assign.pages;

import com.workfusion.automation.rpa.driver.DriverWrapper;
import com.workfusion.automation.rpa.pages.AbstractPage;

public class BasePage extends AbstractPage {

    public BasePage(DriverWrapper driver) {
        super(driver);
        //Wait for document['readyState'] equals to 'complete' before return a page instance
        waitForWebPageToLoad();
    }

    public DriverWrapper getWrapper() {
        return this.driver;
    }
}

package com.workfusion.assign.client;

import com.workfusion.assign.pages.MainPage;
import com.workfusion.automation.rpa.driver.DriverType;
import com.workfusion.automation.rpa.driver.DriverWrapper;
import com.workfusion.automation.rpa.driver.RobotDriverWrapper;
import com.workfusion.rpa.helpers.RPA;
import org.slf4j.Logger;


public class FashionClient {
    private final DriverWrapper driverWrapper;
    private final String CATOFASHIONS_URL ="https://www.catofashions.com/";
    public FashionClient(Logger logger) {
        this.driverWrapper = new RobotDriverWrapper(logger);
    }

    public MainPage getMainPage(){
        this.driverWrapper.switchDriver(DriverType.CHROME);
        RPA.openChrome(CATOFASHIONS_URL);
        return new MainPage(driverWrapper);
    }
}

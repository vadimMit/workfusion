package com.workfusion.assign.task;

import com.workfusion.assign.client.FashionClient;
import com.workfusion.assign.dto.LocationDto;
import com.workfusion.assign.pages.MainPage;
import com.workfusion.assign.pages.StoresPage;
import com.workfusion.odf2.compiler.BotTask;
import com.workfusion.odf2.core.cdi.Requires;
import com.workfusion.odf2.core.task.AdHocTask;
import com.workfusion.odf2.core.task.TaskInput;
import com.workfusion.odf2.core.task.output.MultipleResults;
import com.workfusion.odf2.core.task.output.TaskRunnerOutput;
import com.workfusion.odf2.core.webharvest.rpa.RpaDriver;
import com.workfusion.odf2.core.webharvest.rpa.RpaFactory;
import com.workfusion.odf2.core.webharvest.rpa.RpaRunner;
import com.workfusion.odf2.service.ControlTowerServicesModule;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@BotTask(requireRpa = true)
@Requires({ControlTowerServicesModule.class})
public class CatofashionsLocationsTask implements AdHocTask {

    private final RpaRunner rpaRunner;
    private final Logger logger;


    @Inject
    public CatofashionsLocationsTask(RpaFactory rpaFactory, Logger logger){
        this.rpaRunner = rpaFactory.builder(RpaDriver.UNIVERSAL).closeOnCompletion(true).build();
        this.logger = logger;
    }

    @Override
    public TaskRunnerOutput run(TaskInput taskInput) {

        MultipleResults results = new MultipleResults();
        AtomicReference<List<Set<String>>> locations = new AtomicReference<>(new ArrayList<>());
        this.rpaRunner.execute(driver -> {
            FashionClient fashionClient = new FashionClient(this.logger);
            MainPage mainPage = fashionClient.getMainPage();
            StoresPage storesPage = mainPage.switchToStores();
            locations.set(storesPage.getLocations());
           /* Map<String, String> headersWithValues = new LinkedHashMap<>();
            List<Map<String, String>> result = new ArrayList<>();
            for (LocationDto location:
                 locations) {
                headersWithValues.put("locationName", location.getLocationName());
                headersWithValues.put("city", location.getCity());
                headersWithValues.put("state", location.getState());
                headersWithValues.put("address", location.getAddress());
                headersWithValues.put("phone", location.getPhone());
                headersWithValues.put("linkToDirection", location.getLinkToDirection());
                result.add(headersWithValues);
                result.forEach(row -> results.addRow(new SingleResult(row)));
            }*/
        });
        results.setColumn("result", locations.toString());
        return results;
    }
}

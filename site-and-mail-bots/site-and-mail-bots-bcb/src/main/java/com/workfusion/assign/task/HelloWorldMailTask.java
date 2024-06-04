package com.workfusion.assign.task;

import com.workfusion.assign.service.EmailService;
import com.workfusion.odf2.compiler.BotTask;
import com.workfusion.odf2.core.cdi.Injector;
import com.workfusion.odf2.core.task.AdHocTask;
import com.workfusion.odf2.core.task.TaskInput;
import com.workfusion.odf2.core.task.output.MultipleResults;
import com.workfusion.odf2.core.task.output.TaskRunnerOutput;

import javax.inject.Inject;
import javax.mail.Session;

@BotTask
public class HelloWorldMailTask implements AdHocTask {

    private final EmailService emailService;

    @Inject
    public HelloWorldMailTask(Injector injector) {
        this.emailService = injector.instance(EmailService.class);
    }

    @Override
    public TaskRunnerOutput run(TaskInput taskInput) {
        Session session = emailService.setupPropertiesAndInitSession();
        session.setDebug(true);
        String sendingStatus = emailService.sendEmail(session);
        MultipleResults results = new MultipleResults();
        results.setColumn("Status", sendingStatus);
        return results;
    }
}

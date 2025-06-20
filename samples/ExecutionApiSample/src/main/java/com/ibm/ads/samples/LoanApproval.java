/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples;

import com.ibm.decision.run.JSONDecisionRunner;
import com.ibm.decision.run.RunContext;
import com.ibm.decision.run.provider.DecisionRunnerProvider;
import com.ibm.decision.run.provider.URLDecisionRunnerProvider;
import com.ibm.decision.run.trace.Trace;
import com.ibm.decision.run.trace.TraceConfiguration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Logger;

public class LoanApproval {

    private static Logger LOG = Logger.getLogger(LoanApproval.class.getName());

    public static void main(String[] args) throws IOException {


        // Locating decision archive "../../archives/loanApproval.jar";
        final String archivesDir = "../../archives";
        final String archiveFile = "loanApproval.jar";
        final File decisionJar = new File(archivesDir, archiveFile);

        DecisionRunnerProvider provider = new URLDecisionRunnerProvider
                .Builder()
                .addURL(decisionJar.toURI().toURL())
                .build();

        final String operation = "approval";
        JSONDecisionRunner runner = provider.getDecisionRunner(operation, JSONDecisionRunner.class);

        // trace config
        TraceConfiguration trace = new TraceConfiguration() {{
            printedMessages = true;
            rules.allRules = true;
            rules.executedRules = true;
            rules.nonExecutedRules = true;
        }};

        // Execute all json files inside inputDir
        final String inputDir = "../WebMicroServiceSample/test/approval";
        for (File file : new File(inputDir).listFiles((dir, name) -> name.toLowerCase().endsWith(".json"))) {

            LOG.info("Running " + file.getName());

            RunContext context = runner.createRunContext("UniqueID_"+UUID.randomUUID());
            context.setTraceConfiguration(trace);

            // Invoke decision
            Object res = runner.execute(runner.getInputReader().readValue(Files.readAllBytes(file.toPath())), context);

            LOG.info("Result " + runner.getOutputWriter().writeValueAsString(res));

            Trace t = context.getTrace();
            if (t!=null) {
                t.printedMessages.forEach(m -> LOG.fine(m.toString()));
                t.exceptionsRaised.forEach(e -> LOG.severe(e.toString()));
            }
        }
    }
}

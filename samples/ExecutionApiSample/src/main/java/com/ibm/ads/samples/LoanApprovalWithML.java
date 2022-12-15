/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.decision.run.JSONDecisionRunner;
import com.ibm.decision.run.RunContext;
import com.ibm.decision.run.provider.DecisionRunnerProvider;
import com.ibm.decision.run.provider.URLDecisionRunnerProvider;
import com.ibm.decision.run.trace.Trace;
import com.ibm.decision.run.trace.TraceConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.logging.Logger;

public class LoanApprovalWithML {

    private static Logger LOG = Logger.getLogger(LoanApprovalWithML.class.getName());

    /**
     * Initializes the SSLContext by taking the key file provided in the resources
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws IOException
     */
    private static SSLContext getSSLContext() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, CertificateException, IOException {

        String certificat = System.getProperty("ml.certificat");

        if (certificat==null || certificat.isEmpty()) {
            LOG.info("No Machive Learning SSL cerfificate used ");
            return null;
        }

        File file = new File(certificat);

        if (file.exists()==false) throw new RuntimeException(String.format("Certifcat %s not found", certificat));

        LOG.info("Using ML SSL certificat " + certificat);

        InputStream is =  new FileInputStream(file);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate caCert = (X509Certificate) cf.generateCertificate(is);

        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null);
        ks.setCertificateEntry("caCert", caCert);
        tmf.init(ks);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        return context;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException {

        SSLContext ssl = getSSLContext();

        // Locating decision archive  "../../archives/approvalWithML-XXXXXX.jar";
        final String archivesDir = "../../archives";
        final String archivePrefix = "approvalWithML-";
        final File decisionJar = new File(archivesDir).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().startsWith(archivePrefix);
            }
        })[0];

        DecisionRunnerProvider provider = new URLDecisionRunnerProvider
                .Builder()
                .addURL(decisionJar.toURI().toURL())
                .build();

        final String operation = "approvalWithML";
        JSONDecisionRunner runner = provider.getDecisionRunner(operation, JSONDecisionRunner.class);

        // Trace config
        TraceConfiguration trace = new TraceConfiguration() {{
            printedMessages = true;
            rules.allRules = true;
            rules.executedRules = true;
            rules.nonExecutedRules = true;
        }};

        // Machine Learning config
        // the value "ADS Sample Machine Learning Provider13" is predefined in the approvalWithML archive used in this sample
        // (see ../../archives/README.md#for-an-ml-archive-deploy-the-ml-models)
        final String ML_PROVIDER_INFO_KEY = "ADS Sample Machine Learning Provider13";
        final String ML_DEPLOYMENT_ID = System.getProperty("ml.deployment.id");
        if (ML_DEPLOYMENT_ID==null || ML_DEPLOYMENT_ID.isEmpty() || ML_DEPLOYMENT_ID.equals("TO_BE_SET"))
            throw new RuntimeException(String.format("Unexpected 'ml.deployment.id' property value (= '%s')", ML_DEPLOYMENT_ID));
        final String ML_PROVIDER_URL = System.getProperty("ml.provider.url");
        if (ML_PROVIDER_URL==null || ML_PROVIDER_URL.isEmpty() || ML_PROVIDER_URL.equals("TO_BE_SET"))
            throw new RuntimeException(String.format("Unexpected 'ml.provider.url' property value (= '%s')", ML_PROVIDER_URL));

        String mlproviderKey = "MyProvider";
        String mlproviderInfo = String.format("{ 'id': '%s', 'providerId': '%s', 'deploymentId': '%s'}",
                ML_PROVIDER_INFO_KEY, mlproviderKey, ML_DEPLOYMENT_ID).replace("'", "\"");
        String mlprovider = String.format("{'mlUrl': '%s', 'type': 'OPS', 'name': '%s'}",
                ML_PROVIDER_URL, mlproviderKey).replace("'", "\"");

        // Execute all json files inside inputDir
        final String inputDir = "../WebMicroServiceSample/test/approvalWithML";
        for (File file : new File(inputDir).listFiles((dir, name) -> name.toLowerCase().endsWith(".json"))) {

            LOG.info("Running " + file.getName());

            RunContext context = runner.createRunContext("UniqueID_"+UUID.randomUUID());
            // set trace
            context.setTraceConfiguration(trace);
            // set ML config
            ObjectNode decisionMetadata = ((ObjectMapper)context.get(ObjectMapper.class.getName())).createObjectNode();
            decisionMetadata.put(ML_PROVIDER_INFO_KEY, mlproviderInfo);
            decisionMetadata.put(mlproviderKey, mlprovider);
            context.put("decisionMetadata", decisionMetadata);
            // set SSL for OPS server
            if (ssl!=null) context.put(SSLContext.class.getName(), ssl);

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

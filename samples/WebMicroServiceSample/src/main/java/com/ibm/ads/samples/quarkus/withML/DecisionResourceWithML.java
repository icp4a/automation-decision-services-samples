/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples.quarkus.withML;

import java.io.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.decision.run.DecisionServiceProvider;
import com.ibm.decision.run.JSONDecisionRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.ibm.decision.run.RunContext;
import com.ibm.decision.run.provider.ClassLoaderDecisionRunnerProvider;
import com.ibm.decision.run.provider.NoSuchDecisionRunnerException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


@Path("/decision")
public class DecisionResourceWithML {

    private static final String ML_PROVIDER_INFO_KEY = "ADS Sample Machine Learning Provider13"; // taken from ../archives/README.md
    private static final String ML_DEPLOYMENT_ID = "TO BE SET"; // TO BE SET WHEN THE ML model is deployed
    private static final String ML_PROVIDER_URL = "TO BE SET";
    private static boolean ML_CODE_CERTIFICATE_REQUIRED = true; // TO CHANGE to false IF the trusted certificate is already added to the keystore.

    private static final Logger LOGGER = Logger.getLogger( DecisionResourceWithML.class.getName() );
    private static SSLContext SSL_CONTEXT = null;

    /**
     * Initializes the SSLContext by taking the key file provided in the resources
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws IOException
     */
    private static SSLContext getSSLContext() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, CertificateException {
        if (SSL_CONTEXT == null) {
            try (InputStream is =  DecisionResourceWithML.class.getClassLoader().getResourceAsStream("adsSamplesQuarkus/ads-ml.pem")) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate caCert = (X509Certificate) cf.generateCertificate(is);

                TrustManagerFactory tmf = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                ks.load(null);
                ks.setCertificateEntry("caCert", caCert);
                tmf.init(ks);

                SSL_CONTEXT = SSLContext.getInstance("TLS");
                SSL_CONTEXT.init(null, tmf.getTrustManagers(), null);
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Cannot find the certificate file in resources/adsSamplesQuarkus/ads-ml.pem", e);
            }
        }
        return SSL_CONTEXT;
    }

    private JSONDecisionRunner getDecisionRunner(String operation) throws  MalformedURLException {
        ClassLoaderDecisionRunnerProvider provider = new ClassLoaderDecisionRunnerProvider.Builder().build();
        return  provider.getDecisionRunner(operation, JSONDecisionRunner.class);
     }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object execute(String inputString, @QueryParam(value = "operation") String operation)
            throws IOException{
        try {
            var decisionRunner = getDecisionRunner(operation);
            if (decisionRunner == null)
                return Response.status(404);

            String executionId = getUniqueVersion();

            RunContext context = decisionRunner.createRunContext(executionId);

            String mlproviderKey = "MyProvider";
            String mlproviderInfo =
                    JsonUtils.toString("{ 'id': '%s', 'providerId': '%s', 'deploymentId': '%s'}",
                            ML_PROVIDER_INFO_KEY, mlproviderKey, ML_DEPLOYMENT_ID);
            String mlprovider =
                    JsonUtils.toString("{'mlUrl': '%s', 'type': 'OPS', 'name': '%s'}",
                            ML_PROVIDER_URL, mlproviderKey);

            ObjectMapper mapper = (ObjectMapper) context.get(ObjectMapper.class.getName());
            if (mapper == null)
                mapper = new ObjectMapper();
            ObjectNode decisionMetadata = mapper.createObjectNode();
            decisionMetadata.put(ML_PROVIDER_INFO_KEY, mlproviderInfo);
            decisionMetadata.put(mlproviderKey, mlprovider);

            if (ML_CODE_CERTIFICATE_REQUIRED) {
                SSLContext sslContext = getSSLContext();
                if (sslContext == null)
                    return Response.status(404);
                context.put(SSLContext.class.getName(), sslContext);
            }

            context.put("decisionMetadata", decisionMetadata);

            Object input = decisionRunner.getInputReader().readValue(inputString);
            var result = decisionRunner.execute(input, context);
            return result;

        } catch (NoSuchDecisionRunnerException e) {
            LOGGER.log(Level.SEVERE, "Cannot find the decision operation " + operation + " in the web service", e);
            return Response.status(404);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "SSL Trustore to be completed ", e);
            return Response.status(404);
        } catch (KeyManagementException e) {
            LOGGER.log(Level.SEVERE, "SSL Trustore to be completed ", e);
            return Response.status(404);
        } catch (KeyStoreException e) {
            LOGGER.log(Level.SEVERE, "SSL Trustore to be completed ", e);
            return Response.status(404);
        } catch (CertificateException e) {
            LOGGER.log(Level.SEVERE, "SSL Certificate to be checked ", e);
            return Response.status(404);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/input-sample")
    public Object inputSample(@QueryParam(value = "operation") String operation) throws IOException {
        try {
        var decisionRunner = getDecisionRunner(operation);
        if (decisionRunner == null)
            return Response.status(404);

        var inputExample = decisionRunner.createInputExample();

        return decisionRunner.getInputWriter().writeValueAsString(inputExample);
        } catch (NoSuchDecisionRunnerException e) {
            LOGGER.log(Level.SEVERE, "Cannot find the decision operation " + operation + " in the web service", e);
            return Response.status(404);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/operations")
    public Object getOperations() {
        var serviceLoader = ServiceLoader.load(DecisionServiceProvider.class, DecisionResourceWithML.class.getClassLoader());
    
        var operations = new ArrayList<String>();
        
        for (final DecisionServiceProvider provider : serviceLoader)
            operations.addAll(provider.getDecisionOperations());
        
        return operations;
    }

    private String getUniqueVersion() {
        return java.util.UUID.randomUUID().toString();

        //LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }


}
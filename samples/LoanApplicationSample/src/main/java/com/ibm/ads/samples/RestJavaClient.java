/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2025. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */

package com.ibm.ads.samples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.logging.Logger;

import javax.net.ssl.*;

public class RestJavaClient {
    private Logger logger;

    public RestJavaClient()  {
        logger = Logger.getAnonymousLogger();
    }

    public  AdsResponse executeDecision(String host, String spaceId, String decisionServiceId, int majorVersion, int minorVersion,
                                        String operationName, String user,
                                        String password, String requestBody) throws Exception {
        // method to be used to send request : POST
        // possible return codes are :
        //  200 OK
        //  404 decisionId or decision operation invalid (not found)
        //  500 error during the execution
        // response content is a JSON payload (if status is ok)
        //
        //Server response
        //Code	Details
        AdsResponse result = new AdsResponse();

        String stringUrl = null;
        try {
            stringUrl = String.format("https://%s/ads/runtime/api/v1/selectors/lastSemanticDecisionServiceVersion" +
                            "/deploymentSpaces/%s/operations/%s/extendedExecute?decisionServiceId=%s%s%s",
                    host, URLEncoder.encode(spaceId,"UTF-8"), URLEncoder.encode(operationName,"UTF-8"),
                    URLEncoder.encode(decisionServiceId,"UTF-8"),
                    majorVersion<0 ? "": "&majorVersion="+majorVersion,
                    minorVersion<0 ? "": "&minorVersion="+minorVersion);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Exception " + e);
            logger.throwing(this.getClass().getName(),"executeProject",e);
        }
        System.out.println("URL: " + stringUrl);
        logger.info("URL: " + stringUrl);

        URL url = new URL(stringUrl);

        // Configure the SSLContext with a TrustManager (avoid certificate issues + force TLS 1.2)
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(
                null,
                new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                            @Override
                            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                },
                new SecureRandom()
        );
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        // Creating the Request
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Basic Authentication
        String encoding = Base64.getEncoder().encodeToString((user +":"+password).getBytes("UTF-8"));
        connection.setRequestProperty  ("Authorization", "Basic " + encoding);

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        // Sending the payload
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBody.getBytes("UTF-8"));
        logger.info("INPUT: " + outputStream.toString());
        System.out.println("INPUT: " + outputStream.toString());
        outputStream.close();

        // Get response code
        result.status = connection.getResponseCode();

        // Get response content
        BufferedReader input = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = input.readLine()) != null) {
            content.append(inputLine);
        }
        result.payload = content.toString();
        logger.info("OUTPUT: " + result);
        System.out.println("OUTPUT: " + result);

        input.close();
        connection.disconnect();

        return result;

    }

}

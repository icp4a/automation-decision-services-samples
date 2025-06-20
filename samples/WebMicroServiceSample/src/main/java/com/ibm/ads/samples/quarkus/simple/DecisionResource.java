/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples.quarkus.simple;

import com.ibm.decision.run.DecisionServiceProvider;
import com.ibm.decision.run.JSONDecisionRunner;
import com.ibm.decision.run.provider.ClassLoaderDecisionRunnerProvider;
import com.ibm.decision.run.provider.NoSuchDecisionRunnerException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/decision")
public class DecisionResource {
    private static final Logger LOGGER = Logger.getLogger( DecisionResource.class.getName() );

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

            Object input = decisionRunner.getInputReader().readValue(inputString);
            var result = decisionRunner.execute(input);
            return result;

        } catch (NoSuchDecisionRunnerException e) {
            LOGGER.log(Level.SEVERE, "Cannot find the decision operation in the web service");
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
            LOGGER.log(Level.SEVERE, "Cannot find the decision operation in the web service");
            return Response.status(404);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/operations")
    public Object getOperations() {
        var serviceLoader = ServiceLoader.load(DecisionServiceProvider.class, DecisionResource.class.getClassLoader());
    
        var operations = new ArrayList<String>();
        
        for (final DecisionServiceProvider provider : serviceLoader)
            operations.addAll(provider.getDecisionOperations());
        
        return operations;
    }

}
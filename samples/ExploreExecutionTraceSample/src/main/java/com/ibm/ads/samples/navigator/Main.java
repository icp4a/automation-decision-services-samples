/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples.navigator;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
  private static final Logger LOGGER = Logger.getLogger( com.ibm.ads.samples.navigator.Main.class.getName() );

  public static void main(String[] args)
  {
    if (args.length < 1) {
      LOGGER.severe("Expect one input json file containing the trace.");
      return;
    }
    try {
      String traceFileName = args[0];
      File traceFile = new File(traceFileName);
      if (!traceFile.exists() || !traceFile.isFile() || !traceFile.getName().endsWith(".json")) {
        LOGGER.severe("Input specified is not a JSON file.");
        return;
      }
      generateReport(traceFile, null);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE,"ERROR: " + e.getMessage(), e);
    }
  }

  private static void generateReport(File f, File dir) throws Exception {
    String fn = f.getName().substring(0, f.getName().indexOf('.'));
    FileOutputStream ostream = new FileOutputStream((dir == null ? "" : dir.getPath() + "/") + fn + "-nav.html", false);
    TraceNavigatorHtmlWriter writer = new TraceNavigatorHtmlWriter(ostream, f);
    writer.generate();
    LOGGER.log(Level.INFO, "File '" + (dir == null ? "" : dir.getPath() + "/") + fn + "-nav.html' generated.");
  }

}
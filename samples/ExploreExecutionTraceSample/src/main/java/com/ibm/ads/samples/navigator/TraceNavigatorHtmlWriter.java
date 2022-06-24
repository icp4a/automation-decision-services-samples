/*
 * Licensed Materials - Property of IBM
 * 5737-I23
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples.navigator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class TraceNavigatorHtmlWriter
{
  protected OutputStream stream;
  protected File f;
  
  public TraceNavigatorHtmlWriter(OutputStream stream, File f)
  {
    this.stream = stream;
    this.f = f;
  }
  
  protected void writeln(String value)
    throws IOException
  {
    this.stream.write(value.getBytes());
    this.stream.write("\n".getBytes());
  }
  
  protected void write(String value)
    throws IOException
  {
    this.stream.write(value.getBytes());
  }
  
  public void generate()
    throws IOException
  {
    writeln("<!DOCTYPE html>");
    writeln("<html lang=\"en\">");
    writeln("<head>");
    writeln("  <title>Trace Navigator sample</title>");
    writeln("  <meta charset=\"utf-8\">");
    writeln("  <script src=\"https://d3js.org/d3.v6.min.js\"></script>");
    writeln("</head>");
    writeln("<body style=\"font-family:arial\">");
    writeln("\t<div>Trace Navigator<br><br></div>");
    writeln("\t<svg style=\"font-size:12px\" id=\"nav_area\" height=700 width=1200></svg>");
    writeln("\t<div style=\"position: absolute;left: 1250px;top: 15px;height=350px;\">Messages:<br><br><div style=\"font-size:12px;height:280px;overflow-y:auto;\" id=\"messages_area\"></div></div>");
    writeln("\t<div style=\"position: absolute;left: 1250px;top: 360px;height=350px;\">Exceptions:<br><br><div style=\"font-size:12px;height:280px;overflow-y:auto;\" id=\"exceptions_area\"></div></div>");
    writeln("</body>");
    writeln("<script>");
    writeln("");
    
    write("data = ");
    BufferedReader br = new BufferedReader(new FileReader(this.f));
    String line;
    while ((line = br.readLine()) != null) {
      writeln(line);
    }
    br.close();

    writeln("if (data.executionTrace) {");
    writeln("\tdata = data.executionTrace;");
    writeln("}");
    writeln("");
    writeln("");
    writeln("function trace2Data(elt) {");
    writeln("\tvar root = {}");
    writeln("\troot[\"type\"] = elt.recordType");
    writeln("\troot[\"index\"] = \"a\" + elt.recordIndex.replaceAll(\"\\.\",\"a\")");
    writeln("\tif (root[\"type\"] == \"RuleFlow\") {");
    writeln("\t\troot[\"name\"] = 'RuleFlow'");
    writeln("\t} else {");
    writeln("\t\troot[\"name\"] = elt.properties.name");
    writeln("\t}");
    writeln("\troot[\"properties\"] = elt.properties");
    writeln("\tif (elt.properties.nestedRecords) {");
    writeln("\t\troot[\"children\"] = []");
    writeln("\t\tfor (var i = 0; i < elt.properties.nestedRecords.length; i++) {");
    writeln("\t\t\troot[\"children\"].push(trace2Data(elt.properties.nestedRecords[i]));\t");
    writeln("\t\t}");
    writeln("\t} else {");
    writeln("\t\troot[\"value\"] = 1");
    writeln("\t}");
    writeln("\treturn root");
    writeln("}");
    writeln("\t");
    writeln("function partition(data) {");
    writeln("\t  const root = d3.hierarchy(data)");
    writeln("\t      .sum(d => d.value);");
    writeln("\t  return d3.partition()");
    writeln("\t      .size([height, (root.height + 1) * width / 3])");
    writeln("\t    (root);");
    writeln("}");
    writeln("");
    writeln("function color(data) {");
    writeln("\tif (data.type == \"Rule\") {");
    writeln("\t\treturn \"#A8EB12\";");
    writeln("\t} else if (data.type == \"DecisionModel\") {");
    writeln("\t\treturn \"#0C69F4\";");
    writeln("\t} else if ((data.type == \"DecisionModelNode\") && (data.properties.kind == \"InputData\")) {");
    writeln("\t\treturn \"#4de290\";");
    writeln("\t} else if ((data.type == \"DecisionModelNode\") && (data.properties.kind == \"Decision\")) {");
    writeln("\t\treturn \"#80ec88\";");
    writeln("\t} else if (data.type == \"ReteEngine\") {");
    writeln("\t\treturn \"#F4A30C\";");
    writeln("\t} else if (data.type == \"Task\") {");
    writeln("\t\treturn \"#f4360b\";");
    writeln("\t} else if (data.type == \"RuleFlow\") {");
    writeln("\t\treturn \"#F40C16\";");
    writeln("\t}");
    writeln("}");
    writeln("");
    writeln("var svg = d3.select(\"#nav_area\")");
    writeln("var height = 700");
    writeln("var width = 1200");
    writeln("");
    writeln("format = d3.format(\",d\")");
    writeln("");
    writeln("const root = partition(trace2Data(data.rootRecord));");
    writeln("let focus = root;");
    writeln("");
    writeln("");
    writeln("const cell = svg");
    writeln(".selectAll(\"g\")");
    writeln(".data(root.descendants())");
    writeln(".join(\"g\")");
    writeln("  .attr(\"transform\", d => `translate(${d.y0},${d.x0})`);");
    writeln("");
    writeln("const rect = cell.append(\"rect\")");
    writeln("  .attr(\"width\", d => d.y1 - d.y0 - 1)");
    writeln("  .attr(\"height\", d => rectHeight(d))");
    writeln("  .attr(\"fill-opacity\", 0.6)");
    writeln("  .attr(\"fill\", d => {");
    writeln("    return color(d.data);");
    writeln("  })");
    writeln("  .style(\"cursor\", \"pointer\")");
    writeln("  .attr(\"stroke-width\", 0)");
    writeln("  .attr(\"stroke\", \"rgb(255,0,0)\")");
    writeln("  .on(\"click\", clicked);");
    writeln("");
    writeln("const text = cell.append(\"text\")");
    writeln("  .style(\"user-select\", \"none\")");
    writeln("  .attr(\"pointer-events\", \"none\")");
    writeln("  .attr(\"x\", 4)");
    writeln("  .attr(\"y\", 13)");
    writeln("  .attr(\"fill-opacity\", d => +labelVisible(d));");
    writeln("");
    writeln("text.append(\"tspan\")");
    writeln("  .text(d => \"Type : \" + d.data.type);");
    writeln("  ");
    writeln("function addProperties(d,i) { ");
    writeln("\tvar first = true;");
    writeln("\tvar lines = 2; // tspan (line) + margin 30");
    writeln("\tfor ([key, value] of Object.entries(d.data.properties)) {");
    writeln("\t\tif (key !== \"nestedRecords\") {");
    writeln("\t\t\tif (Array.isArray(value) && value.length == 0) {");
    writeln("\t\t\t\tvalue = \"none\"");
    writeln("\t\t\t}");
    writeln("\t\t\tif (typeof value === 'object' && value !== null) {");
    writeln("\t\t\t\tvalue = JSON.stringify(value, undefined, 2);");
    writeln("\t\t\t\tsvalue = value.split('\\n')");
    writeln("\t\t\t\tvar first2 = true;");
    writeln("\t\t\t\tkeySpan = \"\"; ");
    writeln("\t\t\t\tfor (var j = 0 ; j < key.length; j++) {");
    writeln("\t\t\t\t\tkeySpan += \"&nbsp;\"; ");
    writeln("\t\t\t\t}");
    writeln("\t\t\t\tfor (var i = 0; i < svalue.length; i++) {");
    writeln("\t\t\t\t\tssvalue = svalue[i].match(/.{1,40}/g);");
    writeln("\t\t\t\t\tfor (var k = 0; k < ssvalue.length; k++) {");
    writeln("\t\t\t\t\t\tlines++;");
    writeln("\t\t\t\t\t\td3.select(this).append(\"tspan\")");
    writeln("\t\t\t\t\t\t\t.attr(\"dy\", 20 + (first ? 10 : 0))");
    writeln("\t\t\t\t\t\t\t.attr(\"x\", \"4\")");
    writeln("\t\t\t\t\t\t\t.html((first2 ? key + \" : \": keySpan + \"&nbsp;&nbsp;&nbsp;\") + ssvalue[k]);");
    writeln("\t\t\t\t\t\tfirst2 = false;");
    writeln("\t\t\t\t\t}");
    writeln("\t\t\t\t}");
    writeln("\t\t\t} else {");
    writeln("\t\t\t\tlines++;");
    writeln("\t\t\t\td3.select(this).append(\"tspan\")");
    writeln("\t\t\t\t\t.attr(\"dy\", 20 + (first ? 10 : 0))");
    writeln("\t\t\t\t\t.attr(\"x\", \"4\")");
    writeln("\t\t\t\t\t.text(key + \" : \" + value);");
    writeln("\t\t\t}");
    writeln("\t\t\tfirst = false");
    writeln("\t\t}");
    writeln("\t}");
    writeln("\tif (d.data.properties.nestedRecords) {");
    writeln("\t\tlines = lines + 2; // tspan + margin 30");
    writeln("\t\td3.select(this).append(\"tspan\")");
    writeln("\t\t.attr(\"dy\", 30)");
    writeln("\t\t.attr(\"x\", \"4\")");
    writeln("\t\t.html(\"&#11208;\");");
    writeln("\t}");
    writeln("\td.text_lines = lines;");
    writeln("}");
    writeln("");
    writeln("svg.selectAll(\"text\").each(addProperties)");
    writeln("");
    writeln("function addIndexes(d,i) {");
    writeln("\td3.select(this).attr('id', d.data.index)");
    writeln("}");
    writeln("");
    writeln("svg.selectAll(\"rect\").each(addIndexes)");
    writeln("");
    writeln("if (data.printedMessages.length == 0) {");
    writeln("\tdocument.getElementById(\"messages_area\").innerHTML = \"None\"");
    writeln("} else {");
    writeln("\tvar result = \"\"");
    writeln("\tfor (var i=0; i < data.printedMessages.length; i++) {");
    writeln("\t\tresult += \"<span class='message' style='cursor: pointer;' index='a\" + data.printedMessages[i].recordIndex.replaceAll(\"\\.\",\"a\") + \"' onclick='click_message(this)'><i>\" + data.printedMessages[i].message + \"</i></span><br><br>\"");
    writeln("\t}");
    writeln("\tdocument.getElementById(\"messages_area\").innerHTML = result");
    writeln("}");
    writeln("");
    writeln("d3.selectAll(\".message\").on(\"mouseover\", function (d) {");
    writeln("\td3.select(this).style(\"color\", \"red\");");
    writeln("}).on(\"mouseout\", function (d) {");
    writeln("\td3.select(this).style(\"color\", \"black\");");
    writeln("})");
    writeln("");
    writeln("if (data.exceptionsRaised.length == 0) {");
    writeln("\tdocument.getElementById(\"exceptions_area\").innerHTML = \"None\"");
    writeln("} else {");
    writeln("\tvar result = \"\"");
    writeln("\tfor (var i=0; i < data.exceptionsRaised.length; i++) {");
    writeln("\t\tresult += \"<span class='exception' style='cursor: pointer;' index='a\" + data.exceptionsRaised[i].recordIndex.replaceAll(\"\\.\",\"a\") + \"' onclick='click_message(this)'><i>\" + data.exceptionsRaised[i].exception.type + \": \" + data.exceptionsRaised[i].exception.message + \" (\" + (data.exceptionsRaised[i].exception.internallyHandled ? \"\" : \"not \") +  \"handled)\" + \"</i></span><br><br>\"");
    writeln("\t}");
    writeln("\tdocument.getElementById(\"exceptions_area\").innerHTML = result");
    writeln("}");
    writeln("");
    writeln("d3.selectAll(\".exception\").on(\"mouseover\", function (d) {");
    writeln("\td3.select(this).style(\"color\", \"red\");");
    writeln("}).on(\"mouseout\", function (d) {");
    writeln("\td3.select(this).style(\"color\", \"black\");");
    writeln("})");
    writeln("");
    writeln("function click_message(elt) {");
    writeln("\tvar p = d3.select(\"#\" + elt.getAttribute(\"index\").replaceAll(\"\\.\",\"a\")).each(function(d, i){");
    writeln("        if (i == 0) {");
    writeln("        \tclicked(null, d);");
    writeln("        \td3.select(this).attr(\"stroke-width\", 4)");
    writeln("        }");
    writeln("    });");
    writeln("}");
    writeln("");
    writeln("//const tspan = text.append(\"tspan\")");
    writeln("//  .attr(\"fill-opacity\", d => labelVisible(d) * 0.7)");
    writeln("//  .text(d => ` ${format(d.value)}`);");
    writeln("");
    writeln("cell.append(\"title\")");
    writeln("  .text(d => `${d.ancestors().map(d => d.data.name).reverse().join(\"/\")}`);");
    writeln("");
    writeln("function rectTHeight(d, p) {");
    writeln("\tvar x0 = ((d.x0 - p.x0) / (p.x1 - p.x0) * height);");
    writeln("\tvar x1 = ((d.x1 - p.x0) / (p.x1 - p.x0) * height);");
    writeln("\treturn x1 - x0 - Math.min(1, (x1 - x0) / 2);");
    writeln("}");
    writeln("");
    writeln("function zoom(z, p, item) {");
    writeln("\tif (rectTHeight(item, p) < parseInt(item.text_lines)  * 20) {");
    writeln("\t\tzi = (parseInt(item.text_lines)  * 20) / rectTHeight(item, p);");
    writeln("\t\tif (zi > z) {");
    writeln("\t\t\tz = zi;");
    writeln("\t\t}");
    writeln("\t}");
    writeln("\treturn z;");
    writeln("}");
    writeln("function clickedWithDuration(event, p, duration) {");
    writeln("\tduration = duration - d3.selectAll(\"g\").size() / 100;");
    writeln("\td3.selectAll(\"rect\").each(function(d, i) {d3.select(this).attr(\"stroke-width\", 0)})");
    writeln("\t");
    writeln("\tfocus = focus === p ? p = (p.parent ? p.parent : p )  : p;");
    writeln("");
    writeln("\tz = 1; // zoom not compute for the first element because it takes all the height");
    writeln("\tif (p.children) {");
    writeln("\t\tp.children.forEach((item, index) => {");
    writeln("\t\t\tz = zoom(z, p, item);");
    writeln("\t\t\tif (item.children) {");
    writeln("\t\t\t\titem.children.forEach((item2, index2) => {");
    writeln("\t\t\t\t\tz = zoom(z, p, item2);");
    writeln("\t\t\t\t})");
    writeln("\t\t\t}");
    writeln("\t\t})");
    writeln("\t}");
    writeln("\troot.each(d => d.target = {");
    writeln("  \t\tx0: ((d.x0 - p.x0) / (p.x1 - p.x0) * height) * z,");
    writeln("  \t\tx1: ((d.x1 - p.x0) / (p.x1 - p.x0) * height) * z,");
    writeln("  \t\ty0: d.y0 - p.y0,");
    writeln("  \t\ty1: d.y1 - p.y0");
    writeln("\t});");
    writeln("\tconst t = cell.transition().duration(duration)");
    writeln("    \t.attr(\"transform\", d => `translate(${d.target.y0},${d.target.x0})`);");
    writeln("");
    writeln("\trect.transition(t).attr(\"height\", d => rectHeight(d.target));");
    writeln("\ttext.transition(t).attr(\"fill-opacity\", d => +labelVisible(d.target));");
    writeln("\tsvg.attr(\"height\", (height * z))");
    writeln("\t\t// tspan.transition(t).attr(\"fill-opacity\", d => labelVisible(d.target) * 0.7);");
    writeln("}");
    writeln("");
    writeln("function clicked(event, p) {");
    writeln("\tclickedWithDuration(event, p, 750);");
    writeln("}");
    writeln("");
    writeln("function rectHeight(d) {");
    writeln("\treturn d.x1 - d.x0 - Math.min(1, (d.x1 - d.x0) / 2);");
    writeln("}");
    writeln("");
    writeln("function labelVisible(d) {");
    writeln("\treturn d.y1 <= width && d.y0 >= 0 && d.x1 - d.x0 > 16;");
    writeln("}");
    writeln("");
    writeln("clickedWithDuration(null, root, 0);");
    writeln("");
    writeln("</script>");
    writeln("</html> ");
  }
}

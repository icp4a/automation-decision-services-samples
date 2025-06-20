/*
 * Licensed Materials - Property of IBM
 * 5737-I23 5900-AUD
 * Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
 * U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp.
 */
package com.ibm.ads.samples;

import com.ibm.ads.samples.banking.loan_approval.approval_decision_model.approval_decision_model.Input;
import com.ibm.ads.samples.banking.loan_approval.loan_validation_data.Borrower;
import com.ibm.ads.samples.banking.loan_approval.loan_validation_data.Loan;
import com.ibm.ads.samples.banking.loan_approval.loan_validation_data.SSN;
import com.ibm.decision.run.JSONDecisionRunner;
import com.ibm.decision.run.provider.DecisionRunnerProvider;
import com.ibm.decision.run.provider.URLDecisionRunnerProvider;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public class LoanApprovalPOJO {

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

        // Approved input
        {
            SSN ssn = new SSN();
            ssn.setAreaNumber("123");
            ssn.setGroupCode("45");
            ssn.setSerialNumber("6789");

            Borrower borrower = new Borrower();
            borrower.setFirstName("John");
            borrower.setLastName("Doe");
            borrower.setBirthDate(ZonedDateTime.parse("1968-05-12T00:00:00Z"));
            borrower.setYearlyIncome(100000);
            borrower.setZipCode("91320");
            borrower.setCreditScore(500);
            borrower.setSSN(ssn);

            Loan loan = new Loan();
            loan.setAmount(185000);
            loan.setNumberOfMonthlyPayments(72);
            loan.setStartDate(ZonedDateTime.parse("2005-06-01T00:00:00Z"));
            loan.setLoanToValue(0.7);

            Input input = new Input();
            input.borrower = borrower;
            input.loan = loan;

            Object res = runner.execute(input);

            LOG.info("Approved input result: " + runner.getOutputWriter().writeValueAsString(res));
        }

        // Refused input
        {
            SSN ssn = new SSN();
            ssn.setAreaNumber("123");
            ssn.setGroupCode("45");
            ssn.setSerialNumber("6789");

            Borrower borrower = new Borrower();
            borrower.setFirstName("John");
            borrower.setLastName("Doe");
            borrower.setBirthDate(ZonedDateTime.parse("1968-05-12T00:00:00Z"));
            borrower.setYearlyIncome(100000);
            borrower.setZipCode("91320");
            borrower.setCreditScore(700);
            borrower.setSSN(ssn);

            Loan loan = new Loan();
            loan.setAmount(200000);
            loan.setNumberOfMonthlyPayments(72);
            loan.setStartDate(ZonedDateTime.parse("2005-06-01T00:00:00Z"));
            loan.setLoanToValue(0.7);

            Input input = new Input();
            input.borrower = borrower;
            input.loan = loan;

            Object res = runner.execute(input);

            LOG.info("Refusal input result: " + runner.getOutputWriter().writeValueAsString(res));
        }
    }
}

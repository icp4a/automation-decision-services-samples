#!/bin/bash
# Licensed Materials - Property of IBM
# 5737-I23
# Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
# U.S. Government Users Restricted Rights:
# Use, duplication or disclosure restricted by GSA ADP Schedule
# Contract with IBM Corp.

set -e

export SERVICE_VERSION=1.0.0

mvn install:install-file -DgroupId=ads.samples \
                         -DartifactId=loanApproval \
                         -Dversion=$SERVICE_VERSION \
                         -DgeneratePom=true \
                         -Dpackaging=jar \
                         -Dfile=../../../archives/loanApproval-22.0.1.jar \
                         -s ../settings.xml
mvn install:install-file -DgroupId=ads.samples \
                         -DartifactId=approvalWithML \
                         -Dversion=$SERVICE_VERSION \
                         -DgeneratePom=true \
                         -Dpackaging=jar \
                         -Dfile=../../../archives/approvalWithML-22.0.1.jar \
                         -s ../settings.xml



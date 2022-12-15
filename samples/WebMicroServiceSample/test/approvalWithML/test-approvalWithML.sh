#!/bin/sh
# Licensed Materials - Property of IBM
# 5737-I23 5900-AUD
# Copyright IBM Corp. 2018 - 2022. All Rights Reserved.
# U.S. Government Users Restricted Rights:
# Use, duplication or disclosure restricted by GSA ADP Schedule
# Contract with IBM Corp.

set -e

SDIR=`dirname $0`

echo "------- Approved loan"
echo ">>>  Input"
cat $SDIR/goodScore.json
echo ""
echo "<<<  Output"
curl -s -d @$SDIR/goodScore.json -X POST -H "Content-Type: application/json" http://localhost:8080/decision?operation=approvalWithML | jq

echo ""
echo "------- Refused loan"
echo ">>>  Input"
cat $SDIR/badScore.json
echo ""
echo "<<<  Output"
curl -s -d @$SDIR/badScore.json -X POST -H "Content-Type: application/json" http://localhost:8080/decision?operation=approvalWithML | jq

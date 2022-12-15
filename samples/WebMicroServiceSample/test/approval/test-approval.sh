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
cat $SDIR/approved.json
echo ""
echo "<<<  Output"
curl -s -d @$SDIR/approved.json -X POST -H "Content-Type: application/json" http://localhost:8080/decision?operation=approval | jq

echo ""
echo "------- Refused loan"
echo ">>>  Input"
cat $SDIR/refused.json
echo ""
echo "<<<  Output"
curl -s -d @$SDIR/refused.json -X POST -H "Content-Type: application/json" http://localhost:8080/decision?operation=approval | jq

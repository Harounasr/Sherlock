#!/usr/bin/env bash

# Author: Leon Foeckersperger

set -e

mvn --no-transfer-progress -Pintegration-tests -DskipUnitTests verify
grep -o '<tfoot>.*</tfoot>' <target/site/jacoco-it/index.html

#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

mvn --no-transfer-progress -DSYSTEM_TEST_BROWSER=edge -DskipUnitTests -DskipIntegerationTests -Psystem-tests clean compile verify
exit_code=$?

grep -o '<tfoot>.*</tfoot>' <target/site/jacoco/index.html

exit $exit_code

#!/usr/bin/env bash

# Author: Leon Foeckersperger

set -e

mvn --no-transfer-progress -DSYSTEM_TEST_BROWSER=chrome -DskipUnitTests -DskipIntegerationTests -Psystem-tests clean compile verify
exit_code=$?

grep -o '<tfoot>.*</tfoot>' <target/site/jacoco/index.html

exit $exit_code

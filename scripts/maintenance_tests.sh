#!/usr/bin/env bash

# Author: Victor Vollmann

set -e

mvn --no-transfer-progress -DskipUnitTests -DskipSystemTests -DskipIntegerationTests -Pmaintenance-tests clean compile verify
exit_code=$?

grep -o '<tfoot>.*</tfoot>' <target/site/jacoco/index.html

exit $exit_code

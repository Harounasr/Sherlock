#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

mvn --no-transfer-progress -DskipUnitTests -DskipIntegrationTests clean compile test-compile spotbugs:check

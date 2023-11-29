#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

mvn --no-transfer-progress clean compile test-compile

#!/usr/bin/env bash

# Author: Leon Foeckersperger

set -e

mvn --no-transfer-progress clean compile test-compile

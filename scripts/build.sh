#!/usr/bin/env bash

# Author: Leon Feckersperger

set -e

mvn --no-transfer-progress clean compile test-compile

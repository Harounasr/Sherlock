#!/usr/bin/env bash

# Author: Leon Fckersperger

set -e

mvn --no-transfer-progress clean compile test-compile

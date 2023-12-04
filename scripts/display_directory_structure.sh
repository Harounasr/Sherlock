#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

tree -a -C --dirsfirst --gitignore -I .git -I documents -I class_diagrams -I .gitkeep -I "*.java" -I .classpath -I .project | sed '$d' | sed '$d'

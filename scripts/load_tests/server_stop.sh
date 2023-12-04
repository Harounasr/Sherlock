# shellcheck disable=SC2016
'#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

cd "${HOME}/Dev/Git/sep-team-5"

tomcatDir="tomcat/apache-tomcat"
"${tomcatDir}/bin/shutdown.sh"

git checkout -
'

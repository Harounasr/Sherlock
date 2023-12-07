#!/usr/bin/env bash

# Author: Leon Föckersperger

set -e

export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

cd "${HOME}/Dev/Git/sep-team-5"

source "scripts/setup_tomcat.sh"
tomcatDir="tomcat/apache-tomcat"

target_dir="target-lt-server"

mvn --no-transfer-progress -DBUILD_DIRECTORY="$target_dir" \
    -DskipUnitTests -DskipIntegerationTests -Pload-tests clean compile package

cp ${target_dir}/*-load_test.war "${tomcatDir}/webapps/schwarzes_brett.war"

"${tomcatDir}/bin/catalina.sh" run


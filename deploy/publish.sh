#!/usr/bin/env bash

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
    cd $BASE_DIR
    mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-RELEASE+build.$TRAVIS_JOB_ID
    mvn deploy --settings $BASE_DIR/deploy/settings.xml -DperformRelease=true -DskipTests=true -Dmaven.javadoc.skip=true
    exit $?
fi
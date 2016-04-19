#!/usr/bin/env bash

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
    mvn deploy --settings $BASE_DIR/settings.xml -DperformRelease=true -DskipTests=true
    exit $?
fi
#!/usr/bin/env bash

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
    cd $BASE_DIR
    mvn deploy --settings $BASE_DIR/deploy/settings.xml -DperformRelease=true -DskipTests=true
    exit $?
fi
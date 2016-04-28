#!/usr/bin/env bash

# fail on error
set -e

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
    cd $BASE_DIR
    mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-RELEASE+build.$TRAVIS_JOB_ID
    mvn deploy --settings $BASE_DIR/deploy/settings.xml -DperformRelease=true -DskipTests=true -Dmaven.javadoc.skip=true

    cd $BASE_DIR/docker/api-backend

#    move jar artifact to docker build folder
    mv $BASE_DIR/target/ru.nbakaev.hishop.jar $BASE_DIR/docker/api-backend/ru.nbakaev.hishop.jar

    docker login -e="$DOCKER_EMAIL" -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"

    docker build --tag=nbakaev/hishop-api-backend .
#    docker tag hishop-api-backend HUB/___
    docker push nbakaev/hishop-api-backend

    exit $?
fi
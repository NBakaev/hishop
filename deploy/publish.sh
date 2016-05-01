#!/usr/bin/env bash

# fail on error
set -e

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then

############# deployMaven

    cd $BASE_DIR
    mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-RELEASE+build.$TRAVIS_JOB_ID
    mvn deploy --settings $BASE_DIR/deploy/settings.xml -DperformRelease=true -DskipTests=true -Dmaven.javadoc.skip=true
    PROJECT_VERSION="`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version 2> /dev/null |grep -Ev '(^\[|Download\w+:)'`"
    PROJECT_VERSION_DOCKER="`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version 2> /dev/null |grep -Ev '(^\[|Download\w+:)' | tr + _`"

    echo "PROJECT_VERSION is $PROJECT_VERSION"
    echo "PROJECT_VERSION_DOCKER is $PROJECT_VERSION_DOCKER"
############# deployDocker
    cd $BASE_DIR/docker/api-backend

#    move jar artifact to docker build folder
    mv $BASE_DIR/target/ru.nbakaev.hishop.jar $BASE_DIR/docker/api-backend/release.jar

    docker login -e="$DOCKER_EMAIL" -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
    docker build --tag=nbakaev/hishop-api-backend:$PROJECT_VERSION_DOCKER .
    docker push nbakaev/hishop-api-backend

    exit $?
fi
#!/usr/bin/env bash

# fail on error
set -e

if [[ $TRAVIS_PULL_REQUEST == "false" ]]; then

####################################
########### deploy maven ###########
####################################

    cd $BASE_DIR

    # semver format. for example 0.2.2-RELEASE+build.15
    mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.incrementalVersion}-RELEASE+build.$TRAVIS_JOB_ID

    # deploy to maven binary repo
    mvn deploy --settings $BASE_DIR/deploy/settings.xml -DperformRelease=true -DskipTests=true -Dmaven.javadoc.skip=true
    PROJECT_VERSION="`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version 2> /dev/null |grep -Ev '(^\[|Download\w+:)'`"

    # in docker tag we can not use plus sign(`+`) - replace it with `_`
    PROJECT_VERSION_DOCKER=`echo $PROJECT_VERSION | tr + _`

    echo "PROJECT_VERSION is $PROJECT_VERSION"
    echo "PROJECT_VERSION_DOCKER is $PROJECT_VERSION_DOCKER"

##############################################
########### deploy docker registry ###########
##############################################

    cd $BASE_DIR/docker/api-backend

    # move jar artifact to docker build folder
    mv $BASE_DIR/target/ru.nbakaev.hishop.jar $BASE_DIR/docker/api-backend/release.jar

    docker login -e="$DOCKER_EMAIL" -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
    docker build --tag=nbakaev/hishop-api-backend:$PROJECT_VERSION_DOCKER .
    docker push nbakaev/hishop-api-backend


# end build
    exit $?
fi
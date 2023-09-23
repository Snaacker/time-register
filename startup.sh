#!/bin/bash
./gradlew clean build buildDocker -x check
docker-compose up -d


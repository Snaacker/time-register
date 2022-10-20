#!/bin/bash
# Add check database is up and running

export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/timeregister"
export DATABASE_URL_CUSTOMIZE="jdbc:postgresql://localhost:5432/timeregister"
export USER_NAME=admin
export USER_PASSWORD=admin
export JWT_SECRET_KEY=cbfZjNawhGCMkqA7yuQyMT7XmepLvW1T123avd
./gradlew bootRun



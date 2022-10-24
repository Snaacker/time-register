#!/bin/bash
# Add check database is up and running
if [ ! "$(docker ps -q -f name=db)" ]; then
    # run your container
    docker run -d --name db -e POSTGRES_USER=admin \
      -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=timeregister \
      -p 5432:5432 --net=host postgres:13.1-alpine
fi

export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/timeregister"
export DATABASE_URL_CUSTOMIZE="jdbc:postgresql://localhost:5432/timeregister"
export USER_NAME=admin
export USER_PASSWORD=admin
export JWT_SECRET_KEY=cbfZjNawhGCMkqA7yuQyMT7XmepLvW1T123avd
./gradlew bootRun



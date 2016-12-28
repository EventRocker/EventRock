#!/bin/bash

# make sure we are going to execute docker-compose at the gateway folder

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR

docker stop mysql || true
docker-compose -f mysql-docker-compose.yml up -d --build

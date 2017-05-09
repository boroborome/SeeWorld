#!/usr/bin/env bash

source env.sh
docker stop ${container_name}
docker ps -a | grep ${container_name} | awk '{print $1}' | xargs -n 1 docker rm -f

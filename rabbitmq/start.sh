#!/usr/bin/env bash
cd `dirname $0`
bash stop.sh

source env.sh

container_id=`docker ps -a | grep ${container_name} | awk '{print $1}'`
if [[ ${container_id} == "" ]]; then
  if [[ ${docker_repository} != "" ]]
  then
      image_prifex=${docker_repository}/
  fi
  docker run -d -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 25672:25672 --name ${container_name} ${image_prifex}${app_image_tag}
else
  docker start ${container_name}
fi

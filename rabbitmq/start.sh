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
  esdir='/usr/share/elasticsearch'
  docker run -d -p 9200:9200 -p 9300:9300 -v `pwd`/config:${esdir}/config -v `pwd`/data:${esdir}/data -v `pwd`/logs:${esdir}/logs -v `pwd`/plugins:${esdir}/plugins --name ${container_name} ${image_prifex}${app_image_tag}
else
  docker start ${container_name}
fi

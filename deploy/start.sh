#!/usr/bin/env bash

source env.sh

if [[ ${docker_repository} != "" ]]
then
    image_prifex=${docker_repository}/
fi

if [ -e 'config/application.properties' ]
then
    echo Use exist config
else
    bash create.sh
fi

container_id=`docker ps -a | grep ${container_name} | awk '{print $1}'`
if [[ ${container_id} == "" ]]; then
  if [[ ${docker_repository} != "" ]]
  then
      image_prifex=${docker_repository}/
  fi
  docker run -d -p ${service_port}:8080 -v `dirname ~/a`/FILE_SERVER:/FILE_SERVER/SCHEDULED_EXPORT -v `pwd`/config:/config -v `pwd`/logs:/var/trackingapi/ --name ${container_name} ${image_prifex}${app_image_tag}
else
  docker start ${container_name}
fi

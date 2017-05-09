#!/usr/bin/env bash

app_image_name=see-world

if [[ ${app_image_version} == '' ]]
	then app_image_version=latest
fi

app_image_tag=${app_image_name}:${app_image_version}

image_base=frolvlad/alpine-oraclejdk8:slim
container_name=See_World_Server
service_port=8090
#docker_repository=docker.scct.lenovo.com:443
project_type=gradle
package_type=jar
build_image_cp_files=../build/libs/SeeWorld-0.0.1-SNAPSHOT.jar

framework_type=spring

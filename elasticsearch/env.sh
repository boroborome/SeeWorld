#!/usr/bin/env bash

app_image_name=elasticsearch
app_image_version=2.4.1
app_image_tag=${app_image_name}:${app_image_version}

container_name=elasticsearch_tracking
docker_repository=docker.scct.lenovo.com:443

#!/usr/bin/env bash
bash stop.sh

source env.sh

set_config(){
  config_file=$1
  var_name=${2//$/\$}
  var_value=$3

  temp_file=tmp
  sed "s/${var_name//\$/\\$/}=.*/${var_name}=${var_value//\//\/}/g" `pwd`/${config_file}>${temp_file}
  mv ${temp_file} ${config_file}
}

if [ -e 'config/application.properties' ]
then
    echo Use exist config
else

    # initialize a local config file
    mkdir config
    cp ../src/main/resources/application.properties config

    local_machine_ip=`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p' | awk 'NR<2'`

    config_file=config/application.properties
    set_config ${config_file} "spring.datasource.url" "jdbc:sqlserver://${local_machine_ip}:1433;databaseName=pcgscct_sit"
    set_config ${config_file} "spring.data.elasticsearch.clusterNodes" "${local_machine_ip}:9300"
    set_config ${config_file} "spring.data.elasticsearch.clusterName" elasticsearch
    set_config ${config_file} "cas.url.prefix" "http://${local_machine_ip}:8089/cas"
    set_config ${config_file} "app.service.home" "http://${local_machine_ip}:${service_port}/"
fi
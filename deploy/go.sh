#!/usr/bin/env bash


set_config(){
  config_file=$1
  var_name=$(sed_escape "$2")
  var_value=$(sed_escape "$3")

  temp_file=tmp
  sed "s/${var_name}=.*/${var_name}=${var_value}/g" `pwd`/${config_file}>${temp_file}
  mv ${temp_file} ${config_file}
}

add_config() {
  config_file=$1
  var_name=$2
  var_value=$3

  echo "$var_name=$var_value" >> $config_file
}
sed_escape(){
  tmp_str=$1
  tmp_str=${tmp_str//\$/\\$}
  tmp_str=${tmp_str//\//\\\/}
  echo ${tmp_str}
}

replace_config(){
  config_file=$1
  var_name=$(sed_escape "$2")
  var_value=$(sed_escape "$3")

  temp_file=tmp
  sed "s/${var_name}/${var_value}/g" `pwd`/${config_file}>${temp_file}
  mv ${temp_file} ${config_file}
}

collect_vars(){
  while [[ $1 == "-e" ]]
  do
    var_define=$2
    shift 2
    arr=(${var_define//=/ })
    var_name=${arr[0]}
    var_value=${arr[1]}
    export ${var_name}=${var_value}
  done
  export rest_parameters=$*
}

project_type=maven
package_type=war
build_code(){
  build_code_${project_type} $*
}
build_code_gradle(){
  cd ..
  ./gradlew clean
  ./gradlew bootRepackage ${package_type}
  cd -
}

build_code_maven(){
  cd ..
  mvn compile
  mvn ${package_type}:${package_type} -Pci
  cd -
}

build_code_npm(){
  registor_server=$1
  npm_repository=$2

  if [[ ${registor_server} != "" ]]
  then
    docker pull ${registor_server}/${compiler_image}
    docker tag ${registor_server}/${compiler_image} ${compiler_image}
  fi
  cd ..
  echo Clean project
  rm -rf dist
  rm -rf node_modules

  container_name=BuildTrackingUI
  docker ps -a | grep ${container_name} | awk '{print $1}' | xargs -n 1 docker rm -f
  docker run -i -v `pwd`:/project_org --name ${container_name} ${compiler_image} bash /project_org/deploy/go.sh build_with_angular_cli ${npm_repository}
  echo ErrorCode=$?
  cd -
}

build_with_angular_cli(){
  mkdir /project
  echo copy files...
  cp -r /project_org/* /project
  cd /project
  npm_repository=$1
  if [[ ${npm_repository} != "" ]]
  then
    echo "registry=${npm_repository}" > .npmrc
  fi
  echo start to install...
  echo npm install
  npm install
  echo start to build..
  npm run build
  cp -r dist /project_org
}

test(){
  test_${project_type} $*
}
test_gradle(){
    cd ..
    ./gradlew test -Dspring.profiles.active=test
    test_result=$?
    cd -
}

build_image(){
  imageBaseID=`docker images -q "${image_base}"`

  if [[ $1 != "" ]]
  then
    docker_repository=$1
  fi
  if [[ ${imageBaseID} == "" &&  ${docker_repository} != "" ]]
  then
    docker pull ${docker_repository}/${image_base}
    docker tag ${docker_repository}/${image_base} ${image_base}
  fi
  cp -r ${build_image_cp_files} .
  docker build -t ${app_image_tag} .
}
tag() {
  if [[ $1 != "" ]]
  then
    docker_repository=$1
  fi
  docker tag ${app_image_tag} ${docker_repository}/${app_image_tag}
}
push_image(){
  docker_repository=$1
  docker tag ${app_image_tag} ${docker_repository}/${app_image_tag}
  docker push ${docker_repository}/${app_image_tag}
}


build_archive(){
  mkdir trackingapi/configx
  cp ../build/libs/TrackingApi-0.0.1-SNAPSHOT.war trackingapi/TrackingApi.jar
}


init_install_path_remote() {
  install_path=$1
  arr=(${install_path//:/ })
  app_server=${arr[0]}
  app_home="${arr[1]}/${container_name}"
}
init_install_path_local() {
  app_server="localhost"
  app_home="$1/${container_name}"
}

clean_target_directory_remote() {
  ssh -T ${app_server}<<EOF
rm -rf "${app_home}"
mkdir -p "${config_dir}"
EOF
}
clean_target_directory_local() {
  rm -rf "${app_home}"
  mkdir -p "${config_dir}"
}

install_product_remote(){
  scp ${new_env_file} ${app_server}:"${app_home}/env.sh"
  scp start.sh ${app_server}:"${app_home}"
  scp stop.sh ${app_server}:"${app_home}"
  scp ${local_config_file} ${app_server}:"${config_dir}"
}
install_product_local(){
  cp ${new_env_file} "${app_home}/env.sh"
  cp start.sh "${app_home}"
  cp stop.sh "${app_home}"
  cp ${local_config_file} "${config_dir}"
}

start_application_remote(){
  ssh -T root@${app_server}<<EOF
cd ${app_home}
bash stop.sh
docker images | grep ${docker_repository}/${app_image_name} | awk '{print \$3}' | xargs -n 1 docker rmi -f
bash start.sh
EOF
}
start_application_local(){
  cd ${app_home}
  bash stop.sh
  docker images | grep ${docker_repository}/${app_image_name} | awk '{print $3}' | xargs -n 1 docker rmi -f
  bash start.sh
  cd -
}

config_file_spring(){
  config_dir="${app_home}/config"
  echo "${config_dir}"
  config_file="${config_dir}/application.properties"
  local_config_file=application.properties
  cp ../src/main/resources/application.properties ${local_config_file}
}
config_file_nginx(){
  config_dir="${app_home}/config"
  config_file="${config_dir}/nginx.conf"
  local_config_file=nginx.conf
  cp nginx.conf.template ${local_config_file}
}

deploy_product(){
  # install_path=root@10.100.78.141:/opt
  install_path=$1
  if [[ ${install_path} == *@* ]]
  then
    install_type="remote"
  else
    install_type="local"
  fi

  # init variables app_server and app_home
  # app_server=root@10.100.78.141
  # app_home=/opt/CAS
  init_install_path_${install_type} ${install_path}

  shift
  docker_repository=$1

  echo make config file "${app_home}/env.sh"
  env_file="${app_home}/env.sh"
  new_env_file=new_env.sh

  cp env.sh ${new_env_file}
  set_config ${new_env_file} docker_repository ${docker_repository}

  if [[ ${config_file_type} == "" ]]
  then
    config_file_type=${framework_type}
  fi
  extend_call config_file_${config_file_type} $*

  if [[ ${config_dir} == "" ]]
  then
    config_dir=${app_home}/config
  fi

  clean_target_directory_${install_type}
  install_product_${install_type}
  start_application_${install_type}
}

call_custom(){
  custom_fun=$1
  shift
  if [[ `(type -t ${custom_fun})` == 'function' ]]
  then
    ${custom_fun} $*
  else
    echo [INFO] No Function:${custom_fun}
  fi
}

# call a method 'M1' by extend logic
# 1.call M1_custom not call M1 if M1_custom exist
# 2.call M1_before if exist before call M1
# 3.call M1_after if exist after call M1
extend_call(){
  extend_able_fun=$1
  shift
  collect_vars $*
  if [[ `(type -t ${extend_able_fun}_custom)` == 'function' ]]
  then
    ${extend_able_fun}_custom ${rest_parameters}
  else
    call_custom ${extend_able_fun}_before ${rest_parameters} && ${extend_able_fun} ${rest_parameters} && call_custom ${extend_able_fun}_after ${rest_parameters}
  fi
}

cd `dirname $0`
source env.sh
if [ -e 'go_custom.sh' ]
then
  echo import custom shell go_custom.sh
  source 'go_custom.sh'
else
  echo not find custom shell go_custom.sh
fi

command=$1
shift
case ${command} in
  build_code)
    extend_call build_code $*
  ;;
  test)
    extend_call test $*
    exit ${test_result}
  ;;
  build_image)
    extend_call build_image $*
  ;;
  tag)
    tag $*
  ;;
  push)
    extend_call push_image $*
  ;;
  deploy)
    extend_call deploy_product $*
  ;;
   *)
   call_custom ${command} $*
  ;;
esac

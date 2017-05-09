#!/usr/bin/env bash

build_code_before() {
  cd ../webapp
  npm install -i && npm run build
  cd -
}

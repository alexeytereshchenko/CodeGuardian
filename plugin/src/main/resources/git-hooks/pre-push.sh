#!/bin/bash

if [[ $(git status --porcelain | grep api/src) ]]; then
  git stash -q --keep-index
  ./gradlew api:test
  ./gradlew clean build -x test
  status=$?
  git stash pop -q
  exit $status
fi

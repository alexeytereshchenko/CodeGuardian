#!/bin/bash

git stash -q --keep-index
./gradlew clean build -x test
status=$?
git stash pop -q
exit $status

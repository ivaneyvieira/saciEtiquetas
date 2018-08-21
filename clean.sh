#!/bin/bash

COMMENT="$1"

git config --global credential.helper 'cache --timeout=3600'
./gradlew --console=rich  clean
rm -vrf bin
rm -vrf out
rm -vrf src/main/webapp/VAADIN/widgetsets


if [[ "$COMMENT" == "" ]]
then
  git add .
  git commit -m"pull"
  git pull
  ./gradlew --console=rich  build
else
  git add .
  git commit -m"$COMMENT"
  git push
fi


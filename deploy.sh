#!/usr/bin/env bash

./gradlew clean bootRepackage

cf push -p build/libs/stubhub-scraper-1.0.0-SNAPSHOT.jar
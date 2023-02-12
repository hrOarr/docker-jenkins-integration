#!/bin/sh
docker build -t spring-test .
docker run -p 8080:8080 spring-test
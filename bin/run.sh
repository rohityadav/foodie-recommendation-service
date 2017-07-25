#!/usr/bin/env bash
mvn spring-boot:run -DPORT=${PORT} -Dspring.profiles.active=${PROFILE}

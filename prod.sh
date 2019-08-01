#!/bin/sh
./gradlew web:bootJar

scp web/build/libs/web-0.0.1-SNAPSHOT.jar root@64.188.16.34:/root/autossav/server/upload/upload.jar
ssh root@64.188.16.34 << remotessh
cd /root/autossav/server/upload
docker stop autossav_upload
docker run --rm -d -it --name autossav_upload --network mynetwork -p 9982:8097 -v /root/autossav/server/upload:/autossav_upload -w /autossav_upload openjdk java -jar upload.jar --logging.config=config/log4j2-dev.xml
exit
remotessh

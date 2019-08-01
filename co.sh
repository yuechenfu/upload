#!/bin/sh
./gradlew web:bootJar

scp web/build/libs/web-0.0.1-SNAPSHOT.jar root@172.168.2.16:/root/freitx/server/upload/upload.jar
ssh root@172.168.2.16 << remotessh
cd /root/freitx/server/upload
docker stop freitx_upload
docker run --privileged --rm -d -it --name freitx_upload --network mynetwork -p 9882:9882 -v /root/freitx/server/upload:/freitx_upload -w /freitx_upload openjdk java -jar upload.jar
exit
remotessh

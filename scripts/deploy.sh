#!/bin/bash
BUILD_JAR=$(ls /home/robocar/action/BE/robocar/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/robocar/deploy.log

echo "> build 파일 복사" >> /home/robocar/deploy.log
DEPLOY_PATH=/home/robocar/action/BE/robocar/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/robocar/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/robocar/deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/robocar/deploy.log
nohup java -jar $DEPLOY_JAR >> /home/robocar/nohup.log 2>/home/robocar/deploy_err.log &

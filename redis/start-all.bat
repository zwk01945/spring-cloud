@echo off
title redis-9001;
start  /D  "D:\projectsources\stage\redis\9001\" redis-server.exe redis.windows.conf
title redis-9002;
start  /D "D:\projectsources\stage\redis\9002\" redis-server.exe redis.windows.conf
title redis-9003;
start  /D  "D:\projectsources\stage\redis\9003\" redis-server.exe redis.windows.conf
title redis-9004;
start  /D  "D:\projectsources\stage\redis\9004\" redis-server.exe redis.windows.conf
title redis-9005;
start  /D  "D:\projectsources\stage\redis\9005\" redis-server.exe redis.windows.conf
title redis-9006;
start /D "D:\projectsources\stage\redis\9006\" redis-server.exe redis.windows.conf
ping /n 1 /w 5000 1.0.0.1>nul
start /wait /D "D:\projectsources\stage\redis\" redis-trib.bat




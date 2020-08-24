@echo off
title redis-9001;
start  /D  "D:\projectsources\stage\redis\9001\" redis-cli.exe -p 9001 shutdown

title redis-9002;
start  /D "D:\projectsources\stage\redis\9002\" redis-cli.exe -p 9002 shutdown

title redis-9003;
start  /D  "D:\projectsources\stage\redis\9003\" redis-cli.exe -p 9003 shutdown

title redis-9004;
start  /D  "D:\projectsources\stage\redis\9004\" redis-cli.exe -p 9004 shutdown

title redis-9005;
start  /D  "D:\projectsources\stage\redis\9005\" redis-cli.exe -p 9005 shutdown

title redis-9006;
start /D "D:\projectsources\stage\redis\9006\" redis-cli.exe -p 9006 shutdown
cmd.exe exit





#!/bin/bash
curl http://192.168.2.157:5000/gateway/refresh
curl http://192.168.2.157:5000/minio/refresh
curl http://192.168.2.157:5000/msgpush/refresh
echo -e "\n"
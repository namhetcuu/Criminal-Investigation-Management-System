#!/bin/bash

# Thiết lập biến môi trường
export DB_HOST=localhost
export DB_PORT=3306
export DB_USER=root
export DB_PASS=123456
export REDIS_HOST=localhost
export KAFKA_HOST=localhost:9092

# Chạy ứng dụng
java -jar build/libs/evidence-service.jar

#!/bin/sh

rm -fvR target/*

./compile-no-test.sh

java -jar target/OnlineStoreAPI-0.0.1.jar



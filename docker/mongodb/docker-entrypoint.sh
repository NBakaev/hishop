#!/bin/bash
mongod --dbpath "/data/db" --storageEngine wiredTiger --config "/etc/mongod.conf"

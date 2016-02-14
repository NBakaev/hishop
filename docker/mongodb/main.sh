#!/bin/bash

build (){
	docker build --tag=nbakaev/mongodb .
}

push (){
	docker push nbakaev/mongodb
}

run (){
	# create folder '/home/mongodb/data' on host
	docker run -d -v /home/mongodb/data:/data/db -P -e "SERVICE_TAGS=master" nbakaev/mongodb
}

# execute function from comand line such as `./main.js build`
$@

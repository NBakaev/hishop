#!/bin/bash

build (){
	docker build --tag=nbakaev/hishop .
}

push (){
	docker push nbakaev/hishop
}

run (){
	docker run -d nbakaev/hishop
}

# execute function from comand line such as `./main.js build`
$@

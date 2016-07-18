#!/bin/sh

function build_server() {
	gradle -b battle/build.gradle clean updateMessage build jar
	gradle -b match/build.gradle clean updateMessage build jar
	gradle -b login/build.gradle clean updateMessage build jar
	gradle -b db/build.gradle clean updateMessage build jar
}

function start_server() {
	if [ $# -eq 1 ] && [ $1 == "build" ]; then
		build_server
	fi

	java -jar db/build/libs/db.jar &
	java -jar match/build/libs/match.jar &
	java -jar login/build/libs/login.jar &
	java -jar battle/build/libs/battle.jar &
}

function stop_server() {
	kill -9 `ps | grep java | grep "battle.jar\|login.jar\|db.jar\|match.jar" | cut -d " " -f1`
}

function print_usage(){
	echo "sh run.sh start|stop|build"
}

case $1 in
    start | --start)
		start_server $2
    	;;
    stop|--stop)
		stop_server
	    ;;
    build|--build)
		build_server
	    ;;
    *)
	    print_usage
	    ;;
esac
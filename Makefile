.PHONY: all build run

all: build

build:
	mvn clean compile package
	mkdir -p bin
	cp target/*.jar bin/
	chmod +x bin/*.jar

run:
	mvn exec:java
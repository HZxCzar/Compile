.PHONY: all build run

all: build

build:
	mvn clean compile

run:
	mvn exec:java
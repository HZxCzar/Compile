.PHONY: all build run

all: build

build:
	mvn clean compile package
	mkdir -p bin
	cp target/*.jar bin/
	chmod +x bin/*.jar

run:
	java -cp bin/my-project-1.0.0.jar Compiler.Src.Compiler
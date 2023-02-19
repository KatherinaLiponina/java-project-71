run-dist:
	./build/install/app/bin/app
all:
	gradle installDist
	./build/install/app/bin/app
.PHONY build: src/java/hexlet/code/App.java
	./gradlew clean build
report:
	./gradlew jacocoTestReport

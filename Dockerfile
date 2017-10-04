FROM java:8-jdk-alpine

MAINTAINER Code Climate

RUN adduser -u 9000 -D app
VOLUME /code

# Create a writeable directory for the code
RUN mkdir -p /code-read-write
RUN chown -R app:app /code-read-write
RUN chmod -R 777 /code-read-write

COPY . /usr/src/app
RUN chown -R app:app /usr/src/app

WORKDIR /usr/src/app
RUN ./gradlew clean build

# Increase Java memory limits
ENV JAVA_OPTS="-XX:+UseParNewGC -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -Xss4096k"

# Switch to app user, copy code to writable directory, and run the engine
USER app
ENTRYPOINT []
WORKDIR /code-read-write
CMD cp -R /code/* . && /usr/src/app/build/codeclimate-sonar /code-read-write

FROM maven:3.3.9-jdk-8-alpine

MAINTAINER Code Climate

# Create user and app directory
RUN adduser -u 9000 -D app
COPY . /usr/src/app
RUN chown -R app:app /usr/src/app

# Package the app with Maven
WORKDIR /usr/src/app
RUN mvn package

# Unzip the packaged app
RUN mkdir /usr/src/app/dest
RUN unzip /usr/src/app/target/sonarlint-cli-*.zip \
  -d /usr/src/app/dest
RUN cp -R /usr/src/app/dest/sonarlint-cli-*/* \
  /usr/src/app/dest

# Specify the /code volume
# as needed by CC
VOLUME /code

# Create a writeable directory for the code
RUN mkdir -p /code-read-write
RUN chown -R app:app /code-read-write
RUN chmod -R 777 /code-read-write

# Increase Java memory limits
ENV JAVA_OPTS="-XX:+UseParNewGC -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -Xss4096k"

# Switch to app user, copy code to writable
# directory, and run the engine
USER app
ENTRYPOINT []
WORKDIR /code-read-write
CMD cp -R /code/* . && \
  /usr/src/app/dest/bin/sonarlint \
  --src '**/*.{js,py,php,java}'

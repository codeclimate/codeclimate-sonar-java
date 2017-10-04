FROM java:8-jdk-alpine

MAINTAINER Code Climate

USER root
RUN adduser -u 9000 -D app
VOLUME /code

# Create a writeable directory for the code as sonar needs to
# create a `.sonarlint` directory during analysis
RUN mkdir -p /code-read-write && \
      chown -R app:app /code-read-write && \
      chmod -R 777 /code-read-write

ENV GRADLE_USER_HOME=/opt/gradle
RUN mkdir -p $GRADLE_USER_HOME && \
      chown -R app:app $GRADLE_USER_HOME && \
      chmod g+s $GRADLE_USER_HOME

COPY . /usr/src/app
RUN chown -R app:app /usr/src/app

WORKDIR /usr/src/app
RUN ./gradlew clean build compileTest -x test && \
      find /opt -name "*.zip" | xargs rm -f

# Increase Java memory limits
ENV JAVA_OPTS="-XX:+UseParNewGC -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -Xss4096k"

# Switch to app user, copy code to writable directory, and run the engine
USER app
ENTRYPOINT []
WORKDIR /code-read-write
CMD cp -R /code/* . && /usr/src/app/build/codeclimate-sonar /code-read-write

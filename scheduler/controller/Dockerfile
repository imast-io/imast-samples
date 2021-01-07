FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir -p /opt/imast/controller

EXPOSE 8801

COPY target/controller.jar /opt/imast/controller

ENTRYPOINT exec java $JAVA_OPTS -jar /opt/imast/controller/controller.jar


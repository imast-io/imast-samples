FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir -p /opt/apps/eureka

EXPOSE 28000

COPY target/eureka.jar /opt/apps/eureka

ENTRYPOINT exec java $JAVA_OPTS -jar /opt/apps/eureka/eureka.jar


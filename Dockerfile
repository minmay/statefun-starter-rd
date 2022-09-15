# docker build -t rd.mvillalobos/statefun/ping:latest -t rd.mvillalobos/statefun/ping:0.0.1-SNAPSHOT .
# docker run -p 8080:8080 rd.mvillalobos/statefun/ping:latest

FROM gradle:6.9.1-jdk11 as build
LABEL vendor="Marco Villalobos"
LABEL app="ping"

ENV APP_DIR /opt/rd/statefun/ping/
ENV GRADLE_OPTS -Dorg.gradle.daemon=false
ENV JVM_OPTIONS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

WORKDIR $APP_DIR
ADD build.gradle $APP_DIR/
ADD settings.gradle $APP_DIR/
RUN mkdir -p $APP_DIR/src/main
ADD src/main $APP_DIR/src/main
RUN gradle build

FROM azul/zulu-openjdk:11 AS deployment

EXPOSE 8080
EXPOSE 5005
ENV SERVER_PORT 8080
ENV APP_DIR /opt/rd/statefun/ping/
WORKDIR $APP_DIR

COPY --from=build $APP_DIR/build/libs/ping.jar $APP_DIR/build/libs/
ADD run.sh $APP_DIR/
RUN chmod 755 $APP_DIR/run.sh

ENTRYPOINT ["/bin/bash"]
CMD ["./run.sh"]
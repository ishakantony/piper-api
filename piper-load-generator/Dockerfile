FROM amazoncorretto:17-alpine-jdk as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY .springjavaformatconfig .springjavaformatconfig
COPY checkstyle.xml checkstyle.xml

COPY piper-backend/ ./piper-backend/
COPY piper-common/ ./piper-common/
COPY piper-load-generator/ ./piper-load-generator/

ARG MODULE=piper-load-generator

RUN ./mvnw install -DskipTests -pl ${MODULE} -am
RUN mkdir -p ${MODULE}/target/dependency && (cd ${MODULE}/target/dependency; jar -xf ../*.jar)

FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
ARG MODULE=piper-load-generator
ARG DEPENDENCY=/workspace/app/${MODULE}/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","app.piper.piper.loadgenerator.PiperLoadGeneratorApplication"]

RUN addgroup -S piper && adduser -S piper -G piper
USER piper

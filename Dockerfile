# 빌드 스테이지 - 자세한 로그 출력
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# 디버그 모드로 실행하여 자세한 로그 확인
RUN mvn clean package -DskipTests -X

# 실행 스테이지
FROM tomcat:9.0-jdk17-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
# 빌드 스테이지 - 최신 Maven 이미지 사용
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 실행 스테이지
FROM tomcat:9.0-jdk17-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
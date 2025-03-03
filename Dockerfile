FROM tomcat:9.0-jdk17-openjdk

# 기존 ROOT 애플리케이션 제거
RUN rm -rf /usr/local/tomcat/webapps/ROOT/*

# 정확한 WAR 파일 이름으로 복사
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
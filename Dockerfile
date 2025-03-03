FROM tomcat:9.0-jdk17-openjdk

# 기존 ROOT 디렉토리 제거
RUN rm -rf /usr/local/tomcat/webapps/ROOT/*

# WAR 파일 복사
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# WAR 파일 언패킹
RUN unzip /usr/local/tomcat/webapps/ROOT.war -d /usr/local/tomcat/webapps/ROOT \
    && rm /usr/local/tomcat/webapps/ROOT.war

# 정적 리소스 경로 수정
RUN mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/views/auth \
    && mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/static/css/auth

EXPOSE 8080

CMD ["catalina.sh", "run"]
FROM tomcat:9.0-jdk17-openjdk

#컨테이너 내에서 실행할 명령어를 지정합니다.
#기본으로 제공되는 Tomcat의 샘플 웹앱들을 모두 삭제합니다.
RUN rm -rf /usr/local/tomcat/webapps/*

# WAR 파일을 webapps 디렉토리에 복사
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# 8080 포트 노출
EXPOSE 8080

# Tomcat 실행
CMD ["catalina.sh", "run"]
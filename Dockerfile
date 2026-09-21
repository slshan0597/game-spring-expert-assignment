# 1. 자바 21 환경을 기본 베이스로 가져옵니다.
FROM eclipse-temurin:21-jdk-jammy

# 2. 컨테이너 내부에서 작업할 폴더를 지정합니다.
WORKDIR /app

# 3. 빌드된 스프링 부트 jar 파일을 컨테이너 내부로 복사합니다.
COPY build/libs/*-SNAPSHOT.jar app.jar

# 4. 컨테이너가 켜질 때 스프링 부트를 실행하는 명령어입니다.
ENTRYPOINT ["java", "-jar", "app.jar"]

# User System
Spring Boot 기반의 JWT를 활용한 사용자 인증 시스템입니다. 

---

## 주요 기능
- 회원가입
- 로그인
- 회원 권한 변경

---

## API 명세 (API Documentation)

배포 주소 : 

| 메서드   | 엔드포인트         | 설명           | 요청 데이터       | 응답 데이터            | 권한 |
|--------  |--------------------|----------------|------------------|-----------------------|-------------------|
| POST   | /signup       | 회원가입     | 아이디, 비밀번호, 닉네임     | 생성된 유저 정보 JSON | 
| POST    | /signin    | 로그인| 아이디, 비밀번호         | JWT 토큰 JSON   |
| PATCH    | /admin/users/{username}/roles    | 권한 변경    | 권한     | 수정된 유저 정보 JSON |ADMIN

---

## 기술 스택 
### 언어
- Java 17

### 프레임워크 및 라이브러리
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security + JWT
- springdoc-openapi (Swagger)

### 빌드 도구
- Gradle

### 테스트
- JUnit 5

---
## Swagger

Swagger UI Local 주소 : http://localhost:8080/swagger-ui/index.html

Swagger UI 배포 주소 : 

---
## 설치 및 실행 방법

```bash
# 레포지토리 클론
git clone https://github.com/username/project.git

# 의존성 설치 및 빌드 (Gradle 기준)
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```
### 테스트 실행
```bash
./gradlew test
```

# 🏢 Board - AI 기반 부동산 경매 플랫폼
**Spring Boot + MongoDB + React 기반의 실시간 부동산 경매 시스템**

# 📋 목차
* ~[🎯 프로젝트 개요](https://www.google.com/search?q=%23-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B0%9C%EC%9A%94)
* ~[✨ 주요 기능](https://www.google.com/search?q=%23-%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5)
* ~[🛠 기술 스택](https://www.google.com/search?q=%23-%EA%B8%B0%EC%88%A0-%EC%8A%A4%ED%83%9D)
* ~[🏗 아키텍처](https://www.google.com/search?q=%23-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98)
* ~[📁 프로젝트 구조](https://www.google.com/search?q=%23-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B5%AC%EC%A1%B0)
* ~[🚀 시작하기](https://www.google.com/search?q=%23-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0)
* ~[📡 API 명세](https://www.google.com/search?q=%23-api-%EB%AA%85%EC%84%B8)
* ~[🎨 개발 방법론](https://www.google.com/search?q=%23-%EA%B0%9C%EB%B0%9C-%EB%B0%A9%EB%B2%95%EB%A1%A0)
* ~[📊 데이터베이스 스키마](https://www.google.com/search?q=%23-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%8A%A4%ED%82%A4%EB%A7%88)~
* ~[🧪 테스트](https://www.google.com/search?q=%23-%ED%85%8C%EC%8A%A4%ED%8A%B8)
* ~[📝 개발 로그](https://www.google.com/search?q=%23-%EA%B0%9C%EB%B0%9C-%EB%A1%9C%EA%B7%B8)
* ~[🤝 기여하기](https://www.google.com/search?q=%23-%EA%B8%B0%EC%97%AC%ED%95%98%EA%B8%B0)
* ~[📄 라이선스](https://www.google.com/search?q=%23-%EB%9D%BC%EC%9D%B4%EC%84%A0%EC%8A%A4)
* ~[📞 Contact](https://www.google.com/search?q=%23-contact)

⠀
# 🎯 프로젝트 개요
**Board**는 **AI 분석 기반** 부동산 경매 플랫폼입니다.
실시간 시세 분석, 개인화 추천 시스템을 통해 사용자에게 최적의 투자 기회를 제공하는 것을 목표로 합니다.
### ✔ 핵심 가치
* **🤖 AI 기반 분석** – ML 기반 가치 평가
* **📊 실시간 시세/거래 정보 제공**
* **🎯 개인화 추천 시스템**
* **🔒 JWT 기반 인증 + 보안 강화**

⠀
# ✨ 주요 기능
### ✅ 1단계 (현재 완료)
* **회원가입 / 로그인 / 로그아웃**
* **JWT 인증** + 토큰 자동 갱신
* 사용자 **프로필 관리**
* **로그인 이력 추적** (비동기 이벤트 기반)

⠀🚧 2단계 (개발 예정)
* 경매 목록 조회 (Pagination)
* 경매 상세
* 입찰 기능
* **AI 기반 추천**
* **WebSocket 기반 실시간 경매 현황**

⠀
# 🛠 기술 스택
### Backend
| **카테고리** | **기술** | **버전** |
|---|---|---|
| Framework | **Spring Boot** | 3.5.7 |
| Language | **Java** | 17 |
| Database | **MongoDB** | 7.0 |
| Security | **Spring Security + JWT** |  |
| Build Tool | **Gradle** |  |
### Frontend
| **카테고리** | **기술** | **버전** |
|---|---|---|
| Framework | **React** | 18.3 |
| Language | **TypeScript** | 5.6 |
| Styling | **Tailwind CSS** |  |
| Networking | **Axios**, React Router v6 |  |
### Infrastructure
* **Docker**
* Swagger/OpenAPI (예정)

⠀
# 🏗 아키텍처
### 시스템 아키텍처
~~~
┌─────────────────────────────────────────────────────┐
│                   Client (React)                    │
│  ┌──────────────┐  ┌──────────────┐  ┌───────────┐ │
│  │   Pages      │  │  Components  │  │   Hooks   │ │
│  └──────────────┘  └──────────────┘  └───────────┘ │
└────────────────────────┬────────────────────────────┘
                         │ REST API
                         ▼
┌─────────────────────────────────────────────────────┐
│              Spring Boot Application                │
│  ┌──────────────────────────────────────────────┐  │
│  │         Presentation Layer (Controller)      │  │
│  └────────────────────┬─────────────────────────┘  │
│                       ▼                             │
│  ┌──────────────────────────────────────────────┐  │
│  │    Application Layer (Command/Query/Event)   │  │
│  └────────────────────┬─────────────────────────┘  │
│                       ▼                             │
│  ┌──────────────────────────────────────────────┐  │
│  │         Domain Layer (Entity/Value)          │  │
│  └────────────────────┬─────────────────────────┘  │
│                       ▼                             │
│  ┌──────────────────────────────────────────────┐  │
│  │    Infrastructure Layer (Repository/DB)      │  │
│  └──────────────────────────────────────────────┘  │
└────────────────────────┬────────────────────────────┘
                         ▼
                     MongoDB
~~~                     
### CQRS + DDD 구조
* **Presentation** → **Application** → **Domain** ← **Infrastructure**
* **의존성 역전 원칙 (DIP)** 적용

⠀
# 📁 프로젝트 구조
### 📌 Backend
~~~
backend/
├── Auth/
│   ├── Application/
│   │   ├── Command/
│   │   │   ├── LoginCommand.java
│   │   │   ├── SignUpCommand.java
│   │   │   ├── RefreshTokenCommand.java
│   │   │   └── handler/
│   │   │       ├── LoginCommandHandler.java
│   │   │       ├── SignUpCommandHandler.java
│   │   │       └── RefreshTokenCommandHandler.java
│   │   ├── Event/
│   │   │   └── UserLoginEventHandler.java
│   │   └── Dto/
│   ├── Domain/
│   │   ├── Model/
│   │   │   ├── Entity/
│   │   │   │   └── User.java
│   │   │   └── Value/
│   │   ├── Event/
│   │   └── Repository/
│   ├── Infrastructure/
│   │   ├── Entity/
│   │   ├── Repository/
│   │   └── Security/
│   └── Presentation/
│       └── Controller/
└── global/
~~~

### 📌 Frontend
~~~
frontend/
├── api/
├── components/
├── hooks/
├── pages/
├── type/
└── App.tsx
~~~

# 🚀 시작하기
### 🔧 Prerequisites
* **Java 17+**
* **Node 18+**
* **Docker**
* **MongoDB 7.0+**

⠀🖥 Backend Setup
Bash

# 1. MongoDB 실행
docker-compose up -d

# 2. Spring Boot 앱 실행
cd backend
./gradlew bootRun
### 💻 Frontend Setup
Bash

cd frontend
npm install
npm run dev
### 🔐 Environment Variables
**Backend (application.properties)**
~~~ Properties

spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=board
spring.data.mongodb.username=admin
spring.data.mongodb.password=password123
spring.data.mongodb.authentication-database=admin

jwt.secret=your-secret-key
jwt.access-token-validity=1800000
jwt.refresh-token-validity=604800000
**Frontend (.env)**
VITE_API_BASE_URL=http://localhost:8080/api
~~~

# 📡 API 명세
| **기능** | **HTTP Method** | **URI** | **설명** |
|---|---|---|---|
| **회원가입** | POST | /api/auth/signup | 사용자 등록 |
| **로그인** | POST | /api/auth/login | JWT 발급 |
| **토큰 갱신** | POST | /api/auth/refresh | Refresh Token으로 Access Token 갱신 |
| **로그아웃** | POST | /api/auth/logout | 토큰 무효화 |
### 회원가입
POST /api/auth/signup
``` json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "홍길동",
  "phone": "01012345678",
  "address": "서울시 강남구"
}
```

### 로그인
POST /api/auth/login
 ``` json
{
  "email": "user@example.com",
  "password": "password123"
}
```
``` json
{
  "email": "user@example.com",
  "password": "password123"
}
```
### 토큰 갱신
POST /api/auth/refresh (Request Body 없음)
### 로그아웃
POST /api/auth/logout
**Header:** Authorization: Bearer {accessToken}

# 🎨 개발 방법론
### Domain-Driven Design (DDD)
도메인 모델을 중심으로 비즈니스 로직을 설계합니다.
``` java
public class User {
    private final UserId id;
    private Email email;
    private UserStatus status;

    public static User register(Email email, String password) {
        return new User(...);
    }

    public void validateLoginAttempt() {
        if (!this.status.isActive()) {
            throw new UserNotActiveException();
        }
    }
}
```
### CQRS (Command Query Responsibility Segregation)
명령(쓰기)과 조회(읽기)의 책임을 분리하여 복잡성을 줄이고 확장성을 높입니다.
``` java
@Service
public class LoginCommandHandler {
    public LoginResponse handle(LoginCommand command) { 
        // 인증 및 토큰 발급 로직
    }
}
```
### Event-Driven Architecture (EDA)
사용자 로그인 이력 추적과 같은 비동기 처리에 이벤트를 활용합니다.
``` java
// 이벤트 발행
eventPublisher.publishEvent(new UserLoginEvent(userId, email));

// 이벤트 리스너 (비동기 처리)
@Async
@EventListener
public void handleUserLogin(UserLoginEvent event) { 
    // 로그인 이력 저장 등
}

```

# 📊 데이터베이스 스키마
### User Collection (MongoDB)
| **필드** | **타입** | **설명** |
|---|---|---|
| _id | ObjectId | 고유 ID |
| email | String | 사용자 이메일 (Unique) |
| password | String | 암호화된 비밀번호 |
| name | String | 이름 |
| phone | String | 전화번호 |
| address | String | 주소 |
| status | String | 사용자 상태 (ACTIVE, INACTIVE) |
| created_at | Date | 생성 일시 |
| last_login_at | Date | 마지막 로그인 일시 |
``` json
{
  "_id": "675508f2ef31e43c18a39e8f",
  "email": "user@example.com",
  "password": "...bcrypt...",
  "name": "홍길동",
  "phone": "01012345678",
  "address": "서울시 강남구",
  "status": "ACTIVE",
  "created_at": "2025-12-08T00:00:00Z",
  "last_login_at": "2025-12-08T10:30:00Z"
}
```

# 🧪 테스트
### Backend
``` bash
./gradlew test
```
### Frontend
``` bash
npm run test
```

# 📝 개발 로그
### Phase 1 (2025.12.08) - ✅ 완료
* **JWT 인증** 구현 완료
* 로그인 이력 **비동기 이벤트 처리**
* React **인증 상태 관리**
* Axios **인터셉터** (자동 토큰 갱신)

⠀Phase 2 (예정) - 🚧 개발 예정
* Auction Aggregate 도메인 모델 설계
* 경매 목록/상세/입찰 기능 구현
* AI 추천 시스템 통합

⠀
# 🤝 기여하기
프로젝트 기여를 환영합니다!
Bash

git checkout -b feature/AmazingFeature
git commit -m "Add some AmazingFeature"
git push origin feature/AmazingFeature
PR을 보내주시면 검토 후 반영하겠습니다.

# 📄 라이선스
**MIT License**

# 📞 Contact
* **Email**: your-email@example.com
* **GitHub**: ~[https://github.com/yourusername/board](https://github.com/yourusername/board)~
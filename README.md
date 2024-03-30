## 게시판 프로젝트

이 프로젝트는 Spring Boot와 Bootstrap을 사용하여 만든 간단한 게시판입니다. 사용자는 게시글을 작성하고 댓글을 작성할 수 있으며, 게시글은 다양한 기준으로 정렬되고 검색할 수 있습니다. 또한 사용자는 게시글을 추천할 수 있습니다.

### 주요 기능

1. **게시글 작성**: 사용자는 제목과 내용을 포함한 게시글을 작성할 수 있습니다.
2. **댓글 작성**: 사용자는 게시글에 댓글을 작성할 수 있습니다.
3. **게시글 정렬**: 게시글은 작성일, 조회수, 추천수 등의 기준으로 정렬될 수 있습니다.
4. **게시글 검색**: 사용자는 특정 키워드로 게시글을 검색할 수 있습니다.
5. **게시글 추천**: 사용자는 게시글에 대해 추천할 수 있습니다.

### 기술 스택

- **Spring Boot**: 웹 애플리케이션을 구축하는 데 사용되었습니다.
- **Spring Security**: 사용자 인증 및 권한 부여를 위해 사용되었습니다.
- **Thymeleaf**: 서버 측 템플릿 엔진으로 사용되었습니다.
- **Bootstrap**: 프론트엔드 디자인 및 레이아웃을 위해 사용되었습니다.
- **MySQL**: 게시글 및 댓글 데이터베이스를 저장하기 위해 사용되었습니다.

### 사용 방법

1. MySQL 데이터베이스를 설정하고 스프링 부트 애플리케이션을 실행합니다.
2. 브라우저에서 `localhost:8080`으로 이동하여 게시판에 접속합니다.
3. 사용자 등록 또는 로그인 후 게시글을 작성하거나 댓글을 작성할 수 있습니다.
4. 게시글은 정렬 및 검색 기능을 통해 필요한 정보를 빠르게 찾을 수 있습니다.
5. 원하는 게시글에 추천을 눌러 다른 사용자에게 인기 있는 게시글을 알릴 수 있습니다.

### 실행 방법

1. 소스 코드를 클론하거나 다운로드하여 로컬 환경에 가져옵니다.
2. MySQL 데이터베이스를 설정하고 `application.properties` 파일에 연결 정보를 구성합니다.
3. 스프링 부트 애플리케이션을 실행합니다.
4. 브라우저에서 `localhost:8080`으로 이동하여 게시판을 사용합니다.

### 프로젝트 구조

- `src/main/java/com/example/StudySpring`: 백엔드 코드가 포함된 패키지입니다.
- `src/main/resources/templates`: 프론트엔드 HTML 템플릿이 포함된 디렉토리입니다.
- `src/main/resources/static`: 정적 리소스(이미지, CSS, JavaScript 등)가 포함된 디렉토리입니다.
- `src/main/resources/application.properties`: 애플리케이션 구성을 위한 속성 파일입니다.

### 참고 사항

- 이 프로젝트는 학습 목적으로 만들어졌으며, 실제 운영환경에 적합하게 보안 및 성능 최적화가 필요합니다.
- 사용자 데이터의 보안을 위해 비밀번호 해싱 및 다른 보안 관련 사항을 구현해야 할 수 있습니다.
- 프로젝트에 필요한 추가 기능이나 개선 사항이 있으면 적극적으로 구현하고 개선해 나가는 것이 좋습니다.

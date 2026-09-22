# notice-project frontend (React)

React 19 + Vite 8 + React Router + Zustand + Axios + Tailwind CSS 4 기반 공지사항 프런트엔드.
검색/페이징은 백엔드가 아닌 이 프로젝트(React)에서 처리합니다.

이전에 만든 Vue 버전은 `../frontend_Vue`에 남아 있습니다.

## 실행 전 준비

- backend(`C:\JUN\Spring\ch08\backend\notice`)를 `8081` 포트로 먼저 실행하세요.
  - `application.yaml` 기준 PostgreSQL `business` DB, `postgres` / `1004` 계정이 필요합니다.
- 별도 CORS 설정이 backend에 없으므로, 이 프로젝트는 Vite dev proxy(`/api` → `http://localhost:8081`)로 통신합니다.

## 설치 및 실행

```bash
cd frontend
npm install
npm run dev
```

`http://localhost:5173` 에서 접속합니다.

## 빌드

```bash
npm run build
npm run preview
```

## 알아두면 좋은 점 (백엔드 실제 코드 확인 결과)

- API 계약: `GET/POST /api/notices`, `GET/PUT/DELETE /api/notices/{id}` (PRD와 동일)
- 서버 포트는 PRD 예시(`8080`)와 달리 실제로는 `8081`입니다. 프런트는 이 값을 기준으로 proxy를 구성했습니다.
- 오류 응답 바디는 정상적으로는 `{"status", "message"}`를 기대하지만, `ApiExceptionHandler`의 일부 메서드에 오탈자가 있어 `{"status", "massage"}` 형태로 내려오는 경우가 있습니다. 프런트(`src/api/axios.js`)는 두 키를 모두 처리하도록 만들었습니다.
- `findById`에서 존재하지 않는 ID 조회 시 `IllegalArgumentException`을 던지지만, `ApiExceptionHandler`는 `IllegalAccessException`만 404로 매핑하고 있어 실제로는 500으로 응답할 수 있습니다. 프런트는 이 경우도 공통 오류 메시지로 처리하지만, 정확한 404 응답을 원하시면 백엔드 예외 타입을 맞춰주는 수정이 필요합니다(요청하시면 처리해 드릴게요).

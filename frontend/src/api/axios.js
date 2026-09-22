import axios from 'axios'

// 공통 API 오류 처리 위치를 이 한 곳으로 유지한다 (NFR-03).
const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const data = error.response?.data
    // 백엔드 ApiExceptionHandler는 정상 케이스에서 "message"를,
    // 오탈자가 있는 일부 케이스에서 "massage" 키를 내려줄 수 있어 둘 다 대응한다.
    const message = data?.message || data?.massage || '요청 처리 중 오류가 발생했습니다.'
    error.friendlyMessage = message
    return Promise.reject(error)
  }
)

export default api

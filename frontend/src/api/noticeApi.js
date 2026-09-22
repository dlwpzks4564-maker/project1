import api from './axios'

// Spring Boot 계약 (검색/페이징 파라미터 없음, 전체 목록만 반환)
export const noticeApi = {
  getList() {
    return api.get('/notices')
  },
  getDetail(id) {
    return api.get(`/notices/${id}`)
  },
  create(payload) {
    return api.post('/notices', payload)
  },
  update(id, payload) {
    return api.put(`/notices/${id}`, payload)
  },
  remove(id) {
    return api.delete(`/notices/${id}`)
  }
}

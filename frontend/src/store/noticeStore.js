import { create } from 'zustand'
import { noticeApi } from '../api/noticeApi'

export const useNoticeStore = create((set) => ({
  notices: [],
  currentNotice: null,
  loading: false,
  error: null,

  fetchNotices: async () => {
    set({ loading: true, error: null })
    try {
      const { data } = await noticeApi.getList()
      set({ notices: data })
    } catch (e) {
      set({ error: e.friendlyMessage || '목록을 불러오지 못했습니다.' })
    } finally {
      set({ loading: false })
    }
  },

  fetchNotice: async (id) => {
    set({ loading: true, error: null })
    try {
      const { data } = await noticeApi.getDetail(id)
      set({ currentNotice: data })
      return data
    } catch (e) {
      set({ error: e.friendlyMessage || '공지사항을 불러오지 못했습니다.' })
      throw e
    } finally {
      set({ loading: false })
    }
  },

  createNotice: async (payload) => {
    set({ loading: true, error: null })
    try {
      const { data } = await noticeApi.create(payload)
      return data
    } catch (e) {
      set({ error: e.friendlyMessage || '등록에 실패했습니다.' })
      throw e
    } finally {
      set({ loading: false })
    }
  },

  updateNotice: async (id, payload) => {
    set({ loading: true, error: null })
    try {
      const { data } = await noticeApi.update(id, payload)
      return data
    } catch (e) {
      set({ error: e.friendlyMessage || '수정에 실패했습니다.' })
      throw e
    } finally {
      set({ loading: false })
    }
  },

  deleteNotice: async (id) => {
    set({ loading: true, error: null })
    try {
      await noticeApi.remove(id)
    } catch (e) {
      set({ error: e.friendlyMessage || '삭제에 실패했습니다.' })
      throw e
    } finally {
      set({ loading: false })
    }
  }
}))

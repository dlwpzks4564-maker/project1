import { useEffect } from 'react'
import { Link, Navigate, useNavigate, useParams } from 'react-router-dom'
import { useNoticeStore } from '../store/noticeStore'

function formatDate(value) {
  if (!value) return ''
  return value.replace('T', ' ').slice(0, 16)
}

function isValidId(value) {
  const n = Number(value)
  return Number.isInteger(n) && n > 0
}

export default function NoticeDetailView() {
  const { id } = useParams()
  const navigate = useNavigate()

  const currentNotice = useNoticeStore((s) => s.currentNotice)
  const loading = useNoticeStore((s) => s.loading)
  const error = useNoticeStore((s) => s.error)
  const fetchNotice = useNoticeStore((s) => s.fetchNotice)
  const deleteNotice = useNoticeStore((s) => s.deleteNotice)

  useEffect(() => {
    if (isValidId(id)) {
      fetchNotice(Number(id)).catch(() => {})
    }
  }, [id, fetchNotice])

  if (!isValidId(id)) {
    return <Navigate to="/notices" replace />
  }

  async function handleDelete() {
    if (!window.confirm('정말 삭제하시겠습니까?')) return
    try {
      await deleteNotice(Number(id))
      navigate('/notices')
    } catch (e) {
      window.alert(useNoticeStore.getState().error)
    }
  }

  return (
    <div className="mx-auto max-w-3xl px-4 py-10">
      {loading ? (
        <div className="py-16 text-center text-gray-400">불러오는 중...</div>
      ) : error ? (
        <div className="py-16 text-center text-red-500">{error}</div>
      ) : currentNotice ? (
        <div>
          <h1 className="text-2xl font-bold text-gray-800">{currentNotice.title}</h1>
          <div className="mt-2 flex gap-4 border-b border-gray-200 pb-4 text-sm text-gray-500">
            <span>작성자 {currentNotice.author}</span>
            <span>조회수 {currentNotice.hits}</span>
            <span>{formatDate(currentNotice.createdAt)}</span>
          </div>
          <p className="min-h-32 whitespace-pre-wrap py-6 text-gray-700">{currentNotice.content}</p>

          <div className="flex justify-between border-t border-gray-200 pt-4">
            <Link
              to="/notices"
              className="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-50"
            >
              목록
            </Link>
            <div className="flex gap-2">
              <Link
                to={`/notices/${id}/edit`}
                className="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-50"
              >
                수정
              </Link>
              <button
                type="button"
                className="rounded-lg bg-red-500 px-4 py-2 text-sm text-white hover:bg-red-600"
                onClick={handleDelete}
              >
                삭제
              </button>
            </div>
          </div>
        </div>
      ) : null}
    </div>
  )
}

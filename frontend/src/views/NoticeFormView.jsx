import { useEffect, useState } from 'react'
import { Navigate, useNavigate, useParams } from 'react-router-dom'
import { useNoticeStore } from '../store/noticeStore'
import NoticeForm from '../components/NoticeForm'

function isValidId(value) {
  if (value === undefined) return false
  const n = Number(value)
  return Number.isInteger(n) && n > 0
}

export default function NoticeFormView() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEdit = id !== undefined

  const fetchNotice = useNoticeStore((s) => s.fetchNotice)
  const createNotice = useNoticeStore((s) => s.createNotice)
  const updateNotice = useNoticeStore((s) => s.updateNotice)

  const [initialValue, setInitialValue] = useState({ title: '', content: '', author: '' })
  const [submitting, setSubmitting] = useState(false)
  const [loadError, setLoadError] = useState('')

  useEffect(() => {
    if (isEdit && isValidId(id)) {
      fetchNotice(Number(id))
        .then((notice) => {
          setInitialValue({ title: notice.title, content: notice.content, author: notice.author })
        })
        .catch(() => {
          setLoadError(useNoticeStore.getState().error)
        })
    }
  }, [id, isEdit, fetchNotice])

  if (isEdit && !isValidId(id)) {
    return <Navigate to="/notices" replace />
  }

  async function handleSubmit(payload) {
    setSubmitting(true)
    try {
      if (isEdit) {
        await updateNotice(Number(id), payload)
        navigate(`/notices/${id}`)
      } else {
        const created = await createNotice(payload)
        navigate(`/notices/${created.id}`)
      }
    } catch (e) {
      window.alert(useNoticeStore.getState().error)
    } finally {
      setSubmitting(false)
    }
  }

  function handleCancel() {
    navigate(-1)
  }

  return (
    <div className="mx-auto max-w-2xl px-4 py-10">
      <h1 className="mb-6 text-2xl font-bold text-gray-800">
        {isEdit ? '공지사항 수정' : '공지사항 등록'}
      </h1>
      {loadError ? (
        <div className="py-16 text-center text-red-500">{loadError}</div>
      ) : (
        <NoticeForm
          initialValue={initialValue}
          submitting={submitting}
          onSubmit={handleSubmit}
          onCancel={handleCancel}
        />
      )}
    </div>
  )
}

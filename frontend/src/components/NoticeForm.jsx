import { useEffect, useState } from 'react'

// 등록/수정 화면에서 공통으로 재사용하는 폼 컴포넌트 (FR-08 클라이언트 검증)
export default function NoticeForm({ initialValue, submitting, onSubmit, onCancel }) {
  const [form, setForm] = useState({
    title: initialValue?.title || '',
    content: initialValue?.content || '',
    author: initialValue?.author || ''
  })
  const [errors, setErrors] = useState({ title: '', content: '', author: '' })

  useEffect(() => {
    setForm({
      title: initialValue?.title || '',
      content: initialValue?.content || '',
      author: initialValue?.author || ''
    })
  }, [initialValue])

  function validate() {
    const next = {
      title: !form.title.trim()
        ? '제목을 입력하세요.'
        : form.title.length > 200
          ? '제목은 200자 이내여야 합니다.'
          : '',
      content: !form.content.trim() ? '내용을 입력하세요.' : '',
      author: !form.author.trim()
        ? '작성자를 입력하세요.'
        : form.author.length > 50
          ? '작성자는 50자 이내여야 합니다.'
          : ''
    }
    setErrors(next)
    return !next.title && !next.content && !next.author
  }

  function handleSubmit(e) {
    e.preventDefault()
    if (!validate()) return
    onSubmit({
      title: form.title.trim(),
      content: form.content.trim(),
      author: form.author.trim()
    })
  }

  return (
    <form className="space-y-5" onSubmit={handleSubmit}>
      <div>
        <label className="mb-1 block text-sm font-medium text-gray-700">제목</label>
        <input
          type="text"
          maxLength={200}
          value={form.title}
          onChange={(e) => setForm((f) => ({ ...f, title: e.target.value }))}
          className="w-full rounded-lg border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none"
        />
        {errors.title && <p className="mt-1 text-sm text-red-500">{errors.title}</p>}
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-gray-700">작성자</label>
        <input
          type="text"
          maxLength={50}
          value={form.author}
          onChange={(e) => setForm((f) => ({ ...f, author: e.target.value }))}
          className="w-full rounded-lg border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none"
        />
        {errors.author && <p className="mt-1 text-sm text-red-500">{errors.author}</p>}
      </div>

      <div>
        <label className="mb-1 block text-sm font-medium text-gray-700">내용</label>
        <textarea
          rows={10}
          value={form.content}
          onChange={(e) => setForm((f) => ({ ...f, content: e.target.value }))}
          className="w-full rounded-lg border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none"
        />
        {errors.content && <p className="mt-1 text-sm text-red-500">{errors.content}</p>}
      </div>

      <div className="flex justify-end gap-2 pt-2">
        <button
          type="button"
          className="rounded-lg border border-gray-300 px-4 py-2 text-sm text-gray-600 hover:bg-gray-50"
          onClick={onCancel}
        >
          취소
        </button>
        <button
          type="submit"
          disabled={submitting}
          className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700 disabled:opacity-50"
        >
          저장
        </button>
      </div>
    </form>
  )
}

import { useEffect, useMemo, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { useNoticeStore } from '../store/noticeStore'
import Pagination from '../components/Pagination'

const PAGE_SIZE = 10

function formatDate(value) {
  if (!value) return ''
  return value.replace('T', ' ').slice(0, 16)
}

export default function NoticeListView() {
  const notices = useNoticeStore((s) => s.notices)
  const loading = useNoticeStore((s) => s.loading)
  const error = useNoticeStore((s) => s.error)
  const fetchNotices = useNoticeStore((s) => s.fetchNotices)

  const [searchParams, setSearchParams] = useSearchParams()
  const [keyword, setKeyword] = useState(searchParams.get('keyword') || '')
  const currentPage = Number(searchParams.get('page')) > 0 ? Number(searchParams.get('page')) : 1

  useEffect(() => {
    fetchNotices()
  }, [fetchNotices])

  // notices -> filteredNotices -> totalPages -> paginatedNotices (원본 notices는 변경하지 않는다)
  const filteredNotices = useMemo(() => {
    const kw = keyword.trim().toLowerCase()
    if (!kw) return notices
    return notices.filter(
      (n) =>
        n.title?.toLowerCase().includes(kw) ||
        n.content?.toLowerCase().includes(kw) ||
        n.author?.toLowerCase().includes(kw)
    )
  }, [notices, keyword])

  const totalPages = Math.max(1, Math.ceil(filteredNotices.length / PAGE_SIZE))

  const paginatedNotices = useMemo(() => {
    const start = (currentPage - 1) * PAGE_SIZE
    return filteredNotices.slice(start, start + PAGE_SIZE)
  }, [filteredNotices, currentPage])

  useEffect(() => {
    if (currentPage > totalPages) {
      updateParams(keyword, totalPages)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [totalPages])

  function updateParams(nextKeyword, nextPage) {
    const params = {}
    if (nextKeyword) params.keyword = nextKeyword
    if (nextPage > 1) params.page = String(nextPage)
    setSearchParams(params, { replace: true })
  }

  function handleKeywordChange(e) {
    const value = e.target.value
    setKeyword(value)
    updateParams(value, 1)
  }

  function handlePageChange(page) {
    updateParams(keyword, page)
  }

  return (
    <div className="mx-auto max-w-4xl px-4 py-10">
      <div className="mb-6 flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">공지사항</h1>
        <Link
          to="/notices/new"
          className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700"
        >
          등록
        </Link>
      </div>

      <div className="mb-4">
        <input
          type="text"
          value={keyword}
          onChange={handleKeywordChange}
          placeholder="제목, 내용, 작성자로 검색"
          className="w-full rounded-lg border border-gray-300 px-4 py-2 focus:border-blue-500 focus:outline-none"
        />
      </div>

      {loading ? (
        <div className="py-16 text-center text-gray-400">불러오는 중...</div>
      ) : error ? (
        <div className="py-16 text-center text-red-500">{error}</div>
      ) : filteredNotices.length === 0 ? (
        <div className="py-16 text-center text-gray-400">등록된 공지사항이 없습니다.</div>
      ) : (
        <>
          <table className="w-full border-collapse text-left text-sm">
            <thead>
              <tr className="border-b border-gray-200 text-gray-500">
                <th className="w-16 px-3 py-2">번호</th>
                <th className="px-3 py-2">제목</th>
                <th className="w-28 px-3 py-2">작성자</th>
                <th className="w-20 px-3 py-2">조회수</th>
                <th className="w-36 px-3 py-2">등록일</th>
              </tr>
            </thead>
            <tbody>
              {paginatedNotices.map((notice) => (
                <tr key={notice.id} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="px-3 py-3 text-gray-400">{notice.id}</td>
                  <td className="px-3 py-3">
                    <Link
                      to={`/notices/${notice.id}`}
                      className="text-gray-800 hover:text-blue-600 hover:underline"
                    >
                      {notice.title}
                    </Link>
                  </td>
                  <td className="px-3 py-3 text-gray-500">{notice.author}</td>
                  <td className="px-3 py-3 text-gray-500">{notice.hits}</td>
                  <td className="px-3 py-3 text-gray-400">{formatDate(notice.createdAt)}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <Pagination currentPage={currentPage} totalPages={totalPages} onChange={handlePageChange} />
        </>
      )}
    </div>
  )
}

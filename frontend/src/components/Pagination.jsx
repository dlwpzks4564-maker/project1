export default function Pagination({ currentPage, totalPages, onChange }) {
  if (totalPages <= 1) return null

  const pages = Array.from({ length: totalPages }, (_, i) => i + 1)

  function go(page) {
    if (page < 1 || page > totalPages || page === currentPage) return
    onChange(page)
  }

  return (
    <div className="mt-6 flex items-center justify-center gap-1">
      <button
        type="button"
        className="rounded px-3 py-1 text-sm text-gray-600 disabled:opacity-30"
        disabled={currentPage === 1}
        onClick={() => go(currentPage - 1)}
      >
        이전
      </button>
      {pages.map((page) => (
        <button
          key={page}
          type="button"
          className={`rounded px-3 py-1 text-sm ${
            page === currentPage ? 'bg-blue-600 text-white' : 'text-gray-600 hover:bg-gray-100'
          }`}
          onClick={() => go(page)}
        >
          {page}
        </button>
      ))}
      <button
        type="button"
        className="rounded px-3 py-1 text-sm text-gray-600 disabled:opacity-30"
        disabled={currentPage === totalPages}
        onClick={() => go(currentPage + 1)}
      >
        다음
      </button>
    </div>
  )
}

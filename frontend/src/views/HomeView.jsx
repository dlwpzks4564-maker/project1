import { useNavigate } from 'react-router-dom'

export default function HomeView() {
  const navigate = useNavigate()

  return (
    <div className="flex min-h-screen flex-col items-center justify-center px-4 text-center">
      <h1 className="text-3xl font-bold text-gray-800">Notice Project</h1>
      <p className="mt-3 max-w-md text-gray-500">
        Spring Boot는 공지사항 CRUD API와 데이터 저장만 담당하고, React가 검색과 페이징을 담당하는
        가장 단순한 공지사항 애플리케이션입니다.
      </p>
      <button
        type="button"
        className="mt-8 rounded-lg bg-blue-600 px-6 py-3 font-medium text-white transition hover:bg-blue-700"
        onClick={() => navigate('/notices')}
      >
        공지사항 바로가기
      </button>
    </div>
  )
}

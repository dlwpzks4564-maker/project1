import { Navigate, Route, Routes } from 'react-router-dom'
import HomeView from '../views/HomeView'
import NoticeListView from '../views/NoticeListView'
import NoticeDetailView from '../views/NoticeDetailView'
import NoticeFormView from '../views/NoticeFormView'

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<HomeView />} />
      <Route path="/notices" element={<NoticeListView />} />
      <Route path="/notices/new" element={<NoticeFormView />} />
      <Route path="/notices/:id" element={<NoticeDetailView />} />
      <Route path="/notices/:id/edit" element={<NoticeFormView />} />
      <Route path="*" element={<Navigate to="/notices" replace />} />
    </Routes>
  )
}

import { useState } from 'react'
import { AdminShell } from './components/layout/AdminShell'
import { AuthPage } from './features/auth/AuthPage'
import { TemplatesPage } from './features/templates/TemplatesPage'
import type { AdminPage } from './types/navigation'
import './App.css'

function App() {
  const [activePage, setActivePage] = useState<AdminPage>('templates')

  return (
    <AdminShell activePage={activePage} onPageChange={setActivePage}>
      {activePage === 'templates' ? <TemplatesPage /> : <AuthPage />}
    </AdminShell>
  )
}

export default App

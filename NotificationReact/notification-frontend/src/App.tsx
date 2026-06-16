import { useState } from 'react'
import { AdminShell } from './components/layout/AdminShell'
import { AccessGate } from './features/auth/AccessGate'
import { AuthPage } from './features/auth/AuthPage'
import { MessagingPage } from './features/messaging/MessagingPage'
import { TemplatesPage } from './features/templates/TemplatesPage'
import { UserPortalPage } from './features/user/UserPortalPage'
import type { AppSession } from './types/auth'
import type { AdminPage } from './types/navigation'
import './App.css'

function App() {
  const [activePage, setActivePage] = useState<AdminPage>('messaging')
  const [session, setSession] = useState<AppSession | null>(null)

  if (!session) {
    return <AccessGate onAuthenticated={setSession} />
  }

  if (session.role === 'user') {
    return (
      <UserPortalPage
        session={session}
        onSignOut={() => {
          setSession(null)
          setActivePage('messaging')
        }}
      />
    )
  }

  return (
    <AdminShell
      activePage={activePage}
      session={session}
      onPageChange={setActivePage}
      onSignOut={() => {
        setSession(null)
        setActivePage('messaging')
      }}
    >
      {activePage === 'messaging' && <MessagingPage />}
      {activePage === 'templates' && <TemplatesPage />}
      {activePage === 'users' && <AuthPage />}
    </AdminShell>
  )
}

export default App

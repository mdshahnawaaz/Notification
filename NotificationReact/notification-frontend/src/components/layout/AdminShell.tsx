import type { ReactNode } from 'react'
import type { AppSession } from '../../types/auth'
import type { AdminPage } from '../../types/navigation'
import { TopNav } from './TopNav'

type AdminShellProps = {
  activePage: AdminPage
  children: ReactNode
  session: AppSession
  onPageChange: (page: AdminPage) => void
  onSignOut: () => void
}

export function AdminShell({
  activePage,
  children,
  session,
  onPageChange,
  onSignOut,
}: AdminShellProps) {
  return (
    <main className="app-shell">
      <TopNav
        activePage={activePage}
        session={session}
        onPageChange={onPageChange}
        onSignOut={onSignOut}
      />
      {children}
    </main>
  )
}

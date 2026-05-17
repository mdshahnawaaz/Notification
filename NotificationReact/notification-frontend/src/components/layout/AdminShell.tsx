import type { ReactNode } from 'react'
import type { AdminPage } from '../../types/navigation'
import { TopNav } from './TopNav'

type AdminShellProps = {
  activePage: AdminPage
  children: ReactNode
  onPageChange: (page: AdminPage) => void
}

export function AdminShell({
  activePage,
  children,
  onPageChange,
}: AdminShellProps) {
  return (
    <main className="app-shell">
      <TopNav activePage={activePage} onPageChange={onPageChange} />
      {children}
    </main>
  )
}

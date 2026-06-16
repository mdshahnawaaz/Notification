import type { AppSession } from '../../types/auth'
import type { AdminPage } from '../../types/navigation'

type TopNavProps = {
  activePage: AdminPage
  session: AppSession
  onPageChange: (page: AdminPage) => void
  onSignOut: () => void
}

const navItems: Array<{ label: string; page: AdminPage }> = [
  { label: 'Messaging', page: 'messaging' },
  { label: 'Templates', page: 'templates' },
  { label: 'Users', page: 'users' },
]

export function TopNav({
  activePage,
  session,
  onPageChange,
  onSignOut,
}: TopNavProps) {
  return (
    <nav className="top-nav" aria-label="Admin navigation">
      <div className="brand-lockup">
        <span className="brand-mark">N</span>
        <span>
          <span className="brand-text">Notification Control Center</span>
          <small>{session.role === 'admin' ? 'Admin workspace' : 'User workspace'}</small>
        </span>
      </div>
      <div className="nav-actions">
        {navItems.map((item) => (
          <button
            className={activePage === item.page ? 'active' : ''}
            key={item.page}
            type="button"
            onClick={() => onPageChange(item.page)}
          >
            {item.label}
          </button>
        ))}
      </div>
      <div className="session-chip" aria-label="Signed in user">
        <span>{session.name}</span>
        <strong>{session.role === 'admin' ? 'Administrator' : session.userType}</strong>
        <button type="button" onClick={onSignOut}>
          Sign out
        </button>
      </div>
    </nav>
  )
}

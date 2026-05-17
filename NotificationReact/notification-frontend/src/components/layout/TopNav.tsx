import type { AdminPage } from '../../types/navigation'

type TopNavProps = {
  activePage: AdminPage
  onPageChange: (page: AdminPage) => void
}

const navItems: Array<{ label: string; page: AdminPage }> = [
  { label: 'Templates', page: 'templates' },
  { label: 'Users', page: 'users' },
]

export function TopNav({ activePage, onPageChange }: TopNavProps) {
  return (
    <nav className="top-nav" aria-label="Admin navigation">
      <div>
        <span className="brand-mark">N</span>
        <span className="brand-text">Notification Admin</span>
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
    </nav>
  )
}

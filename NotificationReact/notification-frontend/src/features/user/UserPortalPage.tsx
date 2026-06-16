import type { UserSession } from '../../types/auth'

type UserPortalPageProps = {
  session: UserSession
  onSignOut: () => void
}

const userCapabilities = {
  FREE: ['Transactional alerts', 'Email notifications', 'Basic delivery history'],
  PREMIUM: ['Priority alerts', 'SMS and WhatsApp routing', 'Template based updates'],
  SUBSCRIBED: ['All delivery channels', 'Campaign notifications', 'Premium support queue'],
}

export function UserPortalPage({ session, onSignOut }: UserPortalPageProps) {
  return (
    <main className="app-shell user-portal">
      <nav className="top-nav" aria-label="User navigation">
        <div className="brand-lockup">
          <span className="brand-mark">N</span>
          <span>
            <span className="brand-text">Notification Portal</span>
            <small>User workspace</small>
          </span>
        </div>
        <div className="session-chip" aria-label="Signed in user">
          <span>{session.name}</span>
          <strong>{session.userType}</strong>
          <button type="button" onClick={onSignOut}>
            Sign out
          </button>
        </div>
      </nav>

      <section className="user-dashboard" aria-labelledby="user-title">
        <div className="user-hero">
          <span className="eyebrow">Customer dashboard</span>
          <h1 id="user-title">Welcome, {session.name}</h1>
          <p>
            Review your account tier, notification coverage, and delivery
            readiness without exposing administrative tools.
          </p>
        </div>

        <section className="user-grid">
          <article className="enterprise-card profile-card">
            <span className="card-kicker">Profile</span>
            <h2>{session.email}</h2>
            <dl>
              <div>
                <dt>Account ID</dt>
                <dd>#{session.id}</dd>
              </div>
              <div>
                <dt>Plan</dt>
                <dd>{session.userType}</dd>
              </div>
              <div>
                <dt>Status</dt>
                <dd>Active</dd>
              </div>
            </dl>
          </article>

          <article className="enterprise-card">
            <span className="card-kicker">Included services</span>
            <h2>Notification access</h2>
            <ul className="capability-list">
              {userCapabilities[session.userType].map((capability) => (
                <li key={capability}>{capability}</li>
              ))}
            </ul>
          </article>

          <article className="enterprise-card">
            <span className="card-kicker">Preferences</span>
            <h2>Delivery channels</h2>
            <div className="channel-list">
              <span>Email enabled</span>
              <span>SMS enabled</span>
              <span>WhatsApp available</span>
            </div>
          </article>
        </section>
      </section>
    </main>
  )
}

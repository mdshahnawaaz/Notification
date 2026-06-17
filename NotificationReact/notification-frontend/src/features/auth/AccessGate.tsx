import { useState } from 'react'
import type { FormEvent } from 'react'
import { loginUser } from '../../services/authApi'
import { setAuthToken } from '../../services/http'
import type { AccessRole, AppSession } from '../../types/auth'
import { StatusNotice } from '../../components/ui/StatusNotice'

type AccessGateProps = {
  onAuthenticated: (session: AppSession) => void
}

const adminCredentials = {
  email: 'admin@notification.local',
  password: 'admin123',
}

export function AccessGate({ onAuthenticated }: AccessGateProps) {
  const [role, setRole] = useState<AccessRole>('admin')
  const [email, setEmail] = useState(adminCredentials.email)
  const [password, setPassword] = useState(adminCredentials.password)
  const [message, setMessage] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const switchRole = (nextRole: AccessRole) => {
    setRole(nextRole)
    setMessage('')
    setEmail(nextRole === 'admin' ? adminCredentials.email : '')
    setPassword(nextRole === 'admin' ? adminCredentials.password : '')
  }

  const submitLogin = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setMessage('')
    setIsSubmitting(true)

    try {
      const auth = await loginUser({ email, password })
      const resolvedRole = auth.user.role === 'ADMIN' ? 'admin' : 'user'

      if (role !== resolvedRole) {
        throw new Error(`This account has ${resolvedRole} access. Choose the ${resolvedRole} portal.`)
      }

      setAuthToken(auth.token)

      if (resolvedRole === 'admin') {
        onAuthenticated({
          role: 'admin',
          name: auth.user.name,
          email: auth.user.email,
          token: auth.token,
        })
        return
      }

      onAuthenticated({ ...auth.user, role: 'user', token: auth.token })
    } catch (error) {
      setAuthToken(null)
      setMessage(
        error instanceof Error
          ? error.message
          : 'Login failed. Please check the credentials and try again.',
      )
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <main className="login-page">
      <section className="login-hero" aria-labelledby="login-title">
        <div className="login-copy">
          <span className="eyebrow">Enterprise notification platform</span>
          <h1 id="login-title">Secure access for operators and customers</h1>
          <p>
            Admins manage dispatch operations, templates, and user access. Users
            receive a focused portal for their profile, plan, and notification
            preferences.
          </p>
        </div>

        <div className="trust-strip" aria-label="Platform controls">
          <div>
            <strong>Role based</strong>
            <span>Admin and user views stay separated.</span>
          </div>
          <div>
            <strong>Audit ready</strong>
            <span>Operational actions are grouped in one console.</span>
          </div>
          <div>
            <strong>Channel aware</strong>
            <span>Email, SMS, WhatsApp, and Instagram workflows.</span>
          </div>
        </div>
      </section>

      <section className="login-panel" aria-label="Sign in">
        <div className="portal-switch" role="tablist" aria-label="Choose portal">
          <button
            className={role === 'admin' ? 'active' : ''}
            type="button"
            onClick={() => switchRole('admin')}
          >
            Admin
          </button>
          <button
            className={role === 'user' ? 'active' : ''}
            type="button"
            onClick={() => switchRole('user')}
          >
            User
          </button>
        </div>

        <div className="login-panel-copy">
          <span>{role === 'admin' ? 'Administrator login' : 'User login'}</span>
          <h2>{role === 'admin' ? 'Control center access' : 'Customer portal'}</h2>
          <p>
            {role === 'admin'
              ? 'Use the seeded admin credential to enter the protected operations workspace.'
              : 'Use a registered account from the notification service database.'}
          </p>
        </div>

        <form className="auth-form" onSubmit={submitLogin}>
          <label>
            Email
            <input
              autoComplete="email"
              onChange={(event) => setEmail(event.target.value)}
              placeholder={role === 'admin' ? adminCredentials.email : 'you@example.com'}
              required
              type="email"
              value={email}
            />
          </label>

          <label>
            Password
            <input
              autoComplete="current-password"
              minLength={6}
              onChange={(event) => setPassword(event.target.value)}
              placeholder={role === 'admin' ? 'admin123' : 'Your password'}
              required
              type="password"
              value={password}
            />
          </label>

          <button className="submit-button" disabled={isSubmitting} type="submit">
            {isSubmitting
              ? 'Checking access...'
              : role === 'admin'
                ? 'Enter admin console'
                : 'Enter user portal'}
          </button>
        </form>

        {message && <StatusNotice tone="danger">{message}</StatusNotice>}
      </section>
    </main>
  )
}

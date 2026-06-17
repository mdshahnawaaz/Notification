import { useMemo, useState } from 'react'
import type { FormEvent } from 'react'
import { StatusNotice } from '../../components/ui/StatusNotice'
import { SectionHeader } from '../../components/ui/SectionHeader'
import { loginUser, signupUser } from '../../services/authApi'
import type { AuthMode, UserResponse, UserType } from '../../types/auth'
import { AuthForm } from './AuthForm'
import { AuthModeSwitch } from './AuthModeSwitch'
import { UserSummaryCard } from './UserSummaryCard'

export function AuthPage() {
  const [mode, setMode] = useState<AuthMode>('signup')
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [userType, setUserType] = useState<UserType>('FREE')
  const [message, setMessage] = useState('')
  const [savedUser, setSavedUser] = useState<UserResponse | null>(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const title = useMemo(
    () => (mode === 'signup' ? 'Create account' : 'Welcome back'),
    [mode],
  )

  const submitAuth = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setMessage('')
    setSavedUser(null)
    setIsSubmitting(true)

    try {
      const user =
        mode === 'signup'
          ? await signupUser({ name, email, password, userType })
          : await loginUser({ email, password })

      setSavedUser(user.user)
      setMessage(
        mode === 'signup'
          ? 'Account created and saved in database.'
          : 'Login successful.',
      )
    } catch (error) {
      setMessage(
        error instanceof Error
          ? error.message
          : 'Something went wrong. Please try again.',
      )
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <section className="auth-shell">
      <section className="auth-panel" aria-labelledby="auth-title">
        <SectionHeader
          className="auth-copy"
          description="Create customer accounts, assign a service tier, and verify user credentials without leaving the admin workspace."
          eyebrow="Admin user management"
          id="auth-title"
          title={title}
        />

        <AuthModeSwitch mode={mode} onModeChange={setMode} />

        <AuthForm
          email={email}
          isSubmitting={isSubmitting}
          mode={mode}
          name={name}
          password={password}
          userType={userType}
          onEmailChange={setEmail}
          onNameChange={setName}
          onPasswordChange={setPassword}
          onSubmit={submitAuth}
          onUserTypeChange={setUserType}
        />

        {message && (
          <StatusNotice tone={savedUser ? 'success' : 'danger'}>
            {message}
          </StatusNotice>
        )}

        {savedUser && <UserSummaryCard user={savedUser} />}
      </section>
    </section>
  )
}

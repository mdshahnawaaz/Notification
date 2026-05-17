import type { FormEvent } from 'react'
import type { AuthMode, UserType } from '../../types/auth'

type AuthFormProps = {
  email: string
  isSubmitting: boolean
  mode: AuthMode
  name: string
  password: string
  userType: UserType
  onEmailChange: (value: string) => void
  onNameChange: (value: string) => void
  onPasswordChange: (value: string) => void
  onSubmit: (event: FormEvent<HTMLFormElement>) => void
  onUserTypeChange: (value: UserType) => void
}

export function AuthForm({
  email,
  isSubmitting,
  mode,
  name,
  password,
  userType,
  onEmailChange,
  onNameChange,
  onPasswordChange,
  onSubmit,
  onUserTypeChange,
}: AuthFormProps) {
  return (
    <form className="auth-form" onSubmit={onSubmit}>
      {mode === 'signup' && (
        <label>
          Name
          <input
            autoComplete="name"
            onChange={(event) => onNameChange(event.target.value)}
            placeholder="Your name"
            required
            type="text"
            value={name}
          />
        </label>
      )}

      <label>
        Email
        <input
          autoComplete="email"
          onChange={(event) => onEmailChange(event.target.value)}
          placeholder="you@example.com"
          required
          type="email"
          value={email}
        />
      </label>

      <label>
        Password
        <input
          autoComplete={mode === 'signup' ? 'new-password' : 'current-password'}
          minLength={6}
          onChange={(event) => onPasswordChange(event.target.value)}
          placeholder="At least 6 characters"
          required
          type="password"
          value={password}
        />
      </label>

      {mode === 'signup' && (
        <label>
          User type
          <select
            onChange={(event) => onUserTypeChange(event.target.value as UserType)}
            value={userType}
          >
            <option value="FREE">Free</option>
            <option value="PREMIUM">Premium</option>
            <option value="SUBSCRIBED">Subscribed</option>
          </select>
        </label>
      )}

      <button className="submit-button" disabled={isSubmitting} type="submit">
        {isSubmitting
          ? 'Please wait...'
          : mode === 'signup'
            ? 'Create account'
            : 'Login'}
      </button>
    </form>
  )
}

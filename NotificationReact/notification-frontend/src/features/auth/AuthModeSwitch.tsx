import type { AuthMode } from '../../types/auth'

type AuthModeSwitchProps = {
  mode: AuthMode
  onModeChange: (mode: AuthMode) => void
}

export function AuthModeSwitch({ mode, onModeChange }: AuthModeSwitchProps) {
  return (
    <div className="mode-switch" aria-label="Authentication mode">
      <button
        className={mode === 'signup' ? 'active' : ''}
        type="button"
        onClick={() => onModeChange('signup')}
      >
        Sign up
      </button>
      <button
        className={mode === 'login' ? 'active' : ''}
        type="button"
        onClick={() => onModeChange('login')}
      >
        Login
      </button>
    </div>
  )
}

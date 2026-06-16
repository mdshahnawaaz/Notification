export type AuthMode = 'login' | 'signup'
export type AccessRole = 'admin' | 'user'
export type UserType = 'FREE' | 'PREMIUM' | 'SUBSCRIBED'

export type LoginRequest = {
  email: string
  password: string
}

export type SignupRequest = LoginRequest & {
  name: string
  userType: UserType
}

export type UserResponse = {
  id: number
  name: string
  email: string
  userType: UserType
}

export type AdminSession = {
  role: 'admin'
  name: string
  email: string
}

export type UserSession = UserResponse & {
  role: 'user'
}

export type AppSession = AdminSession | UserSession

export type AuthMode = 'login' | 'signup'
export type AccessRole = 'admin' | 'user'
export type ApiRole = 'ADMIN' | 'USER'
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
  role: ApiRole
}

export type AuthResponse = {
  token: string
  user: UserResponse
}

export type AdminSession = {
  role: 'admin'
  name: string
  email: string
  token: string
}

export type UserSession = Omit<UserResponse, 'role'> & {
  role: 'user'
  token: string
}

export type AppSession = AdminSession | UserSession

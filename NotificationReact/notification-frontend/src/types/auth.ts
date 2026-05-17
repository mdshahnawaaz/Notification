export type AuthMode = 'login' | 'signup'
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

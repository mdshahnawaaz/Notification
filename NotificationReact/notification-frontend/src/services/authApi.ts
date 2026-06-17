import { apiRequest } from './http'
import type { AuthResponse, LoginRequest, SignupRequest } from '../types/auth'

export function loginUser(payload: LoginRequest) {
  return apiRequest<AuthResponse>('/auth/login', {
    method: 'POST',
    body: payload,
  })
}

export function signupUser(payload: SignupRequest) {
  return apiRequest<AuthResponse>('/auth/signup', {
    method: 'POST',
    body: payload,
  })
}

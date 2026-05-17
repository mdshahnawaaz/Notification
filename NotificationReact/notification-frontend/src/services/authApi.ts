import { apiRequest } from './http'
import type { LoginRequest, SignupRequest, UserResponse } from '../types/auth'

export function loginUser(payload: LoginRequest) {
  return apiRequest<UserResponse>('/auth/login', {
    method: 'POST',
    body: payload,
  })
}

export function signupUser(payload: SignupRequest) {
  return apiRequest<UserResponse>('/auth/signup', {
    method: 'POST',
    body: payload,
  })
}

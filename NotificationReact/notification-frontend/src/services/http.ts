import { API_BASE_URL } from '../config/api'

type RequestOptions = Omit<RequestInit, 'body'> & {
  body?: unknown
}

let authToken: string | null = null

export function setAuthToken(token: string | null) {
  authToken = token
}

export async function apiRequest<T>(
  path: string,
  options: RequestOptions = {},
): Promise<T> {
  const headers = new Headers(options.headers)
  headers.set('Content-Type', 'application/json')

  if (authToken) {
    headers.set('Authorization', `Bearer ${authToken}`)
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
    body: options.body === undefined ? undefined : JSON.stringify(options.body),
  })

  const data = await response.json().catch(() => null)

  if (!response.ok) {
    const message =
      data && typeof data.message === 'string'
        ? data.message
        : 'Request failed. Please try again.'

    throw new Error(message)
  }

  return data as T
}

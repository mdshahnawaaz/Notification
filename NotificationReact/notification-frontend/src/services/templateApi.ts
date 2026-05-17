import { apiRequest } from './http'
import type {
  CreateTemplateRequest,
  TemplateResponse,
} from '../types/templates'

export function createTemplate(payload: CreateTemplateRequest) {
  return apiRequest<TemplateResponse>('/templates', {
    method: 'POST',
    body: payload,
  })
}

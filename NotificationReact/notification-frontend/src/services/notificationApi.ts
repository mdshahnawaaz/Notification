import { apiRequest } from './http'
import type {
  BulkNotificationRequest,
  BulkNotificationResponse,
  DirectNotificationRequest,
  UrgentNotificationRequest,
  UrgentNotificationResponse,
} from '../types/notifications'

export function sendDirectNotification(payload: DirectNotificationRequest) {
  return apiRequest<string>('/notifications', {
    method: 'POST',
    body: payload,
  })
}

export function sendUrgentNotification(payload: UrgentNotificationRequest) {
  return apiRequest<UrgentNotificationResponse>('/notifications/urgent', {
    method: 'POST',
    body: payload,
  })
}

export function sendBulkNotification(payload: BulkNotificationRequest) {
  return apiRequest<BulkNotificationResponse>('/notifications/bulk', {
    method: 'POST',
    body: payload,
  })
}

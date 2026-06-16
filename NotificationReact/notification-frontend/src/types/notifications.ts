import type { TemplateType } from './templates'
import type { UserType } from './auth'

export type MessageMode = 'urgent' | 'direct' | 'campaign'

export type DeliveryType = Lowercase<TemplateType>

export type DirectNotificationRequest = {
  type: DeliveryType
  recipient: string
  message: string
}

export type UrgentNotificationRequest = DirectNotificationRequest & {
  eventType: string
  referenceId?: string
  priority: number
  metadata?: Record<string, string>
}

export type UrgentNotificationResponse = {
  status: string
  eventType: string
  referenceId?: string
  type: DeliveryType
  recipient: string
  priority: number
  queuedAt: string
}

export type BulkRecipient = {
  name?: string
  email?: string
  recipient?: string
  attributes?: Record<string, string>
}

export type BulkNotificationRequest = {
  templateId: number
  userType?: UserType
  recipients?: BulkRecipient[]
}

export type BulkNotificationResponse = {
  templateId: number
  notificationType: string
  requestedRecipients: number
  publishedCount: number
  skippedRecipients: string[]
}

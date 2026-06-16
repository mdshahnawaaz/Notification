import { useEffect, useMemo, useState } from 'react'
import type { FormEvent } from 'react'
import { SectionHeader } from '../../components/ui/SectionHeader'
import { StatusNotice } from '../../components/ui/StatusNotice'
import {
  sendBulkNotification,
  sendDirectNotification,
  sendUrgentNotification,
} from '../../services/notificationApi'
import { getTemplates } from '../../services/templateApi'
import type { UserType } from '../../types/auth'
import type {
  DeliveryType,
  MessageMode,
  UrgentNotificationResponse,
} from '../../types/notifications'
import type { TemplateResponse } from '../../types/templates'

const deliveryOptions: Array<{ label: string; value: DeliveryType }> = [
  { label: 'Email', value: 'email' },
  { label: 'SMS', value: 'sms' },
  { label: 'WhatsApp', value: 'whatsapp' },
  { label: 'Instagram', value: 'instagram' },
]

const userTypeOptions: UserType[] = ['FREE', 'PREMIUM', 'SUBSCRIBED']

const sampleRecipientJson = `[
  {
    "name": "Aman",
    "email": "aman@example.com",
    "recipient": "+919876543210",
    "attributes": {
      "amount": "500",
      "plan": "Gold"
    }
  }
]`

type AudienceMode = 'userType' | 'recipients'

export function MessagingPage() {
  const [mode, setMode] = useState<MessageMode>('urgent')
  const [deliveryType, setDeliveryType] = useState<DeliveryType>('sms')
  const [recipient, setRecipient] = useState('')
  const [message, setMessage] = useState(
    'Rs. 500 has been deducted from your account. Ref: TXN12345',
  )
  const [eventType, setEventType] = useState('AMOUNT_DEDUCTION')
  const [referenceId, setReferenceId] = useState('TXN12345')
  const [priority, setPriority] = useState(10)
  const [templates, setTemplates] = useState<TemplateResponse[]>([])
  const [templateId, setTemplateId] = useState('')
  const [audienceMode, setAudienceMode] = useState<AudienceMode>('userType')
  const [userType, setUserType] = useState<UserType>('PREMIUM')
  const [recipientsJson, setRecipientsJson] = useState(sampleRecipientJson)
  const [statusMessage, setStatusMessage] = useState('')
  const [isSuccess, setIsSuccess] = useState(false)
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [isLoadingTemplates, setIsLoadingTemplates] = useState(true)
  const [lastUrgentResponse, setLastUrgentResponse] =
    useState<UrgentNotificationResponse | null>(null)

  useEffect(() => {
    let isMounted = true

    getTemplates()
      .then((response) => {
        if (!isMounted) return
        setTemplates(response)
        setTemplateId(response[0]?.id ? String(response[0].id) : '')
      })
      .catch(() => {
        if (!isMounted) return
        setStatusMessage('Templates could not be loaded. Campaign mode still works after refresh once the API is available.')
        setIsSuccess(false)
      })
      .finally(() => {
        if (isMounted) setIsLoadingTemplates(false)
      })

    return () => {
      isMounted = false
    }
  }, [])

  const selectedTemplate = useMemo(
    () => templates.find((template) => String(template.id) === templateId),
    [templateId, templates],
  )

  const modeSummary = useMemo(() => {
    if (mode === 'urgent') {
      return 'Dedicated priority queue for financial, OTP, account, and incident alerts.'
    }

    if (mode === 'campaign') {
      return 'Template-based sends to a user segment or an explicit recipient list.'
    }

    return 'One-off operational message routed to the selected delivery channel.'
  }, [mode])

  const submitMessage = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setStatusMessage('')
    setIsSuccess(false)
    setLastUrgentResponse(null)
    setIsSubmitting(true)

    try {
      if (mode === 'urgent') {
        const response = await sendUrgentNotification({
          type: deliveryType,
          recipient,
          message,
          eventType,
          referenceId,
          priority,
          metadata: { source: 'product-manager-console' },
        })

        setLastUrgentResponse(response)
        setStatusMessage(`Urgent ${deliveryType} message queued with priority ${response.priority}.`)
        setIsSuccess(true)
        return
      }

      if (mode === 'direct') {
        await sendDirectNotification({
          type: deliveryType,
          recipient,
          message,
        })

        setStatusMessage(`Direct ${deliveryType} message published.`)
        setIsSuccess(true)
        return
      }

      const parsedTemplateId = Number(templateId)
      if (!parsedTemplateId) {
        throw new Error('Select a template before launching a campaign.')
      }

      const payload =
        audienceMode === 'userType'
          ? { templateId: parsedTemplateId, userType }
          : {
              templateId: parsedTemplateId,
              recipients: JSON.parse(recipientsJson),
            }

      const response = await sendBulkNotification(payload)
      setStatusMessage(
        `Campaign queued. Published ${response.publishedCount} of ${response.requestedRecipients} recipient messages.`,
      )
      setIsSuccess(true)
    } catch (error) {
      setStatusMessage(
        error instanceof Error
          ? error.message
          : 'Message could not be triggered. Please review the form.',
      )
      setIsSuccess(false)
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <section className="admin-layout messaging-layout" aria-labelledby="messaging-title">
      <SectionHeader
        description="Trigger urgent alerts, direct notifications, and template campaigns from one controlled workspace."
        eyebrow="Product manager console"
        id="messaging-title"
        title="Message operations"
      />

      <section className="message-console">
        <form className="message-form" onSubmit={submitMessage}>
          <div className="message-mode-tabs" role="tablist" aria-label="Message type">
            {[
              ['urgent', 'Urgent alert'],
              ['direct', 'Direct'],
              ['campaign', 'Campaign'],
            ].map(([value, label]) => (
              <button
                className={mode === value ? 'active' : ''}
                key={value}
                type="button"
                onClick={() => setMode(value as MessageMode)}
              >
                {label}
              </button>
            ))}
          </div>

          <div className="form-section">
            <div>
              <span className="section-kicker">Routing</span>
              <h2>{mode === 'campaign' ? 'Template routing' : 'Delivery channel'}</h2>
            </div>
            <p>{modeSummary}</p>
          </div>

          {mode !== 'campaign' && (
            <div className="field-grid two">
              <label>
                Channel
                <select
                  value={deliveryType}
                  onChange={(event) => setDeliveryType(event.target.value as DeliveryType)}
                >
                  {deliveryOptions.map((option) => (
                    <option key={option.value} value={option.value}>
                      {option.label}
                    </option>
                  ))}
                </select>
              </label>

              <label>
                Recipient
                <input
                  placeholder={deliveryType === 'email' ? 'customer@example.com' : '+919876543210'}
                  value={recipient}
                  onChange={(event) => setRecipient(event.target.value)}
                />
              </label>
            </div>
          )}

          {mode === 'urgent' && (
            <div className="field-grid three">
              <label>
                Event type
                <input value={eventType} onChange={(event) => setEventType(event.target.value)} />
              </label>
              <label>
                Reference ID
                <input value={referenceId} onChange={(event) => setReferenceId(event.target.value)} />
              </label>
              <label>
                Priority
                <input
                  max="10"
                  min="1"
                  type="number"
                  value={priority}
                  onChange={(event) => setPriority(Number(event.target.value))}
                />
              </label>
            </div>
          )}

          {mode !== 'campaign' && (
            <label>
              Message body
              <textarea
                className="compact-textarea"
                value={message}
                onChange={(event) => setMessage(event.target.value)}
              />
            </label>
          )}

          {mode === 'campaign' && (
            <>
              <div className="field-grid two">
                <label>
                  Template
                  <select
                    disabled={isLoadingTemplates || templates.length === 0}
                    value={templateId}
                    onChange={(event) => setTemplateId(event.target.value)}
                  >
                    {templates.map((template) => (
                      <option key={template.id} value={template.id}>
                        #{template.id} · {template.type}
                      </option>
                    ))}
                  </select>
                </label>

                <label>
                  Audience source
                  <select
                    value={audienceMode}
                    onChange={(event) => setAudienceMode(event.target.value as AudienceMode)}
                  >
                    <option value="userType">User segment</option>
                    <option value="recipients">Recipient list</option>
                  </select>
                </label>
              </div>

              {audienceMode === 'userType' ? (
                <label>
                  User segment
                  <select
                    value={userType}
                    onChange={(event) => setUserType(event.target.value as UserType)}
                  >
                    {userTypeOptions.map((option) => (
                      <option key={option} value={option}>
                        {option}
                      </option>
                    ))}
                  </select>
                </label>
              ) : (
                <label>
                  Recipients JSON
                  <textarea
                    className="json-textarea"
                    value={recipientsJson}
                    onChange={(event) => setRecipientsJson(event.target.value)}
                  />
                </label>
              )}
            </>
          )}

          <button className="submit-button" disabled={isSubmitting} type="submit">
            {isSubmitting ? 'Triggering...' : mode === 'campaign' ? 'Launch campaign' : 'Send message'}
          </button>
        </form>

        <aside className="ops-panel" aria-label="Message summary">
          <div className="ops-header">
            <span className="preview-label">{mode}</span>
            <h2>Operational summary</h2>
          </div>

          <div className="metric-grid">
            <div>
              <span>Queue path</span>
              <strong>{mode === 'urgent' ? 'urgent.notification.queue' : mode === 'campaign' ? 'channel queues' : `${deliveryType}.queue`}</strong>
            </div>
            <div>
              <span>Priority</span>
              <strong>{mode === 'urgent' ? priority : 'Standard'}</strong>
            </div>
          </div>

          <div className="preview-message">
            <span>Preview</span>
            <p>
              {mode === 'campaign'
                ? selectedTemplate?.template ?? 'Select a saved template to preview the campaign body.'
                : message || 'Write the message body to preview it here.'}
            </p>
          </div>

          {lastUrgentResponse && (
            <div className="dispatch-receipt">
              <span>Last dispatch</span>
              <strong>{lastUrgentResponse.status}</strong>
              <small>{lastUrgentResponse.referenceId || lastUrgentResponse.eventType}</small>
            </div>
          )}
        </aside>
      </section>

      {statusMessage && (
        <StatusNotice tone={isSuccess ? 'success' : 'danger'}>
          {statusMessage}
        </StatusNotice>
      )}
    </section>
  )
}

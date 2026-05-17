import { useState } from 'react'
import type { FormEvent } from 'react'
import { SectionHeader } from '../../components/ui/SectionHeader'
import { StatusNotice } from '../../components/ui/StatusNotice'
import { createTemplate } from '../../services/templateApi'
import type { TemplateResponse, TemplateType } from '../../types/templates'
import { TemplateForm } from './TemplateForm'
import { TemplatePreview } from './TemplatePreview'

export function TemplatesPage() {
  const [templateType, setTemplateType] = useState<TemplateType>('EMAIL')
  const [templateBody, setTemplateBody] = useState('')
  const [message, setMessage] = useState('')
  const [savedTemplate, setSavedTemplate] = useState<TemplateResponse | null>(
    null,
  )
  const [isSubmitting, setIsSubmitting] = useState(false)

  const uploadTemplate = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    setMessage('')
    setSavedTemplate(null)
    setIsSubmitting(true)

    try {
      const template = await createTemplate({
        type: templateType,
        template: templateBody,
      })

      setSavedTemplate(template)
      setTemplateBody('')
      setMessage('Template uploaded to the database.')
    } catch (error) {
      setMessage(
        error instanceof Error
          ? error.message
          : 'Something went wrong. Please try again.',
      )
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <section className="admin-layout" aria-labelledby="template-title">
      <SectionHeader
        description="Save reusable notification copy for email, SMS, WhatsApp, and Instagram delivery."
        eyebrow="Message templates"
        id="template-title"
        title="Upload a new template"
      />

      <section className="work-surface">
        <TemplateForm
          isSubmitting={isSubmitting}
          templateBody={templateBody}
          templateType={templateType}
          onSubmit={uploadTemplate}
          onTemplateBodyChange={setTemplateBody}
          onTemplateTypeChange={setTemplateType}
        />

        <TemplatePreview
          savedTemplate={savedTemplate}
          templateBody={templateBody}
          templateType={templateType}
        />
      </section>

      {message && (
        <StatusNotice tone={savedTemplate ? 'success' : 'danger'}>
          {message}
        </StatusNotice>
      )}
    </section>
  )
}

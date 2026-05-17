import type { FormEvent } from 'react'
import type { TemplateType } from '../../types/templates'
import { TEMPLATE_TYPE_OPTIONS } from './templateOptions'

type TemplateFormProps = {
  isSubmitting: boolean
  templateBody: string
  templateType: TemplateType
  onSubmit: (event: FormEvent<HTMLFormElement>) => void
  onTemplateBodyChange: (value: string) => void
  onTemplateTypeChange: (value: TemplateType) => void
}

export function TemplateForm({
  isSubmitting,
  templateBody,
  templateType,
  onSubmit,
  onTemplateBodyChange,
  onTemplateTypeChange,
}: TemplateFormProps) {
  return (
    <form className="admin-form" onSubmit={onSubmit}>
      <label>
        Channel
        <select
          onChange={(event) =>
            onTemplateTypeChange(event.target.value as TemplateType)
          }
          value={templateType}
        >
          {TEMPLATE_TYPE_OPTIONS.map((option) => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </label>

      <label>
        Template message
        <textarea
          minLength={8}
          onChange={(event) => onTemplateBodyChange(event.target.value)}
          placeholder="Hi {{name}}, your notification is ready."
          required
          rows={9}
          value={templateBody}
        />
      </label>

      <button className="submit-button" disabled={isSubmitting} type="submit">
        {isSubmitting ? 'Uploading...' : 'Upload template'}
      </button>
    </form>
  )
}

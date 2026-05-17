import type { TemplateType } from '../../types/templates'

export const TEMPLATE_TYPE_OPTIONS: Array<{
  label: string
  value: TemplateType
}> = [
  { label: 'Email', value: 'EMAIL' },
  { label: 'SMS', value: 'SMS' },
  { label: 'WhatsApp', value: 'WHATSAPP' },
  { label: 'Instagram', value: 'INSTAGRAM' },
]

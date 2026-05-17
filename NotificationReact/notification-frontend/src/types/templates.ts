export type TemplateType = 'EMAIL' | 'SMS' | 'WHATSAPP' | 'INSTAGRAM'

export type CreateTemplateRequest = {
  type: TemplateType
  template: string
}

export type TemplateResponse = CreateTemplateRequest & {
  id: number
}

import type { TemplateResponse, TemplateType } from '../../types/templates'

type TemplatePreviewProps = {
  savedTemplate: TemplateResponse | null
  templateBody: string
  templateType: TemplateType
}

export function TemplatePreview({
  savedTemplate,
  templateBody,
  templateType,
}: TemplatePreviewProps) {
  return (
    <aside className="preview-panel" aria-label="Template preview">
      <span className="preview-label">{templateType}</span>
      <h2>Preview</h2>
      <p>
        {templateBody ||
          'Write a template message to preview what the admin will save.'}
      </p>
      {savedTemplate && (
        <div className="saved-row">
          <span>Saved template</span>
          <strong>#{savedTemplate.id}</strong>
        </div>
      )}
    </aside>
  )
}

type SectionHeaderProps = {
  eyebrow: string
  id: string
  title: string
  description: string
  className?: string
}

export function SectionHeader({
  eyebrow,
  id,
  title,
  description,
  className = 'admin-copy',
}: SectionHeaderProps) {
  return (
    <div className={className}>
      <span className="eyebrow">{eyebrow}</span>
      <h1 id={id}>{title}</h1>
      <p>{description}</p>
    </div>
  )
}

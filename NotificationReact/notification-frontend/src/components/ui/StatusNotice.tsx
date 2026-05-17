type StatusNoticeProps = {
  children: string
  tone?: 'danger' | 'success'
}

export function StatusNotice({
  children,
  tone = 'danger',
}: StatusNoticeProps) {
  return <div className={tone === 'success' ? 'notice success' : 'notice'}>{children}</div>
}

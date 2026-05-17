import type { UserResponse } from '../../types/auth'

type UserSummaryCardProps = {
  user: UserResponse
}

export function UserSummaryCard({ user }: UserSummaryCardProps) {
  return (
    <section className="user-card" aria-label="Saved user">
      <span>User #{user.id}</span>
      <strong>{user.name}</strong>
      <small>
        {user.email} · {user.userType}
      </small>
    </section>
  )
}

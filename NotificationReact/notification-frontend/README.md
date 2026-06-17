# Notification Control Center Frontend

React + TypeScript admin and customer portal for the Omni-Channel Notification Platform.

For the full project overview, architecture diagrams, ER diagram, UML diagram, API examples, and setup instructions, see the main repository README:

```text
../../README.md
```

## Frontend Responsibilities

- Secure login screen for admin and user access.
- JWT-aware API client that sends `Authorization: Bearer <token>` on protected requests.
- Admin console for message operations, templates, and user management.
- User portal for customer profile and plan visibility.
- Channel-aware forms for Email, SMS, WhatsApp, and Instagram notification workflows.

## Tech Stack

- React
- TypeScript
- Vite
- React Router
- TanStack Query
- Axios / Fetch API

## Local Development

```bash
npm install
npm run dev
```

Default URL:

```text
http://localhost:5173
```

## Production Build

```bash
npm run build
```


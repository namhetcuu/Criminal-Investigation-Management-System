# Dockerfile

# --- Stage 1: Dependencies ---
FROM node:18-alpine AS deps
WORKDIR /app

# Copy package files
COPY package.json package-lock.json* pnpm-lock.yaml* ./

# Install dependencies only
RUN npm ci --only=production

# --- Stage 2: Build ---
FROM node:18-alpine AS builder
WORKDIR /app

# Copy node_modules from deps stage
COPY --from=deps /app/node_modules ./node_modules

# Copy package files and config files
COPY package.json package-lock.json* pnpm-lock.yaml* ./
COPY next.config.js ./
COPY tsconfig.json ./
COPY components.json ./

# Copy Tailwind CSS v4 config files
COPY tailwind.config.ts ./
COPY postcss.config.mjs ./

# Copy source code
COPY . .

# Install all dependencies (including dev dependencies for build)
RUN npm ci

# Build the application
ENV NEXT_TELEMETRY_DISABLED 1
RUN npm run build

# --- Stage 3: Runtime ---
FROM node:18-alpine AS runner
WORKDIR /app

# Set environment
ENV NODE_ENV production
ENV NEXT_TELEMETRY_DISABLED 1

# Create user for security
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

# Copy public assets
COPY --from=builder /app/public ./public

# Copy Next.js build output
COPY --from=builder --chown=nextjs:nodejs /app/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static

# Switch to non-root user
USER nextjs

EXPOSE 3000
ENV PORT 3000
ENV HOSTNAME "0.0.0.0"

CMD ["node", "server.js"]
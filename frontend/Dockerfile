# ============================================
# Stage 1: Build Stage
# ============================================
FROM node:20-alpine AS builder

WORKDIR /app

# Copy package files for dependency installation
COPY package*.json ./

# Install dependencies with clean install for reproducible builds
RUN npm ci

# Copy application source code
COPY . .

# Build Next.js application for production
# This generates optimized static files in .next directory
RUN npm run build

# ============================================
# Stage 2: Production Runtime Stage
# ============================================
FROM node:20-alpine AS runner

WORKDIR /app

# Set environment to production
ENV NODE_ENV=production

# Create a non-root user for security
RUN addgroup --system --gid 1001 nodejs && \
    adduser --system --uid 1001 nextjs

# Copy necessary files from builder stage
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/next.config.mjs ./
COPY --from=builder /app/public ./public

# Copy built Next.js application
# Set correct ownership for non-root user
COPY --from=builder --chown=nextjs:nodejs /app/.next ./.next
COPY --from=builder --chown=nextjs:nodejs /app/node_modules ./node_modules

# Switch to non-root user for security
USER nextjs

# Expose Next.js production port
EXPOSE 3000

# Set environment variable for Next.js telemetry
ENV NEXT_TELEMETRY_DISABLED=1

# Health check to ensure container is running
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD node -e "require('http').get('http://localhost:3000/api/health', (r) => {process.exit(r.statusCode === 200 ? 0 : 1)})" || exit 1

# Start Next.js production server
CMD ["npm", "start"]

# Development stage with hot-reloading support
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Install dependencies first (for better caching)
COPY package*.json ./
RUN npm ci

# Copy the rest of the application
COPY . .

# Expose Next.js dev server port
EXPOSE 3000

# Start development server with hot-reloading
CMD ["npm", "run", "dev"]

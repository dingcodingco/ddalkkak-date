import * as Sentry from '@sentry/nextjs';

Sentry.init({
  dsn: process.env.NEXT_PUBLIC_SENTRY_DSN,
  environment: process.env.NEXT_PUBLIC_SENTRY_ENVIRONMENT || 'development',

  // Performance Monitoring
  tracesSampleRate: parseFloat(
    process.env.NEXT_PUBLIC_SENTRY_TRACES_SAMPLE_RATE || '1.0'
  ),

  // Debug mode
  debug: process.env.NODE_ENV === 'development',

  // Release tracking
  release: process.env.NEXT_PUBLIC_SENTRY_RELEASE,
});

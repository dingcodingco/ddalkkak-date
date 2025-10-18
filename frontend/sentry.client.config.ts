import * as Sentry from '@sentry/nextjs';

Sentry.init({
  dsn: process.env.NEXT_PUBLIC_SENTRY_DSN,
  environment: process.env.NEXT_PUBLIC_SENTRY_ENVIRONMENT || 'development',

  // Performance Monitoring
  tracesSampleRate: parseFloat(
    process.env.NEXT_PUBLIC_SENTRY_TRACES_SAMPLE_RATE || '1.0'
  ),

  // Session Replay
  replaysSessionSampleRate: 0.1,
  replaysOnErrorSampleRate: 1.0,

  // Debug mode
  debug: process.env.NODE_ENV === 'development',

  // Release tracking
  release: process.env.NEXT_PUBLIC_SENTRY_RELEASE,

  // Integrations
  integrations: [
    Sentry.replayIntegration({
      maskAllText: true,
      blockAllMedia: true,
    }),
  ],

  // Filter sensitive data
  beforeSend(event) {
    // Remove sensitive query parameters
    if (event.request?.url) {
      const url = new URL(event.request.url);
      ['token', 'api_key', 'password'].forEach((param) => {
        if (url.searchParams.has(param)) {
          url.searchParams.set(param, '[Filtered]');
        }
      });
      event.request.url = url.toString();
    }
    return event;
  },
});

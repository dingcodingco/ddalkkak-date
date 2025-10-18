import { withSentryConfig } from '@sentry/nextjs';

/** @type {import('next').NextConfig} */
const nextConfig = {
  // Sentry configuration will be added by withSentryConfig
};

// Sentry webpack plugin options
const sentryWebpackPluginOptions = {
  // Suppress source map upload logs
  silent: true,

  // Organization and project names
  org: process.env.SENTRY_ORG,
  project: process.env.SENTRY_PROJECT,

  // Auth token for uploading source maps
  authToken: process.env.SENTRY_AUTH_TOKEN,
};

// Additional Sentry configuration
const sentryOptions = {
  // Automatically tree-shake Sentry logger statements
  widenClientFileUpload: true,

  // Hide source maps from generated client bundles
  hideSourceMaps: true,

  // Disable Sentry during development
  disableLogger: true,
};

export default withSentryConfig(
  nextConfig,
  sentryWebpackPluginOptions,
  sentryOptions
);

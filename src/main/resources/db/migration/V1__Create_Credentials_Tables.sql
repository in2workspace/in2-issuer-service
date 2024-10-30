SELECT version();
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS credentials;

-- Create tenant table if it doesn't exist
CREATE TABLE IF NOT EXISTS credentials.tenant
(
    id UUID PRIMARY KEY DEFAULT (md5(random()::text || clock_timestamp()::text)::uuid),
    tenant_id     VARCHAR(255) NOT NULL,
    tenant_domain VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (tenant_id, tenant_domain) -- Opcional
);

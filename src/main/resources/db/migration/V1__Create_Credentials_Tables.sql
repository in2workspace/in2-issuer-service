SELECT version();

-- Create extension if it doesn't exist - Needed for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS issuers;

-- Create tenant table if it doesn't exist
CREATE TABLE IF NOT EXISTS issuer.tenants
(
    id            UUID PRIMARY KEY DEFAULT (md5(random()::text || clock_timestamp()::text)::uuid),
    tenant_id     VARCHAR(255) NOT NULL,
    tenant_domain VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (tenant_id, tenant_domain) -- Opcional
);

CREATE TABLE IF NOT EXISTS issuer.configurations
(
    id           SERIAL PRIMARY KEY,
    tenant_id    VARCHAR(100) NOT NULL,
    config_key   VARCHAR(100) NOT NULL,
    config_value VARCHAR(255) NOT NULL,
    -- Avoiding duplicates by tenant
    UNIQUE (tenant_id, config_key)
);

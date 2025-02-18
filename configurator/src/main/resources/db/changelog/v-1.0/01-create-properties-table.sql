CREATE TABLE texture_properties
(
    uuid               UUID PRIMARY KEY,
    bump_scale         DOUBLE PRECISION,
    metalness          DOUBLE PRECISION NOT NULL,
    roughness          DOUBLE PRECISION NOT NULL,
    emissive_intensity DOUBLE PRECISION
);
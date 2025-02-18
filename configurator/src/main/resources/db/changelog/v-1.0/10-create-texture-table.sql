CREATE TABLE textures
(
    uuid             UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL UNIQUE,
    base_texture_url VARCHAR(255) NOT NULL,
    alpha_map_url    VARCHAR(255),
    bump_map_url     VARCHAR(255),
    properties_uuid  UUID         NOT NULL,
    FOREIGN KEY (properties_uuid) REFERENCES texture_properties (uuid) ON DELETE CASCADE
);
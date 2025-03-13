CREATE TABLE textures
(
    id               UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL UNIQUE,
    base_texture_url TEXT NOT NULL,
    alpha_map_url    TEXT,
    bump_map_url     TEXT,
    properties_id    UUID         NOT NULL,
    icon_id          UUID         NOT NULL,
    FOREIGN KEY (properties_id) REFERENCES texture_properties (id) ON DELETE CASCADE,
    FOREIGN KEY (icon_id) REFERENCES icons (id) ON DELETE CASCADE
);
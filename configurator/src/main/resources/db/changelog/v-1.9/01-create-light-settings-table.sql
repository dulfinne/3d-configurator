CREATE TABLE light_settings
(
    id                BIGSERIAL PRIMARY KEY,
    react_intensity   DOUBLE PRECISION NOT NULL,
    react_color       VARCHAR          NOT NULL,
    ambient_intensity DOUBLE PRECISION NOT NULL,
    ambient_color     VARCHAR          NOT NULL
);

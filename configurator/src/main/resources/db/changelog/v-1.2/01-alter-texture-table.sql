ALTER TABLE textures
    ADD COLUMN normal_map_url TEXT,
    ADD COLUMN metalness_map_url TEXT,
    ADD COLUMN roughness_map_url TEXT,
    ADD COLUMN ao_map_url TEXT,
    ADD COLUMN displacement_map_url TEXT;

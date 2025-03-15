ALTER TABLE textures DROP COLUMN base_color;

ALTER TABLE textures
    ADD COLUMN base_color_url TEXT;

UPDATE textures
SET base_color_url = base_texture_url
WHERE base_color_url IS NULL;

ALTER TABLE textures
    ALTER COLUMN base_color_url SET NOT NULL;

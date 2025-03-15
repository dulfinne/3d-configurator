ALTER TABLE textures DROP COLUMN base_color_url;

ALTER TABLE textures
    ADD COLUMN base_color VARCHAR(7);

UPDATE textures
SET base_color = '#ffffff'
WHERE base_color IS NULL;

ALTER TABLE textures
    ALTER COLUMN base_color SET NOT NULL;

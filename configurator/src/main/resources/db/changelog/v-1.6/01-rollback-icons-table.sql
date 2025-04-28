ALTER TABLE icons
    ADD COLUMN type INT NOT NULL DEFAULT 0;

UPDATE icons
SET type =
        CASE
            WHEN is_door THEN 1
            WHEN is_wall THEN 2
            WHEN is_floor THEN 3
            WHEN is_ceiling THEN 4
            WHEN is_ceiling_material THEN 5
            WHEN is_control_panel THEN 6
            WHEN is_handrail THEN 7
            WHEN is_bumper THEN 8
            WHEN is_indication_board THEN 9
            ELSE 0
            END;

ALTER TABLE icons
DROP COLUMN is_door,
DROP COLUMN is_wall,
DROP COLUMN is_floor,
DROP COLUMN is_ceiling,
DROP COLUMN is_ceiling_material,
DROP COLUMN is_control_panel,
DROP COLUMN is_handrail,
DROP COLUMN is_bumper,
DROP COLUMN is_indication_board;

ALTER TABLE icons
ADD COLUMN is_door BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_wall BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_floor BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_ceiling BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_ceiling_material BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_control_panel BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_handrail BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_bumper BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_indication_board BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE icons
SET is_door             = (type = 1),
    is_wall             = (type = 2),
    is_floor            = (type = 3),
    is_ceiling          = (type = 4),
    is_ceiling_material = (type = 5),
    is_control_panel    = (type = 6),
    is_handrail         = (type = 7),
    is_bumper           = (type = 8),
    is_indication_board = (type = 9);

ALTER TABLE icons DROP COLUMN type;

CREATE TABLE project_templates
(
    id                UUID PRIMARY KEY,
    design_project_id UUID,
    name              VARCHAR(20) NOT NULL,
    preview_image_url TEXT        NOT NULL,
    configuration     TEXT        NOT NULL,
    CONSTRAINT fk_design_project FOREIGN KEY (design_project_id) REFERENCES design_projects (id) ON DELETE CASCADE
);
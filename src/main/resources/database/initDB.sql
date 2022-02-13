CREATE TABLE IF NOT EXISTS project
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(200) ,
    startDate timestamp ,
    finishDate timestamp ,
    status VARCHAR(200) ,
    priority integer
    );

CREATE TABLE IF NOT EXISTS task
(
    id    BIGSERIAL PRIMARY KEY,
    projectId BIGINT,
    task json
);
CREATE SCHEMA IF NOT EXISTS ewm;
CREATE TABLE IF NOT EXISTS ewm.users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                            NOT NULL,
    email VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS ewm.categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS ewm.events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(400)                            NOT NULL,
    category_id        BIGINT,
    confirmed_requests BOOLEAN,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(5000),
    event_date         TIMESTAMP,
    initiator_id       BIGINT,
    paid               BOOLEAN,
    participant_limit  BIGINT,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              VARCHAR(255),
    title              VARCHAR(255),
    views              BIGINT,
    lat                FLOAT,
    lon                FLOAT,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES ewm.categories (id),
    CONSTRAINT FK_EVENT_ON_INITIATOR FOREIGN KEY (initiator_id) REFERENCES ewm.users (id)
);

CREATE TABLE IF NOT EXISTS ewm.participation_requests
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created  TIMESTAMP WITHOUT TIME ZONE,
    event_id BIGINT,
    user_id  BIGINT,
    state    VARCHAR(255),
    CONSTRAINT pk_participation PRIMARY KEY (id),
    CONSTRAINT FK_PARTICIPATION_ON_EVENT FOREIGN KEY (event_id) REFERENCES ewm.events (id),
    CONSTRAINT FK_PARTICIPATION_ON_USER FOREIGN KEY (user_id) REFERENCES ewm.users (id)

);
CREATE TABLE IF NOT EXISTS ewm.compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title  VARCHAR(255),
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event_compilation
(
    compilation_id BIGINT,
    event_id       BIGINT,
    CONSTRAINT pk_event_compilation PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT FK_EVENT_COMPILATION_ON_COMPILATION FOREIGN KEY (compilation_id) REFERENCES ewm.compilations (id),
    CONSTRAINT FK_EVENT_COMPILATION_ON_EVENT FOREIGN KEY (event_id) REFERENCES ewm.events (id)
);
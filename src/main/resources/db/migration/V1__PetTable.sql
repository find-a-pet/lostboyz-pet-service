CREATE TABLE IF NOT EXISTS pets (
    id UUID NOT NULL PRIMARY KEY,
    age INT,
    sex CHAR,
    description TEXT,
    name VARCHAR,
    type VARCHAR,
    url TEXT NOT NULL,
    breed VARCHAR,
    is_found BOOLEAN DEFAULT FALSE,
    last_seen_location VARCHAR
);
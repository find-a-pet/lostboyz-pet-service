CREATE TABLE IF NOT EXISTS pets (
    id UUID NOT NULL PRIMARY KEY,
    age INT,
    sex CHAR,
    description TEXT,
    name VARCHAR,
    type VARCHAR,
    url TEXT NOT NULL,
    breed VARCHAR,
    found BOOLEAN DEFAULT FALSE,
    location VARCHAR
);
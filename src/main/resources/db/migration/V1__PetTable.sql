CREATE TABLE IF NOT EXISTS pets (
    id UUID NOT NULL PRIMARY KEY,
    age INT,
    sex VARCHAR(100),
    description TEXT,
    name VARCHAR(100),
    type VARCHAR(100),
    url TEXT NOT NULL,
    breed VARCHAR(100),
    is_found BOOLEAN DEFAULT FALSE,
    last_seen_location VARCHAR(100)
);
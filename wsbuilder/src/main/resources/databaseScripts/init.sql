CREATE TABLE users.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    profile_image TEXT,
    personal_info TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE config.configuration (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users.users(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    share_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE config.configurationSetting (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255),
    description TEXT,
    key VARCHAR(255) NOT NULL,
    value TEXT NOT NULL,
    value_type VARCHAR(50) NOT NULL
);


CREATE table config.configurationSettingMapping (
    configuration_id INT REFERENCES config.configuration(id) ON DELETE CASCADE,
    setting_id INT REFERENCES config.configurationSetting(id) ON DELETE CASCADE,
    PRIMARY KEY (configuration_id, setting_id)
);


CREATE TABLE extension.extension (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    version VARCHAR(50) NOT NULL
);


CREATE TABLE configurationExtension (
    configuration_id INT REFERENCES config.configuration(id) ON DELETE CASCADE,
    extension_id INT REFERENCES extension.extension(id) ON DELETE CASCADE,
    PRIMARY KEY (configuration_id, extension_id)
);
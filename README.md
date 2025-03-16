# WSBuilder

## Overview

WSBuilder is a Java-based application that provides a user-friendly interface for managing and building web services. It
leverages JavaFX for the graphical user interface and integrates with a SQL database for data persistence.

## Features

- User authentication (Login and Sign In)
- Guest user access
- Data management through a SQL database
- Responsive UI with JavaFX

## How It Works

### User Authentication

The application provides a login and sign-in interface for users. The authentication process is managed by the
`AuthenticationController` which handles user input and validation.

### Guest User Access

Guest users can access the application without authentication by clicking the "GUEST USER" button, which switches the
view to the main application interface.

### VSCode Marketplace Integration
The application connects to the VSCode Marketplace API to fetch and display a list of extensions in an organized manner.

## Database Information

The application uses a SQL database to store user information and other relevant data. The database schema includes
tables for users, roles, and permissions.


### Database Schema

The database schema includes the following tables:

- `users.users`: Stores user information including username, email, password hash, profile image, personal info,
  description, and timestamps for creation and updates.
- `config.configuration`: Stores configuration data associated with users, including name, description, visibility,
  share count, and timestamps for creation and updates.
- `config.configurationSetting`: Stores individual configuration settings, including type, name, description, key,
  value, and value type.
- `config.configurationSettingMapping`: Maps configurations to their settings.
- `extension.extension`: Stores information about extensions, including name, description, and version.
- `configurationExtension`: Maps configurations to their extensions.

```sql
CREATE TABLE users.users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    profile_image TEXT,
    personal_info TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_username_email UNIQUE (username, email)
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
```
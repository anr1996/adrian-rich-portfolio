-- V1__init.sql: initial schema for the portfolio website.
-- Flyway applies this file once, in order, and will record that it ran.

-- ========================================================================
-- Profile: the home page name, headline and bio. Exactly one row.
-- ========================================================================
CREATE TABLE profile (
	id 	  SMALLINT     PRIMARY KEY DEFAULT 1 CHECK (id = 1),
	full_name VARCHAR(100) NOT NULL,
	headline  VARCHAR(200) NOT NULL,
	bio 	  TEXT 	       NOT NULL,
	location  VARCHAR(100)
);


-- ========================================================================
-- Projects shown on the Projects and Home pages.
-- ========================================================================
CREATE TABLE projects (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, 
	slug          VARCHAR(100) NOT NULL UNIQUE 
				       CHECK (slug ~ '^[a-z0-9]+(-[a-z0-9]+)*$'),
	title         VARCHAR(120) NOT NULL,
	summary       VARCHAR(300) NOT NULL,
	description   TEXT,
	repo_url      VARCHAR(500),
	demo_url      VARCHAR(500),
	featured      BOOLEAN      NOT NULL DEFAULT false,
	published     BOOLEAN      NOT NULL DEFAULT false,
	display_order INTEGER      NOT NULL DEFAULT 0,
	created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
	updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- The public Projects page will ask for published projects in display order.
CREATE INDEX idx_projects_published_order ON projects (published, display_order);

-- ========================================================================
-- Technology tags (Java, React, PostgreSQL...).
-- ========================================================================
CREATE TABLE tags (
	id   BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE
);


-- ========================================================================
-- Join table: which tags belong to which projects (many-to-many).
-- ========================================================================
CREATE TABLE project_tags (
	project_id BIGINT NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
	tag_id BIGINT NOT NULL REFERENCES tags (id) ON DELETE CASCADE,
	PRIMARY KEY (project_id, tag_id)
);

-- The primary key index starts with project_id, so it can't speed up
-- "find projects with this tag". The index covers that direction.
CREATE INDEX idx_project_tags_tag_id ON project_tags (tag_id);

-- ========================================================================
-- Education: WGU degree, OMSCS
-- ========================================================================

CREATE TABLE education (
	id 	      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	institution   VARCHAR(150) NOT NULL,
	credential    VARCHAR(150) NOT NULL,
	status 	      VARCHAR(20)  NOT NULL CHECK (status IN ('COMPLETED', 'IN_PROGRESS', 'ADMITTED')),
	start_date    DATE         NOT NULL,
	end_date      DATE,
	description   TEXT,
	display_order INTEGER      NOT NULL DEFAULT 0,
	CONSTRAINT education_dates_check CHECK (end_date IS NULL OR end_date >= start_date)
);

-- ========================================================================
-- Certifications and micro-credentials.
-- ========================================================================

CREATE TABLE certifications (
	id 	      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name          VARCHAR(150) NOT NULL,
	issuer        VARCHAR(150) NOT NULL,
	issued_on     DATE         NOT NULL,
	expires_on    DATE,
	display_order INTEGER      NOT NULL DEFAULT 0,
	CONSTRAINT certifications_dates_check CHECK (expires_on IS NULL OR expires_on > issued_on)
	
);


-- ========================================================================
-- Work experience. end_date NULL means a current role.
-- ========================================================================
CREATE TABLE experience (
	id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title         VARCHAR(150) NOT NULL,
	organization  VARCHAR(150) NOT NULL,
	location      VARCHAR(100),
	start_date    DATE NOT NULL,
	end_date      DATE,
	description   TEXT,
	display_order INTEGER NOT NULL DEFAULT 0,
	CONSTRAINT experience_dates_check CHECK (end_date IS NULL OR  end_date >= start_date)
);


-- ========================================================================
-- Resume skills, grouped by category (Language, Frameworks...).
-- ========================================================================
CREATE TABLE skills (
	id 	     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name          VARCHAR(50) NOT NULL UNIQUE,
	category      VARCHAR(50) NOT NULL,	
	display_order INTEGER     NOT NULL DEFAULT 0
);

-- ========================================================================
-- Footer and Contact page links (Github, LinkedIn, email).
-- ========================================================================
CREATE TABLE links (
	id 	      BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	label 	      VARCHAR(50) NOT NULL,
	url           VARCHAR(500) NOT NULL CHECK (url ~ '^(https://|mailto:)'),
	display_order INTEGER     NOT NULL DEFAULT 0
	
);

-- ========================================================================
-- Messages sent through the Contact form.
-- ========================================================================
CREATE TABLE contact_messages (
	id 	   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name 	   VARCHAR(100) NOT NULL,
	email      VARCHAR(254) NOT NULL,
	body       TEXT         NOT NULL CHECK (char_length(body) BETWEEN 10 and 5000),
	is_read    BOOLEAN      NOT NULL DEFAULT false,
	created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- ========================================================================
-- Uploaded files. One row per kind; for now only the resume PDF.
-- ========================================================================
CREATE TABLE documents (
	id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	kind         VARCHAR(30)   NOT NULL UNIQUE CHECK (kind IN ('RESUME')),
	filename     VARCHAR(255)  NOT NULL,
	content_type VARCHAR(100)  NOT NULL CHECK (content_type = 'application/pdf'),
	size_bytes   BIGINT 	   NOT NULL CHECK (size_bytes BETWEEN 1 AND 5242880),
	data 	     BYTEA  	   NOT NULL,
	uploaded_at  TIMESTAMPTZ   NOT NULL DEFAULT now()
);

-- ========================================================================
-- The admin login. Stores an Argon2id hash, never the password.
-- ========================================================================
CREATE TABLE admin_users(
	id 	      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	username      VARCHAR(50)  NOT NULL UNIQUE,
	password_hash VARCHAR(255) NOT NULL,
	created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
	last_login_at TIMESTAMPTZ
);

-- ========================================================================
-- Anonymous page views: path and time only, no personal data.
-- ========================================================================
CREATE TABLE page_views (
	id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	path 	  VARCHAR(200) NOT NULL,
	viewed_at TIMESTAMPTZ  NOT NULL DEFAULT now()

);

-- Stats queries filter by time ("last 30 days"),
CREATE INDEX idx_page_views_viewed_at ON page_views (viewed_at);



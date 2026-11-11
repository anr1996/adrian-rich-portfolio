-- V2__seed.sql: initial site content. Applied once, right after V1.
-- After launch, all content is edited through the admin dashboard.

-- ================================================================
-- Profile (singleton row; id defaults to 1).
-- ================================================================
INSERT INTO profile (full_name, headline, location, bio)
VALUES (
	'Adrian Rich',
	'Software engineer | Full-stack Java, Spring Boot & React | Interested in AI-native engineering',
	'Keizer, OR',
	$$I'm a software engineer and a 2026 Computer Science graduate of Western Governors University, starting Georgia Tech's Online Master of Science in Computer Science in Spring 2027. I build full-stack applications with Java, Spring Boot, React, TypeScript and PostgreSQL, and I've also worked in C++ and Python, including an XGBoost regression model that reached an R squared of about 0.965. Before software, I spent nearly six years as a shift supervisor and almost a decade in customer-facing roles, which taught me to start with the user's actual problem.

I have a strong interest in AI-native software engineering. I'd like to learn to build production LLM applications: retrieval-augmented generation (RAG), agents that call tools, and evaluating model output. My coursework includes Introduction to AI for Computer Scientists, AI Optimization, Advanced AI and ML, and prompt engineering, and I hold WGU's AI Optimization Developer micro-credential.$$
);


-- ================================================================
-- Footer and Contact page links. No email or phone (kept private).
-- ================================================================

INSERT INTO links (label, url, display_order)
VALUES
	('GitHub', 'https://github.com/anr1996',			1),
	('LinkedIn', 'https://www.linkedin.com/in/adrianrich1996/', 	2);


-- ================================================================
-- Education. display_order: lower numbers show first.
-- OMSCS start day is a placeholder: the site shows month and year only.
-- ================================================================

INSERT INTO education (institution, credential, status, start_date, end_date, description, display_order)
VALUES
	('Georgia Institute of Technology',
	 'Master of Science in Computer Science (OMSCS)',
	 'ADMITTED', '2027-01-01', NULL,
	 'Online Master of Science in Computer Science, starting Spring 2027.',
	  1
	),
	('Western Governors University',
	 'Bachelor of Science in Computer Science',
	 'COMPLETED', '2025-03-01', '2026-06-29',
	 'Relevant coursework: Data Structures and Algorithms I and II, Computer Architecture, Operating Systems, Software Engineering, Advanced Java, Back-End Programming, Advanced AI and ML, AI Optimization.', 2);

-- ================================================================
-- Certifications and micro-credentials, newest first.
-- expires_on is left out: none of these expire.
-- ================================================================
INSERT INTO certifications (name, issuer, issued_on, display_order)
VALUES
	('AI Optimization Developer (micro-credential)', 'Western Governors University',       '2026-05-11', 1),
        ('ITIL Foundation',				 'AXELOS',			       '2026-02-09', 2),
	('Java Developer (micro-credential)',		 'Western Governors University',       '2025-10-15', 3),
        ('Back-End Developer (micro-credential)',	 'Western Governors University',       '2025-10-09', 4),
        ('Linux Essentials',				 'Linux Professional Institute (LPI)', '2025-09-20', 5);	


-- ================================================================
-- Projects. The portfolio stays unpublished until the site is live.
-- description is left NULL for now; it is written later in the admin dashboard.
-- ================================================================

INSERT INTO projects (slug, title, summary, repo_url, featured, published, display_order)
VALUES
	('rich-social-cpp',
	 'Rich Social',
	 'Desktop social app in C++20 with wxWidgets and PostgreSQL: Argon2id login, validated profiles, posts, follows and a personal feed.',
	 'https://github.com/anr1996/rich-social-cpp',
	  true, true, 1),
	('adrian-rich-portfolio',
	 'Portfolio Website',
	 'This site: a React and TypeScript single-page app backed by a Java Spring Boot REST API and PostgreSQL, with a secured admin dashboard.',
         'https://github.com/anr1996/adrian-rich-portfolio',
         true, false, 2),
	('xgboost-regression',
	 'XGBoost Regression Model',
	 'Trained an XGBoost regression model reaching an R squared of about 0.965, using regularization and ensemble methods, with a written evaluation report.',
	 NULL,
         false, true, 3);

-- ================================================================
-- Technology tags.
-- ================================================================
INSERT INTO tags (name)
VALUES ('C++'), ('wxWidgets'), ('PostgreSQL'), ('Docker'), ('Java'), ('Spring Boot'),
        ('React'), ('TypeScript'), ('Python'), ('XGBoost'), ('Machine Learning');

-- ================================================================
-- Link projects to tags. Ids are looked up by slug and tag name,
-- never typed by hand, because the database generates them.
-- ================================================================
INSERT INTO project_tags (project_id, tag_id)
SELECT p.id, t.id
FROM projects p
JOIN tags t ON t.name IN ('C++', 'wxWidgets', 'PostgreSQL', 'Docker')
WHERE p.slug = 'rich-social-cpp';

INSERT INTO project_tags (project_id, tag_id)
SELECT p.id, t.id
FROM projects p
JOIN tags t ON t.name IN ('Java', 'Spring Boot', 'React', 'TypeScript', 'PostgreSQL', 'Docker')
WHERE p.slug = 'adrian-rich-portfolio';

INSERT INTO project_tags (project_id, tag_id)
SELECT p.id, t.id
FROM projects p
JOIN tags t ON t.name IN ('Python', 'XGBoost', 'Machine Learning')
WHERE p.slug = 'xgboost-regression';

-- ================================================================
-- Work experience, newest first. end_date NULL = current role.
-- Months are stored as the 1st; the site shows month and year only.
-- Each description line is one bullet point.
-- ================================================================

INSERT INTO experience (title, organization, start_date, end_date, description, display_order)
VALUES
 ('Sales Associate', 'Michaels', '2025-10-01', NULL,
  $$Process high-volume transactions, returns and exchanges accurately, applying store policy and customer-first problem-solving.
Build lasting relationships with repeat customers through product recommendations and cost-saving guidance, earning positive online reviews.$$,
  1),
 ('Front Desk Consultant', 'Prosper Athletic Club', '2023-06-01', '2025-06-01',
  $$Primary point of contact for 500+ active members: onboarding, scheduling, account resolution and collections outreach.
Handled confidential billing disputes and member complaints, reducing churn through proactive follow-up.
Worked across front desk, training and management teams to streamline check-in workflow.$$,
  2),
 ('Shift Supervisor', 'Round Table Pizza', '2017-10-01', '2023-06-01',
  $$Led a team of 5 to 8 crew members per shift, delegating tasks and setting performance expectations.
Owned end-to-end shift operations, including cash handling, deposits, inventory counts and conflict resolution.
De-escalated customer complaints and turned negative experiences into repeat business.$$,
  3);

-- ================================================================
-- Resume skills. display_order restarts within each category.
-- ================================================================

INSERT INTO skills (name, category, display_order)
VALUES
      ('Java',                'Languages',  1),
      ('Python',              'Languages',  2),
      ('C++',                 'Languages',  3),
      ('C',                   'Languages',  4),
      ('TypeScript',          'Languages',  5),
      ('JavaScript',          'Languages',  6),
      ('SQL',                 'Languages',  7),
      ('HTML/CSS',            'Languages',  8),
      ('Spring Boot',         'Frameworks', 1),
      ('React',               'Frameworks', 2),
      ('wxWidgets',           'Frameworks', 3),
      ('PostgreSQL',          'Databases',  1),
      ('MySQL',               'Databases',  2),
      ('XGBoost',             'AI/ML',      1),
      ('Regression Modeling', 'AI/ML',      2),
      ('Prompt Engineering',  'AI/ML',      3),
      ('Git',                 'Tools',      1),
      ('Docker',              'Tools',      2),
      ('Linux',               'Tools',      3),
      ('Maven',               'Tools',      4); 

INSERT INTO users (
  username,
  email,
  password_hash,
  role,
  created_at
) VALUES (
  'admin',
  'admin@gametracker.com',
  '123456',
  'ADMIN',
  NOW()
);

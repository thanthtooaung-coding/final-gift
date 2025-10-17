psql -U postgres -p 1800 -d postgres
----------------------
DO
$$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'shop_user') THEN
        CREATE ROLE shop_user WITH LOGIN PASSWORD 'shop_pass';
    END IF;
END
$$;

CREATE DATABASE shop_management
    WITH 
    OWNER = shop_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0
    CONNECTION LIMIT = -1;
-----------------------
psql -U postgres -p 1800 -d shop_management
-----------------------
ALTER SCHEMA public OWNER TO shop_user;
GRANT ALL ON SCHEMA public TO shop_user;
GRANT ALL ON SCHEMA public TO public;

CREATE OR REPLACE FUNCTION reset_sequence()
    RETURNS trigger AS
$$
DECLARE
    seq_name TEXT;
BEGIN
    -- Sekans adını al
    SELECT pg_get_serial_sequence(TG_TABLE_NAME, 'id') INTO seq_name;

    -- Eğer sekans varsa, sekansı, mevcut en büyük ID ile güncelle
    IF seq_name IS NOT NULL THEN
        EXECUTE format('SELECT setval(%L, COALESCE(MAX(id), 1), true) FROM %I', seq_name, TG_TABLE_NAME);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- borrow_statuses tablosu için trigger
CREATE TRIGGER reset_borrow_statuses_sequence
    BEFORE INSERT ON borrow_statuses
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- common_statuses tablosu için trigger
CREATE TRIGGER reset_common_statuses_sequence
    BEFORE INSERT ON common_statuses
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- inventory_statuses tablosu için trigger
CREATE TRIGGER reset_inventory_statuses_sequence
    BEFORE INSERT ON inventory_statuses
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- user_roles tablosu için trigger
CREATE TRIGGER reset_user_roles_sequence
    BEFORE INSERT ON user_roles
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- users tablosu için trigger
CREATE TRIGGER reset_users_sequence
    BEFORE INSERT ON users
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- admins tablosu için trigger
CREATE TRIGGER reset_admins_sequence
    BEFORE INSERT ON admins
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- employees tablosu için trigger
CREATE TRIGGER reset_employees_sequence
    BEFORE INSERT ON employees
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- authors tablosu için trigger
CREATE TRIGGER reset_authors_sequence
    BEFORE INSERT ON authors
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- students tablosu için trigger
CREATE TRIGGER reset_students_sequence
    BEFORE INSERT ON students
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- categories tablosu için trigger
CREATE TRIGGER reset_categories_sequence
    BEFORE INSERT ON categories
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- book_inventory tablosu için trigger
CREATE TRIGGER reset_book_inventory_sequence
    BEFORE INSERT ON book_inventory
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- books tablosu için trigger
CREATE TRIGGER reset_books_sequence
    BEFORE INSERT ON books
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- tokens tablosu için trigger
CREATE TRIGGER reset_tokens_sequence
    BEFORE INSERT ON tokens
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- book_borrowing tablosu için trigger
CREATE TRIGGER reset_book_borrowing_sequence
    BEFORE INSERT ON book_borrowing
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- book_file tablosu için trigger
CREATE TRIGGER reset_book_file_sequence
    BEFORE INSERT ON book_files
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();

-- images tablosu için trigger
CREATE TRIGGER reset_images_sequence
    BEFORE INSERT ON images
    FOR EACH ROW
EXECUTE FUNCTION reset_sequence();


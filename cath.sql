-- Create a table for categories
CREATE TABLE categories (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

-- Create a table for subcategories
CREATE TABLE subcategories (
    subcategory_id INT PRIMARY KEY,
    subcategory_name VARCHAR(100) NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Insert sample data into categories table
INSERT INTO categories (category_id, category_name) VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Books');

-- Insert sample data into subcategories table
INSERT INTO subcategories (subcategory_id, subcategory_name, category_id) VALUES
(101, 'Mobile Phones', 1),
(102, 'Laptops', 1),
(103, 'T-Shirts', 2),
(104, 'Jeans', 2),
(105, 'Fiction', 3),
(106, 'Non-fiction', 3);

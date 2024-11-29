-- Tạo cơ sở dữ liệu
CREATE DATABASE spring_commerce;
USE spring_commerce;

-- Bảng người dùng
CREATE TABLE user(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name TEXT(200) NOT NULL,
    phone VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    birthday DATETIME DEFAULT NULL,
    date_begin DATETIME NOT NULL DEFAULT NOW(),
    gender BIT NOT NULL
);

-- Bảng danh mục sản phẩm
CREATE TABLE category(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

-- Bảng thương hiệu sản phẩm
CREATE TABLE brand(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

-- Bảng màu sắc sản phẩm
CREATE TABLE color(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- Bảng sản phẩm
CREATE TABLE product(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name TEXT(200) NOT NULL,
    price INT NOT NULL,
    discount INT NOT NULL,
    description TEXT,
    category_id BIGINT,
    brand_id BIGINT,
    color_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (brand_id) REFERENCES brand(id),
    FOREIGN KEY (color_id) REFERENCES color(id)
);

-- Bảng chi tiết sản phẩm
CREATE TABLE product_details (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    sku VARCHAR(50) NOT NULL UNIQUE,  -- Mã sản phẩm
    stock_quantity INT NOT NULL,      -- Số lượng tồn kho
    weight DECIMAL(15, 4),            -- Trọng lượng sản phẩm
    dimensions VARCHAR(255),          -- Kích thước sản phẩm (VD: L x W x H)
    color VARCHAR(50),                -- Màu sắc sản phẩm
    material VARCHAR(100),            -- Chất liệu sản phẩm
    image_url VARCHAR(255),           -- URL ảnh sản phẩm
    short_description TEXT,           -- Mô tả ngắn về sản phẩm
    long_description TEXT,            -- Mô tả chi tiết về sản phẩm
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    FOREIGN KEY (product_id) REFERENCES product(id)  -- Liên kết với bảng sản phẩm
);

-- Bảng giỏ hàng
CREATE TABLE cart(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Bảng chi tiết giỏ hàng (sản phẩm trong giỏ)
CREATE TABLE cart_item(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Bảng đơn hàng
CREATE TABLE orders(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    total_amount INT NOT NULL,
    order_status BIT NOT NULL DEFAULT 0,
    delivery_address TEXT,
    payment_method VARCHAR(50) NOT NULL DEFAULT 'Thanh toán khi nhận hàng',
    order_date DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Bảng chi tiết đơn hàng
CREATE TABLE order_item(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);


-- Thêm vào bảng danh mục sản phẩm
INSERT INTO category (name)
VALUES
('Điện thoại'),
('Laptop'),
('Máy tính bảng'),
('Quần áo'),
('Giày dép'),
('Đồ điện tử'),
('Đồ gia dụng');

-- Thêm vào bảng thương hiệu sản phẩm
INSERT INTO brand (name)
VALUES
('Apple'),
('Samsung'),
('Dell'),
('Nike'),
('Adidas'),
('Sony'),
('LG'),
('HP'),
('Oppo'),
('Converse'),
('Huawei'),
('Panasonic');

-- Thêm vào bảng màu sắc sản phẩm
INSERT INTO color (name)
VALUES
('Đen'),
('Trắng'),
('Xanh'),
('Đỏ'),
('Vàng'),
('Xám');

-- Thêm vào bảng sản phẩm
INSERT INTO product (name, price, discount, description, category_id, brand_id, color_id)
VALUES
-- Điện thoại
('iPhone 15', 25000000, 10, 'Điện thoại thông minh của Apple', 1, 1, 1),
('Galaxy S23', 20000000, 15, 'Điện thoại thông minh của Samsung', 1, 2, 2),
('Oppo Reno 8', 12000000, 5, 'Điện thoại thông minh của Oppo', 1, 9, 3),
-- Laptop
('MacBook Pro', 50000000, 5, 'Laptop cao cấp của Apple', 2, 1, 3),
('Laptop Dell XPS', 22000000, 8, 'Laptop Dell với màn hình FHD', 2, 3, 6),
('HP Spectre x360', 30000000, 10, 'Laptop HP cao cấp với màn hình cảm ứng', 2, 8, 6),
-- Máy tính bảng
('iPad Pro', 20000000, 12, 'Máy tính bảng Apple với màn hình Retina', 3, 1, 6),
('Samsung Galaxy Tab S8', 15000000, 10, 'Máy tính bảng Samsung với màn hình AMOLED', 3, 2, 1),
('Huawei MatePad Pro', 18000000, 8, 'Máy tính bảng cao cấp của Huawei', 3, 11, 2),
-- Quần áo
('Áo thun nam', 300000, 10, 'Áo thun cotton dành cho nam', 4, 5, 4),
('Áo sơ mi nam', 500000, 15, 'Áo sơ mi nam thoải mái, dễ chịu', 4, 5, 5),
('Áo khoác nữ', 800000, 5, 'Áo khoác nữ dày dặn cho mùa đông', 4, 5, 1),
-- Giày dép
('Giày thể thao Nike', 1500000, 5, 'Giày thể thao Nike với đế cao su', 5, 4, 1),
('Giày Adidas Ultraboost', 2500000, 8, 'Giày thể thao Adidas Ultraboost 2024', 5, 5, 5),
('Giày Converse Chuck Taylor', 1200000, 12, 'Giày thể thao Converse kiểu dáng cổ điển', 5, 10, 4),
-- Đồ điện tử
('Sony Playstation 5', 15000000, 10, 'Console game Sony Playstation 5', 6, 6, 2),
('Sony WH-1000XM5', 8500000, 5, 'Tai nghe chống ồn Sony WH-1000XM5', 6, 6, 2),
('Máy chiếu LG PF50KA', 10000000, 15, 'Máy chiếu mini LG PF50KA với độ phân giải HD', 6, 7, 2),
-- Đồ gia dụng
('Tủ lạnh LG', 10000000, 12, 'Tủ lạnh LG với công nghệ Inverter', 7, 7, 1),
('Máy giặt Samsung', 12000000, 10, 'Máy giặt Samsung lồng ngang', 7, 2, 1),
('Nồi cơm điện Panasonic', 1500000, 5, 'Nồi cơm điện Panasonic dung tích 1.8L', 7, 12, 2);

-- Thêm vào bảng chi tiết sản phẩm
INSERT INTO product_details (product_id, sku, stock_quantity, weight, dimensions, color, material, image_url, short_description, long_description)
VALUES
(1, 'IP15-001', 100, 0.175, '146.7 x 71.5 x 7.8 mm', 'Đen', 'Kim loại, kính', 'assets/img/ip-15.jpg', 'iPhone 15 cao cấp', 'Điện thoại thông minh Apple với camera 48MP và hiệu suất cao'),
(2, 'GS23-002', 150, 0.169, '146.3 x 70.9 x 7.6 mm', 'Trắng', 'Kim loại, kính', 'assets/img/samsung-s23.jpg', 'Samsung Galaxy S23', 'Điện thoại Galaxy S23 với màn hình AMOLED và bộ xử lý mạnh mẽ'),
(3, 'OPPO-003', 200, 0.180, '160.0 x 75.0 x 8.0 mm', 'Xanh', 'Kim loại, kính', 'assets/img/oppo-reno8.jpg', 'Oppo Reno 8', 'Điện thoại Oppo Reno 8 với màn hình AMOLED và camera 50MP'),
(4, 'MBP-003', 50, 1.4, '30.41 x 21.24 x 1.56 cm', 'Xanh', 'Kim loại, kính', 'assets/img/macbook-pro.jpg', 'MacBook Pro 16-inch', 'Laptop Apple với chip M1 Pro và màn hình Retina'),
(5, 'DELL-004', 80, 2.0, '15.6 inch', 'Xám', 'Nhôm, kính', 'assets/img/laptop-dell-xps.jpg', 'Laptop Dell', 'Laptop Dell với màn hình FHD và bộ vi xử lý Intel Core i5'),
(6, 'HP-005', 60, 2.1, '13.3 inch', 'Đỏ', 'Nhôm, kính', 'assets/img/hp-spectre.jpg', 'HP Spectre x360', 'Laptop HP Spectre x360 với màn hình cảm ứng và bàn phím sáng'),
(7, 'IPAD-006', 100, 0.641, '280 x 210 x 5.9 mm', 'Đen', 'Kim loại, kính', 'assets/img/ipad-pro.jpg', 'iPad Pro', 'Máy tính bảng Apple với màn hình Retina và Apple Pencil hỗ trợ'),
(8, 'TAB-007', 80, 0.503, '254.3 x 165.3 x 5.7 mm', 'Trắng', 'Kim loại, kính', 'assets/img/samsung-tab-s8.jpg', 'Samsung Galaxy Tab S8', 'Máy tính bảng Samsung Galaxy Tab S8 với màn hình 120Hz'),
(9, 'HUAWEI-008', 60, 0.57, '253.8 x 165.3 x 7.2 mm', 'Xanh', 'Kim loại, kính', 'assets/img/huawei-matepad-pro.jpg', 'Huawei MatePad Pro', 'Máy tính bảng Huawei MatePad Pro với màn hình OLED'),
(10, 'TSHIRT-001', 200, 0.2, 'M', 'Đen', 'Cotton', 'assets/img/ao-thun-nam.jpg', 'Áo thun nam', 'Áo thun cotton cho nam, thoải mái và dễ chịu'),
(11, 'TSHIRT-002', 150, 0.3, 'L', 'Trắng', 'Cotton', 'assets/img/ao-so-mi-nam.jpg', 'Áo sơ mi nam', 'Áo sơ mi nam dáng suông, chất liệu cotton'),
(12, 'JACKET-003', 80, 0.7, 'M', 'Đỏ', 'Nỉ', 'assets/img/ao-khoac-nu.jpg', 'Áo khoác nữ', 'Áo khoác nữ dày dặn giữ ấm trong mùa đông'),
(13, 'NIKE-002', 120, 0.8, '42', 'Trắng', 'Da, cao su', 'assets/img/giay-nike.jpg', 'Giày thể thao Nike', 'Giày thể thao Nike thiết kế thời trang và năng động'),
(14, 'ADIDAS-003', 150, 1.0, '41', 'Đen', 'Da, cao su', 'assets/img/giay-adidas.jpg', 'Giày Adidas Ultraboost', 'Giày thể thao Adidas với công nghệ Ultraboost thoải mái'),
(15, 'CONVERSE-004', 100, 0.9, '42', 'Đỏ', 'Vải, cao su', 'assets/img/giay-converse.jpg', 'Giày Converse Chuck Taylor', 'Giày Converse phong cách cổ điển'),
(16, 'PS5-005', 50, 4.5, '30 x 27 x 10 cm', 'Đen', 'Nhựa, kim loại', 'assets/img/ps5.jpg', 'Sony Playstation 5', 'Console game Sony Playstation 5 chơi game 4K'),
(17, 'SONY-006', 70, 0.3, '17 x 17 x 8 cm', 'Đen', 'Nhựa', 'assets/img/sony-wh.jpg', 'Sony WH-1000XM5', 'Tai nghe chống ồn Sony với chất âm tuyệt vời'),
(18, 'LG-007', 30, 5.0, '35 x 25 x 20 cm', 'Trắng', 'Nhựa', 'assets/img/may-chieu-lg.jpg', 'Máy chiếu LG PF50KA', 'Máy chiếu LG với độ phân giải HD và độ sáng cao'),
(19, 'FRIDGE-008', 50, 50.0, '180 x 80 x 60 cm', 'Đen', 'Thép, nhựa', 'assets/img/tu-lanh-lg.jpg', 'Tủ lạnh LG', 'Tủ lạnh LG với công nghệ tiết kiệm điện và không gian rộng rãi'),
(20, 'CLEANER-009', 50, 100.0, '85 x 60 x 60 cm', 'Đen', 'Thép', 'assets/img/may-giat-samsung.jpg', 'Máy giặt Samsung Inverter 9.5 kg WW95TA046AX/SV', 'Máy tiết kiệm điện giặt đồ nhanh chóng'),
(21, 'COOKER-010', 50, 50.0, '35 x 35 x 25 cm', 'Trắng', 'Nhôm', 'assets/img/noi-com-dien.jpg', 'Nồi Cơm Điện Tử Panasonic SR-CP108NRAM 1 Lít', 'Nồi cơm chống dính và nấu cơm nhanh chóng');

-- Thêm vào bảng người dùng
INSERT INTO user (name, phone, email, password, birthday, gender)
VALUES
('Nguyễn Văn A', '0901234567', 'nguyenvana@example.com', 'password123', '1990-01-01', 1),
('Trần Thị B', '0902345678', 'tranthib@example.com', 'password123', '1988-05-20', 0);

-- Thêm vào bảng giỏ hàng
INSERT INTO cart (user_id)
VALUES
(1),
(2);

-- Thêm vào bảng chi tiết giỏ hàng
INSERT INTO cart_item (cart_id, product_id, quantity)
VALUES
(1, 1, 1),
(2, 2, 2);

-- Thêm vào bảng đơn hàng
INSERT INTO orders (user_id, total_amount, order_status, delivery_address, payment_method)
VALUES
(1, 25000000, 0, '123 Đường ABC, TP.HCM', 'Thanh toán khi nhận hàng'),
(2, 40000000, 1, '456 Đường DEF, Hà Nội', 'Thanh toán khi nhận hàng');

-- Thêm vào bảng chi tiết đơn hàng
INSERT INTO order_item (order_id, product_id, quantity, price)
VALUES
(1, 1, 1, 25000000),
(2, 2, 2, 20000000);

-- Tắt kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 0;

-- Dữ liệu mẫu cho bảng users
-- Mật khẩu mặc định: 123456 (được mã hóa)
INSERT INTO users (user_id, username, password, full_name, date_of_birth, gender, address, phone, email, avatar_url, status, role) VALUES
(1, 'admin', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Quản trị viên', '1990-01-01', 'NAM', 'Hà Nội', '0987654321', 'admin@gmail.com', '/images/avatar/admin.png', 'ACTIVE', 'ADMIN'),
(2, 'accountant', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Kế toán viên', '1992-02-15', 'NU', 'Hà Nội', '0987654322', 'accountant@gmail.com', '/images/avatar/accountant.png', 'ACTIVE', 'ACCOUNTANT'),
(3, 'sales', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Nhân viên bán hàng', '1995-05-20', 'NAM', 'Hồ Chí Minh', '0987654323', 'sales@gmail.com', '/images/avatar/sales.png', 'ACTIVE', 'SALES'),
(4, 'customer1', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 1', '1985-10-10', 'NAM', 'Đà Nẵng', '0987654324', 'customer1@gmail.com', '/images/avatar/customer1.png', 'ACTIVE', 'CUSTOMER'),
(5, 'customer2', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 2', '1988-11-12', 'NU', 'Hải Phòng', '0987654325', 'customer2@gmail.com', '/images/avatar/customer2.png', 'ACTIVE', 'CUSTOMER');

-- Dữ liệu mẫu cho bảng employees
INSERT INTO employees (employee_id, employee_code, position, user_id) VALUES
(1, 'EMP001', 'Quản trị hệ thống', 1),
(2, 'EMP002', 'Kế toán', 2),
(3, 'EMP003', 'Nhân viên bán hàng', 3);

-- Dữ liệu mẫu cho bảng customers
INSERT INTO customers (customer_id, customer_code, name, type, address, phone, email, user_id) VALUES
(1, 'CUS001', 'Khách hàng 1', 'RETAIL', 'Đà Nẵng', '0987654324', 'customer1@gmail.com', 4),
(2, 'CUS002', 'Khách hàng 2', 'WHOLESALE', 'Hải Phòng', '0987654325', 'customer2@gmail.com', 5),
(3, 'CUS003', 'Công ty ABC', 'WHOLESALE', 'Hà Nội', '0987654326', 'company@gmail.com', null),
(4, 'CUS004', 'Nhà thuốc XYZ', 'SUPPLIER', 'Hồ Chí Minh', '0987654327', 'pharmacy@gmail.com', null);

-- Dữ liệu mẫu cho bảng suppliers
INSERT INTO suppliers (id, name, address, phone_number) VALUES
(1, 'Công ty Dược phẩm A', 'Hà Nội', '0987654328'),
(2, 'Công ty Thiết bị Y tế B', 'Hồ Chí Minh', '0987654329'),
(3, 'Công ty Vật tư Y tế C', 'Đà Nẵng', '0987654330');

-- Dữ liệu mẫu cho bảng categories
INSERT INTO categories (id, name, description) VALUES
(1, 'Dụng cụ y tế', 'Các loại dụng cụ y tế cơ bản'),
(2, 'Thuốc', 'Các loại thuốc điều trị'),
(3, 'Thiết bị y tế', 'Các thiết bị y tế chuyên dụng'),
(4, 'Vật tư tiêu hao', 'Các vật tư tiêu hao trong y tế');

-- Dữ liệu mẫu cho bảng materials
INSERT INTO materials (id, material_code, name, description, img_url, price, quantity, unit, expiration_date, category_id, supplier_id) VALUES
(1, 'MAT001', 'Bông gòn y tế', 'Bông gòn vô trùng dùng trong y tế', '/images/materials/cotton.jpg', 15000, 100, 'Gói', '2025-12-31', 4, 1),
(2, 'MAT002', 'Khẩu trang y tế', 'Khẩu trang 3 lớp kháng khuẩn', '/images/materials/mask.jpg', 50000, 500, 'Hộp', '2025-06-30', 4, 2),
(3, 'MAT003', 'Nhiệt kế điện tử', 'Nhiệt kế đo thân nhiệt điện tử', '/images/materials/thermometer.jpg', 120000, 50, 'Cái', '2030-01-01', 3, 2),
(4, 'MAT004', 'Găng tay y tế', 'Găng tay cao su dùng trong y tế', '/images/materials/gloves.jpg', 80000, 200, 'Hộp', '2025-05-15', 4, 3),
(5, 'MAT005', 'Paracetamol', 'Thuốc hạ sốt, giảm đau', '/images/materials/paracetamol.jpg', 35000, 100, 'Hộp', '2025-10-20', 2, 1);

-- Dữ liệu mẫu cho bảng material_image
INSERT INTO material_image (id, material_id, image_url) VALUES
(1, 1, '/images/materials/cotton_1.jpg'),
(2, 1, '/images/materials/cotton_2.jpg'),
(3, 2, '/images/materials/mask_1.jpg'),
(4, 3, '/images/materials/thermometer_1.jpg'),
(5, 3, '/images/materials/thermometer_2.jpg');

-- Dữ liệu mẫu cho bảng medical_supplies
INSERT INTO medical_supplies (supply_id, supply_code, name, type, price, unit, expiry_date, supplier_id, category_id) VALUES
(1, 'MED001', 'Bông băng vô trùng', 'Vật tư y tế', 20000, 'Gói', '2025-12-31', 4, 4),
(2, 'MED002', 'Ống tiêm vô trùng', 'Vật tư y tế', 15000, 'Hộp', '2026-06-30', 4, 4),
(3, 'MED003', 'Máy đo huyết áp', 'Thiết bị y tế', 550000, 'Cái', NULL, 4, 3);

-- Dữ liệu mẫu cho bảng invoices
INSERT INTO invoices (id, invoice_code, created_at, employee_id, customer_id, total_amount, discount_amount, discount_rate, discount_percent, vat_amount, vat_percent, note) VALUES
(1, 'INV001', '2023-01-15 10:30:00', 3, 1, 500000, 0, 0, 0, 50000, 10, 'Hóa đơn bán hàng thông thường'),
(2, 'INV002', '2023-02-20 14:45:00', 3, 2, 1200000, 120000, NULL, 10, 108000, 10, 'Hóa đơn có giảm giá'),
(3, 'INV003', '2023-03-10 09:15:00', 3, 3, 3500000, 0, 0, 0, 350000, 10, 'Hóa đơn bán buôn');

-- Dữ liệu mẫu cho bảng invoice_items
INSERT INTO invoice_items (id, invoice_id, material_id, quantity, unit_price, amount) VALUES
(1, 1, 1, 10, 15000, 150000),
(2, 1, 2, 7, 50000, 350000),
(3, 2, 3, 10, 120000, 1200000),
(4, 3, 4, 20, 80000, 1600000),
(5, 3, 5, 50, 35000, 1750000);

-- Dữ liệu mẫu cho bảng import_invoices
INSERT INTO import_invoices (id, import_code, supplier_id, employee_id, total_amount, import_date, note) VALUES
(1, 'IMP001', 1, 2, 2000000, '2023-01-05 08:30:00', 'Nhập hàng từ công ty A'),
(2, 'IMP002', 2, 2, 5000000, '2023-02-10 09:45:00', 'Nhập hàng từ công ty B');

-- Dữ liệu mẫu cho bảng import_invoice_details
INSERT INTO import_invoice_details (id, import_invoice_id, material_id, quantity, unit_price, amount) VALUES
(1, 1, 1, 100, 10000, 1000000),
(2, 1, 2, 20, 50000, 1000000),
(3, 2, 3, 50, 100000, 5000000);

-- Dữ liệu mẫu cho bảng sales_invoices
INSERT INTO sales_invoices (id, invoice_id, payment_method, payment_status, delivery_address, delivery_status) VALUES
(1, 1, 'CASH', 'PAID', 'Đà Nẵng', 'DELIVERED'),
(2, 2, 'BANK_TRANSFER', 'PAID', 'Hải Phòng', 'DELIVERED'),
(3, 3, 'CREDIT_CARD', 'PAID', 'Hà Nội', 'DELIVERING');

-- Dữ liệu mẫu cho bảng sales_invoice_details
INSERT INTO sales_invoice_details (id, sales_invoice_id, material_id, quantity, unit_price) VALUES
(1, 1, 1, 10, 15000),
(2, 1, 2, 7, 50000),
(3, 2, 3, 10, 120000),
(4, 3, 4, 20, 80000),
(5, 3, 5, 50, 35000);

-- Dữ liệu mẫu cho bảng payment_settings
INSERT INTO payment_settings (id, setting_name, setting_value, description, active) VALUES
(1, 'VAT_PERCENT', '10', 'Phần trăm VAT mặc định', TRUE),
(2, 'DISCOUNT_CUSTOMER_VIP', '15', 'Phần trăm giảm giá cho khách hàng VIP', TRUE),
(3, 'DISCOUNT_WHOLESALE', '8', 'Phần trăm giảm giá cho đơn hàng bán buôn', TRUE);

-- Dữ liệu mẫu cho bảng salaries
-- Dữ liệu mẫu cho bảng salaries
INSERT INTO salaries (advance, base_salary, month, payment_date, payment_status, salary_due, year, employee_id)
VALUES
    (1000000, 5000000, 7, '2025-07-31', 'PAID', 4000000, 2025, 1),
    (500000, 4500000, 7, '2025-07-31', 'PAID', 4000000, 2025, 2),
    (0, 4000000, 7, '2025-07-31', 'PENDING', 4000000, 2025, 3);
    
-- Additional data for users
INSERT INTO users (user_id, username, password, full_name, date_of_birth, gender, address, phone, email, avatar_url, status, role) VALUES
(6, 'customer3', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 3', '1990-07-12', 'NU', 'Hải Dương', '0987654331', 'customer3@gmail.com', '/images/avatar/customer3.png', 'ACTIVE', 'CUSTOMER'),
(7, 'customer4', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 4', '1993-03-05', 'NAM', 'Cần Thơ', '0987654332', 'customer4@gmail.com', '/images/avatar/customer4.png', 'ACTIVE', 'CUSTOMER'),
(8, 'customer5', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 5', '1991-09-21', 'NAM', 'Huế', '0987654333', 'customer5@gmail.com', '/images/avatar/customer5.png', 'ACTIVE', 'CUSTOMER'),
(9, 'customer6', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 6', '1996-12-12', 'NU', 'Nha Trang', '0987654334', 'customer6@gmail.com', '/images/avatar/customer6.png', 'ACTIVE', 'CUSTOMER'),
(10, 'customer7', '$2a$12$yCj1Zn/GimQGoRRcNSyRVu9AmSrGZMftKRQlGuMnGFlAdm91BJaY2', 'Khách hàng 7', '1989-04-18', 'NAM', 'Vũng Tàu', '0987654335', 'customer7@gmail.com', '/images/avatar/customer7.png', 'ACTIVE', 'CUSTOMER');

-- Additional customers
INSERT INTO customers (customer_id, customer_code, name, type, address, phone, email, user_id) VALUES
(5, 'CUS005', 'Khách hàng 3', 'RETAIL', 'Hải Dương', '0987654331', 'customer3@gmail.com', 6),
(6, 'CUS006', 'Khách hàng 4', 'RETAIL', 'Cần Thơ', '0987654332', 'customer4@gmail.com', 7),
(7, 'CUS007', 'Khách hàng 5', 'RETAIL', 'Huế', '0987654333', 'customer5@gmail.com', 8),
(8, 'CUS008', 'Khách hàng 6', 'WHOLESALE', 'Nha Trang', '0987654334', 'customer6@gmail.com', 9),
(9, 'CUS009', 'Khách hàng 7', 'WHOLESALE', 'Vũng Tàu', '0987654335', 'customer7@gmail.com', 10),
(10, 'CUS010', 'Bệnh viện DEF', 'SUPPLIER', 'Hà Nội', '0987654336', 'hospital@gmail.com', null);

-- Additional suppliers
INSERT INTO suppliers (id, name, address, phone_number) VALUES
(4, 'Công ty Dược phẩm D', 'Cần Thơ', '0987654337'),
(5, 'Công ty Thiết bị Y tế E', 'Huế', '0987654338'),
(6, 'Công ty Vật tư Y tế F', 'Nha Trang', '0987654339');

-- Additional materials
INSERT INTO materials (id, material_code, name, description, img_url, price, quantity, unit, expiration_date, category_id, supplier_id) VALUES
(6, 'MAT006', 'Băng gạc vô trùng', 'Băng gạc kích thước 10x10', '/images/materials/bandage.jpg', 25000, 150, 'Gói', '2025-11-30', 4, 4),
(7, 'MAT007', 'Cồn y tế 70%', 'Dung dịch sát khuẩn', '/images/materials/alcohol.jpg', 30000, 200, 'Chai', '2025-12-30', 2, 4),
(8, 'MAT008', 'Ống nghe y tế', 'Ống nghe bác sĩ', '/images/materials/stethoscope.jpg', 900000, 40, 'Cái', '2030-01-01', 3, 5),
(9, 'MAT009', 'Gạc y tế', 'Miếng gạc 5x5', '/images/materials/gauze.jpg', 10000, 300, 'Gói', '2025-05-15', 4, 5),
(10, 'MAT010', 'Nước muối sinh lý', 'Dung dịch NaCl 0,9%', '/images/materials/saline.jpg', 10000, 500, 'Chai', '2026-06-01', 2, 6),
(11, 'MAT011', 'Khẩu trang N95', 'Khẩu trang 5 lớp chống bụi mịn', '/images/materials/mask_n95.jpg', 70000, 300, 'Hộp', '2025-06-30', 4, 6),
(12, 'MAT012', 'Máy đo đường huyết', 'Dùng kiểm tra đường huyết', '/images/materials/glucometer.jpg', 650000, 25, 'Cái', '2030-05-05', 3, 4),
(13, 'MAT013', 'Thuốc kháng sinh Amoxicillin', 'Kháng sinh phổ rộng', '/images/materials/amoxicillin.jpg', 45000, 200, 'Hộp', '2025-09-10', 2, 4),
(14, 'MAT014', 'Găng tay cao su', 'Găng tay không bột', '/images/materials/gloves_nopowder.jpg', 85000, 150, 'Hộp', '2025-07-20', 4, 5),
(15, 'MAT015', 'Máy đo SpO2', 'Máy đo nồng độ oxy trong máu', '/images/materials/spo2.jpg', 320000, 60, 'Cái', NULL, 3, 6);

-- Additional material_image
INSERT INTO material_image (id, material_id, image_url) VALUES
(6, 6, '/images/materials/bandage_1.jpg'),
(7, 7, '/images/materials/alcohol_1.jpg'),
(8, 8, '/images/materials/stethoscope_1.jpg'),
(9, 9, '/images/materials/gauze_1.jpg'),
(10, 10, '/images/materials/saline_1.jpg'),
(11, 11, '/images/materials/mask_n95_1.jpg'),
(12, 12, '/images/materials/glucometer_1.jpg'),
(13, 13, '/images/materials/amoxicillin_1.jpg'),
(14, 14, '/images/materials/gloves_nopowder_1.jpg'),
(15, 15, '/images/materials/spo2_1.jpg');

-- Additional medical_supplies
INSERT INTO medical_supplies (supply_id, supply_code, name, type, price, unit, expiry_date, supplier_id, category_id) VALUES
(4, 'MED004', 'Băng gạc vô trùng', 'Vật tư y tế', 25000, 'Gói', '2025-11-30', 4, 4),
(5, 'MED005', 'Cồn y tế 70%', 'Vật tư y tế', 30000, 'Chai', '2025-12-30', 4, 2),
(6, 'MED006', 'Ống nghe bác sĩ', 'Thiết bị y tế', 900000, 'Cái', NULL, 5, 3),
(7, 'MED007', 'Máy đo đường huyết', 'Thiết bị y tế', 650000, 'Cái', NULL, 4, 3),
(8, 'MED008', 'Găng tay cao su', 'Vật tư y tế', 85000, 'Hộp', '2025-07-20', 5, 4),
(9, 'MED009', 'Thuốc kháng sinh Amoxicillin', 'Thuốc', 45000, 'Hộp', '2025-09-10', 4, 2),
(10, 'MED010', 'Máy đo SpO2', 'Thiết bị y tế', 320000, 'Cái', NULL, 6, 3);

-- Additional invoices
INSERT INTO invoices (id, invoice_code, created_at, employee_id, customer_id, total_amount, discount_amount, discount_rate, discount_percent, vat_amount, vat_percent, note) VALUES
(4, 'INV004', '2023-04-12 10:10:00', 3, 5, 350000, 0, 0, 0, 35000, 10, 'Hóa đơn bán lẻ'),
(5, 'INV005', '2023-05-17 11:20:00', 3, 6, 1200000, 120000, NULL, 10, 108000, 10, 'Hóa đơn có giảm giá'),
(6, 'INV006', '2023-06-20 14:40:00', 3, 7, 2000000, 0, 0, 0, 200000, 10, 'Hóa đơn bán buôn'),
(7, 'INV007', '2023-07-15 09:30:00', 3, 8, 800000, 0, 0, 0, 80000, 10, 'Hóa đơn bán lẻ'),
(8, 'INV008', '2023-08-02 16:50:00', 3, 9, 550000, 0, 0, 0, 55000, 10, 'Hóa đơn bán lẻ');

-- Additional invoice_items
INSERT INTO invoice_items (id, invoice_id, material_id, quantity, unit_price, amount) VALUES
(6, 4, 6, 5, 25000, 125000),
(7, 4, 7, 3, 30000, 90000),
(8, 5, 8, 1, 900000, 900000),
(9, 5, 9, 20, 10000, 200000),
(10, 6, 11, 10, 70000, 700000),
(11, 6, 12, 2, 650000, 1300000),
(12, 7, 13, 15, 45000, 675000),
(13, 7, 14, 3, 85000, 255000),
(14, 8, 15, 2, 320000, 640000),
(15, 8, 10, 10, 10000, 100000);

-- Additional import_invoices
INSERT INTO import_invoices (id, import_code, supplier_id, employee_id, total_amount, import_date, note) VALUES
(3, 'IMP003', 4, 2, 3000000, '2023-03-12 10:30:00', 'Nhập hàng từ công ty D'),
(4, 'IMP004', 5, 2, 2500000, '2023-04-10 09:20:00', 'Nhập hàng từ công ty E'),
(5, 'IMP005', 6, 2, 4000000, '2023-05-18 15:10:00', 'Nhập hàng từ công ty F');

-- Additional import_invoice_details
INSERT INTO import_invoice_details (id, import_invoice_id, material_id, quantity, unit_price, amount) VALUES
(4, 3, 6, 200, 15000, 3000000),
(5, 4, 7, 100, 25000, 2500000),
(6, 5, 8, 50, 80000, 4000000);

-- Additional sales_invoices
INSERT INTO sales_invoices (id, invoice_id, payment_method, payment_status, delivery_address, delivery_status) VALUES
(4, 4, 'CASH', 'PAID', 'Hải Dương', 'DELIVERED'),
(5, 5, 'BANK_TRANSFER', 'PAID', 'Cần Thơ', 'DELIVERED'),
(6, 6, 'CREDIT_CARD', 'PENDING', 'Huế', 'DELIVERING'),
(7, 7, 'CASH', 'PAID', 'Nha Trang', 'DELIVERED'),
(8, 8, 'CASH', 'PAID', 'Vũng Tàu', 'DELIVERING');

-- Additional sales_invoice_details
INSERT INTO sales_invoice_details (id, sales_invoice_id, material_id, quantity, unit_price) VALUES
(6, 4, 6, 5, 25000),
(7, 4, 7, 3, 30000),
(8, 5, 8, 1, 900000),
(9, 5, 9, 20, 10000),
(10, 6, 11, 10, 70000),
(11, 6, 12, 2, 650000),
(12, 7, 13, 15, 45000),
(13, 7, 14, 3, 85000),
(14, 8, 15, 2, 320000),
(15, 8, 10, 10, 10000);

-- Additional salaries
INSERT INTO salaries (advance, base_salary, month, payment_date, payment_status, salary_due, year, employee_id)
VALUES
(0, 5000000, 8, '2025-08-31', 'PAID', 5000000, 2025, 1),
(700000, 4500000, 8, '2025-08-31', 'PAID', 3800000, 2025, 2),
(200000, 4000000, 8, '2025-08-31', 'PENDING', 3800000, 2025, 3);

-- Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1; 
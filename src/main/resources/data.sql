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
INSERT INTO employees (id, employee_code, position, user_id) VALUES
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

-- Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1; 
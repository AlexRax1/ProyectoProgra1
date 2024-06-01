CREATE SEQUENCE libros_libro_id_seq START WITH 100000;
CREATE TABLE libros (
    libro_id int PRIMARY KEY default nextval('libros_libro_id_seq'),
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
	anoPublicacion date not null, 
	editorial varchar(30),
	disponibles int not null default 0 check(disponibles >= 0)

);

CREATE SEQUENCE usuarios_usuario_id_seq START WITH 1000;
CREATE TABLE usuarios (
    usuario_id int PRIMARY KEY default nextval('usuarios_usuario_id_seq'),
    nombre VARCHAR(255) NOT NULL,
	direccion varchar(200),
	telefono varchar(8),
	email varchar(250),
	saldo DECIMAL(10, 2) DEFAULT 0.00,
	contrasena varchar(50),
	es_administrador boolean not null default FALSE
);

CREATE TABLE prestamos (
    prestamo_id SERIAL PRIMARY KEY,
    libro_id INT REFERENCES libros(libro_id),
    usuario_id INT REFERENCES usuarios(usuario_id),
    fecha_prestamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_vencimiento date,
    fecha_devolucion DATE
);

CREATE TABLE historial_transacciones (
    transaccion_id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(usuario_id) DEFAULT NULL,
    accion VARCHAR(50) NOT NULL,
    fecha_transaccion DATE DEFAULT CURRENT_DATE
);
	
drop table historial_transacciones;

INSERT INTO libros (titulo, autor, anoPublicacion, editorial, disponibles) VALUES
('El Quijote', 'Miguel de Cervantes', '1605-01-01', 'Francisco de Robles', 5),
('Cien Años de Soledad', 'Gabriel Garcia Marquez', '1967-05-30', 'Sudamericana', 3),
('Orgullo y Prejuicio', 'Jane Austen', '1813-01-28', 'T. Egerton', 4),
('1984', 'George Orwell', '1949-06-08', 'Secker & Warburg', 2),
('Moby-Dick', 'Herman Melville', '1851-10-18', 'Harper & Brothers', 3);

INSERT INTO usuarios (nombre, direccion, telefono, email, saldo, contrasena, es_administrador) VALUES
('Juan Perez', 'Calle Falsa 123', '12345678', 'juan@example.com', 0.00, 'juan123', FALSE),
('Ana Gomez', 'Avenida Siempreviva 456', '87654321', 'ana@example.com', 0.00, 'ana123', FALSE),
('Carlos Ruiz', 'Boulevard del Sol 789', '11223344', 'carlos@example.com', 0.00, 'carlos123', FALSE),
('Maria Lopez', 'Calle Luna 321', '44332211', 'maria@example.com', 0.00, 'maria123', FALSE),
('Admin', 'Oficina Central', '00000000', 'admin@example.com', 0.00, 'admin123', TRUE);

INSERT INTO prestamos (libro_id, usuario_id, fecha_prestamo, fecha_vencimiento, fecha_devolucion) VALUES
(100005, 1009, '2024-05-01 10:00:00', '2024-06-01', NULL),
(100006, 1010, '2024-05-02 11:00:00', '2024-06-02', NULL),
(100007, 1011, '2024-05-03 12:00:00', '2024-06-03', NULL),
(100008, 1012, '2024-05-04 13:00:00', '2024-06-04', NULL),
(100009, 1013, '2024-05-05 14:00:00', '2024-06-05', NULL);

INSERT INTO historial_transacciones (usuario_id, accion, fecha_transaccion) VALUES
(1011, 'Prestamo realizado', '2024-05-01'),
(1012, 'Prestamo realizado', '2024-05-02'),
(1013, 'Prestamo realizado', '2024-05-03'),
(1014, 'Prestamo realizado', '2024-05-04'),
(1009, 'Prestamo realizado', '2024-05-05');


select * from usuarios;
select * from libros;
select * from prestamos;
select * from historial_transacciones;


SELECT
    ht.transaccion_id,
    p.prestamo_id ,
    u.usuario_id ,
    u.nombre ,
    ht.accion,
    ht.fecha_transaccion
FROM
    historial_transacciones ht
JOIN
    prestamos p ON ht.prestamo_id = p.prestamo_id
JOIN
    usuarios u ON p.usuario_id = u.usuario_id;


UPDATE usuarios SET saldo = saldo +10 WHERE usuario_id = 1009;



--eliminar todas las tablas
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;

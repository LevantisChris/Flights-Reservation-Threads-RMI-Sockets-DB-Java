CREATE DATABASE FLIGHTS_DB;
USE FLIGHTS_DB;

## INSERT AIRPORTS
INSERT INTO AIRPORT (AIRPORT_NAME, CITY, COUNTRY, TIME_ZONE) 
VALUES 
    ('Samos Aristarchos Samios Airport', 'Samos', 'Greece', '01:46'),
    ('Athens Airport Eleftherios Venizelos', 'Athens', 'Greece', '01:46'),
    ('Mykonos International Airport', 'Mykonos', 'Greece', '01:46'),
    ('Milos Airport', 'Milos', 'Greece', '01:46'),
    ('Airport of Thessaloniki Macedonia', 'Thessaloniki', 'Greece', '01:46');


## INSERT GOING FLIGHTS
INSERT INTO FLIGHT (AIRLINE_NAME, DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DEPARTURE_DATE, DEPARTURE_TIME, ARRIVAL_TIME, FLIGHT_DURATION, PRICE) 
VALUES 
	('Olympic Air', 'Athens Airport Eleftherios Venizelos', 'Samos Aristarchos Samios Airport', '2023-06-01', '08:00:00', '09:30:00', 90, 120.50),
	('Aegean Airlines', 'Mykonos International Airport', 'Milos Airport', '2023-06-05', '14:30:00', '15:15:00', 45, 75.00),
	('Ellinair', 'Airport of Thessaloniki Macedonia', 'Athens Airport Eleftherios Venizelos', '2023-06-08', '11:00:00', '12:15:00', 75, 90.00);
## INSERT THE RETURNS
INSERT INTO FLIGHT (AIRLINE_NAME, DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DEPARTURE_DATE, DEPARTURE_TIME, ARRIVAL_TIME, FLIGHT_DURATION, PRICE) 
VALUES 
	('Olympic Air', 'Samos Aristarchos Samios Airport', 'Athens Airport Eleftherios Venizelos', '2023-06-15', '08:00:00', '09:30:00', 90, 120.50),
	('Aegean Airlines', 'Milos Airport', 'Mykonos International Airport', '2023-06-20', '14:30:00', '15:15:00', 45, 75.00),
	('Ellinair', 'Athens Airport Eleftherios Venizelos', 'Airport of Thessaloniki Macedonia', '2023-06-25', '11:00:00', '12:15:00', 75, 90.00);



## INSERT SEATS TO FLIGHT

## TO 1 FLIGHT FROM 1 to 10 is business
INSERT INTO SEAT (SEAT_NUMBER, FLIGHT_NUMBER, IS_AVAILABLE, CLASS, PRICE)
VALUES
    (1, 1, true, 'business', 1000.0),
    (2, 1, true, 'business', 1000.0),
    (3, 1, true, 'business', 1000.0),
    (4, 1, true, 'business', 1000.0),
    (5, 1, true, 'business', 1000.0),
    (6, 1, true, 'business', 500.0),
    (7, 1, true, 'business', 500.0),
    (8, 1, true, 'business', 500.0),
    (9, 1, true, 'business', 500.0),
    (10, 1, true, 'business', 500.0);
    
## TO 1 FLIGHT FROM 11 to 20 is economy
INSERT INTO SEAT (SEAT_NUMBER, FLIGHT_NUMBER, IS_AVAILABLE, CLASS, PRICE)
VALUES
    (11, 1, true, 'economy', 200.0),
    (12, 1, true, 'economy', 200.0),
    (13, 1, true, 'economy', 200.0),	
    (14, 1, true, 'economy', 200.0),
    (15, 1, true, 'economy', 200.0),
    (16, 1, true, 'economy', 100.0),
    (17, 1, true, 'economy', 100.0),
    (18, 1, true, 'economy', 100.0),
    (19, 1, true, 'economy', 100.0),
    (20, 1, true, 'economy', 100.0);
#### --> WE RUN THE SAME CODE FOR THE 5 MORE FLIGHTS SO ALL THE FLIGHTS HAVE 20 SEATS, WE ALSO SUPPOSE FROM THE START THEY ALL BE AVAILABLE (TRUE) ...



#### --> WE ADD THIS IN THE TABLE SEAT SO WE CAN KNOW WHICH CLIENT HAS CLOSED THE SEAT ...
ALTER TABLE SEAT
ADD COLUMN PASSENGER_PASSPORT_NUMBER INT;

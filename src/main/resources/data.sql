-- data.sql
INSERT INTO active_region (region_name, station_name)
VALUES ('Tallinn', 'Tallinn-Harku');
INSERT INTO active_region (region_name, station_name)
VALUES ('Tartu', 'Tartu-Tõravere');
INSERT INTO active_region (region_name, station_name)
VALUES ('Pärnu', 'Pärnu');

INSERT INTO weather_phenomenon (phenomenon_type, prohibition, fee)
VALUES
    ('snow', FALSE, 1.0),
    ('sleet', FALSE, 1.0),
    ('rain', FALSE, 0.5),
    ('glaze', TRUE, 0.0),
    ('hail', TRUE, 0.0),
    ('thunder', TRUE, 0.0);

INSERT INTO regional_base_fee (vehicle_type, regional_base_fee, region_name)
VALUES
    ('Car', 4.0, 'Tallinn'),
    ('Scooter', 3.5, 'Tallinn'),
    ('Bike', 3.0, 'Tallinn'),
    ('Car', 3.5, 'Tartu'),
    ('Scooter', 3.0, 'Tartu'),
    ('Bike', 2.5, 'Tartu'),
    ('Car', 3.0, 'Pärnu'),
    ('Scooter', 2.5, 'Pärnu'),
    ('Bike', 2.0, 'Pärnu');

INSERT INTO business_rule (vehicle_name, metric_type, min_value, max_value, additional_fee)
VALUES
    ('Bike',
    'Air Temperature', -2147483648, -10, 1),
    ('Bike',
     'Air Temperature', -10, 0, 0.5),
    ('Scooter',
     'Air Temperature', -2147483648, -10, 1),
    ('Scooter',
     'Air Temperature', -10, 0, 0.5),
    ('Scooter',
     'Weather Phenomenon', 0, 0, 0),
    ('Bike',
     'Weather Phenomenon', 0, 0, 0);


INSERT INTO business_rule (vehicle_name, metric_type, min_value, max_value, additional_fee)
VALUES
    ('Bike',
    'Wind Speed', 10, 20, 0.5);

INSERT INTO business_rule (vehicle_name, metric_type, min_value, max_value, additional_fee)
VALUES
    ('Bike',
    'Wind Speed', 0, 9, 0);


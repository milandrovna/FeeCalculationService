-- data.sql
INSERT INTO active_region (region_name, station_name, carRBFee, scooterRBFee, bikeRBFee)
VALUES ('Tallinn', 'Tallinn-Harku', 4, 3.5, 3);
INSERT INTO active_region (region_name, station_name, carRBFee, scooterRBFee, bikeRBFee)
VALUES ('Tartu', 'Tartu-Tõravere', 3.5, 3, 2.5);
INSERT INTO active_region (region_name, station_name, carRBFee, scooterRBFee, bikeRBFee)
VALUES ('Pärnu', 'Pärnu', 3, 2.5, 2);

INSERT INTO metric (metric_type)
VALUES ('Air Temperature');
INSERT INTO metric (metric_type)
VALUES ('Wind Speed');

INSERT INTO business_rule (vehicle_type, metric_type, min_value, max_value, additional_fee)
VALUES
    ('Bike', 'Air Temperature', -2147483648, -10, 1),
    ('Bike', 'Air Temperature', -10, 0, 0.5),
    ('Scooter', 'Air Temperature', -2147483648, -10, 1),
    ('Scooter', 'Air Temperature', -10, 0, 0.5);

INSERT INTO business_rule (vehicle_type, metric_type, min_value, max_value, additional_fee)
VALUES
    ('Bike', 'Wind Speed', 10, 20, 0.5);
INSERT INTO business_rule (vehicle_type, metric_type, min_value, max_value, additional_fee)
VALUES
    ('Bike', 'Wind Speed', 21, 2147483647, NULL);
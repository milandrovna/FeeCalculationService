-- data.sql
INSERT INTO active_region (region_name, station_name)
VALUES ('Tallinn', 'Tallinn-Harku');
INSERT INTO active_region (region_name, station_name)
VALUES ('Tartu', 'Tartu-Tõravere');
INSERT INTO active_region (region_name, station_name)
VALUES ('Pärnu', 'Pärnu');

INSERT INTO metric (metric_type)
VALUES ('Air Temperature');
INSERT INTO metric (metric_type)
VALUES ('Wind Speed');

INSERT INTO weather_phenomenon (phenomenon_type, prohibition, fee)
VALUES
    ('snow', FALSE, 1.0),
    ('sleet', FALSE, 1.0),
    ('rain', FALSE, 0.5),
    ('glaze', TRUE, 0.0),
    ('hail', TRUE, 0.0),
    ('thunder', TRUE, 0.0);

INSERT INTO vehicle (vehicle_type, regional_base_fee, region_name)
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

INSERT INTO business_rule (vehicle_id, metric_type, min_value, max_value, additional_fee)
VALUES
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Bike' AND region_name = 'Tallinn' LIMIT 1),
    'Air Temperature', -2147483648, -10, 1),
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Bike' AND region_name = 'Tallinn' LIMIT 1),
     'Air Temperature', -10, 0, 0.5),
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Scooter' AND region_name = 'Tallinn' LIMIT 1),
     'Air Temperature', -2147483648, -10, 1),
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Scooter' AND region_name = 'Tallinn' LIMIT 1),
     'Air Temperature', -10, 0, 0.5),
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Scooter' AND region_name = 'Tallinn' LIMIT 1),
     'Weather Phenomenon', 0, 0, 0),
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Bike' AND region_name = 'Tallinn' LIMIT 1),
     'Weather Phenomenon', 0, 0, 0);


INSERT INTO business_rule (vehicle_id, metric_type, min_value, max_value, additional_fee)
VALUES
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Bike' AND region_name = 'Tallinn' LIMIT 1),
    'Wind Speed', 10, 20, 0.5);

INSERT INTO business_rule (vehicle_id, metric_type, min_value, max_value, additional_fee)
VALUES
    ((SELECT id FROM vehicle WHERE vehicle_type = 'Bike' AND region_name = 'Tallinn' LIMIT 1),
    'Wind Speed', 21, 2147483647, NULL);

---test
insert into weather_condition(station_name, wmo_station_code, air_temperature, wind_speed, weather_phenomenon, timestamp)
values ('Tartu-Tõravere', 223, -11, 15, 'Light snow shower', 1742500890); --- 4.5

insert into weather_condition(station_name, wmo_station_code, air_temperature, wind_speed, weather_phenomenon, timestamp)
values ('Tartu-Tõravere', 223, -2.1, 21, 'Light snow shower', 1742500880); --- 4.5

insert into weather_condition(station_name, wmo_station_code, air_temperature, wind_speed, weather_phenomenon, timestamp)
values ('Tartu-Tõravere', 223, -2.1, 10, 'Thunder', 174250081); --- 4.5
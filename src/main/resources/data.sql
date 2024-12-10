INSERT INTO location(latitude, longitude) VALUES(0,0);
INSERT INTO location(latitude, longitude) VALUES(99,99);

INSERT INTO address(address, location_location_id ) VALUES ("College Place, Syracuse", 1);

INSERT INTO bus_stop(default_shuttle,students_waiting,address_address_id) VALUES (0,0,1);

INSERT INTO shuttle( capacity, current_count, registration_number, request_count, shuttle_status, wait_time, current_location_location_id ) VALUES ( 3, 0, "HRB386", 0, 0, 0, 1);
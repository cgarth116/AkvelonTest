INSERT INTO project VALUES
(1, 'first project', '2021-01-24 11:00:00-03', '2022-02-10 19:00:00-03', 'Active', 5),
(2, 'second project', '2021-01-24 11:00:00-03', '2022-02-10 19:00:00-03', 'Active', 6);

INSERT INTO task VALUES
(1,2, '{"description": "description", "name": "name", "priority": 1, "status": "Done"}'),
(2,1, '{"description": "description1", "name": "name1", "priority": 5, "status": "Done"}');
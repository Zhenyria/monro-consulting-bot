INSERT INTO text_template (key_value, text_value)
VALUES ('GREETING', 'Привет, я умный бот!'),
       ('GREETING_FOR_RETURNED', 'Привет, хорошо, что ты вернулся!')
ON CONFLICT DO NOTHING;

INSERT INTO scale (size, volume, foot_length, foot_girth)
VALUES (35, 3, 22.5, 20.5),
       (36, 3, 23, 20.9),
       (37, 3, 24, 21.3),
       (38, 3, 24.5, 21.7),
       (39, 3, 25, 22),
       (40, 3, 26, 22.5),
       (41, 3, 26.5, 22.9),
       (42, 3, 27, 23.3),
       (35, 6, 22.5, 21.7),
       (36, 6, 23, 22.1),
       (37, 6, 24, 22.5),
       (38, 6, 24.5, 22.9),
       (39, 6, 25, 23.3),
       (40, 6, 26, 23.7),
       (41, 6, 26.5, 24.1),
       (42, 6, 27, 24.5),
       (35, 9, 22.5, 23.2),
       (36, 9, 23, 23.6),
       (37, 9, 24, 24),
       (38, 9, 24.6, 24.4),
       (39, 9, 25, 24.8),
       (40, 9, 26, 25.2),
       (41, 9, 26.5, 25.6),
       (42, 9, 27, 26)
ON CONFLICT DO NOTHING;

INSERT INTO season (name_val, localized_name)
VALUES ('Summer', 'Лето'),
       ('Winter', 'Зима'),
       ('Demi-season', 'Весна-осень')
ON CONFLICT DO NOTHING;

INSERT INTO shoes_model (name_val, localized_name)
VALUES ('high_boots', 'Сапоги'),
       ('shoes', 'Туфли'),
       ('boots', 'Ботинки'),
       ('slippers', 'Сланцы'),
       ('sneakers', 'Кроссовки'),
       ('ankle_boots', 'Полусапоги')
ON CONFLICT DO NOTHING;

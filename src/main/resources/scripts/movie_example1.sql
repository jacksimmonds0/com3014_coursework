INSERT INTO `group6`.`directors`
    (id, first_name, last_name)
VALUES
       (1, 'Denis', 'Villeneuve');

INSERT INTO `group6`.`actors`
    (id, first_name, last_name)
VALUES
       (1, 'Ryan', 'Gosling'),
       (2, 'Dave', 'Bautista'),
       (3, 'Robin', 'Wright'),
       (4, 'Harrison', 'Ford'),
       (5, 'Ana', 'de Armas');

INSERT INTO `group6`.`movies`
    (id, title, description, year, director_id)
VALUES
       (1, 'Blade Runner 2049', 'A young blade runner''s discovery of a long-buried secret leads him to track down former blade runner Rick Deckard, who''s been missing for thirty years.',
        2017, 1);

INSERT INTO `group6`.`movie_actor`
    (movie_id, actor_id)
VALUES
       (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);

INSERT INTO `group6`.`movie_genre`
    (movie_id, genre_id)
VALUES
       (1, 8),
       (1, 9),
       (1, 3);
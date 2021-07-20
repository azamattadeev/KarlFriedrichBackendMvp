INSERT INTO A_TABLE
    (id, qr_code, table_number, r_keeper_id)
VALUES
    (1, 'table1', 1, 100001),
    (2, 'table2', 2, 100002),
    (3, 'table3', 3, 100003);

INSERT INTO MENU_ITEM_CATEGORY
    (id, name)
VALUES
    (1, 'Салаты'),
    (2, 'Супы'),
    (3, 'Рыба'),
    (4, 'Мясо'),
    (5, 'Соки'),
    (6, 'Горячие напитки');

INSERT INTO MENU_ITEM
    (id, available, description, image_url, name, preview_image_url, price, category_id, r_keeper_id)
VALUES
    (1, TRUE,
    '«Цезарь» — овощной салат. Популярное блюдо американской кухни',
    'http://192.168.43.78/karl-images/cezar.jpg',
    'Цезарь',
    'http://192.168.43.78/karl-images/cezar.jpg',
    250, 1, 200021
    ),

    (2, TRUE,
    'Описание салата Оливье',
    'http://192.168.43.78/karl-images/olivie.jpeg',
    'Оливье',
    'http://192.168.43.78/karl-images/olivie.jpeg',
    300, 1, 200022
    ),

    (3, TRUE,
    'Борщ — разновидность супа на основе свёклы, которая придаёт ему характерный красный цвет.',
    'http://192.168.43.78/karl-images/borsch.jpeg',
    'Борщ',
    'http://192.168.43.78/karl-images/borsch.jpeg',
    450, 2, 200023
    ),

    (4, TRUE,
    'Солянка — блюдо русской кухни, суп на крутом мясном, рыбном или грибном бульоне с острыми приправами.',
    'http://192.168.43.78/karl-images/solyanka.jpeg',
    'Солянка',
    'http://192.168.43.78/karl-images/solyanka.jpeg',
    420, 2, 200024
    ),

    (5, TRUE,
    'Рибай — наиболее известный стейк в мире.',
    'http://192.168.43.78/karl-images/steak.jpg',
    'Рибай стейк',
    'http://192.168.43.78/karl-images/steak.jpg',
    1800, 4, 200051
    ),

    (6, TRUE,
    'Свежевыжатый яблочный сок.',
    'http://192.168.43.78/karl-images/apple_fresh.jpg',
    'Яблочный сок',
    'http://192.168.43.78/karl-images/apple_fresh.jpg',
    200, 5, 200026
    ),

    (7, TRUE,
    'Свежевыжатый морковный сок.',
    'http://192.168.43.78/karl-images/carrot.jpg',
    'Морковный сок',
    'http://192.168.43.78/karl-images/carrot.jpg',
    200, 5, 200027
    ),

    (8, TRUE,
    'Свежевыжатый грейпфрутовый сок.',
    'http://192.168.43.78/karl-images/grape.jpg',
    'Грейпфрутовый сок',
    'http://192.168.43.78/karl-images/grape.jpg',
    200, 5, 200042
    ),

    (9, TRUE,
    'Свежевыжатый апельсиновый сок.',
    'http://192.168.43.78/karl-images/orange_fresh.jpg',
    'Апельсиновый сок',
    'http://192.168.43.78/karl-images/orange_fresh.jpg',
    200, 5, 200081
    ),

    (10, TRUE,
    'Американо - способ приготовления кофе, заключающийся в соединении определённого количества горячей воды и эспрессо',
    'http://192.168.43.78/karl-images/amerikano.jpg',
    'Американо',
    'http://192.168.43.78/karl-images/amerikano.jpg',
    250, 6, 200091
    ),

    (11, TRUE,
    'Капучино - кофейный напиток итальянской кухни на основе эспрессо с добавлением в него подогретого вспененного молока.',
    'http://192.168.43.78/karl-images/capp.jpeg',
    'Капучино',
    'http://192.168.43.78/karl-images/capp.jpeg',
    280, 6, 200321
    ),

    (12, TRUE,
    'Латте - кофейный напиток родом из Италии, состоящий из молока и кофе эспрессо. Варится на основе молока, образуя в чашке или бокале трёхслойную смесь из кофе, молока и пены.',
    'http://192.168.43.78/karl-images/latte.jpg',
    'Латте',
    'http://192.168.43.78/karl-images/latte.jpg',
    320, 6, 200521
    ),

    (13, TRUE,
    'Эспрессо - метод приготовления кофе путём прохождения горячей воды под давлением 9 бар через фильтр с молотым кофе.',
    'http://192.168.43.78/karl-images/espresso.jpg',
    'Эспрессо',
    'http://192.168.43.78/karl-images/espresso.jpg',
    260, 6, 200721
    );

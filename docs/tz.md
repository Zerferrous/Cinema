# Техническое задание
## Общие сведения
Программа создана для автоматизации и повышения эффективности работы кинотеатра.

## Цели и назначение создания автоматизированной системы;
- **Цель работы** — разработать базу данных и настольное приложение для учета работ в кинотеатре.
- **Назначение** — управление и автоматизация процессов кинотеатра.

##  Характеристика объектов автоматизации
### Основные функции:
- Разграничение прав доступа
- Добавление новых фильмов
- Создание сеансов фильмов
- Учет брони на определенное место сеанса
- Просмотр зарегистрированных пользователей
- Разделение пользователей: Админы и зрители

## Требования к производительности
- Быстрое загрузка страниц
- Отсутствие долгих задержек при выполнении запросов

## Требования к безопасности
- Защита от несанкционированного доступа к системе
- Протокол HTTPS для безопасной передачи данных Регулярное обновление системы безопасности

## Требования к тестированию
- Проведение нагрузочного тестирования системы
- Проверка работы системы на различных операционных системах

## Требования к автоматизированной системе;
- Многопользовательский доступ 
- Разграничение функционала по ролям 
- Клиент и сотрудник кинотеатра могут осуществлять следующие
   действия: 
  - получать оперативную информацию о наличии, описании, фото,
     сеансах и стоимости билета;
  - подбирать фильм по заявленным критериям;
  - осуществлять отбор фильмов по категориям; 
  - получать список свободных мест на рассадке зала.
4. Сотрудник кинотеатра может осуществлять следующие действия:
   - вести справочники (добавление, удаление, редактирование);
   - оформлять заказ на билеты (билеты продаются только на свободные
      места и не позднее, чем за 1 час до начала сеанса);
   - стоимость заказа рассчитывается динамически.

#### Схема данных:
Таблицы: 

    age_groups (Возрастные категории)
        AgeGroupId (PK): Идентификатор возрастной категории.
        AgeGroupTile: Название возрастной категории.
    
    films (Фильмы)
        FilmId (PK): Идентификатор фильма.
        FilmTitle: Название фильма.
        AgeGroupId (FK): Ссылка на возрастную категорию.
        FilmLenght: Длительность фильма.
        FilmDescription: Описание фильма.
        FilmTrailer: Ссылка на трейлер.
        FilmImage: Ссылка на изображение фильма.
        FilmCountry: Страна производства.
        FilmYear: Год производства.

    genres (Жанры)
        GenreId (PK): Идентификатор жанра.
        GenreTitle: Название жанра.

    films_has_genres (Связь фильмы-жанры)
        films_FilmId (PK, FK): Ссылка на фильм.
        genres_GenreId (PK, FK): Ссылка на жанр.
        halls_seats (Места в зале)
        HallId (PK): Идентификатор зала.
        SeatId (PK): Идентификатор места.

    roles (Роли пользователей)
    RoleId (PK): Идентификатор роли.
    RoleName: Название роли.
    sessions (Сеансы)

    SessionId (PK): Идентификатор сеанса.
        FilmId (FK): Ссылка на фильм.
        SessionDatetime: Дата и время сеанса.
        SessionPrice: Цена билета на сеанс.
        HallId (FK): Ссылка на зал.

    sessions_has_halls_seats (Места в зале на сеансе)
        SessionId (PK, FK): Ссылка на сеанс.
        HallId (PK, FK): Ссылка на зал.
        SeatId (PK, FK): Ссылка на место.
        SeatStatus: Статус места (по умолчанию "Свободно").

    users (Пользователи)
        UserNumber (PK): Номер пользователя.
        UserFio: ФИО пользователя.
        UserDateOfBirth: Дата рождения.
        RoleId (FK): Ссылка на роль пользователя.
        UserPassword: Пароль пользователя.

    tickets (Билеты)
        TicketId (PK): Идентификатор билета.
        SessionId (FK): Ссылка на сеанс.
        UserNumber (FK): Ссылка на пользователя.
        HallId (FK): Ссылка на зал.
        SeatId (FK): Ссылка на место.

Связи между таблицами:

    films (AgeGroupId) → age_groups (AgeGroupId) (многие к одному).
    films_has_genres (films_FilmId) → films (FilmId) (многие к одному).
    films_has_genres (genres_GenreId) → genres (GenreId) (многие к одному).
    sessions (FilmId) → films (FilmId) (многие к одному).
    sessions (HallId) → halls_seats (HallId) (многие к одному).
    sessions_has_halls_seats (SessionId) → sessions (SessionId) (многие к одному).
    sessions_has_halls_seats (HallId, SeatId) → halls_seats (HallId, SeatId) (многие к одному).
    tickets (SessionId) → sessions (SessionId) (многие к одному).
    tickets (UserNumber) → users (UserNumber) (многие к одному).
    tickets (HallId, SeatId) → halls_seats (HallId, SeatId) (многие к одному).
    users (RoleId) → roles (RoleId) (многие к одному).

Описание триггеров:

    films_BEFORE_DELETE: Удаляет все связанные сеансы из таблицы sessions, если удаляется фильм.

    sessions_BEFORE_DELETE: Удаляет связанные записи из таблиц tickets и sessions_has_halls_seats, если удаляется сеанс.

    tickets_AFTER_INSERT: Обновляет статус места на "Занято" в таблице sessions_has_halls_seats при продаже билета.

    after_sessions_insert: Автоматически создает записи мест в таблице sessions_has_halls_seats при добавлении нового сеанса.

    before_sessions_insert: Создает места в таблице halls_seats, если зал не существует.

## Состав и содержание работ по созданию автоматизированной системы
1) Анализ требований
- Анализ методов получения информации об админах и пользователях
- Изучение способов создания сессий для пользователей
2) Концептуальное проектирование
- Проектирование базы данных
- Проектирование интерфейса
3) Разработка системы
- Разработка базы данных
- Разработка приложения
4) Тестирование
- Тестирование отдельных компонентов приложения
- Тестирование полного процесса создания сеансов и авторизации пользователя, с последующей бронью билетов
5) Документация
- Создание руководства для пользователей
- Создание тех документации

## Порядок разработки автоматизированной системы;
1) Анализ требований
2) Концептуальное проектирование
3) Разработка системы
4) Тестирование
5) Документация

## Сроки выполнения
- Дата выдачи задания: 06 ноября 2023 года
- Срок сдачи работы: 20 ноября 2023 года

## Заключение
Разработка базы данных и настольного приложения для кинотеатра должна обеспечивать эффективное управление. Система должна быть безопасной, простой и удобной для админов и для посетителей.
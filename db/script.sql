create table if not exists classrooms
(
    id       int auto_increment
        primary key,
    name     varchar(50)          not null,
    location varchar(120)         not null,
    capacity int                  not null,
    inactive tinyint(1) default 0 not null
);

create table if not exists disciplines
(
    id          int auto_increment
        primary key,
    name        varchar(100)         not null,
    code        varchar(20)          not null,
    description text                 not null,
    inactive    tinyint(1) default 0 not null
);

create table if not exists professors
(
    id          int auto_increment
        primary key,
    name        varchar(100)         not null,
    email       varchar(100)         not null,
    phoneNumber varchar(20)          not null,
    inactive    tinyint(1) default 0 not null
);

create table if not exists classes
(
    id            int auto_increment
        primary key,
    professor_id  int                  not null,
    discipline_id int                  not null,
    classroom_id  int                  not null,
    start_time    time                 not null,
    finish_time   time                 not null,
    semester      varchar(7)           not null,
    inactive      tinyint(1) default 0 not null,
    constraint classes_ibfk_1
        foreign key (professor_id) references professors (id),
    constraint classes_ibfk_2
        foreign key (discipline_id) references disciplines (id),
    constraint classes_ibfk_3
        foreign key (classroom_id) references classrooms (id)
);

create index ID_discipline
    on classes (discipline_id);

create index ID_professor
    on classes (professor_id);

create index ID_classroom
    on classes (classroom_id);

create table if not exists students
(
    id          int auto_increment
        primary key,
    name        varchar(100)         not null,
    cpf         varchar(11)          not null,
    email       varchar(100)         not null,
    phoneNumber varchar(20)          not null,
    address     varchar(100)         not null,
    inactive    tinyint(1) default 0 not null
);

create table if not exists student_class
(
    id_student int                  not null,
    id_class   int                  not null,
    inactive   tinyint(1) default 0 not null,
    primary key (id_student, id_class),
    constraint student_class_ibfk_1
        foreign key (id_student) references students (id),
    constraint student_class_ibfk_2
        foreign key (id_class) references classes (id)
);

create index ID_class
    on student_class (id_class);



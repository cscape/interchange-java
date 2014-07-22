
    drop table ApiKeys cascade constraints;

    create table ApiKeys (
        applicationName varchar(255) not null,
        applicationKey varchar(255),
        applicationUrl varchar(255),
        description varchar(255),
        email varchar(255),
        phone varchar(255),
        primary key (applicationName)
    );

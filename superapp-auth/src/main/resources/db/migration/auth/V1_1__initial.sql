create table mobile_as_user (
    mobile                  varchar(13),
    password                char(60),
    creation_time           timestamp not null default NOW(),
    last_changed_time       timestamp not null default NOW(),
    CONSTRAINT pk_mobile_as_user PRIMARY KEY(mobile)
);

-- ALTER SEQUENCE mobile_as_user_{columnname}_seq RESTART WITH 100
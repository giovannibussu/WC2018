create sequence seq_punteggi start 1 increment 1;

    create table punteggi (
       id int8 not null,
        identificativo varchar(35) not null,
        punti_passaggi int4 not null,
        punti_posizione int4 not null,
        punti_risultato int4 not null,
        punti_risultato_esatto int4 not null,
        primary key (id)
    );

insert into punteggi(id, identificativo, punti_risultato, punti_risultato_esatto, punti_posizione, punti_passaggi)
 values(nextval('seq_punteggi'), 'gironi', 1, 3, 5,0);

insert into punteggi(id, identificativo, punti_risultato, punti_risultato_esatto, punti_posizione, punti_passaggi)
 values(nextval('seq_punteggi'), 'ottavi', 1, 3, 5,7);

insert into punteggi(id, identificativo, punti_risultato, punti_risultato_esatto, punti_posizione, punti_passaggi)
 values(nextval('seq_punteggi'), 'quarti', 1, 3, 5,7);

insert into punteggi(id, identificativo, punti_risultato, punti_risultato_esatto, punti_posizione, punti_passaggi)
 values(nextval('seq_punteggi'), 'semifinali', 1, 3, 5,7);

insert into punteggi(id, identificativo, punti_risultato, punti_risultato_esatto, punti_posizione, punti_passaggi)
 values(nextval('seq_punteggi'), 'finale', 1, 3, 5,7);

alter table subdivisions add column id_punteggi int8;

update subdivisions set id_punteggi = (select id from punteggi where identificativo = 'gironi') where tipo = 'GIRONE';  
update subdivisions set id_punteggi = (select id from punteggi where identificativo = 'ottavi') where tipo = 'OTTAVI';  
update subdivisions set id_punteggi = (select id from punteggi where identificativo = 'quarti') where tipo = 'QUARTI';  
update subdivisions set id_punteggi = (select id from punteggi where identificativo = 'semifinali') where tipo = 'SEMIFINALE';  
update subdivisions set id_punteggi = (select id from punteggi where identificativo = 'finale') where tipo = 'FINALE';  

--
-- PostgreSQL database dump
--

-- Dumped from database version 11.7
-- Dumped by pg_dump version 11.7

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: dati_partite; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.dati_partite (
    id bigint NOT NULL,
    codice_partita character varying(255) NOT NULL,
    goal_casa integer NOT NULL,
    goal_trasferta integer NOT NULL,
    id_pronostico bigint
);


ALTER TABLE public.dati_partite OWNER TO openspcoop2;

--
-- Name: giocatori; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.giocatori (
    id bigint NOT NULL,
    nome character varying(35) NOT NULL,
    tags character varying(35)
);


ALTER TABLE public.giocatori OWNER TO openspcoop2;

--
-- Name: partite; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.partite (
    id bigint NOT NULL,
    codice_calcolo_casa character varying(255),
    codice_calcolo_trasferta character varying(255),
    codice_partita character varying(255) NOT NULL,
    data timestamp without time zone NOT NULL,
    descrizione character varying(255) NOT NULL,
    id_squadra_casa bigint,
    id_stadio bigint,
    id_subdivision bigint,
    id_squadra_trasferta bigint
);


ALTER TABLE public.partite OWNER TO openspcoop2;

--
-- Name: pronostici; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.pronostici (
    id bigint NOT NULL,
    id_pronostico character varying(35) NOT NULL,
    pronostico_originale bytea,
    id_giocatore bigint,
    id_torneo bigint,
    id_squadra_vincente bigint
);


ALTER TABLE public.pronostici OWNER TO openspcoop2;

--
-- Name: seq_dati_partite; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_dati_partite
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_dati_partite OWNER TO openspcoop2;

--
-- Name: seq_giocatori; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_giocatori
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_giocatori OWNER TO openspcoop2;

--
-- Name: seq_partite; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_partite
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_partite OWNER TO openspcoop2;

--
-- Name: seq_pronostici; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_pronostici
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_pronostici OWNER TO openspcoop2;

--
-- Name: seq_squadre; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_squadre
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_squadre OWNER TO openspcoop2;

--
-- Name: seq_stadi; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_stadi
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_stadi OWNER TO openspcoop2;

--
-- Name: seq_subdivisions; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_subdivisions
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_subdivisions OWNER TO openspcoop2;

--
-- Name: seq_tornei; Type: SEQUENCE; Schema: public; Owner: openspcoop2
--

CREATE SEQUENCE public.seq_tornei
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_tornei OWNER TO openspcoop2;

--
-- Name: squadre; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.squadre (
    id bigint NOT NULL,
    bandiera character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    ranking_fifa integer NOT NULL
);


ALTER TABLE public.squadre OWNER TO openspcoop2;

--
-- Name: squadre_subdivisions; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.squadre_subdivisions (
    id_subdivision bigint NOT NULL,
    id_squadra bigint NOT NULL
);


ALTER TABLE public.squadre_subdivisions OWNER TO openspcoop2;

--
-- Name: stadi; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.stadi (
    id bigint NOT NULL,
    citta character varying(255) NOT NULL,
    link character varying(255) NOT NULL,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.stadi OWNER TO openspcoop2;

--
-- Name: subdivisions; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.subdivisions (
    id bigint NOT NULL,
    nome character varying(35) NOT NULL,
    tipo character varying(35) NOT NULL,
    id_torneo bigint
);


ALTER TABLE public.subdivisions OWNER TO openspcoop2;

--
-- Name: tornei; Type: TABLE; Schema: public; Owner: openspcoop2
--

CREATE TABLE public.tornei (
    id bigint NOT NULL,
    nome character varying(35) NOT NULL,
    regole bytea,
    id_pronostico_ufficiale bigint
);


ALTER TABLE public.tornei OWNER TO openspcoop2;

--
-- Data for Name: dati_partite; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--



--
-- Data for Name: giocatori; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.giocatori VALUES (1, 'UFFICIALE', NULL);


--
-- Data for Name: partite; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.partite VALUES (1, NULL, NULL, '15', '2021-06-16 21:00:00', 'Fase a gironi - Gruppo A - Giornata 2/3', 2, 5, 1, 4);
INSERT INTO public.partite VALUES (2, NULL, NULL, '26', '2021-06-20 18:00:00', 'Fase a gironi - Gruppo A - Giornata 3/3', 2, 5, 1, 3);
INSERT INTO public.partite VALUES (3, NULL, NULL, '25', '2021-06-20 18:00:00', 'Fase a gironi - Gruppo A - Giornata 3/3', 4, 1, 1, 1);
INSERT INTO public.partite VALUES (4, NULL, NULL, '2', '2021-06-12 15:00:00', 'Fase a gironi - Gruppo A - Giornata 1/3', 3, 1, 1, 4);
INSERT INTO public.partite VALUES (5, NULL, NULL, '14', '2021-06-16 18:00:00', 'Fase a gironi - Gruppo A - Giornata 2/3', 1, 1, 1, 3);
INSERT INTO public.partite VALUES (6, NULL, NULL, '1', '2021-06-11 21:00:00', 'Fase a gironi - Gruppo A - Giornata 1/3', 1, 5, 1, 2);
INSERT INTO public.partite VALUES (7, NULL, NULL, '29', '2021-06-21 21:00:00', 'Fase a gironi - Gruppo B - Giornata 3/3', 8, 2, 2, 5);
INSERT INTO public.partite VALUES (8, NULL, NULL, '3', '2021-06-12 18:00:00', 'Fase a gironi - Gruppo B - Giornata 1/3', 5, 2, 2, 6);
INSERT INTO public.partite VALUES (9, NULL, NULL, '4', '2021-06-12 21:00:00', 'Fase a gironi - Gruppo B - Giornata 1/3', 7, 8, 2, 8);
INSERT INTO public.partite VALUES (10, NULL, NULL, '13', '2021-06-16 15:00:00', 'Fase a gironi - Gruppo B - Giornata 2/3', 6, 8, 2, 8);
INSERT INTO public.partite VALUES (11, NULL, NULL, '17', '2021-06-17 18:00:00', 'Fase a gironi - Gruppo B - Giornata 2/3', 5, 2, 2, 7);
INSERT INTO public.partite VALUES (12, NULL, NULL, '30', '2021-06-21 21:00:00', 'Fase a gironi - Gruppo B - Giornata 3/3', 6, 8, 2, 7);
INSERT INTO public.partite VALUES (13, NULL, NULL, '16', '2021-06-17 15:00:00', 'Fase a gironi - Gruppo C - Giornata 2/3', 14, 7, 3, 12);
INSERT INTO public.partite VALUES (14, NULL, NULL, '6', '2021-06-13 18:00:00', 'Fase a gironi - Gruppo C - Giornata 1/3', 11, 7, 3, 12);
INSERT INTO public.partite VALUES (15, NULL, NULL, '28', '2021-06-21 18:00:00', 'Fase a gironi - Gruppo C - Giornata 3/3', 14, 7, 3, 11);
INSERT INTO public.partite VALUES (16, NULL, NULL, '18', '2021-06-17 21:00:00', 'Fase a gironi - Gruppo C - Giornata 2/3', 13, 6, 3, 11);
INSERT INTO public.partite VALUES (17, NULL, NULL, '27', '2021-06-21 18:00:00', 'Fase a gironi - Gruppo C - Giornata 3/3', 12, 6, 3, 13);
INSERT INTO public.partite VALUES (18, NULL, NULL, '7', '2021-06-13 21:00:00', 'Fase a gironi - Gruppo C - Giornata 1/3', 13, 6, 3, 14);
INSERT INTO public.partite VALUES (19, NULL, NULL, '32', '2021-06-22 21:00:00', 'Fase a gironi - Gruppo D - Giornata 3/3', 16, 4, 4, 9);
INSERT INTO public.partite VALUES (20, NULL, NULL, '8', '2021-06-14 15:00:00', 'Fase a gironi - Gruppo D - Giornata 1/3', 15, 9, 4, 16);
INSERT INTO public.partite VALUES (21, NULL, NULL, '20', '2021-06-18 18:00:00', 'Fase a gironi - Gruppo D - Giornata 2/3', 10, 9, 4, 16);
INSERT INTO public.partite VALUES (22, NULL, NULL, '21', '2021-06-18 21:00:00', 'Fase a gironi - Gruppo D - Giornata 2/3', 9, 4, 4, 15);
INSERT INTO public.partite VALUES (23, NULL, NULL, '5', '2021-06-13 15:00:00', 'Fase a gironi - Gruppo D - Giornata 1/3', 9, 4, 4, 10);
INSERT INTO public.partite VALUES (24, NULL, NULL, '31', '2021-06-22 21:00:00', 'Fase a gironi - Gruppo D - Giornata 3/3', 10, 9, 4, 15);
INSERT INTO public.partite VALUES (25, '1F', '3', '42', '2021-06-28 21:00:00', 'Ottavi', NULL, 7, 5, NULL);
INSERT INTO public.partite VALUES (26, '2D', '2E', '41', '2021-06-28 18:00:00', 'Ottavi', NULL, 2, 5, NULL);
INSERT INTO public.partite VALUES (27, '1A', '2C', '38', '2021-06-26 21:00:00', 'Ottavi', NULL, 4, 5, NULL);
INSERT INTO public.partite VALUES (28, '1E', '3', '44', '2021-06-29 21:00:00', 'Ottavi', NULL, 9, 5, NULL);
INSERT INTO public.partite VALUES (29, '2A', '2B', '37', '2021-06-26 18:00:00', 'Ottavi', NULL, 6, 5, NULL);
INSERT INTO public.partite VALUES (30, '1C', '3', '39', '2021-06-27 18:00:00', 'Ottavi', NULL, 11, 5, NULL);
INSERT INTO public.partite VALUES (31, '1D', '2F', '43', '2021-06-29 18:00:00', 'Ottavi', NULL, 4, 5, NULL);
INSERT INTO public.partite VALUES (32, '1B', '3', '40', '2021-06-27 21:00:00', 'Ottavi', NULL, 10, 5, NULL);
INSERT INTO public.partite VALUES (33, NULL, NULL, '9', '2021-06-14 18:00:00', 'Fase a gironi - Gruppo E - Giornata 1/3', 17, 8, 6, 18);
INSERT INTO public.partite VALUES (34, NULL, NULL, '33', '2021-06-23 18:00:00', 'Fase a gironi - Gruppo E - Giornata 3/3', 20, 8, 6, 17);
INSERT INTO public.partite VALUES (35, NULL, NULL, '10', '2021-06-14 21:00:00', 'Fase a gironi - Gruppo E - Giornata 1/3', 19, 10, 6, 20);
INSERT INTO public.partite VALUES (36, NULL, NULL, '19', '2021-06-18 15:00:00', 'Fase a gironi - Gruppo E - Giornata 2/3', 20, 8, 6, 18);
INSERT INTO public.partite VALUES (39, 'W40', 'W38', '46', '2021-07-02 21:00:00', 'Quarti', NULL, 3, 7, NULL);
INSERT INTO public.partite VALUES (37, NULL, NULL, '24', '2021-06-19 21:00:00', 'Fase a gironi - Gruppo E - Giornata 2/3', 19, 10, 6, 17);
INSERT INTO public.partite VALUES (38, NULL, NULL, '34', '2021-06-23 18:00:00', 'Fase a gironi - Gruppo E - Giornata 3/3', 18, 10, 6, 19);
INSERT INTO public.partite VALUES (40, 'W42', 'W41', '45', '2021-07-02 18:00:00', 'Quarti', NULL, 8, 7, NULL);
INSERT INTO public.partite VALUES (41, 'W39', 'W37', '47', '2021-07-03 18:00:00', 'Quarti', NULL, 1, 7, NULL);
INSERT INTO public.partite VALUES (42, 'W44', 'W43', '48', '2021-07-03 21:00:00', 'Quarti', NULL, 5, 7, NULL);
INSERT INTO public.partite VALUES (43, NULL, NULL, '23', '2021-06-19 18:00:00', 'Fase a gironi - Gruppo F - Giornata 2/3', 22, 3, 8, 24);
INSERT INTO public.partite VALUES (44, NULL, NULL, '36', '2021-06-23 21:00:00', 'Fase a gironi - Gruppo F - Giornata 3/3', 24, 3, 8, 21);
INSERT INTO public.partite VALUES (45, NULL, NULL, '12', '2021-06-15 21:00:00', 'Fase a gironi - Gruppo F - Giornata 1/3', 23, 3, 8, 24);
INSERT INTO public.partite VALUES (46, NULL, NULL, '35', '2021-06-23 21:00:00', 'Fase a gironi - Gruppo F - Giornata 3/3', 22, 11, 8, 23);
INSERT INTO public.partite VALUES (47, NULL, NULL, '11', '2021-06-15 18:00:00', 'Fase a gironi - Gruppo F - Giornata 1/3', 21, 11, 8, 22);
INSERT INTO public.partite VALUES (48, NULL, NULL, '22', '2021-06-19 15:00:00', 'Fase a gironi - Gruppo F - Giornata 2/3', 21, 11, 8, 23);
INSERT INTO public.partite VALUES (49, 'W47', 'W48', '50', '2021-07-07 21:00:00', 'Semifinale', NULL, 4, 9, NULL);
INSERT INTO public.partite VALUES (50, 'W45', 'W46', '49', '2021-07-06 21:00:00', 'Semifinale', NULL, 4, 9, NULL);
INSERT INTO public.partite VALUES (51, 'W49', 'W50', '51', '2021-07-11 21:00:00', 'Finale', NULL, 4, 10, NULL);


--
-- Data for Name: pronostici; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.pronostici VALUES (1, 'ufficiale', NULL, 1, NULL, NULL);


--
-- Data for Name: squadre; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.squadre VALUES (1, 'images/turchia.png', 'Turchia', 0);
INSERT INTO public.squadre VALUES (2, 'images/italia.png', 'Italia', 0);
INSERT INTO public.squadre VALUES (3, 'images/galles.png', 'Galles', 0);
INSERT INTO public.squadre VALUES (4, 'images/svizzera.png', 'Svizzera', 0);
INSERT INTO public.squadre VALUES (5, 'images/danimarca.png', 'Danimarca', 1);
INSERT INTO public.squadre VALUES (6, 'images/finlandia.png', 'Finlandia', 0);
INSERT INTO public.squadre VALUES (7, 'images/belgio.png', 'Belgio', 0);
INSERT INTO public.squadre VALUES (8, 'images/russia.png', 'Russia', 0);
INSERT INTO public.squadre VALUES (9, 'images/inghilterra.png', 'Inghilterra', 0);
INSERT INTO public.squadre VALUES (10, 'images/croazia.png', 'Croazia', 0);
INSERT INTO public.squadre VALUES (11, 'images/austria.png', 'Austria', 0);
INSERT INTO public.squadre VALUES (12, 'images/macedonia.png', 'Macedonia del Nord', 0);
INSERT INTO public.squadre VALUES (13, 'images/olanda.png', 'Olanda', 0);
INSERT INTO public.squadre VALUES (14, 'images/ucraina.png', 'Ucraina', 0);
INSERT INTO public.squadre VALUES (15, 'images/scozia.png', 'Scozia', 0);
INSERT INTO public.squadre VALUES (16, 'images/repubblica_ceca.png', 'Repubblica Ceca', 0);
INSERT INTO public.squadre VALUES (17, 'images/polonia.png', 'Polonia', 0);
INSERT INTO public.squadre VALUES (18, 'images/slovacchia.png', 'Slovacchia', 0);
INSERT INTO public.squadre VALUES (19, 'images/spagna.png', 'Spagna', 0);
INSERT INTO public.squadre VALUES (20, 'images/svezia.png', 'Svezia', 0);
INSERT INTO public.squadre VALUES (21, 'images/ungheria.png', 'Ungheria', 0);
INSERT INTO public.squadre VALUES (22, 'images/portogallo.png', 'Portogallo', 0);
INSERT INTO public.squadre VALUES (23, 'images/francia.png', 'Francia', 0);
INSERT INTO public.squadre VALUES (24, 'images/germania.png', 'Germania', 0);


--
-- Data for Name: squadre_subdivisions; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.squadre_subdivisions VALUES (1, 1);
INSERT INTO public.squadre_subdivisions VALUES (1, 4);
INSERT INTO public.squadre_subdivisions VALUES (1, 2);
INSERT INTO public.squadre_subdivisions VALUES (1, 3);
INSERT INTO public.squadre_subdivisions VALUES (2, 7);
INSERT INTO public.squadre_subdivisions VALUES (2, 6);
INSERT INTO public.squadre_subdivisions VALUES (2, 8);
INSERT INTO public.squadre_subdivisions VALUES (2, 5);
INSERT INTO public.squadre_subdivisions VALUES (3, 12);
INSERT INTO public.squadre_subdivisions VALUES (3, 13);
INSERT INTO public.squadre_subdivisions VALUES (3, 14);
INSERT INTO public.squadre_subdivisions VALUES (3, 11);
INSERT INTO public.squadre_subdivisions VALUES (4, 10);
INSERT INTO public.squadre_subdivisions VALUES (4, 15);
INSERT INTO public.squadre_subdivisions VALUES (4, 16);
INSERT INTO public.squadre_subdivisions VALUES (4, 9);
INSERT INTO public.squadre_subdivisions VALUES (6, 18);
INSERT INTO public.squadre_subdivisions VALUES (6, 17);
INSERT INTO public.squadre_subdivisions VALUES (6, 19);
INSERT INTO public.squadre_subdivisions VALUES (6, 20);
INSERT INTO public.squadre_subdivisions VALUES (8, 24);
INSERT INTO public.squadre_subdivisions VALUES (8, 23);
INSERT INTO public.squadre_subdivisions VALUES (8, 21);
INSERT INTO public.squadre_subdivisions VALUES (8, 22);


--
-- Data for Name: stadi; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.stadi VALUES (1, 'Baku', 'https://it.wikipedia.org/wiki/Stadio_olimpico_di_Baku', 'Baku Olympic Stadium');
INSERT INTO public.stadi VALUES (2, 'Copenaghen', 'https://it.wikipedia.org/wiki/Stadio_Parken', 'Parken Stadium');
INSERT INTO public.stadi VALUES (3, 'Monaco di Baviera', 'https://it.wikipedia.org/wiki/Allianz_Arena', 'Allianz Arena');
INSERT INTO public.stadi VALUES (4, 'Londra', 'https://it.wikipedia.org/wiki/Stadio_di_Wembley_(2007)', 'Wembley Stadium');
INSERT INTO public.stadi VALUES (5, 'Roma', 'https://it.wikipedia.org/wiki/Stadio_Olimpico_(Roma)', 'Stadio Olimpico');
INSERT INTO public.stadi VALUES (6, 'Amsterdam', 'https://it.wikipedia.org/wiki/Johan_Cruijff_Arena', 'Johan Cruijff Arena');
INSERT INTO public.stadi VALUES (7, 'Bucarest', 'https://it.wikipedia.org/wiki/Arena_Na%C8%9Bional%C4%83', 'Arena Națională');
INSERT INTO public.stadi VALUES (8, 'San Pietroburgo', 'https://it.wikipedia.org/wiki/Stadio_San_Pietroburgo', 'Gazprom Arena');
INSERT INTO public.stadi VALUES (9, 'Glasgow', 'https://it.wikipedia.org/wiki/Hampden_Park', 'Hampden Park');
INSERT INTO public.stadi VALUES (10, 'Siviglia', 'https://it.wikipedia.org/wiki/Stadio_de_la_Cartuja', 'Estadio de La Cartuja');
INSERT INTO public.stadi VALUES (11, 'Budapest', 'https://it.wikipedia.org/wiki/Pusk%C3%A1s_Ar%C3%A9na', 'Puskás Aréna');


--
-- Data for Name: subdivisions; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.subdivisions VALUES (1, 'A', 'GIRONE', 1);
INSERT INTO public.subdivisions VALUES (2, 'B', 'GIRONE', 1);
INSERT INTO public.subdivisions VALUES (3, 'C', 'GIRONE', 1);
INSERT INTO public.subdivisions VALUES (4, 'D', 'GIRONE', 1);
INSERT INTO public.subdivisions VALUES (5, 'Ottavi', 'OTTAVI', 1);
INSERT INTO public.subdivisions VALUES (6, 'E', 'GIRONE', 1);
INSERT INTO public.subdivisions VALUES (7, 'Quarti', 'QUARTI', 1);
INSERT INTO public.subdivisions VALUES (8, 'F', 'GIRONE', 1);
INSERT INTO public.subdivisions VALUES (9, 'Semifinale', 'SEMIFINALE', 1);
INSERT INTO public.subdivisions VALUES (10, 'Finale', 'FINALE', 1);


--
-- Data for Name: tornei; Type: TABLE DATA; Schema: public; Owner: openspcoop2
--

INSERT INTO public.tornei VALUES (1, 'EURO2021', NULL, 1);


--
-- Name: seq_dati_partite; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_dati_partite', 1, false);


--
-- Name: seq_giocatori; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_giocatori', 1, true);


--
-- Name: seq_partite; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_partite', 51, true);


--
-- Name: seq_pronostici; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_pronostici', 1, true);


--
-- Name: seq_squadre; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_squadre', 24, true);


--
-- Name: seq_stadi; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_stadi', 11, true);


--
-- Name: seq_subdivisions; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_subdivisions', 10, true);


--
-- Name: seq_tornei; Type: SEQUENCE SET; Schema: public; Owner: openspcoop2
--

SELECT pg_catalog.setval('public.seq_tornei', 1, true);


--
-- Name: dati_partite dati_partite_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.dati_partite
    ADD CONSTRAINT dati_partite_pkey PRIMARY KEY (id);


--
-- Name: giocatori giocatori_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.giocatori
    ADD CONSTRAINT giocatori_pkey PRIMARY KEY (id);


--
-- Name: partite partite_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.partite
    ADD CONSTRAINT partite_pkey PRIMARY KEY (id);


--
-- Name: pronostici pronostici_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.pronostici
    ADD CONSTRAINT pronostici_pkey PRIMARY KEY (id);


--
-- Name: squadre squadre_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.squadre
    ADD CONSTRAINT squadre_pkey PRIMARY KEY (id);


--
-- Name: squadre_subdivisions squadre_subdivisions_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.squadre_subdivisions
    ADD CONSTRAINT squadre_subdivisions_pkey PRIMARY KEY (id_subdivision, id_squadra);


--
-- Name: stadi stadi_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.stadi
    ADD CONSTRAINT stadi_pkey PRIMARY KEY (id);


--
-- Name: subdivisions subdivisions_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.subdivisions
    ADD CONSTRAINT subdivisions_pkey PRIMARY KEY (id);


--
-- Name: tornei tornei_pkey; Type: CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.tornei
    ADD CONSTRAINT tornei_pkey PRIMARY KEY (id);


--
-- Name: partite fk3jv8t8hadadqyqvx0p7x3sg58; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.partite
    ADD CONSTRAINT fk3jv8t8hadadqyqvx0p7x3sg58 FOREIGN KEY (id_squadra_trasferta) REFERENCES public.squadre(id);


--
-- Name: pronostici fk5vts3koh998bphfi7otb5gekh; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.pronostici
    ADD CONSTRAINT fk5vts3koh998bphfi7otb5gekh FOREIGN KEY (id_squadra_vincente) REFERENCES public.squadre(id);


--
-- Name: squadre_subdivisions fkbxfy2as00n8unt2wquiq83i4; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.squadre_subdivisions
    ADD CONSTRAINT fkbxfy2as00n8unt2wquiq83i4 FOREIGN KEY (id_subdivision) REFERENCES public.subdivisions(id);


--
-- Name: partite fkeq9i0qoo45q9x51gvggjiabu; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.partite
    ADD CONSTRAINT fkeq9i0qoo45q9x51gvggjiabu FOREIGN KEY (id_squadra_casa) REFERENCES public.squadre(id);


--
-- Name: pronostici fkg3v3bqyr1m3yjo8deicoohlfi; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.pronostici
    ADD CONSTRAINT fkg3v3bqyr1m3yjo8deicoohlfi FOREIGN KEY (id_giocatore) REFERENCES public.giocatori(id);


--
-- Name: partite fkk2y9sv5heymaun72miv5jr8jl; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.partite
    ADD CONSTRAINT fkk2y9sv5heymaun72miv5jr8jl FOREIGN KEY (id_stadio) REFERENCES public.stadi(id);


--
-- Name: tornei fkldfxisehu2qudx2kc2okdnbqw; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.tornei
    ADD CONSTRAINT fkldfxisehu2qudx2kc2okdnbqw FOREIGN KEY (id_pronostico_ufficiale) REFERENCES public.pronostici(id);


--
-- Name: squadre_subdivisions fkm7f8n7044b6u1o6ojhauvew7q; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.squadre_subdivisions
    ADD CONSTRAINT fkm7f8n7044b6u1o6ojhauvew7q FOREIGN KEY (id_squadra) REFERENCES public.squadre(id);


--
-- Name: pronostici fkmv30o222onbw8y4yi5vto4vir; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.pronostici
    ADD CONSTRAINT fkmv30o222onbw8y4yi5vto4vir FOREIGN KEY (id_torneo) REFERENCES public.tornei(id);


--
-- Name: subdivisions fkp1umbl3bf9n55wq27wpneyk9a; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.subdivisions
    ADD CONSTRAINT fkp1umbl3bf9n55wq27wpneyk9a FOREIGN KEY (id_torneo) REFERENCES public.tornei(id);


--
-- Name: dati_partite fkr5j7a954dcs8e9fqcx6ccl6my; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.dati_partite
    ADD CONSTRAINT fkr5j7a954dcs8e9fqcx6ccl6my FOREIGN KEY (id_pronostico) REFERENCES public.pronostici(id);


--
-- Name: partite fkt34y5udixitjnhqi0vby21ttb; Type: FK CONSTRAINT; Schema: public; Owner: openspcoop2
--

ALTER TABLE ONLY public.partite
    ADD CONSTRAINT fkt34y5udixitjnhqi0vby21ttb FOREIGN KEY (id_subdivision) REFERENCES public.subdivisions(id);


--
-- PostgreSQL database dump complete
--


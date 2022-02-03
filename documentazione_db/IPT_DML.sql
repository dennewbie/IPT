
-- cittadino

INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00001',
'watermelon',
'01234chan',
'milano@libero.it',
'Leonardo',
'Manolo',
'2022-2-22',
'1998-05-16');




INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00002',
'dom',
'dom00',
'dom@libero.it',
'Dominick',
'Ferraro',
'2022-2-22',
'2000-7-20');



INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00003',
'mungowz',
'alfred00',
'alfredo@libero.it',
'Alfredo',
'Mungari',
'2022-2-22',
'2000-12-20');




INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00004',
'gomax',
'max00',
'orsini@libero.it',
'Massimiliano',
'Orsini',
'2022-2-22',
'2001-10-10');





INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00005',
'dennewbie',
'caruso123',
'denny@libero.it',
'Denny',
'Caruso',
'2022-2-22',
'1998-1-05');





INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00006',
'.gif',
'pringles123',
'francesco@libero.it',
'Frank',
'Fogli',
'2022-2-22',
'1993-11-15');



INSERT INTO `ipt`.`cittadino`
(`id_cittadino`,
`username`,
`password`,
`email`,
`nome`,
`cognome`,
`data_registrazione`,
`data_nascita`)
VALUES
('00007',
'matty',
'scotty123',
'mattew@libero.it',
'Matteo',
'Scotts',
'2022-1-22',
'1995-10-15');



-- transazione
INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00001',
'00001',
'10',
'PayPal',
'2022-01-12');

INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00002',
'00002',
'5',
'Phone Bill',
'2022-02-22');


INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00003',
'00002',
'4',
'PayPal',
'2022-02-12');


INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00004',
'00003',
'7',
'Credit Card',
'2022-01-10');



INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00005',
'00004',
'7',
'Credit Card',
'2022-01-30');




INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00006',
'00005',
'4',
'Credit Card',
'2022-04-19');




INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00007',
'00006',
'8',
'Phone Bill',
'2022-03-29');



INSERT INTO `ipt`.`transazione`
(`id_transazione`,
`id_cittadino`,
`costo`,
`metodo_pagamento`,
`data_pagamento`)
VALUES
('00008',
'00006',
'5',
'PayPal',
'2022-06-23');


-- linea
INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0001', 
 '13', 
 'Napoli', 
 'Baiano', 
 '2020-01-01', 
 '20:00', 
 '7:00');

INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0002', 
 '15', 
 'Napoli', 
 'Sorrento', 
 '2020-01-01', 
 '20:00', 
 '7:00');


INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0003', 
 '15', 
 'Sarno', 
 'Nola', 
 '2020-01-01', 
 '20:00', 
 '7:00');


INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0004', 
 '15', 
 'Nola', 
 'Salerno', 
 '2020-01-01', 
 '20:00', 
 '7:00');

INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0005', 
 '20', 
 'Casalnuovo', 
 'Centro Direzionale', 
 '2020-01-01', 
 '20:00', 
 '7:00');

INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0006', 
 '15', 
 'Avellino', 
 'Montella', 
 '2020-01-01', 
 '20:00', 
 '7:00');


INSERT INTO `ipt`.`linea` (`id_linea`, `lunghezza`, `fermata_inizio`, `fermata_fine`, `data_attivazione`, `orario_chiusura`, `orario_apertura`)
 VALUES ( 'A0007', 
 '5', 
 'Napoli Centro', 
 'Mergellina', 
 '2020-01-01', 
 '20:00', 
 '7:00');



-- corsa
INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0001',
'A0001',
'ATTIVA',
'10:00',
'12:00',
'1');


INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0002',
'A0002',
'ATTIVA',
'14:00',
'18:00',
'1');



INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0003',
'A0003',
'FUORI SERVIZIO',
'14:00',
'18:00',
'0');



INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0004',
'A0004',
'NON ATTIVA',
'10:00',
'12:00',
'0');


INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0005',
'A0005',
'ATTIVA',
'10:00',
'12:00',
'1');



INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0006',
'A0006',
'ATTIVA',
'16:00',
'18:00',
'1');


INSERT INTO `ipt`.`corsa`
(`id_corsa`,
`id_linea`,
`stato`,
`ora_inizio`,
`ora_fine`,
`priorita`)
VALUES
(
'C0007',
'A0007',
'ATTIVA',
'10:00',
'13:00',
'1');



-- AVVISI
INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES ( 'AVV01', '2022-01-14', 'SCIOPERO', 'SI AVVISA LA GENTILE CLIENTELA CHE IL GIORNO 14/01 SARA'' INDETTO UNO SCIOPERO SINDACALE, CI SCUSIAMO PER IL DISAGIO', 'C0001', 'A0001');


INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV02',
 '2022-02-24', 
 'GUASTO MOTORE', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' SUBIRA'' DEI RITARDI A CAUSA DI UN GUASTO AL MOTORE, CI SCUSIAMO PER IL DISAGIO',
 'C0002', 
 'A0002');


INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV03',
 '2022-03-04', 
 'INCIDENTE', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' SUBIRA'' DEI RITARDI A CAUSA DI UN INCIDENTE, CI SCUSIAMO PER IL DISAGIO',
 'C0003', 
 'A0003');




INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV04',
 '2022-05-30', 
 'RITARDO', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' SUBIRA'' UN RITARDO PER PROBLEMI INTERNI, CI SCUSIAMO PER IL DISAGIO',
 'C0004', 
 'A0004');


INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV05',
 '2022-05-30', 
 'SOPPRESSIONE', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' SARA'' SOPPRESSA, CI SCUSIAMO PER IL DISAGIO',
 'C0005', 
 'A0005');

INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV06',
 '2022-11-01', 
 'RITARDO', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' PARTIRA'' CON 10 MINUTI DI RITARDO, CI SCUSIAMO PER IL DISAGIO',
 'C0005', 
 'A0005');


INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV07',
 '2022-04-11', 
 'MANCANZA PERSONALE', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' VERRA'' SOPPRESSA A CAUSA DI MANCANZA PERSONALE, CI SCUSIAMO PER IL DISAGIO',
 'C0006', 
 'A0006');

INSERT INTO `ipt`.`avviso_utenza` (`id_avviso_utenza`, `data`, `nome_avviso`, `testo`, `id_corsa`, `id_linea`) VALUES 
( 'AVV08',
 '2022-04-11', 
 'SCIOPERO', 
 'SI AVVISA LA GENTILE CLIENTELA CHE LA SEGUENTE CORSA'' VERRA'' SOPPRESSA A CAUSA DI UNO SCIOPERO, CI SCUSIAMO PER IL DISAGIO',
 'C0001', 
 'A0001');


-- biglietto
INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC01',
'2022-01-10',
'2022-01-11',
'2022-01-11',
'1.50',
'C0001',
'A0001',
'00001',
'00001');


INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC02',
'2022-02-10',
'2022-02-11',
'2022-02-11',
'1.50',
'C0002',
'A0002',
'00002',
'00002');


INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC03',
'2022-01-20',
'2022-01-21',
'2022-01-21',
'1.50',
'C0003',
'A0003',
'00003',
'00003');



INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC04',
'2022-04-25',
'2022-04-26',
'2022-04-26',
'1.50',
'C0004',
'A0004',
'00004',
'00004');



INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC05',
'2022-01-25',
'2022-01-26',
'2022-01-26',
'1.50',
'C0001',
'A0001',
'00001',
'00001');


INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC07',
'2022-10-25',
'2022-10-26',
'2022-10-26',
'1.50',
'C0002',
'A0002',
'00002',
'00002');


INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC08',
'2022-04-01',
'2022-04-02',
'2022-04-02',
'1.50',
'C0006',
'A0006',
'00006',
'00006');
INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC09',
'2022-07-01',
'2022-07-02',
'2022-07-02',
'1.50',
'C0003',
'A0003',
'00003',
'00003');



INSERT INTO `ipt`.`biglietto`
(`id_biglietto`,
`data_emissione`,
`data_scadenza`,
`data_timbro`,
`prezzo`,
`id_corsa`,
`id_linea`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'TIC10',
'2022-07-10',
'2022-07-11',
'2022-07-11',
'1.50',
'C0005',
'A0005',
'00005',
'00005');



-- abbonamento

INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB01',
'2022-01-11',
'2022-02-11',
'2022-01-12',
'25.00',
'00001',
'00001');





INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB02',
'2022-01-14',
'2022-02-14',
'2022-01-16',
'25.00',
'00002',
'00002');


INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB03',
'2022-02-20',
'2022-03-20',
'2022-02-22',
'25.00',
'00003',
'00003');




INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB04',
'2022-03-01',
'2022-04-01',
'2022-03-03',
'25.00',
'00004',
'00004');




INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB05',
'2022-06-01',
'2022-07-01',
'2022-06-03',
'25.00',
'00005',
'00005');





INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB06',
'2022-02-11',
'2022-07-11',
'2022-06-13',
'25.00',
'00006',
'00006');





INSERT INTO `ipt`.`abbonamento`
(`id_abbonamento`,
`data_emissione`,
`data_scadenza`,
`data_inizio_validita`,
`prezzo`,
`id_transazione`,
`id_cittadino`)
VALUES
(
'ABB07',
'2022-12-11',
'2023-01-11',
'2022-12-13',
'25.00',
'00006',
'00006');


-- CONVALIDA_ABBONAMENTO

INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB01',
'C0001',
'A0001',
'2022-01-04 10:00');




INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB01',
'C0001',
'A0001',
'2022-01-04 16:00');





INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB02',
'C0004',
'A0004',
'2022-11-14 11:00');


INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB02',
'C0004',
'A0005',
'2022-05-14 12:00');




INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB05',
'C0002',
'A0002',
'2022-07-24 10:00');




INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB06',
'C0005',
'A0005',
'2022-01-11 11:10');




INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB07',
'C0006',
'A0006',
'2022-01-11 11:10');



INSERT INTO `ipt`.`convalida_abbonamento`
(`id_abbonamento`,
`id_corsa`,
`id_linea`,
`data_convalida`)
VALUES
(
'ABB02',
'C0005',
'A0005',
'2022-04-13 15:40');

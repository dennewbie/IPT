SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE  IF EXISTS  `ipt`.`cittadino` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`transazione` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`linea` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`corsa` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`avviso_utenza` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`biglietto` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`abbonamento` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`convalida_abbonamento` CASCADE;
DROP TABLE  IF EXISTS  `ipt`.`salttable` CASCADE;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `ipt`.`salttable` (
  `salt` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`salt`)
);

CREATE TABLE `ipt`.`cittadino` (
  `id_cittadino` CHAR(5) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  `data_registrazione` DATE NOT NULL,
  `data_nascita` DATE NOT NULL,
  PRIMARY KEY (`id_cittadino`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `cittadinocol_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `check_date1` check (`data_nascita` < `data_registrazione`),
  CONSTRAINT `check_email1` check (REGEXP_LIKE(`email`, '^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$'))
  );

-- totalità rispetto a Cittadino espressa implicitamente dalla pk
CREATE TABLE `ipt`.`transazione` (
  `id_transazione` CHAR(5) NOT NULL,
  `id_cittadino` CHAR(5) NOT NULL,
  `costo` FLOAT NOT NULL,
  `metodo_pagamento` VARCHAR(45) NOT NULL,
  `data_pagamento` DATE NOT NULL,
  PRIMARY KEY (`id_transazione`, `id_cittadino`),
  INDEX `id_cittadino_idx` (`id_cittadino` ASC) VISIBLE,
  CONSTRAINT `id_cittadino` FOREIGN KEY (`id_cittadino`) REFERENCES `ipt`.`cittadino` (`id_cittadino`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `check_costo1` check (`costo` > 0),
  CONSTRAINT `check_metodo_pagamento1` check ( `metodo_pagamento` IN (
	'CREDIT CARD', 'Credit Card', 'credit card',  
    'CARTA DI CREDITO', 'Carta di Credito',
    'PAYPAL', 'Paypal', 'paypal', 'PayPal',
    'PHONE BILL', 'Phone Bill', 'phone bill',
    'Addebito numero di telefono'))    
);

CREATE TABLE `ipt`.`linea` (
  `id_linea` CHAR(5) NOT NULL,
  `lunghezza` INT NOT NULL,
  `fermata_inizio` VARCHAR(45) NOT NULL,
  `fermata_fine` VARCHAR(45) NOT NULL,
  `data_attivazione` DATE NOT NULL,
  `orario_chiusura` TIME NOT NULL,
  `orario_apertura` TIME NOT NULL,
  PRIMARY KEY (`id_linea`),
  CONSTRAINT `check_lunghezza` check (`lunghezza` > 0),
  CONSTRAINT `check_date10` check (`orario_apertura` < `orario_chiusura`),
  CONSTRAINT `check_fermata` check (`fermata_inizio` <> `fermata_fine`));

-- totalità rispetto a Linea espressa implicitamente dalla pk
CREATE TABLE `ipt`.`corsa` (
  `id_corsa` CHAR(5) NOT NULL,
  `id_linea` CHAR(5) NOT NULL,
  `stato` VARCHAR(45) NOT NULL,
  `ora_inizio` TIME NOT NULL,
  `ora_fine` TIME NOT NULL,
  `priorita` INT NOT NULL,
  PRIMARY KEY (`id_corsa`, `id_linea`),
  INDEX `id_linea_idx` (`id_linea` ASC) VISIBLE,
  CONSTRAINT `id_linea` FOREIGN KEY (`id_linea`) REFERENCES `ipt`.`linea` (`id_linea`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `check_date2` check (`ora_inizio` < `ora_fine`),
  CONSTRAINT `check_stato1` check (`stato` in (
	'ATTIVA', 'Attiva', 'attiva',
    'NON ATTIVA', 'Non Attiva', 'non attiva',
    'FUORI SERVIZIO', 'Fuori Servizio', 'fuori servizio'
  )),
  CONSTRAINT `check_priorita1` check (`priorita` >= 0),
  CONSTRAINT `check_priorita2` check (`priorita`<= 1)
);

-- totalità rispetto a corsa espressa
CREATE TABLE `ipt`.`avviso_utenza` (
  `id_avviso_utenza` CHAR(5) NOT NULL,
  `data` DATE NOT NULL,
  `nome_avviso` VARCHAR(45) NOT NULL,
  `testo` VARCHAR(500) NOT NULL,
  `id_corsa` CHAR(5) NOT NULL,
  `id_linea` CHAR(5) NOT NULL,
  PRIMARY KEY (`id_avviso_utenza`),
  INDEX `id_linea_idx` (`id_linea` ASC) VISIBLE,
  INDEX `id_corsa_idx` (`id_corsa` ASC) VISIBLE,
  CONSTRAINT `id_linea_avviso_utenza` FOREIGN KEY (`id_linea`) REFERENCES `ipt`.`linea` (`id_linea`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_corsa_avviso_utenza` FOREIGN KEY (`id_corsa`) REFERENCES `ipt`.`corsa` (`id_corsa`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- totalità rispetto a transazione espressa
CREATE TABLE `ipt`.`biglietto` (
  `id_biglietto` CHAR(5) NOT NULL,
  `data_emissione` DATE NOT NULL,
  `data_scadenza` DATE,
  `data_timbro` DATE,
  `prezzo` FLOAT NOT NULL,
  `id_corsa` CHAR(5),
  `id_linea` CHAR(5),
  `id_transazione` CHAR(5),
  `id_cittadino` CHAR(5),
  PRIMARY KEY (`id_biglietto`),
  INDEX `id_linea_idx` (`id_linea` ASC) VISIBLE,
  INDEX `id_corsa_idx` (`id_corsa` ASC) VISIBLE,
  INDEX `id_cittadino_idx` (`id_cittadino` ASC) VISIBLE,
  INDEX `id_transazione_idx` (`id_transazione` ASC) VISIBLE,
  CONSTRAINT `id_linea_biglietto` FOREIGN KEY (`id_linea`) REFERENCES `ipt`.`corsa` (`id_linea`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `id_corsa_biglietto` FOREIGN KEY (`id_corsa`) REFERENCES `ipt`.`corsa` (`id_corsa`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `id_cittadino_biglietto` FOREIGN KEY (`id_cittadino`) REFERENCES `ipt`.`transazione` (`id_cittadino`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `id_transazione_biglietto` FOREIGN KEY (`id_transazione`) REFERENCES `ipt`.`transazione` (`id_transazione`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `check_date3` check (`data_timbro` <= `data_scadenza`),
  CONSTRAINT `check_date4` check (`data_emissione` <= `data_timbro`),
  CONSTRAINT `check_date5` check (`data_emissione`< `data_scadenza`),
  CONSTRAINT `check_prezzo1` check (`prezzo` > 0)
);

-- totalità rispetto a transazione espressa
CREATE TABLE `ipt`.`abbonamento` (
  `id_abbonamento` CHAR(5) NOT NULL,
  `data_emissione` DATE NOT NULL,
  `data_scadenza` DATE NOT NULL,
  `data_inizio_validita` DATE NOT NULL,
  `prezzo` FLOAT NOT NULL,
  `id_transazione` CHAR(5),
  `id_cittadino` CHAR(5),
  PRIMARY KEY (`id_abbonamento`),
  INDEX `id_cittadino_idx` (`id_cittadino` ASC) VISIBLE,
  INDEX `id_transazione_idx` (`id_transazione` ASC) VISIBLE,
  CONSTRAINT `id_cittadino_biglietto_abbonamento` FOREIGN KEY (`id_cittadino`) REFERENCES `ipt`.`transazione` (`id_cittadino`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `id_transazione_biglietto_abbonamento` FOREIGN KEY (`id_transazione`) REFERENCES `ipt`.`transazione` (`id_transazione`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `check_date6` check (`data_emissione` < `data_scadenza`),
  CONSTRAINT `check_date7` check (`data_inizio_validita` >= `data_emissione`),
  CONSTRAINT `check_date8` check (`data_inizio_validita` < `data_scadenza`),
  CONSTRAINT `check_prezzo2` check (`prezzo` > 0)
);

-- totalità espressa per corsa e abbonamento in entrambi i lati sebbene non presente
CREATE TABLE `ipt`.`convalida_abbonamento` (
  `id_abbonamento` CHAR(5) NOT NULL,
  `id_corsa` CHAR(5) NOT NULL,
  `id_linea` CHAR(5) NOT NULL,
  `data_convalida` DATETIME NOT NULL,
  PRIMARY KEY (`id_abbonamento`, `id_corsa`, `id_linea`, `data_convalida`),
  INDEX `id_linea_idx` (`id_linea` ASC) VISIBLE,
  INDEX `id_corsa_idx` (`id_corsa` ASC) VISIBLE,
  CONSTRAINT `id_linea_convalida_abbonamento` FOREIGN KEY (`id_linea`) REFERENCES `ipt`.`corsa` (`id_linea`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_abbonamento` FOREIGN KEY (`id_abbonamento`) REFERENCES `ipt`.`abbonamento` (`id_abbonamento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_corsa_convalida_abbonamento` FOREIGN KEY (`id_corsa`) REFERENCES `ipt`.`corsa` (`id_corsa`) ON DELETE CASCADE ON UPDATE CASCADE
);

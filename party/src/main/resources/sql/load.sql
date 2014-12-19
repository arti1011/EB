
-- ===============================================================================
-- Jede SQL-Anweisung muss in genau 1 Zeile
-- Kommentare durch -- am Zeilenanfang
-- ===============================================================================


-- Nutzer
--
-- Attribute:
-- id email aktualisiert erzeugt hausnr nachname ort password plz 
-- strasse version vorname
--

INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('1','arti1011@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Arzner','p',1,'Till');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('2','mumi1012@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Mueller','p',1,'Michael');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('3','gupa1012@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Guenther-Schmidt','p',1,'Patrick');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('4','ride1011@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Richter','p',1,'Dennis');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('5','ride1011@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Rumpel','p',1,'Gerd');

-- Adresse

-- Attribute
-- id aktualisiert erzeugt hausnr ort plz strasse nutzer_fk
INSERT INTO ADRESSE (ID, AKTUALISIERT, ERZEUGT, HAUSNR, ORT, PLZ, STRASSE, NUTZER_FK) VALUES ('1','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Busenbach','76337','Carl-Benz-Str.','1');
INSERT INTO ADRESSE (ID, AKTUALISIERT, ERZEUGT, HAUSNR, ORT, PLZ, STRASSE, NUTZER_FK) VALUES ('2','01.08.2007 00:00:00','01.08.2007 00:00:00','36','Karlsruhe','76133','Durlacher Allee', '2');
INSERT INTO ADRESSE (ID, AKTUALISIERT, ERZEUGT, HAUSNR, ORT, PLZ, STRASSE, NUTZER_FK) VALUES ('3','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Pfalzdorf','12345','Pfaelzer-Weinschorle-Strasse','3');
INSERT INTO ADRESSE (ID, AKTUALISIERT, ERZEUGT, HAUSNR, ORT, PLZ, STRASSE, NUTZER_FK) VALUES ('4','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Malsch','34569','Suff-Strasse','4');
INSERT INTO ADRESSE (ID, AKTUALISIERT, ERZEUGT, HAUSNR, ORT, PLZ, STRASSE, NUTZER_FK) VALUES ('5','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Malsch','34569','Sauefer-Allee','5');

-- Freundschaft
--  
-- Attribute:
-- id owner_id friend_id
--
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('1', '2')
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('1', '3')
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('2', '4')
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('2', '3')

-- PARTY
--
-- Attribute:
--  id beschreibung datum titel uhrzeit adresse_fk veranstalter_fk
--
INSERT INTO PARTY(ID, BESCHREIBUNG, DATUM, TITEL, UHRZEIT, VERANSTALTER) VALUES('1','WG-Party zu Halloween','30.10.2014 08:00:00','Halloween-Party','20:00','1');

-- PARTYTEILNAHME
--
-- Attribute:
-- id istveranstalter status party_id teilnehmer_id
--
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('1','OFFEN','1','2');
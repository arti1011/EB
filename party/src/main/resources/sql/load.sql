
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
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('5','rumpel@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Rumpel','p',1,'Gerd');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('6','wurst@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Wurst','p',1,'Hans');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, NACHNAME, PASSWORD, VERSION, VORNAME) VALUES ('7','meier@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','Meier','p',1,'Klaus');



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
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('1', '6')
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('2', '4')
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('2', '3')
INSERT INTO FREUNDSCHAFT (OWNER_ID, FRIEND_ID) VALUES ('7', '1')


-- NEUE_PARTY
--
-- Attribute:
--  id beschreibung datum titel uhrzeit adresse_fk veranstalter_fk
--
-- Testfälle für offene(=zukünftige) und geschlossen(=Datum in der Vergangenheit) Parties
-- 
-- Veranstalter-ID=1
-- +++ zukünftig +++--
INSERT INTO PARTY(ID, BESCHREIBUNG, DATUM, TITEL, UHRZEIT, VERANSTALTER, LATITUDE, LONGITUDE) VALUES('1','WG-Party zu Halloween','30.10.2015 08:00:00','TEST STATUS OFFEN','20:00','1','49.015666','8.389606');

-- +++ abgeschlossen +++--
<<<<<<< HEAD
INSERT INTO PARTY(ID, BESCHREIBUNG, DATUM, TITEL, UHRZEIT, VERANSTALTER, LATITUDE, LONGITUDE) VALUES('2','Geburtstagsparty','21.05.2014 08:00:00','TEST STATUS ABGESCHLOSSEN','20:00','1','49.015666','8.389606');
=======
INSERT INTO PARTY(ID, BESCHREIBUNG, DATUM, TITEL, UHRZEIT, VERANSTALTER) VALUES('2','Geburtstagsparty','21.05.2014 08:00:00','TEST STATUS ABGESCHLOSSEN','20:00','1');
INSERT INTO PARTY(ID, BESCHREIBUNG, DATUM, TITEL, UHRZEIT, VERANSTALTER) VALUES('3','RATING TEST','21.05.2014 08:00:00','TEST RATING','20:00','2');
>>>>>>> branch 'master' of https://github.com/arti1011/Eb


-- PARTYTEILNAHME
--
-- Attribute:
-- id istveranstalter status party_id teilnehmer_id
--
-- PARTY_ID = 1
-- OFFEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('1','OFFEN','1','2');
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('2','OFFEN','1','3');

-- ABSAGEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('3','ABSAGE','1','4');

-- ZUSAGEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('4','ZUSAGE','1','5');

-- PARTY_ ID =2
-- OFFEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('5','OFFEN','2','2');
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('6','OFFEN','2','3');

-- ABSAGEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('7','ABSAGE','2','4');

-- ZUSAGEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('8','ZUSAGE','2','5');

-- PARTY_ID = 3
-- ZUSAGEN
INSERT INTO PARTYTEILNAHME(ID, STATUS, PARTY_ID, TEILNEHMER_ID) VALUES ('9','ZUSAGE','3','5');

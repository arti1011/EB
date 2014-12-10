
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

INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, HAUSNR, NACHNAME, ORT, PASSWORD, PLZ, STRASSE, VERSION, VORNAME) VALUES ('1','arti1011@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Arzner','Busenbach','p','76337','Carl-Benz-Str.',1,'Till');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, HAUSNR, NACHNAME, ORT, PASSWORD, PLZ, STRASSE, VERSION, VORNAME) VALUES ('2','mumi1012@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Mueller','Programmhausen','p','1337','Java-Avenue',1,'Michael');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, HAUSNR, NACHNAME, ORT, PASSWORD, PLZ, STRASSE, VERSION, VORNAME) VALUES ('3','gupa1012@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Guenther-Schmidt','Pfalzdorf','p','12345','Pfaelzer-Weinschorle-Strasse',1,'Patrick');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, HAUSNR, NACHNAME, ORT, PASSWORD, PLZ, STRASSE, VERSION, VORNAME) VALUES ('4','ride1011@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Richter','Malsch','p','34569','Suff-Strasse',1,'Dennis');
INSERT INTO NUTZER (ID, EMAIL, PICTURE_ID, AKTUALISIERT, ERZEUGT, HAUSNR, NACHNAME, ORT, PASSWORD, PLZ, STRASSE, VERSION, VORNAME) VALUES ('5','ride1011@test.de','999999999','01.08.2007 00:00:00','01.08.2007 00:00:00','4','Rumpel','Malsch','p','34569','Suff-Strasse',1,'Gerd');


-- Freundschaft
--  
-- Attribute:
-- id owner_id person_id
--
INSERT INTO FREUNDSCHAFT (ID, OWNER_ID, PERSON_ID) VALUES ('1', '1', '2')
INSERT INTO FREUNDSCHAFT (ID, OWNER_ID, PERSON_ID) VALUES ('2', '1', '3')
INSERT INTO FREUNDSCHAFT (ID, OWNER_ID, PERSON_ID) VALUES ('3', '2', '4')
INSERT INTO FREUNDSCHAFT (ID, OWNER_ID, PERSON_ID) VALUES ('4', '2', '3')
--MUESTRA EL LISTADO DE EVENTOS, (TALLRES (T), CURSOS LIBRES (C), Y CONVOCATORIAS(V) )
CREATE OR REPLACE VIEW LISTA_EVENTOS AS
select NOMBRE, DESCRIPCION, TIPO_TALLER
from	TALLER
UNION
SELECT NOMBRE, DESCRIPCION, 'V'
FROM CONVOCATORIA;


-- MUESTRA EL NOMBRE DE LOS USUARIOS Y SU RESPECTIVO USERNAME
CREATE OR REPLACE VIEW USUARIO_NOMBRE AS
SELECT 	NOMBRES||' '||APELLIDOS AS NOMBRE, USERNAME
FROM 	USUARIO


-- MUESTRA LOS ROLES DE CADA USUARIO.
CREATE OR REPLACE VIEW ROL_BY_USER AS
SELECT USUARIO.USERNAME, ROL.NOMBRE
FROM	USUARIO JOIN ROL_USUARIO 
	ON (USUARIO.ID_USUARIO = ROL_USUARIO.ID_USUARIO) JOIN ROL
	ON (ROL_USUARIO.ID_ROL = ROL.ID_ROL)


--MUESTRA LOS EVENTOS EN LOS QUE ESTÁ INSCRITO UN USUARIO.
CREATE OR REPLACE VIEW EVENTO_BY_USER AS
SELECT USUARIO.USERNAME, TALLER.NOMBRE, TALLER.DESCRIPCION, TALLER.TIPO_TALLER
from USUARIO JOIN USUARIO_TALLER
		ON(USUARIO.ID_USUARIO = USUARIO_TALLER.ID_USUARIO) JOIN TALLER
		ON(USUARIO_TALLER.ID_TALLER = TALLER.ID_TALLER)
union

SELECT USUARIO.USERNAME, CONVOCATORIA.NOMBRE, CONVOCATORIA.DESCRIPCION, 'V'
from USUARIO JOIN USUARIO_CONVOCATORIA
		ON(USUARIO.ID_USUARIO = USUARIO_CONVOCATORIA.ID_USUARIO) JOIN CONVOCATORIA
		ON(USUARIO_CONVOCATORIA.ID_CONVOCATORIA = CONVOCATORIA.ID_CONVOCATORIA)

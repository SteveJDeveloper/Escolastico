CREATE DEFINER=`root`@`localhost` PROCEDURE `usuario_matriculas`(IN `materia` INT)
    NO SQL
SELECT mat.fk_curso, us.nombre, COUNT(mat.fk_curso) AS conteo FROM matriculas as mat 
INNER JOIN usuarios as us ON mat.creado_por = us.nombre AND mat.fk_curso = materia
GROUP by us.nombres

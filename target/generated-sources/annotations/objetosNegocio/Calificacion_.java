package objetosNegocio;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objetosNegocio.Alumno;
import objetosNegocio.Materia;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-19T00:21:21")
@StaticMetamodel(Calificacion.class)
public class Calificacion_ { 

    public static volatile SingularAttribute<Calificacion, Materia> materia;
    public static volatile SingularAttribute<Calificacion, Alumno> alumno;
    public static volatile SingularAttribute<Calificacion, Integer> id;
    public static volatile SingularAttribute<Calificacion, Integer> nota;

}
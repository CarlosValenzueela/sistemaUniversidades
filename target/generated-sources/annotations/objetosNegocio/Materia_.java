package objetosNegocio;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objetosNegocio.Calificacion;
import objetosNegocio.MateriaPlandeestudio;
import objetosNegocio.MateriasSerializacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-19T00:21:21")
@StaticMetamodel(Materia.class)
public class Materia_ { 

    public static volatile SingularAttribute<Materia, String> clave;
    public static volatile ListAttribute<Materia, MateriasSerializacion> materiasSeriadas;
    public static volatile ListAttribute<Materia, Calificacion> calificaciones;
    public static volatile ListAttribute<Materia, MateriaPlandeestudio> planesDeEstudio;
    public static volatile SingularAttribute<Materia, Integer> id;
    public static volatile SingularAttribute<Materia, String> nombre;
    public static volatile ListAttribute<Materia, MateriasSerializacion> materias;

}
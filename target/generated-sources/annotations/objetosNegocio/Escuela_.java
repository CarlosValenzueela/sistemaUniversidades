package objetosNegocio;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objetosNegocio.EscuelaPlandeestudio;
import objetosNegocio.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-19T00:21:21")
@StaticMetamodel(Escuela.class)
public class Escuela_ { 

    public static volatile SingularAttribute<Escuela, String> clave;
    public static volatile SingularAttribute<Escuela, byte[]> logotipo;
    public static volatile ListAttribute<Escuela, EscuelaPlandeestudio> planesDeEstudio;
    public static volatile SingularAttribute<Escuela, Integer> id;
    public static volatile ListAttribute<Escuela, Usuario> usuarios;
    public static volatile SingularAttribute<Escuela, String> nombre;

}
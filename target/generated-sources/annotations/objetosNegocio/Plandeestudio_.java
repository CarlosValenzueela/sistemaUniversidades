package objetosNegocio;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import objetosNegocio.EscuelaPlandeestudio;
import objetosNegocio.MateriaPlandeestudio;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-19T00:21:21")
@StaticMetamodel(Plandeestudio.class)
public class Plandeestudio_ { 

    public static volatile ListAttribute<Plandeestudio, EscuelaPlandeestudio> escuela;
    public static volatile SingularAttribute<Plandeestudio, Integer> id;
    public static volatile SingularAttribute<Plandeestudio, String> nombre;
    public static volatile ListAttribute<Plandeestudio, MateriaPlandeestudio> materias;

}
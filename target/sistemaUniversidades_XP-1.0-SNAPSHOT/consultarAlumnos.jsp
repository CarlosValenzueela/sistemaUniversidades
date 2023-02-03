<%@page import="jwt.JWT"%>
<%@page import="java.util.ArrayList"%>
<%@page import="objetosNegocio.Alumno" %>
<%@page import="java.util.List" %>
<%@page import="dao.AlumnoJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    List<Alumno> listaAlumnos = new ArrayList<>();
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    AlumnoJpaController alumnoDAO = new AlumnoJpaController(factory);
    listaAlumnos = alumnoDAO.findAlumnoEntities();
    
    if (JWT.validarJWT(request, response)) {

    } else {
        session = request.getSession();
        session.removeAttribute("token");
        response.sendRedirect("inicioSesion.jsp");
        //request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
    }

%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar alumnos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.css"/>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    </head>



    <body>
        <article>
            <h1 class="text-center">Consultar Alumnos</h1>

            <hr>

            <hr>
            <br>
            <div class="table-responsive col-md">
                <table id="alumnosTabla" class="table table-striped border border-black border-3">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Matricula</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">CURP</th>
                            <th scope="col">Plan de estudio</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%                            for (int i = 0; i < listaAlumnos.size(); i++) {
                                out.print("<tr>"
                                        + "<td scope=\"row\">" + listaAlumnos.get(i).getCurp() + "</td>"
                                        + "<td>" + listaAlumnos.get(i).getNombre() + "</td>"
                                        + "<td>" + listaAlumnos.get(i).getMatricula() + "</td>"
                                        + "<td>" + listaAlumnos.get(i).getNumeroPlan() + "</td>"
                                        + "</tr>"
                                );
                            }
                        %>

                    </tbody>
                </table>
            </div>
            <br>
            <div class="m-3">
                <div class=" gap-6 mx-auto">
                    <button class="btn btn-danger pull-left" style="width:250px;" onclick="location.href = 'administrarAlumnos.jsp'">Cancelar</button>
                </div>
            </div>
        </article>
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.js"></script>
        <script src="js/app.js" charset="utf-8"></script>
    </body>



</html>

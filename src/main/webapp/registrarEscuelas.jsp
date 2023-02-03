<%@page import="java.util.Base64"%>
<%@page import="dao.EscuelaJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="jwt.JWT"%>
<%@page import="objetosNegocio.Escuela"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    EscuelaJpaController escuelaDAO = new EscuelaJpaController(factory);
    List<Escuela> listaEscuelas = escuelaDAO.findEscuelaEntities();

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
        <title>Registrar Escuelas</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    </head>

    <body>
        <article>
            <h1 class="text-center">Registrar Escuelas</h1>
            <hr />
            <hr />

            <div class="alert alert-warning alert-dismissible fade show container-fluid" role="alert" 
                 <%
                     if (request.getAttribute("errorEscuela") == null) {
                         out.print("hidden");
                     }
                 %>
                 >
                <h4 class="alert-heading">¡Ya existe una escuela registrada con los mismos datos!</h4>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>

            <div class="container-fluid">
                <div class="row justify-content-center">
                    <div class="col-auto">
                        <div class="border border-black border-3 mb-3">
                            
                            
                            <div class="m-3" class="form-group">
                                <form action="registrarEscuelas" method="POST" class="form">
                                    <p>
                                        <label class="form-label">Clave</label>
                                        <input class="form-control form-control-sm" type="text" name="clave" required="">
                                    </p>
                                    <p>
                                        <label for="formFileSm" class="form-label">Nombre</label>
                                        <input id="nom" class="form-control form-control-sm" type="text" name="nombre" required="">
                                    </p>
                                    <p>
                                        <label for="formFileSm" class="form-label">Logotipo</label>
                                        <input id="formFileSm" class="form-control form-control-sm" name="logotipo" type="file" accept="image/*" formenctype="multipart/form-data" required> 
                                    </p>
                                    <div class="m-3">
                                        <div class="d-grid gap-6 mx-auto">
                                            <button class="btn btn-secondary" type="reset">Limpiar</button>
                                        </div>
                                    </div>
                                    <div class="m-3">
                                        <div class="d-grid gap-6 mx-auto">

                                            <button id="btn_guardar" class="btn btn-primary" type="sumit" name="registrarEscuela" value="RegistrarEscuela">Registrar</button>
                                        </div>
                                    </div>

                                </form>
                                <div class="m-3">
                                    <div class="d-grid gap-6 mx-auto">
                                        <button class="btn btn-danger" onclick="location.href='menuPrincipal.jsp'">Cancelar</button>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>

                    <div class="table-responsive col-md">
                        <table class="table table-striped border border-black border-3" id="escuelasTabla">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="col">Clave</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Logotipo</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <%
                                        for (int i = 0; i < listaEscuelas.size(); i++) {
                                            if (i <= listaEscuelas.size()) {
                                                Escuela escuelaPrueba = listaEscuelas.get(i);
                                                byte[] photo = escuelaPrueba.getLogotipo();
                                                String bphoto = Base64.getEncoder().encodeToString(photo);
                                                request.setAttribute("bphoto", bphoto);
                                            }
                                            out.print("<tr>"
                                                    + "<td scope=\"row\">" + listaEscuelas.get(i).getClave() + "</td>"
                                                    + "<td>" + listaEscuelas.get(i).getNombre() + "</td>"
                                                    + "<td>" + "<img src=\"data:image/png;base64," + request.getAttribute("bphoto") + "\" width=\"100\"  height=\"100\" />" + "</td>"
                                                    + "</tr>"
                                            );

                                        }
                                    %>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </article>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.js"></script>
        <script src="js/app.js" charset="utf-8"></script>
    </body>
</html>

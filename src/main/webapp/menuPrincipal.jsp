<%@page import="jwt.JWT"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <title>Menú Principal</title>
        <link href="estilos/estiloLogin.css" rel="stylesheet" type="text/css"/>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <h4>
                                <a class="nav-link active" aria-current="page" href="#">
                                    <img src="img/iconoUsuario.png" alt="iconoUsuario" width="60" height="60" class="icono-menu"/>
                                    Bienvenido ${usuario}
                                </a>
                            </h4>
                        </li>   
                    </ul>
                </div>
                <form action="ControlServlet" method="POST">
                    <button name="accion" value="Salir" type="submit">
                        Cerrar sesión 
                        <img src="img/logout.png" alt="logout" width="30" height="30" class="icono-logout"/>
                    </button>
                </form>

            </div>
        </nav>
        <br>

        <div class="text-center">
            <img src="img/imgInicioSesion.png" alt="iconoEscuela" width="250" height="250"/>
            <br><br>
            <h3>Sistema para captura y administración de calificaciones</h3>
            <br>
            <form action="ControlServlet" method="POST">
                <button class="btn btn-secondary btn-lg botones-menu" type="submit" name="accion" value="administrarAlumnos"><b>Administrar alumnos</b></button>
            </form>       
            <form action="ControlServlet" method="POST">
                <button class="btn btn-secondary btn-lg btn-block botones-menu" type="submit" name="accion" value="administrarEscuelas"><b>Administrar escuelas</b></button>
            </form>      
            <form action="ControlServlet" method="POST">
                <button class="btn btn-secondary btn-lg botones-menu" type="submit" name="accion" value="administrarUsuarios"><b>Administrar usuarios</b></button>  
            </form>
            <form action="ControlServlet" method="POST">
                <button class="btn btn-secondary btn-lg botones-menu" type="submit" name="accion" value="administrarMaterias"><b>Administrar materias</b></button>  
            </form>

            <form action="ControlServlet" method="POST">
                <button class="btn btn-secondary btn-lg botones-menu" type="submit" name="accion" value="administrarPE"><b>Administrar planes de estudio</b></button>  
            </form>

        </div>                    

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>

</html>

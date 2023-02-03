<%@page import="jwt.JWT"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html>
    <head>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <title>Inicio de sesión</title>
        <link href="estilos/estiloLogin.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <div class="container mt-5 col-lg-7">
            <div class="card col-sm-12 border-light mb-3">
                <div class="cuadro-login">
                    <form class="form-sign" action="ControlServlet" method="POST">
                        <div class="form-group text-center">
                            <h1 class="card-title">Sistema de universidades</h1>
                            <img src="img/imgInicioSesion.png" alt="logo" width="170"/>
                            <br>
                            <h3 class="card-subtitle mb-2 text-muted">Sistema para captura y administración de calificaciones</h3>
                        </div>
                        <br>
                        <div class="form-group">
                            <label><b>Usuario:</b></label>
                            <input type="text" name="txtuser" class="form-control" placeholder="Introduzca su usuario">
                        </div>
                        <br>

                        <div class="form-group">
                            <label class="col-md-4 control-label"><b>Contraseña:</b></label>
                            <input id="password-field" type="password" name="txtpass" class="form-control" placeholder="Introduzca su contraseña">
                        </div>

                        <br>
                        <div class="form-group" style="text-align: center">
                            <input type="submit" name="accion" value="Ingresar" class="btn btn-primary btn-success btn-lg"/>
                            <br><br>
                            <h3 style="color:red">${error}</h3>
                        </div>
                    </form>
                        
                </div>
            </div>
        </div>    
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>

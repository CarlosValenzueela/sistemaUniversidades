<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Comparator"%>
<%@page import="dao.MateriaJpaController"%>
<%@page import="objetosNegocio.Materia"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jwt.JWT"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.UsuarioJpaController"%>
<%@page import="dao.EscuelaJpaController"%>
<%@page import="objetosNegocio.Usuario"%>
<%@page import="objetosNegocio.Escuela"%>


<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    List<Usuario> listaUsuarios = new ArrayList<>();
    List<Escuela> listaEscuelas = new ArrayList<>();
    List<Materia> listaMateriasSeleccionables = new ArrayList<>();

    if (JWT.validarJWT(request, response)) {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    MateriaJpaController materiaDAO = new MateriaJpaController(factory);
    listaMateriasSeleccionables = materiaDAO.findMateriaEntities();

     request.setAttribute("listaUsuarios", listaUsuarios);
    } else {
        session = request.getSession();
        session.removeAttribute("token");
        response.sendRedirect("inicioSesion.jsp");
        request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.css"/>
        <title>Administrar Planes de Estudio</title>
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    </head>
    <body>
        <h1 class="text-center">Administrar Planes de Estudio</h1>
        <hr />
        <hr />
        
        <div class="alert alert-warning alert-dismissible fade show container-fluid" role="alert" 
             <%
                 if (request.getAttribute("siErrorPlan") == null) {
                     out.print("hidden");
                 }
             %>
             <h4 class="alert-heading">${siErrorPlan}</h4>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>

        <div class="container mt-12 col-lg-12">
            <div class="col-lg-12 card mx-2">
                <div class="card-body">
                    <form class="form" action="ControlServlet" method="POST">
                        <div class="form-group">                    
                            <div class="mb-4 row">                         
                                <label class="col-sm-3 col-form-label" align="center">Nombre del plan de estudio:</label>
                                <div class="col-sm-8">
                                    <input minlength="1" maxlength="40" type="text" name="txtNombrePlan" class="form-control" placeholder="Introduzca el nombre del plan de estudio" required="">
                                </div>
                            </div>
                            <div class="col-lg-12 card table-responsive">
                                <div class="card-body" style="">
                                    <table class="table table-striped border border-black border-3" id="planesTabla">
                                        <thead class="table-dark">
                                            <tr>
                                                <th>Semestre</th>
                                                <th>Materia 1</th>
                                                <th>Materia 2</th>
                                                <th>Materia 3</th>
                                                <th>Materia 4</th>
                                                <th>Materia 5</th>
                                                <th>Materia 6</th>
                                                <th>Materia 7</th>
                                                <th>Materia 8</th>
                                            </tr>
                                        </thead>
                                        <tbody>       
                                            <tr name="semestre1">
                                                <td>Semestre 1</td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat1" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option> 
                                                            <%                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat2" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat3" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat4" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat5" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat6" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat7" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem1mat8" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr name="semestre2">
                                                <td>Semestre 2</td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat1" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat2" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat3" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat4" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat5" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat6" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat7" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem2mat8" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr name="semestre3">
                                                <td>Semestre 3</td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat1" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat2" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat3" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat4" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat5" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat6" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat7" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem3mat8" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr name="semestre4">
                                                <td>Semestre 4</td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat1" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat2" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat3" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat4" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat5" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat6" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat7" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem4mat8" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr name="semestre5">
                                                <td>Semestre 5</td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat1" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat2" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat3" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat4" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat5" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat6" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat7" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem5mat8" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>

                                            <tr name="semestre6">
                                                <td>Semestre 6</td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat1" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat2" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat3" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat4" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat5" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat6" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat7" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td>    
                                                    <div class="col-sm-auto">
                                                        <select class="form-select" name="sem6mat8" required onchange="seleccionarMateria(value, name);">
                                                            <option  selected="true" value="" >Seleccione materia</option>
                                                            <%
                                                                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {
                                                                    out.print("<option value=\"" + listaMateriasSeleccionables.get(i).getNombre() + "\">" + listaMateriasSeleccionables.get(i).getNombre() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </td>
                                            </tr>                                          

                                        </tbody>
                                    </table> 
                                </div>
                            </div>
                            <br>
                            <div class="mb-3 row">
                                <div class="col-sm-6" align="center">
                                    <button id="submitButton" type="submit" name="accion" value="crearPlan" class="btn btn-success">Crear plan</button>
                                </div>

                                <div class="col-sm-6" align="center">
                                    <button class="btn btn-danger" onclick="location.href = 'menuPrincipal.jsp'">Cancelar</button>
                                </div>

                            </div>

                        </div>           
                    </form>
                </div>
            </div>
        </div>

        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.js"></script>
        <script src="js/app.js" charset="utf-8"></script>  

        <script type="text/javascript">

                                        var semestre1 = [];
                                        var semestre2 = [];
                                        var semestre3 = [];
                                        var semestre4 = [];
                                        var semestre5 = [];
                                        var semestre6 = [];
                                        var listaMaterias = [];
                                        var serializacion = new Map();
                                        //Guarda las serializaciones de las materias en un Map
            <%
                for (int i = 0; i < listaMateriasSeleccionables.size(); i++) {

                    out.print("listaMaterias.push('" + listaMateriasSeleccionables.get(i).getNombre() + "');");
                    out.println();

                    if (!listaMateriasSeleccionables.get(i).getMaterias().isEmpty()) {

                        String materiasSeriadas = "[";
                        for (int j = 0; j < listaMateriasSeleccionables.get(i).getMaterias().size(); j++) {
                            materiasSeriadas += "'" + listaMateriasSeleccionables.get(i).getMaterias().get(j).getMateriaSeriada().getNombre() + "'";
                            if (listaMateriasSeleccionables.get(i).getMaterias().size() - j != 1) {
                                materiasSeriadas += ", ";
                            }
                        }
                        materiasSeriadas += "]";

                        out.print("serializacion.set('" + listaMateriasSeleccionables.get(i).getNombre() + "', " + materiasSeriadas + ");");
                        out.println();
                    }

                }
            %>


                                        function seleccionarMateria(value, name) {
                                            console.log(value);
                                            console.log(name);
                                            var sem1 = [document.getElementsByName("sem1mat1")[0].value, document.getElementsByName("sem1mat2")[0].value, document.getElementsByName("sem1mat3")[0].value, document.getElementsByName("sem1mat4")[0].value, document.getElementsByName("sem1mat5")[0].value, document.getElementsByName("sem1mat6")[0].value, document.getElementsByName("sem1mat7")[0].value, document.getElementsByName("sem1mat8")[0].value]
                                            var sem2 = [document.getElementsByName("sem2mat1")[0].value, document.getElementsByName("sem2mat2")[0].value, document.getElementsByName("sem2mat3")[0].value, document.getElementsByName("sem2mat4")[0].value, document.getElementsByName("sem2mat5")[0].value, document.getElementsByName("sem2mat6")[0].value, document.getElementsByName("sem2mat7")[0].value, document.getElementsByName("sem2mat8")[0].value]
                                            var sem3 = [document.getElementsByName("sem3mat1")[0].value, document.getElementsByName("sem3mat2")[0].value, document.getElementsByName("sem3mat3")[0].value, document.getElementsByName("sem3mat4")[0].value, document.getElementsByName("sem3mat5")[0].value, document.getElementsByName("sem3mat6")[0].value, document.getElementsByName("sem3mat7")[0].value, document.getElementsByName("sem3mat8")[0].value]
                                            var sem4 = [document.getElementsByName("sem4mat1")[0].value, document.getElementsByName("sem4mat2")[0].value, document.getElementsByName("sem4mat3")[0].value, document.getElementsByName("sem4mat4")[0].value, document.getElementsByName("sem4mat5")[0].value, document.getElementsByName("sem4mat6")[0].value, document.getElementsByName("sem4mat7")[0].value, document.getElementsByName("sem4mat8")[0].value]
                                            var sem5 = [document.getElementsByName("sem5mat1")[0].value, document.getElementsByName("sem5mat2")[0].value, document.getElementsByName("sem5mat3")[0].value, document.getElementsByName("sem5mat4")[0].value, document.getElementsByName("sem5mat5")[0].value, document.getElementsByName("sem5mat6")[0].value, document.getElementsByName("sem5mat7")[0].value, document.getElementsByName("sem5mat8")[0].value]
                                            var sem6 = [document.getElementsByName("sem6mat1")[0].value, document.getElementsByName("sem6mat2")[0].value, document.getElementsByName("sem6mat3")[0].value, document.getElementsByName("sem6mat4")[0].value, document.getElementsByName("sem6mat5")[0].value, document.getElementsByName("sem6mat6")[0].value, document.getElementsByName("sem6mat7")[0].value, document.getElementsByName("sem6mat8")[0].value]

                                            var semestre = document.getElementsByName(name)[0].parentNode.parentNode.parentNode.getAttribute("name");
                                            if (value == "") {
                                                console.log("Filtro Inicio: " + sem1);
                                                console.log("Semestre: " + semestre1);
                                                sem1 = semestre1.filter(function (el) {
                                                    return sem1.indexOf(el) < 0;
                                                });
                                                sem2 = semestre2.filter(function (el) {
                                                    return sem2.indexOf(el) < 0;
                                                });
                                                sem3 = semestre3.filter(function (el) {
                                                    return sem3.indexOf(el) < 0;
                                                });
                                                sem4 = semestre4.filter(function (el) {
                                                    return sem4.indexOf(el) < 0;
                                                });
                                                sem5 = semestre5.filter(function (el) {
                                                    return sem5.indexOf(el) < 0;
                                                });
                                                sem6 = semestre6.filter(function (el) {
                                                    return sem6.indexOf(el) < 0;
                                                });
                                                console.log("Filtro: " + sem1);
                                                if (semestre1.includes(sem1[0]) ||
                                                        semestre2.includes(sem2[0]) ||
                                                        semestre3.includes(sem3[0]) ||
                                                        semestre4.includes(sem4[0]) ||
                                                        semestre5.includes(sem5[0]) ||
                                                        semestre6.includes(sem6[0])) {

                                                    var index;
                                                    switch (semestre) {
                                                        case "semestre1":
                                                            index = semestre1.indexOf(sem1[0]);
                                                            console.log("antes: " + semestre1);
                                                            if (index > -1) {
                                                                semestre1.splice(index, 1);
                                                            }
                                                            console.log(semestre1);
                                                            var o = new Option(sem1[0], sem1[0]);
                                                            $(o).html(sem1[0]);
                                                            $('select').not(document.getElementsByName(name)).append(o);
                                                            break;
                                                        case "semestre2":
                                                            index = semestre2.indexOf(sem2[0]);
                                                            if (index > -1) {
                                                                semestre2.splice(index, 1);
                                                            }

                                                            var o = new Option(sem2[0], sem2[0]);
                                                            $(o).html(sem2[0]);
                                                            $('select').not(document.getElementsByName(name)).append(o);
                                                            break;
                                                        case "semestre3":
                                                            index = semestre3.indexOf(sem3[0]);
                                                            if (index > -1) {
                                                                semestre3.splice(index, 1);
                                                            }
                                                            var o = new Option(sem3[0], sem3[0]);
                                                            $(o).html(sem3[0]);
                                                            $('select').not(document.getElementsByName(name)).append(o);
                                                            break;
                                                        case "semestre4":
                                                            index = semestre4.indexOf(sem4[0]);
                                                            if (index > -1) {
                                                                semestre4.splice(index, 1);
                                                            }
                                                            var o = new Option(sem4[0], sem4[0]);
                                                            $(o).html(sem4[0]);
                                                            $('select').not(document.getElementsByName(name)).append(o);
                                                            break;
                                                        case "semestre5":
                                                            index = semestre5.indexOf(sem5[0]);
                                                            if (index > -1) {
                                                                semestre5.splice(index, 1);
                                                            }
                                                            var o = new Option(sem5[0], sem5[0]);
                                                            $(o).html(sem5[0]);
                                                            $('select').not(document.getElementsByName(name)).append(o);
                                                            break;
                                                        case "semestre6":
                                                            index = semestre6.indexOf(sem6[0]);
                                                            if (index > -1) {
                                                                semestre6.splice(index, 1);
                                                            }
                                                            var o = new Option(sem6[0], sem6[0]);
                                                            $(o).html(sem6[0]);
                                                            $('select').not(document.getElementsByName(name)).append(o);
                                                            break;
                                                    }
                                                }
                                            } else {

                                                if (serializacion.has(value)) {

                                                    var insertable = true;
                                                    console.log(serializacion.get(value));
                                                    for (var i = 0; i < serializacion.get(value).length; i++) {

                                                        console.log(serializacion.get(value)[i]);
                                                        switch (semestre) {
                                                            case "semestre1":
                                                                insertable = false;
                                                                break;
                                                            case "semestre2":
                                                                console.log(semestre1);
                                                                if (!semestre1.includes(serializacion.get(value)[i])) {
                                                                    insertable = false;
                                                                }
                                                                break;
                                                            case "semestre3":
                                                                if (!semestre1.includes(serializacion.get(value)[i]) &&
                                                                        !semestre2.includes(serializacion.get(value)[i])) {
                                                                    insertable = false;
                                                                }
                                                                break;
                                                            case "semestre4":
                                                                if (!semestre1.includes(serializacion.get(value)[i]) &&
                                                                        !semestre2.includes(serializacion.get(value)[i]) &&
                                                                        !semestre3.includes(serializacion.get(value)[i])) {
                                                                    insertable = false;
                                                                }
                                                                break;
                                                            case "semestre5":
                                                                if (!semestre1.includes(serializacion.get(value)[i]) &&
                                                                        !semestre2.includes(serializacion.get(value)[i]) &&
                                                                        !semestre3.includes(serializacion.get(value)[i]) &&
                                                                        !semestre4.includes(serializacion.get(value)[i])) {
                                                                    insertable = false;
                                                                }
                                                                break;
                                                            case "semestre6":
                                                                if (!semestre1.includes(serializacion.get(value)[i]) &&
                                                                        !semestre2.includes(serializacion.get(value)[i]) &&
                                                                        !semestre3.includes(serializacion.get(value)[i]) &&
                                                                        !semestre4.includes(serializacion.get(value)[i]) &&
                                                                        !semestre5.includes(serializacion.get(value)[i])) {
                                                                    insertable = false;
                                                                }
                                                                break;
                                                        }

                                                    }

                                                    if (insertable) {

                                                        switch (semestre) {
                                                            case "semestre1":
                                                                semestre1.push(value);
                                                                console.log("Semestre: " + semestre1);
                                                                console.log(3);
                                                                break;
                                                            case "semestre2":
                                                                semestre2.push(value);
                                                                break;
                                                            case "semestre3":
                                                                semestre3.push(value);
                                                                break;
                                                            case "semestre4":
                                                                semestre4.push(value);
                                                                break;
                                                            case "semestre5":
                                                                semestre5.push(value);
                                                                break;
                                                            case "semestre6":
                                                                semestre6.push(value);
                                                                break;
                                                        }

                                                        sem1 = semestre1.filter(function (el) {
                                                            console.log(semestre1);
                                                            console.log(1);
                                                            return sem1.indexOf(el) < 0;
                                                        });
                                                        sem2 = semestre2.filter(function (el) {
                                                            return sem2.indexOf(el) < 0;
                                                        });
                                                        sem3 = semestre3.filter(function (el) {
                                                            return sem3.indexOf(el) < 0;
                                                        });
                                                        sem4 = semestre4.filter(function (el) {
                                                            return sem4.indexOf(el) < 0;
                                                        });
                                                        sem5 = semestre5.filter(function (el) {
                                                            return sem5.indexOf(el) < 0;
                                                        });
                                                        sem6 = semestre6.filter(function (el) {
                                                            return sem6.indexOf(el) < 0;
                                                        });
                                                        console.log("Filtro: " + sem1);
                                                        if (semestre1.includes(sem1[0]) ||
                                                                semestre2.includes(sem2[0]) ||
                                                                semestre3.includes(sem3[0]) ||
                                                                semestre4.includes(sem4[0]) ||
                                                                semestre5.includes(sem5[0]) ||
                                                                semestre6.includes(sem6[0])) {

                                                            var index;
                                                            switch (semestre) {
                                                                case "semestre1":
                                                                    index = semestre1.indexOf(sem1[0]);
                                                                    if (index > -1) {
                                                                        semestre1.splice(index, 1);
                                                                    }
                                                                    console.log(semestre1);
                                                                    var o = new Option(sem1[0], sem1[0]);
                                                                    $(o).html(sem1[0]);
                                                                    $('select').not(document.getElementsByName(name)).append(o);
                                                                    break;
                                                                case "semestre2":
                                                                    index = semestre2.indexOf(sem2[0]);
                                                                    if (index > -1) {
                                                                        semestre2.splice(index, 1);
                                                                    }

                                                                    var o = new Option(sem2[0], sem2[0]);
                                                                    $(o).html(sem2[0]);
                                                                    $('select').not(document.getElementsByName(name)).append(o);
                                                                    break;
                                                                case "semestre3":
                                                                    index = semestre3.indexOf(sem3[0]);
                                                                    if (index > -1) {
                                                                        semestre3.splice(index, 1);
                                                                    }
                                                                    var o = new Option(sem3[0], sem3[0]);
                                                                    $(o).html(sem3[0]);
                                                                    $('select').not(document.getElementsByName(name)).append(o);
                                                                    break;
                                                                case "semestre4":
                                                                    index = semestre4.indexOf(sem4[0]);
                                                                    if (index > -1) {
                                                                        semestre4.splice(index, 1);
                                                                    }
                                                                    var o = new Option(sem4[0], sem4[0]);
                                                                    $(o).html(sem4[0]);
                                                                    $('select').not(document.getElementsByName(name)).append(o);
                                                                    break;
                                                                case "semestre5":
                                                                    index = semestre5.indexOf(sem5[0]);
                                                                    if (index > -1) {
                                                                        semestre5.splice(index, 1);
                                                                    }
                                                                    var o = new Option(sem5[0], sem5[0]);
                                                                    $(o).html(sem5[0]);
                                                                    $('select').not(document.getElementsByName(name)).append(o);
                                                                    break;
                                                                case "semestre6":
                                                                    index = semestre6.indexOf(sem6[0]);
                                                                    if (index > -1) {
                                                                        semestre6.splice(index, 1);
                                                                    }
                                                                    var o = new Option(sem6[0], sem6[0]);
                                                                    $(o).html(sem6[0]);
                                                                    $('select').not(document.getElementsByName(name)).append(o);
                                                                    break;
                                                            }
                                                        }


                                                        $('select').not(document.getElementsByName(name)).children('option[value="' + value + '"]').remove();
                                                    } else {
                                                        alert("La materia no se puede ingresar.\nNecesita que sus materias seriadas esten ingresadas en semestres anteriores.");
                                                        document.getElementsByName(name)[0].value = "";
                                                    }

                                                } else {

                                                    switch (semestre) {
                                                        case "semestre1":
                                                            semestre1.push(value);
                                                            console.log("Semestre: " + semestre1);
                                                            console.log(3);
                                                            break;
                                                        case "semestre2":
                                                            semestre2.push(value);
                                                            break;
                                                        case "semestre3":
                                                            semestre3.push(value);
                                                            break;
                                                        case "semestre4":
                                                            semestre4.push(value);
                                                            break;
                                                        case "semestre5":
                                                            semestre5.push(value);
                                                            break;
                                                        case "semestre6":
                                                            semestre6.push(value);
                                                            break;
                                                    }

                                                    sem1 = semestre1.filter(function (el) {
                                                        console.log(semestre1);
                                                        console.log(1);
                                                        return sem1.indexOf(el) < 0;
                                                    });
                                                    sem2 = semestre2.filter(function (el) {
                                                        return sem2.indexOf(el) < 0;
                                                    });
                                                    sem3 = semestre3.filter(function (el) {
                                                        return sem3.indexOf(el) < 0;
                                                    });
                                                    sem4 = semestre4.filter(function (el) {
                                                        return sem4.indexOf(el) < 0;
                                                    });
                                                    sem5 = semestre5.filter(function (el) {
                                                        return sem5.indexOf(el) < 0;
                                                    });
                                                    sem6 = semestre6.filter(function (el) {
                                                        return sem6.indexOf(el) < 0;
                                                    });
                                                    console.log("Filtro: " + sem1);
                                                    if (semestre1.includes(sem1[0]) ||
                                                            semestre2.includes(sem2[0]) ||
                                                            semestre3.includes(sem3[0]) ||
                                                            semestre4.includes(sem4[0]) ||
                                                            semestre5.includes(sem5[0]) ||
                                                            semestre6.includes(sem6[0])) {

                                                        var index;
                                                        switch (semestre) {
                                                            case "semestre1":
                                                                index = semestre1.indexOf(sem1[0]);
                                                                if (index > -1) {
                                                                    semestre1.splice(index, 1);
                                                                }
                                                                console.log(semestre1);
                                                                var o = new Option(sem1[0], sem1[0]);
                                                                $(o).html(sem1[0]);
                                                                $('select').not(document.getElementsByName(name)).append(o);
                                                                break;
                                                            case "semestre2":
                                                                index = semestre2.indexOf(sem2[0]);
                                                                if (index > -1) {
                                                                    semestre2.splice(index, 1);
                                                                }

                                                                var o = new Option(sem2[0], sem2[0]);
                                                                $(o).html(sem2[0]);
                                                                $('select').not(document.getElementsByName(name)).append(o);
                                                                break;
                                                            case "semestre3":
                                                                index = semestre3.indexOf(sem3[0]);
                                                                if (index > -1) {
                                                                    semestre3.splice(index, 1);
                                                                }
                                                                var o = new Option(sem3[0], sem3[0]);
                                                                $(o).html(sem3[0]);
                                                                $('select').not(document.getElementsByName(name)).append(o);
                                                                break;
                                                            case "semestre4":
                                                                index = semestre4.indexOf(sem4[0]);
                                                                if (index > -1) {
                                                                    semestre4.splice(index, 1);
                                                                }
                                                                var o = new Option(sem4[0], sem4[0]);
                                                                $(o).html(sem4[0]);
                                                                $('select').not(document.getElementsByName(name)).append(o);
                                                                break;
                                                            case "semestre5":
                                                                index = semestre5.indexOf(sem5[0]);
                                                                if (index > -1) {
                                                                    semestre5.splice(index, 1);
                                                                }
                                                                var o = new Option(sem5[0], sem5[0]);
                                                                $(o).html(sem5[0]);
                                                                $('select').not(document.getElementsByName(name)).append(o);
                                                                break;
                                                            case "semestre6":
                                                                index = semestre6.indexOf(sem6[0]);
                                                                if (index > -1) {
                                                                    semestre6.splice(index, 1);
                                                                }
                                                                var o = new Option(sem6[0], sem6[0]);
                                                                $(o).html(sem6[0]);
                                                                $('select').not(document.getElementsByName(name)).append(o);
                                                                break;
                                                        }
                                                    }
                                                    $('select').not(document.getElementsByName(name)).children('option[value="' + value + '"]').remove();
                                                }


                                            }
                                        }




        </script>

    </body>
</html>
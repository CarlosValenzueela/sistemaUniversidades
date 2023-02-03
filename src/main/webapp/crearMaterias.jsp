<%@page import="jwt.JWT"%>
<%@page import="objetosNegocio.Materia"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%  
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    List<Materia> materiasRegistrados = new ArrayList<Materia>();
    Boolean claveRepetida = false;
    Boolean nombreRepetida = false;

    if (JWT.validarJWT(request, response)) {
        List<Materia> lista = (List) session.getAttribute("listaMaterias");
        Boolean claveAux = (Boolean) session.getAttribute("claveRepetida");
        Boolean nombreAux = (Boolean) session.getAttribute("nombreRepetida");
        session.removeAttribute("listaMaterias");
        session.removeAttribute("claveRepetida");
        session.removeAttribute("nombreRepetida");

        if (lista != null) {
            materiasRegistrados = lista;
        }
        
        if (claveAux != null) {
            claveRepetida = claveAux;
        }
        
        if (nombreAux != null) {
            nombreRepetida = nombreAux;
        }
    } else {
        session = request.getSession();
        session.removeAttribute("token");
        response.sendRedirect("inicioSesion.jsp");
    }
%>

<!DOCTYPE html>
<html>

    <head>
        <title>Importar Materias</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.css"/>
        <link href="estilos/estilos.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    </head>

    <body>
        <article>
            <h1 class="text-center">Crear Materia</h1>

            <hr />

            <hr />

            <div class="d-flex flex-row">
                <div class="d-flex row align-items-center justify-content-center w-100">
                    <div class="col-sm-5">
                        <form action="ControlServlet" method="POST">
                            <div class="border border-black border-3 mb-3">
                                <div class="m-3">
                                    <label for="claveMateria" class="form-label">Clave</label>
                                    <input class="form-control form-control-sm col" id="claveMateria" name="clave" type="text" required pattern="^([1-6]){1}0([1-8]){1}$"/>
                                </div>
                                <div class="m-3">
                                    <label for="nombreMateria" class="form-label">Materia</label>
                                    <input class="form-control form-control-sm col" id="nombreMateria" name="nombre" type="text" required/>
                                </div>
                                <div class="m-3">
                                    <h6>Serialización</h6>
                                    <div class="p-auto border border-black border-2">
                                        <label for="materiaSeriada" class="form-label mx-3 mt-3">Materia</label>
                                        <div class="row col mx-3 align-items-center">
                                            <select id="materiaSeriada" class="form-select form-select-sm col" aria-label=".form-select-sm example">
                                                <option selected>Selecciona una materia</option>
                                                <% if (!materiasRegistrados.isEmpty()) {
                                                        for (int i = 0; i < materiasRegistrados.size(); i++) {
                                                            out.print("<option value=\"" + materiasRegistrados.get(i).getId() + "\">" + materiasRegistrados.get(i).getNombre() + " (" + materiasRegistrados.get(i).getClave() + ")</option>");
                                                        }
                                                    }
                                                %>
                                            </select>
                                            <button id="aniadirMateriaSeriada" class="btn btn-default col-sm-auto col-auto" type="button">
                                                <span class="fa fa-plus-square" aria-hidden="true"></span>
                                            </button>
                                        </div>
                                        <div id="materiasSeleccionadas" class="d-flex row m-3 border border-black border-2 row-cols-1 align-items-center">
                                            <div class="d-flex flex-grow-1">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="m-3">
                                    <div class="d-grid gap-6 mx-auto">
                                        <button id="boton_limpiar" class="btn btn-secondary" type="reset">Limpiar</button>
                                    </div>
                                </div>
                            </div>
                            <div class="m-3 mt-5">
                                <div class="d-grid gap-6 mx-auto">
                                    <button class="btn btn-primary" type="submit" name="accion" value="crearMateria">Crear Materia</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="table-responsive col h-100 d-flex flex-column">
                        <div class="row flex-grow-1">
                            <table id="alumnosTabla" class="table table-striped border border-black border-3">
                                <thead class="table-dark">
                                    <tr>
                                        <th scope="col">Clave</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Materias seriadas</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <% if (!materiasRegistrados.isEmpty()) {
                                            for (int i = 0; i < materiasRegistrados.size(); i++) {
                                                Materia a = (Materia) materiasRegistrados.get(i);
                                                out.print("<tr>"
                                                        + "<td scope=\"row\">" + a.getClave() + "</td>"
                                                        + "<td>" + a.getNombre() + "</td>"
                                                        + "<td>");

                                                if (a.getMaterias().size() != 0) {
                                                    for (int j = 0; j < a.getMaterias().size(); j++) {
                                                        if (!a.equals(a.getMaterias().get(j).getMateriaSeriada())) {
                                                            if (j == 0) {
                                                                out.print(a.getMaterias().get(j).getMateriaSeriada().getNombre());
                                                            } else {
                                                                out.print(", " + a.getMaterias().get(j).getMateriaSeriada().getNombre());
                                                            }
                                                        }

                                                    }
                                                } 
                                            }
                                        } else {
                                            out.print("<tr>"
                                                    + "<td scope=\"row\"></td>"
                                                    + "<td></td>"
                                                    + "<td></td>"
                                                    + "</tr>");
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <div class="m-3 row">
                            <div class="d-grid justify-content-md-end">
                                <button class="btn btn-danger me-md-2" onclick="location.href='menuPrincipal.jsp'">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>       
            <div class="alert alert-warning alert-dismissible fade show container-fluid" role="alert" <% if (!claveRepetida) {
                                    out.print("hidden");
                                } %>>
                <h4 class="alert-heading">¡Clave repetida!</h4>
                <p>La clave de la materia ingresada esta repetida.</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <div class="alert alert-warning alert-dismissible fade show container-fluid" role="alert" <% if (!nombreRepetida) {
                                    out.print("hidden");
                                } %>>
                <h4 class="alert-heading">¡Nombre repetida!</h4>
                <p>El nombre de la materia ingresada esta repetido.</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>    
        </article>

        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.24/datatables.min.js"></script>
        <script src="js/app.js" charset="utf-8"></script>

        <script>
                                    $(document).ready(function () {

                                        var wrapper = $("#materiasSeleccionadas");
                                        var add_button = $("#aniadirMateriaSeriada");
                                        var selectMaterias = document.getElementById("materiaSeriada");
                                        var reset_button = document.getElementById("boton_limpiar");

                                        var arrayMateriasSeriadas = [];

                                        $(add_button).click(function (e) {

                                            var indice = selectMaterias.selectedIndex;
                                            console.log(indice);

                                            if (indice > 0) {
                                                if (!arrayMateriasSeriadas.includes(selectMaterias.value)) {
                                                    e.preventDefault();

                                                    arrayMateriasSeriadas.push(selectMaterias.value);
                                                    console.log(arrayMateriasSeriadas);

                                                    $(wrapper).append('<div class="d-flex flex-grow-1"> ' +
                                                            '<button id="eliminarMateriaSeriada" class="btn btn-default col-sm-auto col-auto" type="button">' +
                                                            '<span class="fa fa-minus-square" aria-hidden="true"></span>' +
                                                            '</button>' +
                                                            '<div name="listaMateriasSeriadas" class="my-2 col flex-grow-1" value="' + selectMaterias.value + '" >' + selectMaterias.options.item(indice).text + '</div>' +
                                                            '<input name="listaMateriasSeriadas" value="' + selectMaterias.value + '" type="hidden" />' +
                                                            '</div>'); //add input box
                                                } else {
                                                    alert("La materia ya fue seleccionada");
                                                }

                                            } else {
                                                alert("Selecciona una materia para serializarla");
                                            }

                                        });

                                        $(wrapper).on("click", "#eliminarMateriaSeriada", function (e) {
                                            e.preventDefault();

                                            let valor = $(this).siblings("div").attr("value");
                                            console.log($(this).siblings("div"));
                                            console.log(valor);

                                            const index = arrayMateriasSeriadas.indexOf(valor);
                                            if (index > -1) {
                                                arrayMateriasSeriadas.splice(index, 1);
                                            }

                                            $(this).parent('div').remove();
                                        });

                                        reset_button.onclick = () => {
                                            const myNode = document.getElementById("materiasSeleccionadas");
                                            myNode.innerHTML = '';

                                            arrayMateriasSeriadas = [];
                                        };
                                    });
        </script>
    </body>

</html>

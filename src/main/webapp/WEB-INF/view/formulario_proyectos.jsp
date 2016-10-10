<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
	
    <title>Gobierno de México</title>
    
    <link href="../../../resources/css/bootstrap.css" rel="stylesheet"/>
    <link href="../../../resources/css/metisMenu.min.css" rel="stylesheet"/>
    <link href="../../../resources/css/sb-admin-2.css" rel="stylesheet"/>
    <link href="../../../resources/css/style.css" rel="stylesheet"/>
    <link href="../../../resources/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
</head>

<body>
	<div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top nav-white" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
            	<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"><img alt="Gobierno de México" src="../../../resources/img/logo_2.png" th:src="@{/resources/img/logo_2.png}"/></a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li class="divider"></li>
                        <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Cerrar Sesión</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

        </nav>
        <ol class="breadcrumb">
          <li class="pull-right"><i class="fa fa-home fa-fw"></i>  PROYECTOS</li>
          <li><a class="a_white" href="#">Inicio</a></li>
          <li class="active">Proyectos</li>
        </ol>
        
        <div class="navbar-default sidebar" role="navigation">    
        <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a class="a_gray" href="index.html"><i class="fa fa-home fa-fw"></i>  Inicio</a>
                    </li>                        
                    <li>
                        <a class="a_gray" href="#"><i class="fa fa-file-text fa-fw"></i>  Trámites<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="a_gray" href="formulario_proyectos.html"><i class="fa fa-usd fa-fw"></i>  Proyectos</a>
                            </li>
                            <li>
                                <a class="a_gray" href="formulario_gastos.html"><i class="fa fa-usd fa-fw"></i>  Gastos</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a class="a_gray" href="index.html"><i class="fa fa-info-circle fa-fw"></i>  Tutorial</a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->

        <div id="page-wrapper" th:fragment="wrapper">
        
        <!-- Messages -->
        	<div class="row" id="content">
                <div class="col-lg-12">
                	<div class="alert alert-success" role="alert">
                      	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      	                        <p><b>Alta exit&oacute;sa</b></p>
                    </div>
                    <div class="alert alert-warning" role="alert">
                      	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      	                        <p><b>Datos faltantes</b></p>
                    </div>
                    <div class="alert alert-danger" role="alert">
                      	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      	                        <p><b>Ha ocurrido un error</b></p>
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
        	<div class="row content_margin">
              <div class="col-lg-12">
                  <form method="POST" action="/proyectos/nuevo">
                  
                  	  <p>Registro de Proyectos</p>
                  
                      <div class="form-group">
                          <label">Nombre del proyecto:</label>
                          <input type="text" class="form-control" name="nombre"/>
                      </div>
                  
 <!--                    <div class="form-group">
                          <label">Secetaría</label>
                          <select class="form-control" name="groupId">
                              <option>Secretaria de la Educación Pública</option>
							  <option>Secretaría de Turismo</option>
                              <option>Secretaría de Relaciones Exteriores</option>
                              <option>Secretaria de Desarrollo Social</option>
                              <option>Secretaría de Agricultura, Ganadería, Desarrollo Rural Pesca y Alimentación</option>
                              <option>Secretaría de Gobierno</option>
                              <option>Secretaría de Hacienda y Crédito Público</option>
                              <option>Secretaría de la Defensa Nacional</option>
                              <option>Secretaría de Marina</option>
                              <option>Secretaría de Economía</option>
                              <option>Secretaría de la Función Pública</option>
                              <option>Secretaría de Comunicaciones y Transportes</option>
                              <option>Secretaría del Trabajo y Previsión Social</option>
                              <option>Secretaría de Medio Ambiente y Recursos Naturales</option>
                              <option>Secretaría de Energía</option>
                              <option>Secretaría de Salud</option>
                              <option>Secretaría de Desarrollo Agrario Territorial y Urbano</option>
                              <option>Secretaría de Cultura</option>
                          </select>
                      </div> -->

                      <div class="form-group">
                          <label>Descripción:</label>
                          <textarea class="form-control" rows="6" type="text" name="descripcion"></textarea>
                          <p class="help-block">250 caracteres</p>
                      </div>
                      
                      <div class="form-group">
                          <label>Recursos</label>
                          <select class="form-control" name="recurso">
                              <option value="1">Recurso Financiero</option>
                              <option value="2">Recurso Tecnológico</option>
                              <option value="3">Recurso Humano</option>
                              <option value="4">Recurso Materiales</option>
                              <option value="5">Recurso Administrativos</option>
                              <option value="6">Servicios de terceros</option>
                          </select>
                      </div>
                      
                      <div class="form-group">
                          <label">Monto:</label>
                          <input type="text" class="form-control" name="monto"/>
                      </div>
                      
                      <div class="form-group">
                          <label>Categoría</label>
                          <select class="form-control" name="categoria">
                              <option value="1">De Alto Impacto</option>
                              <option value="2">Impacto Moderado Alto</option>
                              <option value="3">Impacto Moderado Bajo</option>
                              <option value="4">De Bajo Impacto</option>
                          </select>
                      </div>
                      
                      <!-- Preguntas extras para clasificar el proyecto -->
                      <p>Clasificación de Proyecto</p>
                      <p>* Los siguientes campos son obligatorios.</p>
                      
                      <div class="form-group">
                          <label>La estabilidad del contexto general del proyecto (considerar los riesgos implícitos en el desarrollo del proyecto) es:</label>
                          <select class="form-control" name="estabilidad">
                              <option value="10">Muy Alta</option>
                              <option value="6">Alta</option>
                              <option value="4">Moderada</option>
                              <option value="2">Baja</option>
                          </select>
                      </div>

                      <div class="form-group">
                          <label>El número y variedad de relaciones del proyecto con otras entidades son:</label>
                          <select class="form-control" name="relaciones">
                              <option value="10">Muy Altas</option>
                              <option value="6">Altas</option>
                              <option value="4">Moderadas</option>
                              <option value="2">Bajas</option>
                          </select>
                      </div>

                      <div class="form-group">
                          <label>La magnitud de las implicaciones jurídicas, sociales o ambientales es:</label>
                          <select class="form-control" name="implicaciones">
                              <option value="10">Muy Alta</option>
                              <option value="6">Alta</option>
                              <option value="4">Moderada</option>
                              <option value="2">Baja</option>
                          </select>
                      </div>

                      <div class="form-group">
                          <label>El impacto financiero esperado (positivo o negativo) sobre las partes interesadas en el proyecto es:</label>
                          <select class="form-control" name="finanzas">
                              <option value="10">Muy Alto</option>
                              <option value="6">Alto</option>
                              <option value="4">Moderado</option>
                              <option value="2">Bajo</option>
                          </select>
                      </div>
 
                       <div class="form-group">
                          <label>La importancia estratégica del proyecto a nivel nacional o para las entidades relacionadas es:</label>
                          <select class="form-control" name="estrategia">
                              <option value="10">Muy Alta</option>
                              <option value="6">Alta</option>
                              <option value="4">Moderada</option>
                              <option value="2">Baja</option>
                          </select>
                      </div>
                                           
                      <button type="submit" class="btn btn-default btn-danger">Registrar</button>
                  </form>
              </div>

            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

        <div class="modal show_modal" id="loader" data-backdrop="static">
    		<div class="modal-dialog">
        		<div class="modal-content">
            		<div class="modal-body">
                		<img class="center-block" src="../../../resources/img/loading.gif" width="60" />
            		</div>
        		</div>
    		</div>
		</div>
    <script src="../../../resources/js/jquery.min.js"></script>
    <script src="../../../resources/js/form.js"></script>
    <script src="../../../resources/js/bootstrap.min.js"></script>
    <script src="../../../resources/js/metisMenu.min.js"></script>
    <script src="../../../resources/js/sb-admin-2.js"></script>
</body>
</html>

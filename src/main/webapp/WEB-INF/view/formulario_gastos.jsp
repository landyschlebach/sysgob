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
          <li class="pull-right"><i class="fa fa-home fa-fw"></i>  GASTOS</li>
          <li><a class="a_white" href="#">Inicio</a></li>
          <li class="active">Gastos</li>
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
                  <form method="POST" action="/gastos/nuevo">
                  
                  	  <p>Registro de Gastos</p>
                  
                      <div class="form-group">
                          <label">Nombre:</label>
                          <input type="text" class="form-control" name="nombre"/>
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
                      
                      <button type="submit" class="btn btn-default btn-danger">Guardar</button>
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

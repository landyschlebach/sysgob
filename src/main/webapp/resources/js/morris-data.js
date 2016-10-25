$(function() {
	event.preventDefault();
	$('#loader').modal('show');	
	
	$.ajax({
		url: 'http://localhost:8080/sysgob/presupuestos/datos',
		type: 'GET',
		dataType: 'JSON',
		success: function(data){
			Morris.Donut({
				  element: 'budgets_chart',
				  data: data
			});
		},
		error: function(){
			$('#loader').modal('hide');
			alert('No se ha podido cargar la grafica correctamente.');
		},
        complete: function(){
			$('#loader').modal('hide');
        }
	});
});

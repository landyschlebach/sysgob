$(document).ready(function() {
	event.preventDefault();
	$('#loader').modal('show');	
	
	$.ajax({
		url: '/dashboard/charts/sms',
		type: 'GET',
		dataType: 'JSON',
		success: function(data){
			Morris.Donut({
				  element: 'sms_chart',
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
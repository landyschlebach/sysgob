
	var monthNames = [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
	                   "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ];

	for (i = new Date().getFullYear(); i > 1900; i--){
		$('#year').append($('<option />').val(i).html(i));
	}
    
	for (i = 1; i < 13; i++){
		$('#month').append($('<option />').val(i).html(i));
	}
	
	for (i = 1; i < 32; i++){
		$('#day').append($('<option />').val(i).html(i));
	}
	
	updateNumberOfDays(); 
    
	$('#year, #month').on("change", function(){
		updateNumberOfDays(); 
	});

	function updateNumberOfDays(){
		$('#day').html('');
		month = $('#month').val();
		year = $('#year').val();
		days = daysInMonth(month, year);

		for(i=1; i < days+1 ; i++){
            $('#day').append($('<option />').val(i).html(i));
		}
	}

	function daysInMonth(month, year) {
		return new Date(year, month, 0).getDate();
	}

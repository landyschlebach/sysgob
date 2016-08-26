var questions;
var index;

$(document).ready(function() {
	event.preventDefault();
	$('#loader').modal('show');	
	//load questions
	$.ajax({
		url: '/dashboard/charts/survey/questions/',
		type: 'GET',
		dataType: 'JSON',
		success: function(data){
			questions = data;
			index = 0;
			loadQuestionChart(index);
		},
		error: function(){
			$('#loader').modal('hide');
			alert('Error al cargar las preguntas.');
		}
	});

	$('#previousQuestion').click(function() {
		$('#loader').modal('show');	
		
		prevIndex = index;
		var size = questions.length;
		if(prevIndex == 0) {
			prevIndex = size;
		}
		prevIndex --;
		loadQuestionChart(prevIndex);
	});	
	
	$('#nextQuestion').click(function() {
		$('#loader').modal('show');
		
		nextIndex = index;
		var size = questions.length;
		if(size == nextIndex+1) {
			nextIndex = -1;
		}
		nextIndex ++;
		loadQuestionChart(nextIndex);
	});
});

function loadQuestionChart(i){
	$('#survey_chart').empty();
	var question = questions[i];
	var quantities = [0,0,0,0];
	index = i;
	$('#question').text(question.text);

	$.ajax({
		url: '/dashboard/charts/survey/answers/' + question.questionId,
		type: 'GET',
		dataType: 'JSON',
		success: function(data){
			$.each(data, function(i, v) {
				if(v[0] == "A" ) {
					quantities[0] = v[1];
				} else if (v[0] == "B") {
					quantities[1] = v[1];
				} else if (v[0] == "C") {
					quantities[2] = v[1];
				} else if (v[0] == "O") {
					quantities[3] = v[1];
				}
			});

			Morris.Bar({
				 element: 'survey_chart',
				 barColors: ["#ee2d24", "#c9302c"],
				 data: [
				   { answer: ($.trim(question.optionA) != ""?question.optionA:'N/A'),	quantity: quantities[0]},
				   { answer: ($.trim(question.optionB) != ""?question.optionB:'N/A'),	quantity: quantities[1]},
				   { answer: ($.trim(question.optionC) != ""?question.optionC:'N/A'),	quantity: quantities[2]},
				   { answer: 'OTRAS', quantity: quantities[3]}
				 ],
				 xkey: 'answer',
				 ykeys: ['quantity'],
				 labels: ['Respuestas']
			});
		},
		error: function(){
			$('#loader').modal('hide');
			alert('Error al cargar las preguntas.');
		},
        complete: function(){
			$('#loader').modal('hide');
        }
	});
}


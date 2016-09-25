$(document).ready(function() {

	$(document.body).on("click", "button[type='submit']", function(event) {
        $(this).addClass('clicked');
    });
    $(document.body).on("submit", "form", function(event) {
    	var submit = $(this).find('button.clicked');
        var url = (submit.attr('data-url') ? submit.attr('data-url') : this.action);
    
            event.preventDefault();
            $('#loader').modal('show');
            
            var form = $(this);
            
            $.ajax({
                url: url,
                data: form.serialize(),
                success: function(data){
                	if(form.attr('data-pie-chart') == 'true') {
                		Morris.Donut({
	  	      				  element: 'sms_chart',
	  	      				  data: data
	  	      			});
                	} else if(form.attr('data-bar-chart') == 'true') {
            			Morris.Bar({
            				element: 'survey_chart',
            				data: data,
            				xkey: 'answer',
            				ykeys: 'quantity',
            				resize: true
            			});
                	} else {
                		$("#page-wrapper").replaceWith(data);
                	}
                },
        		error: function(){
        			$('#loader').modal('hide');
        		},
                complete: function(){
                    $('#loader').modal('hide');
                }
            });
        $(this).find('.clicked').removeClass('clicked');
    });
    
});

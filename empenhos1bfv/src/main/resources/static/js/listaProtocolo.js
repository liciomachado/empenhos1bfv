$(document).ready(function(){
    $('.selectProtocolo').formSelect();
  });

$("#protocolar").submit(function(evt){
	evt.preventDefault();
	
	var form = $('#protocolar')[0];
    var data = new FormData(form);
    
	$.ajax({
		method: "POST",
		url: "/notafiscal/protocolar",
		enctype: 'multipart/form-data',
		data: data,
		processData: false,
        contentType: false,
        cache: false,
		beforeSend: function(){
			$("#loader-form").addClass("loader").show();
		},
		success: function(data){
			$("#protocolar").each(function(){
				this.reset();
			});
			M.toast({html: 'OK! Protocolo realizado com sucesso', timeRemaining: 150000, displayLength: 100000, classes: 'green rounded'});
		},
		statusCode: {
			422: function(xhr){
				console.log('status error: ', xhr.status);
				var errors = $.parseJSON(xhr.responseText);
				$.each(errors, function(key, val){
					$("#"+key).addClass("is-invalid");
					$("#error-"+key).addClass("invalid-feedback").append("<span class='error-span'>"+val+"</span>")
				});
			}
		},
		error: function(xhr){
			console.log("> error: ",xhr.responseText);
			M.toast({html: 'Houve algum imprevisto ao protocolar, cheque as informações !', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
		},
		complete: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#protocolar").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
			setTimeout(function(){ location.reload(); }, 1000);
		}
	});
});

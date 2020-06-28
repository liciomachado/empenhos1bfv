$("#form-empresa").submit(function(evt){
	evt.preventDefault();
	
	var empresa = {};
	
	empresa.nome = $("#nomeEmpresa").val();
	empresa.contato = $("#contato").val();
	empresa.email = $("#email").val();
		
	console.log('empresa > ',empresa);
	
	$.ajax({
		method: "POST",
		url: "/empresas/save",
		data: empresa,
		beforeSend: function(){
			//remover as bordas vermelhas
			$("#nomeEmpresa").removeClass("is-invalid");
			$("#email").removeClass("is-invalid");
			$("#contato").removeClass("is-invalid");
			
			$(".helper-text").text("");
			//habilita o load
			$("#form-add-promo").hide();
			$("#loader-form").addClass("loader").show();
		},
		success: function(){
			$("#form-empresa").each(function(){
				this.reset();
			});
			$("#alert")
			.removeClass("alert alert-danger");		
			M.toast({html: 'OK! Empresa cadastrada com sucesso', timeRemaining: 15000, displayLength: 10000, classes: 'green rounded'});
			setTimeout(function(){ location.reload(); }, 1000);
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
			M.toast({html: 'Não foi possivel salvar esta empresa', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
		},
		complete: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#form-empresa").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		}
		
	});
	
});


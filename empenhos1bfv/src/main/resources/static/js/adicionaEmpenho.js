//SALVANDO E ENVIANDO EMAIL
$("#form-empenho").submit(function(evt){
	evt.preventDefault();
	
	var form = $('#form-empenho')[0];
    var data = new FormData(form);
    
	$.ajax({
		method: "POST",
		type: "POST",
		url: "/empenho/save",
		enctype: 'multipart/form-data',
		data: data,
        processData: false,
        contentType: false,
        cache: false,
		beforeSend: function(){
			$("#loader-form").addClass("loader").show();
		},
		success: function(){
			$("#form-empenho").each(function(){
				this.reset();
			});
			M.toast({html: 'OK! Empenho salvo com sucesso', timeRemaining: 150000, displayLength: 100000, classes: 'green rounded'});
		},
		statusCode: {
			422: function(xhr){
				console.log('status error: ', xhr.status);
				var errors = $.parseJSON(xhr.responseText);
				$.each(errors, function(key, val){
					$("#"+key).addClass("is-invalid");
					$("#error2-"+key).addClass("invalid-feedback").append("<span class='error-span'>"+val+"</span>")
				});
			}
		},
		error: function(xhr){
			console.log("> error: ",xhr.responseText);
			M.toast({html: 'Não foi possivel salvar o empenho', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
		},
		complete: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#form-empenho").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		}
		
	});
	
});
//SOMENTE SALVANDO EMPENHO
$("#cadastrar").click(function(evt){
	evt.preventDefault();
	
	var form = $('#form-empenho')[0];
    var data = new FormData(form);
    
	$.ajax({
		method: "POST",
		type: "POST",
		url: "/empenho/savesememail",
		enctype: 'multipart/form-data',
		data: data,
        processData: false,
        contentType: false,
        cache: false,
		beforeSend: function(){
			$("#loader-form").addClass("loader").show();
		},
		success: function(){
			$("#form-empenho").each(function(){
				this.reset();
			});
			M.toast({html: 'OK! Empenho salvo com sucesso', timeRemaining: 150000, displayLength: 100000, classes: 'green rounded'});
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
			M.toast({html: 'Não foi possivel salvar o empenho', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
			M.toast({html: 'Verifique se todos os campos estão corretos!', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
		},
		complete: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#form-empenho").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		}
		
	});
	
});
//busca nome da empresa
$(document).ready(function() {
	$("select[name='empresa']").change(function() {

		txtid = $("#search1").val();
		$.post("/empenho/functionempresa", {
			id : txtid
		}, function(data, status) {
			$("#email2").val(data);
		});
	});
		$('#valorTotal').val("");
	});
//faz o slide para cadastro de empresa
$(document).ready(function(e) { 
	$("#inputCadastro").click(function(e) { 
		if($(this).is(':checked')) { 
			$("#mostraCadastro").slideToggle(300);
		} else {
			$("#mostraCadastro").slideToggle(300);
			console.log("CheckBox desmarcado não faz nada."); 
		}
	}); 
});

$(document).ready(function(){
    $('.tap-target').tapTarget();
});

$("#menu").click(function(){
	$('.tap-target').tapTarget('open');
});
        
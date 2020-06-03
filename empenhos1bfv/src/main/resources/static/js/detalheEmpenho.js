$('#total').mask('###0.00', {reverse: true});
$(document).ready(function(){
    $('.tooltipped').tooltip();
});
$("#animate").click(function() {

	$("#contentDetalheEmpenho").animate(

			{"width": "100%"},

			1000);
});
jQuery(function($) {
	$("#contentDetalheEmpenhoNaoEntregue").animate(
			{"width": "100%"},
			1000);
}
);
jQuery(function($) {
	$("#contentDetalheEmpenhoEntregue").animate(
			{"width": "100%"},
			1000);
}
);
jQuery(function($) {
	$("#contentDetalheEmpenhoConcluido").animate(
			{"width": "100%"},
			1000);
}
);
$(document).ready(function(e) { 
	$("#adicionaObs").click(function(e) { 
		if($(this).is(':checked')) { 
			$("#txtObs").slideToggle(170);
		} else {
			$("#txtObs").slideToggle(170);
		}
	}); 
});
//slide mostrar cadastro de nota fiscal
$(document).ready(function(e) { 
	$("#adicionaNF").click(function(e) { 
		if($(this).is(':checked')) { 
			$("#txtNF").slideToggle(170);
		} else {
			$("#txtNF").slideToggle(170);
			console.log("CheckBox desmarcado não faz nada."); 
		}
	}); 
});
//modal de exclusao
$(document).ready(function(){
    $('#modal1').modal();
});
//modal de msg automatica
$(document).ready(function(){
    $('#modalEmail').modal();
});
//modal de pedido P.A
$(document).ready(function(){
    $('#modalPA').modal();
});
//modal de editar empenho
$(document).ready(function(){
    $('#modalEditEmpenho').modal();
});
//pega valor do id para excluir nota fiscal
jQuery('.modalDelete').on('click', function() {
	var id = $(this).attr('id');
    var data = 'id=' + id ;

    $("#idNotaFiscal").val(id);
});
//EXCLUSAO DE NOTA FISCAL
$("#modalExclusao").submit(function(evt){
	evt.preventDefault();
	
	var form = $('#modalExclusao')[0];
    var data = new FormData(form);
    
	$.ajax({
		method: "POST",
		url: "/notafiscal/excluir",
		enctype: 'multipart/form-data',
		data: data,
		processData: false,
        contentType: false,
        cache: false,
		beforeSend: function(){
			$("#loader-form").addClass("loader").show();
		},
		error: function(xhr){
			console.log("> error: ",xhr.responseText);
			M.toast({html: 'Não é possivel apagar esta nota! Contato o administrador.', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
		},
		success: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#modalExclusao").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		},
		complete: function(){
			setTimeout(function(){ location.reload(); }, 500);
		}
	});
});

//ENVIO DE MENSAGEM DE COBRANÇA PARA EMPRESA 
$("#modalMSGautomatica").submit(function(evt){
	evt.preventDefault();
	
	var form = $('#modalMSGautomatica')[0];
    var data = new FormData(form);
    
	$.ajax({
		method: "POST",
		url: "/empenho/emailpendencia",
		enctype: 'multipart/form-data',
		data: data,
		processData: false,
        contentType: false,
        cache: false,
		beforeSend: function(){
			$("#loader-form").addClass("loader").show();
		},
		error: function(xhr){
			console.log("> error: ",xhr.responseText);
			M.toast({html: 'Não é possivel realizar esta ação! Contato o administrador.', timeRemaining: 15000, displayLength: 10000, classes: 'red rounded'});
		},
		success: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#modalExclusao").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		},
		complete: function(){
			setTimeout(function(){ location.reload(); }, 500);
		}
	});
});

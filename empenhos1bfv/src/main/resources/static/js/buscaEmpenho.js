$(document).ready(function() {
		$("select[name='empenho']").change(function() {

			txtid = $("#search1").val();
			$.post("/empenho/functionjson", {
				id : txtid
			}, function(data, status) {
				var valores = data.split('/');

				$("#setEmpresa").val(valores[0]);
				$("#setValor").val(valores[1]);
			});
		});
	});
// setup tela de cadastro
$(function() {
	$(':input:enabled:visible:first').focus();

	if ($( ".numero" ).length){
		$(".numero").val($(".numero").val().replace(',', '').replace('.',','));
	}
	
	$('.sexo:first').prop('checked', true);
	
	//inicializa datepicker
	$( ".datepicker" ).datepicker({
	    dateFormat: 'dd/mm/yy',
	    changeYear: true,
	    changeMonth: true,
	    yearRange : '1900:2200',
	    gotoCurrent : true,
	    showButtonPanel: false            
	});

	//masks
	//contato
	$(".telefone").mask("(999) 9999-9999");
	$(".fax").mask("(999) 9999-9999");
	$(".movel").mask("(999) 99999-9999");
	//identificacao
	$(".cpf").mask("999.999.999-99");
	$(".reservista").mask("99-999-999999-9");
	//endereco
	$(".cep").mask("99999-999");
	//empresa
	$(".cnpj").mask("99.999.999/9999-99");
	$(".inscest").mask("99.999.999");
	$(".inscmun").mask("99.999");
	$(".nire").mask("999.999.999.99");
	//horario
	$(".hora").mask("99:99");

	//iniciliza tooltips
	$('[data-toggle="tooltip"]').tooltip();
});

// somente inteiros na caixa de texto
$(document).on("keypress", '.inteiro', function (event) {
	return ( event.which !== 8 && event.which !== 0 && (event.which < 48 || event.which > 57)) ? false : true ;
});

$(document).on("keypress", '.numero', function (event) {
	return ( event.which !== 8 && event.which !== 44 && event.which !== 0 && (event.which < 48 || event.which > 57)) ? false : true ;
});

//caracteres digitados em caixa alta
$(document).on("keyup", '.upper', function () {
	$(this).val($(this).val().toUpperCase());
});

// tratar os números antes de enviar para o bd
$(document).on("submit", ".frm", function () {
	 var numero = $(".numero").val().replace('.', '').replace(',','.');
	 $(".numero").val(numero);
});		


// Usado para carregar dinamicamente o select Detail a partir do select Master
// Deve ser passado o path para a url que vai retornar os dados para o select Detail ex: "/api/ambientes/unidade/"
// O parâmetro ID do Master deve ficar no fim da URL 
// O json retornado tem 2 campos value e text

function carregaSelect(elMaster, elDetail, path){
	$(elMaster).change(function(){
		//alert("carrega combo");
		var id = $(elMaster).val();
		var $el = $(elDetail);
		var url = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + path;
		
		$el.empty();
	    $.getJSON( url + id, function( data ) {
	    	  $.each( data, function( i, item ) {
	    		  $el.append($("<option></option>")
	    				     .attr("value", item.value).text(item.text));
	    	  });
	   	});
	});
}
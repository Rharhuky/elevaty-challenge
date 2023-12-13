function pesquisarPessoas() {
    const dataInicial = document.getElementById("dataInicial").value;
    const dataFinal = document.getElementById("dataFinal").value;

    if (dataInicial && dataFinal) {
    	let dateInicial = new Date(dataInicial);
    	let dateFinal = new Date(dataFinal);
    	
    	if(dateInicial.getTime() < dateFinal.getTime()){
    		const url = `../tratar-dados/index.html?dataInicial=${dataInicial}&dataFinal=${dataFinal}`;
        	window.location.href = url;	 // levar p/ o outro documento
    	}
    	else{
		alert("A data Inicial deve ser anterior à data final");    	
    	}
       
    } else {
        alert("Por favor, preencha ambas as datas antes de pesquisar.");
    }
}

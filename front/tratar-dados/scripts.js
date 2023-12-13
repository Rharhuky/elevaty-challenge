const APPLICATION_ENDPOINT_PERSONS = "http://localhost:8080/api/v1/persons";
const APPLICATION_ENDPOINT_FATURAS = "http://localhost:8080/api/v1/faturas/";
    // clique em pessoa
function handleRectangleClick(event) {
    
    // retirar selecao de pessoa
    const retangulos = document.querySelectorAll('.pessoa-container');
    retangulos.forEach(retangulo => {
        retangulo.classList.remove('selecionado');
    });

    // Aadd propriedade "selecionado"
    event.currentTarget.classList.add('selecionado');

    // detalhes de pessoas
    const pessoaSelecionada = event.currentTarget;
    
}

function exibirInformacoesAdicionais(pessoaSelecionadaEmail) {
    const informacoesAdicionaisContainer = document.getElementById("informacoes-adicionais");
    const endPointDeDetalhes =  APPLICATION_ENDPOINT_PERSONS +"/details/"+ pessoaSelecionadaEmail;
    console.log(endPointDeDetalhes);


    const pessoaResponse = fetch(endPointDeDetalhes)
    .then(resposta => {
        if(!resposta.ok){
            throw new Error("Erro na requisicao", resposta)
        }
        return resposta.json();
    })
    .then(data =>{
        console.log("Dados recebidos", data)

        const nomeCompleto = `${data.nome} ${data.sobrenome}` ;
        const dataNascimento = data.nascimento;
        const email = data.email;
        const telefone = data.celular;
        const id = data.id;
        const genero = data.genero;
        const enderecoPessoa = data.endereco
        const cartoes = data.cartoes;

        const informacoesHtml = `

        <p><strong>ID: </strong> ${id}</p>
        <p> <strong> Nome Completo: </strong> ${nomeCompleto} </p>        
        <p> <strong>Email: </strong>${email} </p>        
        <p> <strong>Nascimento: </strong> ${dataNascimento}</p>     
        <p><strong>Celular: </strong>${telefone}</p>       
        <p><strong>Genero: </strong> ${genero} </p>
        <p> 
            <strong>Endereço: </strong> 
            <div class="identacao-endereco">
                <p> <strong>Rua: </strong> ${enderecoPessoa.rua}                 </p>
                <p> <strong>Nome da Rua: </strong> ${enderecoPessoa.nomeRua}     </p>
                <p> <strong>Número: </strong> ${enderecoPessoa.numero}           </p>
                <p> <strong>Cidade: </strong> ${enderecoPessoa.cidade}           </p>
                <p> <strong>ZipCode: </strong> ${enderecoPessoa.zipCode}         </p>
                <p> <strong>País: </strong> ${enderecoPessoa.pais}               </p>
                <p> <strong>CountyCode: </strong> ${enderecoPessoa.countyCode}   </p>
                <p> <strong>Latitude: </strong> ${enderecoPessoa.latitude}       </p>
                <p> <strong>Longitude: </strong> ${enderecoPessoa.longitude}     </p>
            </div> 
        </p>
            <p>
            <strong> Cartões de Crédito: </strong> 
            <ul>
                ${cartoes.map( cartao => `
                    <li>
                        <strong>Tipo: </strong> ${cartao.tipo}                          <br>
                        <strong>Número: </strong> ${cartao.numero}                      <br>
                        <strong>Data de Vencimento: </strong> ${cartao.dataVencimento}  <br>
                        <strong>Titular: </strong> ${cartao.titular}                    <br>
                        <br>
                    </li>
                
                `).join('')}
                
            </ul>
            
        </p>        
    `;

    // adicionar ao container
    informacoesAdicionaisContainer.innerHTML = informacoesHtml;
    })
    .catch(error => {
        console.log("Erro na requis", error);
    });

}

    // detalhes de pessoa selecionada
function exibirDetalhes() {
    const pessoaSelecionada = document.querySelector('.pessoa-container.selecionado');
    const pessoaSelecionadaEmaiL = pessoaSelecionada.dataset.email;
    
    /*
    console.log(pessoaSelecionadaEmaiL);
    console.log(pessoaSelecionada);
   */

    if(pessoaSelecionadaEmaiL){  
        exibirInformacoesAdicionais(pessoaSelecionadaEmaiL);
    } else {
        alert("Selecione uma pessoa antes de exibir detalhes.");
    }
}

/* "Ouvinte" */
document.addEventListener("DOMContentLoaded", function () {
    
    const urlParams = new URLSearchParams(window.location.search);
        const dataInicial = urlParams.get('dataInicial');
        const dataFinal = urlParams.get('dataFinal');
    
        if (dataInicial && dataFinal) {
    
        const endpointUrl = `${APPLICATION_ENDPOINT_PERSONS}?dataInicio=${dataInicial}&dataFim=${dataFinal}`;
        const pessoasContainer = document.getElementById("pessoas-container");
    
        fetch(endpointUrl)
            .then(response => response.json())
            .then(data => {
               
                const pessoas = data;
    
                const pessoasHtml = pessoas.map((pessoa, index) => `
                    <div class="pessoa-container" id="pessoa-${index}" onclick="handleRectangleClick(event)"
                    data-genero="${pessoa.genero}" data-email="${pessoa.email}" data-celular="${pessoa.celular}">
                        <strong>${pessoa.nome} ${pessoa.sobrenome}</strong>
                        <p>Nascimento: ${pessoa.nascimento}</p>
                    </div>
                `).join("");
    
                //add ao container
                pessoasContainer.innerHTML = pessoasHtml;
            })
            .catch(error => {
                console.error("Erro ao obter dados:", error);
                pessoasContainer.innerHTML = "Erro ao obter dados.";
            });
    }
        else{
            console.error("Parâmetros de dataInicial e/ou dataFinal ausentes na URL.");
        }
    }
    );

function obterPdf() {

    const pessoa = document.querySelector(".pessoa-container.selecionado");
    if(pessoa){
        const email = pessoa.dataset.email;
        const endpoint = `${APPLICATION_ENDPOINT_FATURAS}${email}`;
        console.log("Requisicao GET " + endpoint);
        fetch(endpoint)
        .then(response => {
            if(!response.ok){
                throw new Error("Erro na requisicao", response)
            }
            return response.blob();
        })
        .then(blobData => {
            const blobUrl = URL.createObjectURL(blobData);
            window.open(blobUrl, "newInvoice");
        })
        .catch(error =>{
            console.error("Erro durante requisicao", error)

        })

    }
}

function removerPessoa() {
    const pessoaSelecionada = document.querySelector('.pessoa-container.selecionado');
    // remover info adicionais
    if (pessoaSelecionada) {
        pessoaSelecionada.remove();
        
        const informacoesAdicionaisContainer = document.getElementById("informacoes-adicionais");
        informacoesAdicionaisContainer.innerHTML = "";
    } else {
        alert("Selecione uma pessoa antes de remover.");
    }
}



function ordenarPessoas() {
   
    const retangulos = document.querySelectorAll('.pessoa-container');

    const arrayRetangulos = Array.from(retangulos);

    // Ordenar com base no strong
    arrayRetangulos.sort((a, b) => {
        const nomeA = a.querySelector('strong').textContent;
        const nomeB = b.querySelector('strong').textContent;
        return nomeA.localeCompare(nomeB);
    });

    // reordenar exibicao
    const pessoasContainer = document.getElementById('pessoas-container');
    arrayRetangulos.forEach(retangulo => pessoasContainer.appendChild(retangulo));
}




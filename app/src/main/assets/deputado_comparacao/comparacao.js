var nomeDeputado1 = "Chico Alencar"; // aqui tem q ter uma chamada a interface android pega nome
var idDeputado1 = '74171';
var nomeDeputado2 = "Abel";
var idDeputado2 = '178957';
var flagDeputado = 1; // flag que indica qual deputado estou fazendo a requisição
var gastosDeputado1; // não processado
var gastos1; // apos o processamento
var gastosDeputado2; // não processado
var gastos2; // após o processamento
var baseURL = 'https://dadosabertos.camara.leg.br/api/v2/deputados/';
var ano, mes;

function getTamanhoJanela(){
  var tamanho = { width: screen.width, height: screen.height};

  return tamanho;
}

function setup() {
  nomeDeputado1 = Android.getDeputadoComparacao().split(';')[1];
  idDeputado1 = Android.getDeputadoComparacao().split(';')[0];
  nomeDeputado2 = Android.getDeputadoComparacao().split(';')[3];
  idDeputado2 = Android.getDeputadoComparacao().split(';')[2];
  var now = new Date();
  var iptMes = document.getElementById('mes');
  iptMes.value = now.getFullYear() + "-" + (now.getMonth()-1);
  trocou();
}

function trocou(){
  var iptMes = document.getElementById('mes');
  ano = iptMes.value.split('-')[0];
  mes = iptMes.value.split('-')[1];
  gastosDeputado1 = [];
  gastosDeputado2 = [];
  // fazer chamada para interface android para pegar os dados do deputado e salvar na variavel
  var url = baseURL + idDeputado1 + '/despesas?ano=' + ano + '&mes=' + mes + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=100';
  //console.log(url);
  flagDeputado = 1;
  Android.inicia();
  getDataDep1(url);
}

function getDataDep1(url){
  console.log("dep 1  get data");
  console.log(url);
  loadJSON(url, gotData, errorCallbackDep1);
}

function errorCallbackDep1(e){
  alert("Dados insuficientes para análise do deputado " + nomeDeputado1 + ", ou servidor da câmara indisponível!");
  var url = baseURL + idDeputado2 + '/despesas?ano=' + ano + '&mes=' + mes + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=100';
  flagDeputado = 2;
  getDataDep2(url);
}

function getDataDep2(url){
  console.log("dep 2  get data");
  console.log(url);
  loadJSON(url, gotData, errorCallbackDep2);
}


function errorCallbackDep2(e){
  alert("Dados insuficientes para análise do deputado " + nomeDeputado2 + ", ou servidor da câmara indisponível!");
  processaDados();
}

function gotData(data) {
  if(flagDeputado == 1){
    // armazena as despesas do deputado 1 no array gastosDeputado1
      for(var d in data.dados)
        gastosDeputado1.push(data.dados[d]);

      var next='', self='', last ='';
      for(var link in data.links){
        switch (data.links[link].rel){
          case 'self': self = data.links[link].href; break;
          case 'next': next = data.links[link].href; break;
          case 'last': last = data.links[link].href; break;
        }
      }
      //console.log(next);

      if (self != last) getDataDep1(next)
      else {
        flagDeputado = 2; //trocar o deputado para fazer a requisição das despesas do segundo
        var url2 = baseURL + idDeputado2 + '/despesas?ano=' + ano + '&mes=' + mes + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=100';
        getDataDep2(url2);
      }
  }else{
    // já está no segundo deputados
    for(var d in data.dados)
      gastosDeputado2.push(data.dados[d]);

    var next='', self='', last ='';
    for(var link in data.links){
      switch (data.links[link].rel){
        case 'self': self = data.links[link].href; break;
        case 'next': next = data.links[link].href; break;
        case 'last': last = data.links[link].href; break;
      }
    }

    if (self != last) getDataDep2(next)
    else {
      //significa que já tenho as despesas dos dois deputados logo processar os dados
      processaDados();
    }
  }
}

function processaDados(){
  //console.log(gastosDeputado1);
  //console.log(gastosDeputado2);

  var aux = gastosDeputado1.map(function (d){
    return {nome: d.tipoDespesa, tamanho : d.valorLiquido}
  });
  //console.log("Despesas");
  //console.log(despesas);
  //console.log("aux");
  //console.log(aux);

  gastos1 = [], gastos2 = [];

  for(var key in aux){
    var soma = 0;
    var tem = 0;
    for(var g in gastos1){
      if(gastos1[g].nome === aux[key].nome){
        gastos1[g].tamanho +=aux[key].tamanho;
        gastos1[g].valor +=aux[key].tamanho;
        tem = 1;
        break;
      }
    }
    if(tem === 0){
      gastos1.push({nome: aux[key].nome, tamanho: aux[key].tamanho, valor: aux[key].tamanho});
    } else tem=0;
  }

  aux = gastosDeputado2.map(function (d){
    return {nome: d.tipoDespesa, tamanho : d.valorLiquido}
  });
  for(var key in aux){
     soma = 0;
     tem = 0;
    for(var g in gastos2){
      if(gastos2[g].nome === aux[key].nome){
        gastos2[g].tamanho +=aux[key].tamanho;
        gastos2[g].valor +=aux[key].tamanho;
        tem = 1;
        break;
      }
    }
    if(tem === 0){
      gastos2.push({nome: aux[key].nome, tamanho: aux[key].tamanho, valor: aux[key].tamanho});
    } else tem=0;
  }
  //console.log("gastos");
  //console.log(gastos1);
  //console.log(gastos2);

  gastos1 = colapsaCategorias(gastos1);
  gastos2 = colapsaCategorias(gastos2);
  drawChart();
}

function colapsaCategorias(aux){
  // junta as categorias
  var newgastos = [
    {nome : "Transporte", valor : 0.0, tamanho : 0.0},
    {nome : "Alimentação", valor : 0.0, tamanho : 0.0},
    {nome : "Manutenção de Escritório", valor : 0.0, tamanho : 0.0},
    {nome : "Assinatura de Publicação", valor : 0.0, tamanho : 0.0},
    {nome : "Apoio de Atividade Parlamentar", valor : 0.0, tamanho : 0.0},
    {nome : "Telefonia", valor : 0.0, tamanho : 0.0},
    {nome : "Serviço Postal", valor : 0.0, tamanho : 0.0},
    {nome : "Hospedagem", valor : 0.0, tamanho : 0.0},
    {nome : "Serviços de Segurança", valor : 0.0, tamanho : 0.0},
    {nome : "Participações em Cursos ou Palestras", valor : 0.0, tamanho : 0.0},
    {nome : "Auxílio Moradia", valor : 0.0, tamanho : 0.0}
  ];

  for(var g in aux){
    var cat = aux[g].nome.toLowerCase();
    var index;
    switch (cat) {
      case (cat.match(/passagens/) || cat.match(/fretamento/) || cat.match(/bilhete/)
            || cat.match(/estacionamento/) || cat.match(/lubrificante/) || {}).input:
        index = 0;
        //console.log('transporte');
        break;

      case (cat.match(/alimentação/) || {}).input:
        index = 1;
        //console.log('alimentação');
        break;

      case (cat.match(/imóveis/) || cat.match(/escritório/) || cat.match(/condomínio/)
              || cat.match(/iptu/) || cat.match(/energia/) || cat.match(/móveis/)
              || cat.match(/informática/) || cat.match(/internet/) || cat.match(/tv/)
              || cat.match(/software/) || {}).input:
        index = 2;
        //console.log('Manutenção de Escritório');
        break;
      case (cat.match(/assinatura/) || {}).input:
        index = 3;
        //console.log('Assinatura de Publicação');
        break;

      case (cat.match(/divulgação/) || cat.match(/contratação/) || cat.match(/consultorias/)|| {}).input:
        index = 4;
        //console.log('Apoio de Atividade Parlamentar');
        break;

      case (cat.match(/telefonia/) || {}).input:
        index = 5;
        //console.log('Telefonia');
        break;
      case (cat.match(/postais/) || {}).input:
        index = 6;
        //console.log('Serviço Postal');
        break;

      case (cat.match(/hospedagem/) || {}).input:
        index = 7;
        //console.log('Hospedagem');
        break;

      case (cat.match(/segurança/) || {}).input:
        index = 8;
        //console.log('Serviços de Segurança');
        break;
      case (cat.match(/cursos/) || cat.match(/palestras/) || {}).input:
        index = 9;
        //console.log('Participação em cursos ou palestras');
        break;
      case (cat.match(/moradia/) || {}).input:
        index = 10;
        //console.log('Auxílio Moradia');
        break;
    }
    newgastos[index].valor += aux[g].valor;
    newgastos[index].tamanho += aux[g].tamanho;
  }

  for(var g in newgastos){
    if(newgastos[g].valor == 0.0)
      newgastos[g] = false;
  }

return newgastos.filter(filtraCategoriaValor0);
}

function filtraCategoriaValor0(d) {
    return d != false;
}

function drawChart() {
  var valores1=[], valores2 = [];
  var labels1=[], labels2 = [];

  for (var i in gastos1){
    valores1.push(gastos1[i].valor);
    labels1.push(gastos1[i].nome);
  }

  for (var i in gastos2){
    valores2.push(gastos2[i].valor);
    labels2.push(gastos2[i].nome);
  }

   var tamanho = getTamanhoJanela();

   var data = [{
          type: 'bar',
          y: valores1,
          x: labels1,
          name: nomeDeputado1,
          orientation : "v",
          hoverinfo: 'label+value'
        },{
          type: 'bar',
          name: nomeDeputado2,
          y: valores2,
          x: labels2,
          orientation : "v",
          hoverinfo: 'label+value'
        }];

  var layout = {
    showlegend: false,
    width: tamanho.width,
    height: tamanho.height,
    yaxis : {fixedrange : true}, xaxis: {fixedrange : false},
    colorway: ["#191970", "#4682B4"]
  };

  //console.log(data); console.log(layout);

  Plotly.newPlot('donutchart', data, layout);//, {responsive : true});
  Android.finalizar();
}

var nomeDeputado = "Abel"; // aqui tem q ter uma chamada a interface android pega nome
var idDeputado = '178957';
var gastosDeputado; // não processado
var gastos; // apos o processamento
var total;
var baseURL = 'https://dadosabertos.camara.leg.br/api/v2/deputados/';
var ano, mes;

function setup() {
  var iptMes = document.getElementById('mes');
  var now = new Date();
  iptMes.value = now.getFullYear() + "-" + (now.getMonth()-1);
  nomeDeputado = Android.getDadosDeputado().split(';')[0];
  idDeputado = Android.getDadosDeputado().split(';')[1];

  //console.log(nomeDeputado1 + " id " + idDeputado1);
  //console.log(idDeputado);
  gastos = [];
  var tamanho = getTamanhoJanela();
  var iptMes = document.getElementById('mes');
    ano = iptMes.value.split('-')[0];
    mes = iptMes.value.split('-')[1];
    gastosDeputado = [];
    //console.log(idDeputado);
    gastos = [];
    // fazer chamada para interface android para pegar os dados do deputado e salvar na variavel
    var url = baseURL + idDeputado + '/despesas?ano=' + ano + '&mes=' + mes + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=100';
    //console.log(url);
    //Android.inicia();
    getData(url);
}

function trocou(){
  var iptMes = document.getElementById('mes');
  ano = iptMes.value.split('-')[0];
  mes = iptMes.value.split('-')[1];
  gastosDeputado = [];
  //console.log(idDeputado);
  gastos = [];
  // fazer chamada para interface android para pegar os dados do deputado e salvar na variavel
  var url = baseURL + idDeputado + '/despesas?ano=' + ano + '&mes=' + mes + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=100';
  //console.log(url);
  Android.inicia();
  getData(url);
}

function getData(url){
  loadJSON(url, gotData, errorCallback);
}

function errorCallback(e){
  alert("Dados insuficientes para análise do deputado, ou servidor da câmara indisponível!");
  processaDados();
}

function gotData(data) {
  if(data == null) console.log("pau");
    for(var d in data.dados)
      gastosDeputado.push(data.dados[d]);

    var next='', self='', last ='';
    for(var link in data.links){
      switch (data.links[link].rel){
        case 'self': self = data.links[link].href; break;
        case 'next': next = data.links[link].href; break;
        case 'last': last = data.links[link].href; break;
      }
    }

    if (self != last) getData(next)
    else {
      processaDados();
    }
}


function processaDados(){
  total=0;
  //console.log(gastosDeputado);
  var aux = gastosDeputado.map(function (d){
    return {nome: d.tipoDespesa, tamanho : d.valorLiquido}
  });
  //console.log(aux);
  gastos = [];

  for(var key in aux){
    var soma = 0;
    var tem = 0;
    for(var g in gastos){
      if(gastos[g].nome === aux[key].nome){
        gastos[g].tamanho +=aux[key].tamanho;
        gastos[g].valor +=aux[key].tamanho;
        total += aux[key].tamanho;
        tem = 1;
        break;
      }
    }
    if(tem === 0){
      gastos.push({nome: aux[key].nome, tamanho: aux[key].tamanho, valor: aux[key].tamanho});
      total += aux[key].tamanho;
    } else tem=0;
  }

  colapsaCategorias();
  //console.log(total);
  drawChart();
}

function colapsaCategorias(){
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

  for(var g in gastos){
    var cat = gastos[g].nome.toLowerCase();
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
    newgastos[index].valor += gastos[g].valor;
    newgastos[index].tamanho += gastos[g].tamanho;
  }

  for(var g in newgastos){
    if(newgastos[g].valor == 0.0)
      newgastos[g] = false;
  }

  gastos = newgastos.filter(filtraCategoriaValor0);
}

function filtraCategoriaValor0(d) {
    return d != false;
}

function drawChart() {
  var valores=[];
  var labels=[];

  for (var i in gastos){
    valores.push(gastos[i].valor);
    labels.push(gastos[i].nome);
  }

  var tamanho = getTamanhoJanela();

  var data = [{
    values: valores,
    labels: labels,
    title: "R$"+total.toLocaleString('pt-br', {minimumFractionDigits: 2}),
    hoverinfo: 'label+value',
    hole: .40,
    type: 'pie'
  }];

  var layout = {
    showlegend: true,
    width: tamanho.width + 50,
    height: tamanho.height + 50,
    legend: {
      "orientation": "h"
    },
    colorway: ["#191970", "#0000CD", "#6495ED", "#00BFFF", "#87CEEB", "#4682B4", "#708090", "#00FFFF", "#008B8B"]
  };

  Plotly.newPlot('donutchart', data, layout, {responsive : true});

  Android.finalizar();
}

function getTamanhoJanela(){
  var tamanho = { width: screen.width, height: screen.height};

  return tamanho;
}

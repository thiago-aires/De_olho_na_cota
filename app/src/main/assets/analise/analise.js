// aqui tem q ter uma chamada a interface android pega nome
var idDeputado = '141454';
var idLegislatura = '55'; //legislatura atual
var gastosDeputado; // não processado
var gastos; // apos o processamento
var total;
var tcombustivel;
var self = '';
var last = '';
var next = '';
var baseURL = 'https://dadosabertos.camara.leg.br/api/v2/deputados/';

function setup() {
  trocou();
}

function trocou(){
  var aux = Android.getDadosDeputado();
  idDeputado = aux.split(';')[0];
  //idLegislatura = aux.split(';')[1];
  gastosDeputado = [];
  gastos = [];
  // fazer chamada para interface android para pegar os dados do deputado e salvar na variavel
  var url = baseURL + idDeputado + '/despesas?idLegislatura=' + idLegislatura
                            + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=100';
  getData(url);
}

function getData(url){
  //console.log(url);
  loadJSON(url, gotData, errorCallback);
}

function errorCallback(e){
  if (self != last || self == '' || last == '') alert("Dados insuficientes para análise do deputado, ou servidor da câmara indisponível!");
  //Android.finalizar();
  processaDados();
}

function gotData(data) {
    for(var d in data.dados)
      gastosDeputado.push(data.dados[d]);

    next='', self='', last ='';
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
  total=0; tcombustivel = 0;

  //console.log(gastosDeputado);

  var aux = gastosDeputado.map(function (d){
    return {nome: d.tipoDespesa,
            tamanho: d.valorLiquido,
            tipoDespesa: d.tipoDespesa,
            cnpj: d.cnpjCpfFornecedor,
            nomeFornecedor: d.nomeFornecedor,
            valorString: d.valorDocumento,
            mes: d.mes,
            ano: d.ano}
  });

  //console.log("GASTOS MAP");
  //console.log(aux);
  gastos = [];

  for(var key in aux){
    var soma = 0;
    var tem = 0;
    for(var g in gastos){
      if(gastos[g].nome === aux[key].nome){
        gastos[g].tamanho +=aux[key].tamanho;
        gastos[g].valor +=aux[key].tamanho;
        gastos[g].qtd++;
        total += aux[key].tamanho;
        tem = 1;
        //console.log(String(aux[key].tipoDespesa), String(aux[key].cnpj), String(aux[key].nomeFornecedor), String(aux[key].valorString), aux[key].mes, aux[key].ano);
        Android.addDespesa(String(aux[key].tipoDespesa),
                                        String(aux[key].cnpj),
                                        String(aux[key].nomeFornecedor),
                                        String(aux[key].valorString),
                                        aux[key].mes,
                                        aux[key].ano);
        break;
      }
    }
    if(tem === 0){
      gastos.push({nome: aux[key].nome, tamanho: aux[key].tamanho, valor: aux[key].tamanho, qtd: 1});
      //console.log(aux[key].nome);
      Android.addCategoria(aux[key].nome);
      total += aux[key].tamanho;
    } else tem=0;
  }

  // junta as categorias
  var newgastos = [
    {nome : "Transporte", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Alimentação", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Manutenção de Escritório", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Assinatura de Publicação", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Apoio de Atividade Parlamentar", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Telefonia", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Serviço Postal", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Hospedagem", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Serviços de Segurança", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Participações em Cursos ou Palestras", valor : 0.0, tamanho : 0.0, qtd: 0},
    {nome : "Auxílio Moradia", valor : 0.0, tamanho : 0.0, qtd: 0}
  ];

  for(var g in gastos){
    var cat = gastos[g].nome.toLowerCase();
    var index;
    switch (cat) {
      case (cat.match(/lubrificante/) || {}).input:
        index=0;
        tcombustivel += gastos[g].valor;
        break;
      case (cat.match(/passagens/) || cat.match(/fretamento/) || cat.match(/bilhete/)
            || cat.match(/estacionamento/) || {}).input:
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
    newgastos[index].qtd += gastos[g].qtd;
  }

  for(var g in newgastos){
    if(newgastos[g].valor == 0.0){
        console.log(newgastos[g].valor);
        newgastos[g] = false;
    }
  }

  gastos = newgastos.filter(filtraCategoriaValor0);

  var x = []; var y = []; var t = [];
  for( var g in gastos){
    gastos[g].tamanho = parseFloat((gastos[g].tamanho / total)*100);
    Android.categoriaColapsada(gastos[g].nome, String(gastos[g].tamanho), String(gastos[g].valor), gastos[g].qtd);
    //console.log(gastos[g].nome, String(gastos[g].tamanho), String(gastos[g].valor), gastos[g].qtd);
    x.push(parseFloat(gastos[g].tamanho));
    t.push(gastos[g].nome);
    y.push(parseFloat(gastos[g].valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }));
  }


  var dataset = [{
      type: 'bar',
      x: x,
      y: y,
      text: t.map(String),
      textposition: 'auto',
      orientation : "h",
      hoverinfo: 'none',
      marker: {
          opacity: 0.6,
          colorway: ["#191970", "#0000CD", "#6495ED", "#00BFFF", "#87CEEB", "#4682B4", "#708090", "#00FFFF", "#008B8B"],
          line: {
            width: 2
          }
      }
    }];

  var tamanho = getTamanhoJanela();
  var layout = {
        showlegend: false,
        width: tamanho.width-50,
      yaxis : {fixedrange: true}, xaxis: {fixedrange: true},
        height: tamanho.height-50
      };

  //console.log("total " + parseFloat(total) + " com" + parseFloat(tcombustivel));
  Android.setTotal(String(total), String(tcombustivel));


  Plotly.newPlot("myDiv", dataset, layout);

  Android.finalizar();
}

function getTamanhoJanela(){
  var tamanho = { width: screen.width, height: screen.height};

  return tamanho;
}

function filtraCategoriaValor0(d) {
    return d != false;
}

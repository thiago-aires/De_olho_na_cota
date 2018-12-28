var dados;
var idDeputado = '74395'; // pegar do Android para saber de quem se trata
var nomeDeputado = "José Otávio Germano"; //msm coisa
var baseURL = 'https://dadosabertos.camara.leg.br/api/v2/deputados/';
var dadosNaoProcessadosJson;
var year;

function trocou(){
    dados = [];
    year = ano.options[ano.selectedIndex].text;
    var url = baseURL + idDeputado + '/despesas?ano=' + year + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=15';
    dadosNaoProcessadosJson = [];
    console.log(url);
    Android.inicia();
    getData(url);
}

function setup(){
  nomeDeputado = Android.getDadosDeputado().split(';')[0];
  idDeputado = Android.getDadosDeputado().split(';')[1];
  var ano = document.getElementById("ano");

  var x = 0;
  for(var i = 2018; i>= 1994; i--){
    var o = document.createElement('option');
    o.setAttribute("value",i);
    o.text = i;
    ano.appendChild(o);
    x++;
  }

  ano.selectedIndex = 0;
  //console.log(idDeputado);
  dados = [];
      year = ano.options[ano.selectedIndex].text;
      var url = baseURL + idDeputado + '/despesas?ano=' + year + '&ordem=ASC&ordenarPor=ano&pagina=1&itens=15';
      dadosNaoProcessadosJson = [];
      console.log(url);
      //Android.inicia();
      getData(url);
}

function getData(url){
  loadJSON(url, gotData, errorCallback);
}

function errorCallback(e){
  alert("Dados insuficientes para análise do deputado, ou servidor da câmara indisponível!");
  processaDados();
}

function gotData(data){
  //console.log(data);

  for(var d in data.dados)
    dadosNaoProcessadosJson.push(data.dados[d]);

  var next='', self='', last ='';
  for(var link in data.links){
    switch (data.links[link].rel){
      case 'self': self = data.links[link].href; break;
      case 'next': next = data.links[link].href; break;
      case 'last': last = data.links[link].href; break;
    }
  }

  if (self != last) getData(next)
  else processaDados();
}

function processaDados(){
  console.log(dadosNaoProcessadosJson);
  var totalCategoria = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

  dados.push(['Janeiro', //0
              'Fevereiro', //1
              'Março', //2
              'Abril',//3
              'Maio',//4
              'Junho', //5
              'Julho',//6
              'Agosto',//7
              'Setembro', //8
              'Outubro', //9
              'Novembro', // 10
              'Dezembro']);//11


  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]); //Transporte 1
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]); //Alimentação 2
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]); //Manutenção de Escritório 3
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]); //Assinatura de publicação 4
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]); //Apoio de Atividade Parlamentar 5
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Telefonia 6
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Serviço Postal 7
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Hospedagem 8
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Serviço de Segurança 9
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Participação em Cursos ou Palestras 10
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Auxílio Moradia 11
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//Não processou 12
  dados.push([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);//total mensal 13

   //console.log(dadosNaoProcessadosJson);

  for(var i in dadosNaoProcessadosJson){
    var cat = dadosNaoProcessadosJson[i].tipoDespesa.toLowerCase();
    switch (cat) {
      case (cat.match(/passagens/) || cat.match(/bilhete/) || cat.match(/fretamento/)
            || cat.match(/estacionamento/) || cat.match(/lubrificante/) || {}).input:
        dados[1][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[0] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('transporte');
        break;

      case (cat.match(/alimentação/) || {}).input:
        dados[2][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[1] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('alimentação');
        break;

      case (cat.match(/imóveis/) || cat.match(/escritório/) || cat.match(/condomínio/)
              || cat.match(/iptu/) || cat.match(/energia/) || cat.match(/móveis/)
              || cat.match(/informática/) || cat.match(/internet/) || cat.match(/tv/)
              || cat.match(/software/) || {}).input:
        dados[3][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[2] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Manutenção de Escritório');
        break;
      case (cat.match(/assinatura/) || {}).input:
        dados[4][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[3] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Assinatura de Publicação');
        break;

      case (cat.match(/divulgação/) || cat.match(/contratação/) || cat.match(/consultorias/)|| {}).input:
        dados[5][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[4] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Apoio de Atividade Parlamentar');
        break;

      case (cat.match(/telefonia/) || {}).input:
        dados[6][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[5] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Telefonia');
        break;
      case (cat.match(/postais/) || {}).input:
        dados[7][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[6] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Serviço Postal');
        break;

      case (cat.match(/hospedagem/) || {}).input:
        dados[8][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[7] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Hospedagem');
        break;

      case (cat.match(/segurança/) || {}).input:
        dados[9][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[8] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Serviços de Segurança');
        break;
      case (cat.match(/cursos/) || cat.match(/palestras/) || {}).input:
        dados[10][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[9] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Participação em cursos ou palestras');
        break;
      case (cat.match(/moradia/) || {}).input:
        dados[11][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[10] += dadosNaoProcessadosJson[i].valorLiquido;
        //console.log('Auxílio Moradia');
        break;
      default:
        dados[12][dadosNaoProcessadosJson[i].mes-1] += dadosNaoProcessadosJson[i].valorLiquido;
        totalCategoria[11] += dadosNaoProcessadosJson[i].valorLiquido;
        console.log(dadosNaoProcessadosJson[i]);
        console.log(i);
        break;
    }
  }

  for(var c = 0; c<=11; c++){
    var t = 0;
    for(var l = 1; l<=12; l++){
      t += dados[l][c];
    } // tenho total
    dados[13][c] = t;
  }

    // jan igual a false para dizer que não tem entrada, caso ache algum valor diferente de 0 janeiro aparece no grafico
  var jan = false;
  for(var d in dados[1])
    if(dados[1][d] != 0){
         jan = {
            x: dados[0],
            y: dados[1],
            mode: 'lines',
            name: 'Transporte',
            line: {
              width: 4
            }
          };

          break;
    }

    var fev = false;
    for(var d in dados[2])
      if(dados[2][d] != 0){
         fev = {
            x: dados[0],
            y: dados[2],
            mode: 'lines',
            name: 'Alimentação',
            line: {
              width: 4
            }
        };
        break;
      }


    var mar = false;
    for(var d in dados[3])
        if(dados[3][d] != 0){
             mar = {
                x: dados[0],
                y: dados[3],
                mode: 'lines',
                name: 'Manutenção de Escritório',
                line: {
                  width: 4
                }
            };
            break;
        }

    var abr = false;
      for(var d in dados[4])
        if(dados[4][d] != 0){
           abr = {
            x: dados[0],
            y: dados[4],
            mode: 'lines',
            name: 'Assinatura de publicação',
            line: {
              width: 4
            }
          };
          break;
        }

  var mai = false;
    for(var d in dados[5])
      if(dados[5][d] != 0){
          mai = {
            x: dados[0],
            y: dados[5],
            mode: 'lines',
            name: 'Apoio de Atividade Parlamentar',
            line: {
              width: 4
            }
          };
          break;
      }


  var jun = false;
    for(var d in dados[6])
      if(dados[6][d] != 0){
           jun = {
            x: dados[0],
            y: dados[6],
            mode: 'lines',
            name: 'Telefonia',
            line: {
              width: 4
            }
          };
          break;
      }

  var jul = false;
    for(var d in dados[7])
      if(dados[7][d] != 0){
          jul = {
            x: dados[0],
            y: dados[7],
            mode: 'lines',
            name: 'Serviço Postal',
            line: {
              width: 4
            }
          };
          break;
      }

  var ago = false;
    for(var d in dados[8])
      if(dados[8][d] != 0){
          ago = {
            x: dados[0],
            y: dados[8],
            mode: 'lines',
            name: 'Hospedagem',
            line: {
              width: 4
            }
          };
          break;
      }

  var set = false;
    for(var d in dados[9])
      if(dados[9][d] != 0){
        set = {
            x: dados[0],
            y: dados[9],
            mode: 'lines',
            name: 'Serviço de Segurança',
            line: {
              width: 4
            }
        };
        break;
    }

  var out = false;
    for(var d in dados[10])
      if(dados[10][d] != 0){
        out = {
            x: dados[0],
            y: dados[10],
            mode: 'lines',
            name: 'Participação em Cursos ou Palestras',
            line: {
              width: 4
            }
          };
          break;
        }

  var nov = false;
    for(var d in dados[11])
      if(dados[11][d] != 0){
          nov = {
            x: dados[0],
            y: dados[11],
            mode: 'lines',
            name: 'Auxílio Moradia',
            line: {
              width: 4
            }
          };
          break;
      }

  var dez = false;
    for(var d in dados[12])
      if(dados[12][d] != 0){
          dez = {
            x: dados[0],
            y: dados[12],
            mode: 'lines',
            name: 'Não processou',
            line: {
              width: 4
            }
          };
          break;
    }
    var total = 0;
    for(var d in totalCategoria){
      total += totalCategoria[d];
    }

    //console.log(dadosNaoProcessadosJson.length);
    var m = total / 12;
    //console.log(total);
    //console.log(m);

    var media = {
      x: dados[0],
      y: [m,m,m,m,m,m,m,m,m,m,m,m],
      mode: 'lines',
      name: 'Média Anual',
      line: {
        dash: 'dot',
        color: 'rgba(0,0,0,1)',
        width: 5
      }
    };

    var totalmensal = {
      x: dados[0],
      y: dados[13],
      mode: 'lines',
      name: 'Total mensal',
      line: {
        dash: 'dot',
        color: 'rgba(255,0,0,1)',
        width: 5
      }
    };

  var dataset = [jan, fev, mar, mai, abr, jun, jul, ago, set, out, nov, dez, media, totalmensal].filter(filtraCategoriaValor0);
  var tamanho = getTamanhoJanela();
  var layout = {
        title: 'Tendência dos Gastos no ano de ' + year,
        showlegend: true,
        width: tamanho.width,
        height: tamanho.height,
        yaxis : {fixedrange: true}, xaxis: {fixedrange: false},
        legend: {
          "orientation": "h"
        }
      };

  //console.log(totalCategoria);
  //console.log(dataset);
  //console.log(filtraMesValor0(dataset));
  //console.log(janeiro);

  Plotly.newPlot("myDiv", dataset, layout, {responsive: true});
  Android.finalizar();
}


function filtraCategoriaValor0(d) {
    return d != false;
}

function getTamanhoJanela(){
  var tamanho = { width: screen.width, height: screen.height};

  return tamanho;
}

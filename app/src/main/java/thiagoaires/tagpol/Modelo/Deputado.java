package thiagoaires.tagpol.Modelo;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *  Classe que mantém informações completas para o CardView expandido
 * @author thiaires
 */
public class Deputado implements Serializable {
    private Dados dados;

    public Deputado() {
        this.dados = new Dados();
    }

    public Dados getDados() {
        return this.dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }

    @Override
    public String toString() {
        return "Deputado{" +
                "dados=" + dados +
                '}';
    }

    private ArrayList<Link> links;

    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public class Gabinete implements Serializable{
        private String nome;

        public String getNome() {
            return this.nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        private String predio;

        public String getPredio() {
            return this.predio;
        }

        public void setPredio(String predio) {
            this.predio = predio;
        }

        private String sala;

        public String getSala() {
            return this.sala;
        }

        public void setSala(String sala) {
            this.sala = sala;
        }

        private String andar;

        public String getAndar() {
            return this.andar;
        }

        public void setAndar(String andar) {
            this.andar = andar;
        }

        private String telefone;

        public String getTelefone() {
            return this.telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        private String email;

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Gabinete{" +
                    "nome='" + nome + '\'' +
                    ", predio='" + predio + '\'' +
                    ", sala='" + sala + '\'' +
                    ", andar='" + andar + '\'' +
                    ", telefone='" + telefone + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public class UltimoStatus implements Serializable {

        public UltimoStatus() {
            this.gabinete = new Gabinete();
        }

        private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private String uri;

        public String getUri() {
            return this.uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        private String nome;

        public String getNome() {
            return this.nome;
        }

        public void setNome(String nome) {
            String[] n = nome.split(" ");
            StringBuilder sb = new StringBuilder();

            for(int i=0; i<n.length; i++){
                sb.append(n[i].substring(0,1).toUpperCase() + n[i].substring(1).toLowerCase());
                sb.append(" ");
            }

            this.nome = sb.toString();
        }

        private String siglaPartido;

        public String getSiglaPartido() {
            return this.siglaPartido;
        }

        public void setSiglaPartido(String siglaPartido) {
            this.siglaPartido = siglaPartido;
        }

        private String uriPartido;

        public String getUriPartido() {
            return this.uriPartido;
        }

        public void setUriPartido(String uriPartido) {
            this.uriPartido = uriPartido;
        }

        private String siglaUf;

        public String getSiglaUf() {
            return this.siglaUf;
        }

        public void setSiglaUf(String siglaUf) {
            this.siglaUf = siglaUf;
        }

        private int idLegislatura;

        public int getIdLegislatura() {
            return this.idLegislatura;
        }

        public void setIdLegislatura(int idLegislatura) {
            this.idLegislatura = idLegislatura;
        }

        private String urlFoto;

        public String getUrlFoto() {
            return this.urlFoto;
        }

        public void setUrlFoto(String urlFoto) {
            this.urlFoto = urlFoto;
        }

        private String data;

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        private String nomeEleitoral;

        public String getNomeEleitoral() {
            return this.nomeEleitoral;
        }

        public void setNomeEleitoral(String nomeEleitoral) {
            this.nomeEleitoral = nomeEleitoral;
        }

        private Gabinete gabinete;

        public Gabinete getGabinete() {
            return this.gabinete;
        }

        public void setGabinete(Gabinete gabinete) {
            this.gabinete = gabinete;
        }

        private String situacao;

        public String getSituacao() {
            return this.situacao;
        }

        public void setSituacao(String situacao) {
            this.situacao = situacao;
        }

        private String condicaoEleitoral;

        public String getCondicaoEleitoral() {
            return this.condicaoEleitoral;
        }

        public void setCondicaoEleitoral(String condicaoEleitoral) {
            this.condicaoEleitoral = condicaoEleitoral;
        }

        private String descricaoStatus;

        public String getDescricaoStatus() {
            return this.descricaoStatus;
        }

        public void setDescricaoStatus(String descricaoStatus) {
            this.descricaoStatus = descricaoStatus;
        }

        @Override
        public String toString() {
            return "UltimoStatus{" +
                    "id=" + id +
                    ", uri='" + uri + '\'' +
                    ", nome='" + nome + '\'' +
                    ", siglaPartido='" + siglaPartido + '\'' +
                    ", uriPartido='" + uriPartido + '\'' +
                    ", siglaUf='" + siglaUf + '\'' +
                    ", idLegislatura=" + idLegislatura +
                    ", urlFoto='" + urlFoto + '\'' +
                    ", data='" + data + '\'' +
                    ", nomeEleitoral='" + nomeEleitoral + '\'' +
                    ", gabinete=" + gabinete +
                    ", situacao='" + situacao + '\'' +
                    ", condicaoEleitoral='" + condicaoEleitoral + '\'' +
                    ", descricaoStatus='" + descricaoStatus + '\'' +
                    '}';
        }
    }

    public class Dados implements Serializable{

        public Dados() {
            this.ultimoStatus = new UltimoStatus();
            this.redeSocial = new ArrayList<>();
        }

        private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private String uri;

        public String getUri() {
            return this.uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        private String nomeCivil;

        public String getNomeCivil() {

            return this.nomeCivil;
        }

        public void setNomeCivil(String nomeCivil) {
            this.nomeCivil = nomeCivil;
        }

        private UltimoStatus ultimoStatus;

        public UltimoStatus getUltimoStatus() {
            return this.ultimoStatus;
        }

        public void setUltimoStatus(UltimoStatus ultimoStatus) {
            this.ultimoStatus = ultimoStatus;
        }

        private String cpf;

        public String getCpf() {
            return this.cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        private String sexo;

        public String getSexo() {
            return this.sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }

        private String urlWebsite;

        public String getUrlWebsite() {
            return this.urlWebsite;
        }

        public void setUrlWebsite(String urlWebsite) {
            this.urlWebsite = urlWebsite;
        }

        private ArrayList<String> redeSocial;

        public ArrayList<String> getRedeSocial() {
            return this.redeSocial;
        }

        public void setRedeSocial(ArrayList<String> redeSocial) {
            this.redeSocial = redeSocial;
        }

        private String dataNascimento;

        public String getDataNascimento() {
            return this.dataNascimento;
        }

        public void setDataNascimento(String dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        private String dataFalecimento;

        public String getDataFalecimento() {
            return this.dataFalecimento;
        }

        public void setDataFalecimento(String dataFalecimento) {
            this.dataFalecimento = dataFalecimento;
        }

        private String ufNascimento;

        public String getUfNascimento() {
            return this.ufNascimento;
        }

        public void setUfNascimento(String ufNascimento) {
            this.ufNascimento = ufNascimento;
        }

        private String municipioNascimento;

        public String getMunicipioNascimento() {
            return this.municipioNascimento;
        }

        public void setMunicipioNascimento(String municipioNascimento) {
            this.municipioNascimento = municipioNascimento;
        }

        private String escolaridade;

        public String getEscolaridade() {
            return this.escolaridade;
        }

        public void setEscolaridade(String escolaridade) {
            this.escolaridade = escolaridade;
        }

        @Override
        public String toString() {
            return "Dados{" +
                    "id=" + id +
                    ", uri='" + uri + '\'' +
                    ", nomeCivil='" + nomeCivil + '\'' +
                    ", ultimoStatus=" + ultimoStatus.toString() +
                    ", cpf='" + cpf + '\'' +
                    ", sexo='" + sexo + '\'' +
                    ", urlWebsite=" + urlWebsite +
                    ", redeSocial=" + redeSocial.toString() +
                    ", dataNascimento=" + dataNascimento +
                    ", dataFalecimento=" + dataFalecimento +
                    ", ufNascimento='" + ufNascimento + '\'' +
                    ", municipioNascimento='" + municipioNascimento + '\'' +
                    ", escolaridade='" + escolaridade + '\'' +
                    '}';
        }
    }

    public class Link implements Serializable{
        private String rel;

        public String getRel() {
            return this.rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        private String href;

        public String getHref() {
            return this.href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}

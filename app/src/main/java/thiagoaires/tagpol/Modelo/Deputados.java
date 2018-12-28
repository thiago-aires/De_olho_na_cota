package thiagoaires.tagpol.Modelo;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Classe que mantém apenas informações para o CardView simples
 * @author thiaires
 */
public class Deputados implements Serializable{
    public Deputados() {
        this.dados = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    private ArrayList<Dado> dados;

    public ArrayList<Dado> getDados() { return this.dados; }

    public void addDado(Dado d){
        this.dados.add(d);
    }

    public void remDado(Dado d){
        this.dados.remove(d);
    }

    public void setDados(ArrayList<Dado> dados) { this.dados = dados; }

    private ArrayList<Link> links;

    public ArrayList<Link> getLinks() { return this.links; }

    public void setLinks(ArrayList<Link> links) { this.links = links; }

    @Override
    public String toString() {
        return "Deputados{" +
                "dados=" + dados +
                '}';
    }

    public class Dado implements Serializable {
        private int id;

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        private String uri;

        public String getUri() { return this.uri; }

        public void setUri(String uri) { this.uri = uri; }

        private String nome;

        public String getNome() { return this.nome; }

        public void setNome(String nome) {
            this.nome = nome;
        }

        private String siglaPartido;

        public String getSiglaPartido() { return this.siglaPartido; }

        public void setSiglaPartido(String siglaPartido) { this.siglaPartido = siglaPartido; }

        private String uriPartido;

        public String getUriPartido() { return this.uriPartido; }

        public void setUriPartido(String uriPartido) { this.uriPartido = uriPartido; }

        private String siglaUf;

        public String getSiglaUf() { return this.siglaUf; }

        public void setSiglaUf(String siglaUf) { this.siglaUf = siglaUf; }

        private int idLegislatura;

        public int getIdLegislatura() { return this.idLegislatura; }

        public void setIdLegislatura(int idLegislatura) { this.idLegislatura = idLegislatura; }

        private String urlFoto;

        public String getUrlFoto() { return this.urlFoto; }

        public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

        @Override
        public String toString() {
            return "Dado{" +
                    "id=" + id +
                    ", uri='" + uri + '\'' +
                    ", nome='" + nome + '\'' +
                    ", siglaPartido='" + siglaPartido + '\'' +
                    ", uriPartido='" + uriPartido + '\'' +
                    ", siglaUf='" + siglaUf + '\'' +
                    ", idLegislatura=" + idLegislatura +
                    ", urlFoto='" + urlFoto + '\'' +
                    '}';
        }
    }

    public class Link implements Serializable
    {
        private String rel;

        public String getRel() { return this.rel; }

        public void setRel(String rel) { this.rel = rel; }

        private String href;

        public String getHref() { return this.href; }

        public void setHref(String href) { this.href = href; }

        @Override
        public String toString() {
            return "Link{" +
                    "rel='" + rel + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }
}

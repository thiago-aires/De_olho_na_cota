package thiagoaires.tagpol.Modelo;

import java.util.ArrayList;

public class Despesas {
    private double gastosTotal;

    private double getGastosTotal(){
        return gastosTotal;
    }

    public Despesas(){
        gastosTotal=0.0;
    }

    private ArrayList<Dado> dados;

    public ArrayList<Dado> getDados() { return this.dados; }

    public void setDados(ArrayList<Dado> dados) { this.dados = dados; }

    private ArrayList<Link> links;

    public ArrayList<Link> getLinks() { return this.links; }

    public void setLinks(ArrayList<Link> links) { this.links = links; }

    @Override
    public String toString() {
        return "Despesas{" + dados + links + '}';
    }

    public class Dado{
        private int ano;
        private int mes;
        private String tipoDespesa;
        private int idDocumento;
        private String tipoDocumento;
        private int idTipoDocumento;
        private String dataDocumento;
        private int parcela;
        private int idLote;
        private String numRessarcimento;
        private int valorGlosa;
        private double valorLiquido;
        private String cnpjCpfFornecedor;
        private String nomeFornecedor;
        private String urlDocumento;
        private double valorDocumento;
        private String numDocumento;

        public int getAno() { return this.ano; }

        public void setAno(int ano) { this.ano = ano; }

        public int getMes() { return this.mes; }

        public void setMes(int mes) { this.mes = mes; }

        public String getTipoDespesa() { return this.tipoDespesa; }

        public void setTipoDespesa(String tipoDespesa) { this.tipoDespesa = tipoDespesa.toLowerCase(); }

        public int getIdDocumento() { return this.idDocumento; }

        public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }

        public String getTipoDocumento() { return this.tipoDocumento; }

        public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

        public int getIdTipoDocumento() { return this.idTipoDocumento; }

        public void setIdTipoDocumento(int idTipoDocumento) { this.idTipoDocumento = idTipoDocumento; }

        public String getDataDocumento() { return this.dataDocumento; }

        public void setDataDocumento(String dataDocumento) { this.dataDocumento = dataDocumento; }

        public String getNumDocumento() { return this.numDocumento; }

        public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

        public double getValorDocumento() { return this.valorDocumento; }

        public void setValorDocumento(double valorDocumento) {
            this.valorDocumento = valorDocumento;
            gastosTotal+=valorDocumento;
        }

        public String getUrlDocumento() { return this.urlDocumento; }

        public void setUrlDocumento(String urlDocumento) { this.urlDocumento = urlDocumento; }

        public String getNomeFornecedor() { return this.nomeFornecedor; }

        public void setNomeFornecedor(String nomeFornecedor) { this.nomeFornecedor = nomeFornecedor; }

        public String getCnpjCpfFornecedor() { return this.cnpjCpfFornecedor; }

        public void setCnpjCpfFornecedor(String cnpjCpfFornecedor) { this.cnpjCpfFornecedor = cnpjCpfFornecedor; }

        public double getValorLiquido() { return this.valorLiquido; }

        public void setValorLiquido(double valorLiquido) { this.valorLiquido = valorLiquido; }

        public int getValorGlosa() { return this.valorGlosa; }

        public void setValorGlosa(int valorGlosa) { this.valorGlosa = valorGlosa; }

        public String getNumRessarcimento() { return this.numRessarcimento; }

        public void setNumRessarcimento(String numRessarcimento) { this.numRessarcimento = numRessarcimento; }

        public int getIdLote() { return this.idLote; }

        public void setIdLote(int idLote) { this.idLote = idLote; }

        public int getParcela() { return this.parcela; }

        public void setParcela(int parcela) { this.parcela = parcela; }

        @Override
        public String toString() {
            return "Dado{" +
                    "ano=" + ano +
                    ", mes=" + mes +
                    ", tipoDespesa='" + tipoDespesa + '\'' +
                    ", idDocumento=" + idDocumento +
                    ", tipoDocumento='" + tipoDocumento + '\'' +
                    ", idTipoDocumento=" + idTipoDocumento +
                    ", dataDocumento='" + dataDocumento + '\'' +
                    ", parcela=" + parcela +
                    ", idLote=" + idLote +
                    ", numRessarcimento='" + numRessarcimento + '\'' +
                    ", valorGlosa=" + valorGlosa +
                    ", valorLiquido=" + valorLiquido +
                    ", cnpjCpfFornecedor='" + cnpjCpfFornecedor + '\'' +
                    ", nomeFornecedor='" + nomeFornecedor + '\'' +
                    ", urlDocumento='" + urlDocumento + '\'' +
                    ", valorDocumento=" + valorDocumento +
                    ", numDocumento='" + numDocumento + '\'' +
                    '}';
        }
    }

    public class Link {
        private String rel;
        private String href;

        public String getRel() { return this.rel; }

        public void setRel(String rel) { this.rel = rel; }

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

package com.aroos.metacom.activities.models;

import java.io.Serializable;

/**
 * Created by murilo on 02/04/2016.
 */
public class Atividade implements Serializable {
    private int id;

    private String cor;
    private String status_fechamento;
    private String nr_os;



    private String tipo;
    private String defeito;
    private String abertura;
    private String agendamento;
    private String periodo;
    private String solicitante;
    private String cliente;
    private String telefone;
    private String celular;
    private String observacao;
    private String atendente;

    private String mercado;
    private String observacao_abertura;
    private String tipo_logr;
    private String logradouro;
    private String numero;
    private String quadra;
    private String defeito_reclamado;
    private String desiguinacao;
    private String pop;
    private String interface_;
    private String swit;
    private String porta;
    private int tecnico_id;
    private int ordem;
    private String lat_porta ;
    private String long_porta;
    private String lat_switch;
    private String  long_switch;

    public int getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(int tecnico_id) {
        this.tecnico_id = tecnico_id;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getLat_porta() {
        return lat_porta;
    }

    public void setLat_porta(String lat_porta) {
        this.lat_porta = lat_porta;
    }

    public String getLong_porta() {
        return long_porta;
    }

    public void setLong_porta(String long_porta) {
        this.long_porta = long_porta;
    }

    public String getLat_switch() {
        return lat_switch;
    }

    public void setLat_switch(String lat_switch) {
        this.lat_switch = lat_switch;
    }

    public String getLong_switch() {
        return long_switch;
    }

    public void setLong_switch(String long_switch) {
        this.long_switch = long_switch;
    }

    public String getDesiguinacao() {
        return desiguinacao;
    }

    public void setDesiguinacao(String desiguinacao) {
        this.desiguinacao = desiguinacao;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getInterface_() {
        return interface_;
    }

    public void setInterface_(String interface_) {
        this.interface_ = interface_;
    }

    public String getSwit() {
        return swit;
    }

    public void setSwit(String swit) {
        this.swit = swit;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getDefeito_reclamado() {
        return defeito_reclamado;
    }

    public void setDefeito_reclamado(String defeito_reclamado) {
        this.defeito_reclamado = defeito_reclamado;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public String getObservacao_abertura() {
        return observacao_abertura;
    }

    public void setObservacao_abertura(String observacao_abertura) {
        this.observacao_abertura = observacao_abertura;
    }

    public String getTipo_logr() {
        return tipo_logr;
    }

    public void setTipo_logr(String tipo_logr) {
        this.tipo_logr = tipo_logr;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getKm_inicio() {
        return km_inicio;
    }

    public void setKm_inicio(String km_inicio) {
        this.km_inicio = km_inicio;
    }

    public String getKm_fim() {
        return km_fim;
    }

    public void setKm_fim(String km_fim) {
        this.km_fim = km_fim;
    }

    public String getAntena() {
        return antena;
    }

    public void setAntena(String antena) {
        this.antena = antena;
    }

    public String getProp_equipamento() {
        return prop_equipamento;
    }

    public void setProp_equipamento(String prop_equipamento) {
        this.prop_equipamento = prop_equipamento;
    }

    public String getConexao() {
        return conexao;
    }

    public void setConexao(String conexao) {
        this.conexao = conexao;
    }

    public String getNr_cabo() {
        return nr_cabo;
    }

    public void setNr_cabo(String nr_cabo) {
        this.nr_cabo = nr_cabo;
    }

    public String getPar_eletrico() {
        return par_eletrico;
    }

    public void setPar_eletrico(String par_eletrico) {
        this.par_eletrico = par_eletrico;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getPonto_acesso() {
        return ponto_acesso;
    }

    public void setPonto_acesso(String ponto_acesso) {
        this.ponto_acesso = ponto_acesso;
    }

    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String referencia;
    private String latitude;
    private String longitude;
    private String ip;
    private String mac;
    private String login;
    private String senha;
    private String plano;
    private String km_inicio;
    private String km_fim;
    private String antena;
    private String prop_equipamento;
    private String conexao;
    private String nr_cabo;
    private String par_eletrico;
    private String servidor;
    private String ponto_acesso;

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }



    public Atividade(int id,  String nr_os, String tipo, String cliente, String agendamento,String periodo, String cor){
        this.id = id;

        this.nr_os = nr_os;
        this.tipo = tipo;
        this.cliente = cliente;
        this.agendamento = agendamento;
        this.periodo = periodo;
        this.cor = cor;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCor() {
        cor ="#59d5fe";
        if(getTipo().substring(0,1).equals("I")){
            cor ="#59d5fe";
        }
        if(getTipo().substring(0,1).equals("R")){
            cor ="#666600";
        }
        if(getTipo().substring(0,1).equals( "E")){
            cor ="#993333";
        }
        if(getTipo().substring(0,1).equals("M")){
            cor ="#990099";
        }
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getStatus_fechamento() {
        return status_fechamento;
    }

    public void setStatus_fechamento(String status_fechamento) {
        this.status_fechamento = status_fechamento;
    }
    public String getNr_os() {
        return nr_os;
    }

    public void setNr_os(String nr_os) {
        this.nr_os = nr_os;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getAbertura() {
        return abertura;
    }

    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }

    public String getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(String agendamento) {
        this.agendamento = agendamento;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public Atividade(){}






    @Override
    public String toString() {
        return getNr_os()+"("+ getCliente()+")";
    }
}

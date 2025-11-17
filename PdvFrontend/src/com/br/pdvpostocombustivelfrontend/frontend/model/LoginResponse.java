package com.br.pdvpostocombustivelfrontend.frontend.model;

/**
 * Resposta do login
 */
public class LoginResponse {
    private boolean sucesso;
    private String mensagem;
    private String usuario;
    private TipoAcesso tipoAcesso;

    public LoginResponse() {}

    public LoginResponse(boolean sucesso, String mensagem, String usuario, TipoAcesso tipoAcesso) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.usuario = usuario;
        this.tipoAcesso = tipoAcesso;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(TipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }
}


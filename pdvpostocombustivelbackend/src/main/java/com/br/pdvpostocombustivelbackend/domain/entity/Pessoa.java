package com.br.pdvpostocombustivelbackend.domain.entity; //Pacote que a classe vai receber
import com.br.pdvpostocombustivelbackend.enums.TipoPessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "pessoa")

public class Pessoa{

    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "nome_completo",length = 200, nullable = false)//Limita a 200 caracteres o campo / Jamais permite que o campo esteja vazio
    private String nomeCompleto;

    @Column(name = "cpf_cnpj",length = 14, nullable = false)
    private  String cpfCnpj;

    @Column(name = "data_nascimento",length = 10, nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "numero_ctps",length = 12)
    private Long numeroCtps;

    @NotNull
    @Enumerated(EnumType.STRING) //Tipo enum
    @Column(name = "tipo_pessoa", length = 15, nullable = false)
    private TipoPessoa tipoPessoa;

    @Column(name = "email", length = 70)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "endereco", length = 70)
    private String endereco;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Acesso> acessos = new ArrayList<>();

    //construtor
    public Pessoa (String nomeCompleto,
                   String cpfCnpj,
                   LocalDate dataNascimento,
                   Long numeroCtps,
                   TipoPessoa tipoPessoa,
                   String email,
                   String telefone,
                   String endereco){
        this.nomeCompleto = nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.numeroCtps = numeroCtps;
        this.dataNascimento = dataNascimento;
        this.tipoPessoa = tipoPessoa;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    //construtor vazio
    public Pessoa(){
    }

    //getters
    public Long getId() {
        return Id;
    }

    public String getNomeCompleto(){ // Recebe o set e aplica
        return nomeCompleto;
    }

    public String getCpfCnpj(){
        return cpfCnpj;
    }

    public LocalDate getDataNascimento(){
        return dataNascimento;
    }

    public Long getNumeroCtps(){
        return numeroCtps;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    //setters
    public void setId(Long id) {
        Id = id;
    }

    public void setNomeCompleto(String nomeCompleto){ // Atribui parametros a assinatura vazia (void)
        this.nomeCompleto = nomeCompleto;
    }

    public void setCpfCnpj(String cpfCnpj){
        this.cpfCnpj = cpfCnpj;
    }

    public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;
    }

    public void setNumeroCtps(Long numeroCtps){
        this.numeroCtps = numeroCtps;
    }



    public List<Acesso> getAcessos() { return acessos; }
    public void setAcessos(List<Acesso> acessos) { this.acessos = acessos; }
}

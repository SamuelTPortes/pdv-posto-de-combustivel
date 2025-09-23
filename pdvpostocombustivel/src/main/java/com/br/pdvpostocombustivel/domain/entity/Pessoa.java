package com.br.pdvpostocombustivel.domain.entity; //Pacote que a classe vai receber
import com.br.pdvpostocombustivel.enums.TipoPessoa;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.Timer;

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

    @Enumerated(EnumType.STRING) //Tipo enum
    @Column(name = "tipo_pessoa", length = 15, nullable = false)
    private TipoPessoa tipoPessoa;

    //construtor
    public Pessoa (String nomeCompleto,
                   String cpfCnpj,
                   LocalDate dataNascimento,
                   Long numeroCtps,
                   TipoPessoa tipoPessoa){
        this.nomeCompleto = nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.numeroCtps = numeroCtps;
        this.dataNascimento = dataNascimento;
        this.tipoPessoa = tipoPessoa;

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
}
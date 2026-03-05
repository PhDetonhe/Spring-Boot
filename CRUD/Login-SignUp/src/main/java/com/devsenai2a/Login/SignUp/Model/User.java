package com.devsenai2a.Login.SignUp.Model;

//É o objeto da aplicação

import com.devsenai2a.Login.SignUp.Enums.Perfil;
import jakarta.persistence.*;


@Entity
@Table(name = "usuarios") //representa a tabela dentro do banco de dados
public class User {

    @Id//reconhece a variavel como id, um IDentificador do objeto que vai ser instanciado por essa classe
    @GeneratedValue (strategy = GenerationType.IDENTITY)//Esse id precisa ser gerado de alguma forma, aqui passamos como ele vai ser formado, de preferencia, deve ser formado de forma automatica afim de poupar tempo
    private Integer id;
    private String name;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)//diz ao JPA para salvar um enum dentro do banco de dados, porém por padrão o enum vem como ordinal, o que salvaria as opções dentro dele como numeros, com .STRING ele salva os textos em si nesse caso.
    @Column(nullable = false)//indica ao banco que está coluna não pode ser nula, equivalente ao not null do MySQL
    private Perfil perfil;
    private String endereco;
    private String bairro;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;

    public User(Integer id, String name, String email, String senha, Perfil perfil, String endereco, String bairro, String complemento, String cep, String cidade, String estado) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.endereco = endereco;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}


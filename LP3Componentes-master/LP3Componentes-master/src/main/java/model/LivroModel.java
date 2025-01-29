package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "livro")
public class LivroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID pelo banco
    private Long id;

    //campos obrigatorios e seus limites de caracteres
    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String tema;

    @Column(nullable = false, length = 50)
    private String autor;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private LocalDate dataPublicacao;

    @Column(nullable = false)
    private int quantidadeDisponivel;

    // Construtor vazio obrigatório para JPA
    public LivroModel() {}

    public LivroModel(String titulo, String tema, String autor, String isbn, LocalDate dataPublicacao, int quantidadeDisponivel) {
        this.titulo = titulo;
        this.tema = tema;
        this.autor = autor;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    @Override
    public String toString() {
        return "LivroModel{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tema='" + tema + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                '}';
    }
}

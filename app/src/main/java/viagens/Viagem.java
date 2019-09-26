package viagens;

public class Viagem {
    private String cidade;
    private int foto;
    private String descricao;
    private String pais;

    public Viagem(String cidade, int foto, String descricao, String pais) {
        this.cidade = cidade;
        this.pais = pais;
        this.foto = foto;
        this.descricao = descricao;
    }

    public int getFoto() {
        return foto;
    }

    public String getCidade() {
        return cidade;
    }

    public String getPais() { return pais; }

    public String getDescricao() {
        return descricao;
    }
}

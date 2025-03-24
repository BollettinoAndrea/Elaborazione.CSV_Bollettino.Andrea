package elaborazioneCSV;

public class Record {
    private String comune, provincia, regione, categoria, tipologia, nome, indirizzo, località, sigla_provincia, sito_internet, telefono, email, codice;
    private int longitudine, latitudine;

    public Record(String comune, String provincia, String regione, String categoria, String tipologia, String nome, String indirizzo, String località, String sigla_provincia, String sito_internet, String telefono, String email, String codice, int longitudine, int latitudine) {
        this.comune = comune;
        this.provincia = provincia;
        this.regione = regione;
        this.categoria = categoria;
        this.tipologia = tipologia;
        this.nome = nome;
        this.sigla_provincia = sigla_provincia;
        this.sito_internet = sito_internet;
        this.telefono = telefono;
        this.email = email;
        this.codice = codice;
        this.longitudine = longitudine;
        this.latitudine = latitudine;

    }

    public String getComune() { return comune; }
    public void setComune(String comune) { this.comune = comune; }

    public String getProvincia () { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getRegione() { return regione; }
    public void setRegione(String regione) { this.regione = regione; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getTipologia () { return tipologia; }
    public void setTipologia(String tipologia) { this.tipologia = tipologia; }

    public String getNome () { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getIndirizzo () { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getLocalità () { return località; }
    public void setLocalità(String località) { this.località = località; }

    public String getSigla_provincia () { return sigla_provincia; }
    public void setSigla_provincia(String sigla_provincia) { this.sigla_provincia = sigla_provincia; }

    public String getSito_internet() { return sito_internet; }
    public void setSito_internet(String sito_internet) { this.sito_internet = sito_internet; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }

    public int getLongitudine() { return longitudine; }
    public void setLongitudine(int longitudine) { this.longitudine = longitudine; }

    public int getLatitudine () { return latitudine; }
    public void setLatitudine(int latitudine) { this.latitudine = latitudine; }

    @Override
    public String toString() {
        return comune+";"+provincia+";"+regione+";"+categoria+";"+tipologia+";"+nome+";"+indirizzo+";"+località+";"+sigla_provincia+";"+sito_internet+";"+telefono+";"+email+";"+codice+";"+longitudine+";"+latitudine;
    }
}
package bankmanager.datamanagement;

public class User {
    private Integer idutilisateur;
    private String nomUtilisateur;
    private String email;
    private String motDePasse;

    public Integer getIduser() {
        return idutilisateur;
    }

    public void setIduser(Integer iduser) {
        this.idutilisateur = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPassword() {
        return motDePasse;
    }

    public void setPassword(String password) {
        this.motDePasse = password;
    }
}

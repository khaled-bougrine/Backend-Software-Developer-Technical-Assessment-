package pro.hosteur.T_Assessment._K_bougrine;

public class Mail {
    private String id ;
    private String email;

    public Mail(String email){
        this.id=email;
        this.email=email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

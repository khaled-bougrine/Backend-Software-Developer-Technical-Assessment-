package pro.hosteur.T_Assessment._K_bougrine;

import java.util.List;

public class Model {
    private List<Mail> data;

    public Model(List<Mail> emails) {
        this.data = emails;
    }

    public void setData(List<Mail> data) {
        this.data = data;
    }

    public List<Mail> getData() {
        return data;
    }
}

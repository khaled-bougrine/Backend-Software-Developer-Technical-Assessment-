package pro.hosteur.T_Assessment._K_bougrine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resources;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoValidEmail extends RuntimeException {
    public NoValidEmail(String message) {
        super(message);
    }
}

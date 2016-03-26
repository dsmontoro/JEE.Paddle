package business.api.exceptions;

public class InvalidCourtTrainingException extends ApiException{

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Entrenamiento de pista inválida";

    public static final int CODE = 1;

    public InvalidCourtTrainingException() {
        this("");
    }

    public InvalidCourtTrainingException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}

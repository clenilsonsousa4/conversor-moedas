package exceptions;

public class FinishedByUserException extends Exception {
    @Override
    public String getMessage() {
        return "Process Finished By The User";
    }
}

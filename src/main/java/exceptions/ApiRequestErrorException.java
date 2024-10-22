package exceptions;

public class ApiRequestErrorException extends Exception {
    @Override
    public String getMessage() {
        return "Unable to check current currency value. Please try again in a few minutes.";
    }
}

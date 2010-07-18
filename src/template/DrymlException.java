package template;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class DrymlException extends RuntimeException {
    public DrymlException() {
    }

    public DrymlException(String message) {
        super(message);
    }

    public DrymlException(String message, Throwable cause) {
        super(message, cause);
    }

    public DrymlException(Throwable cause) {
        super(cause);
    }
}

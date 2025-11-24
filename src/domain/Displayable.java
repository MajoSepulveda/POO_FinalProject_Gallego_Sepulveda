package src.domain;

/**
 * Interface to establish a contract for all domain objects
 * that need to provide a summary string for console display.
 */
public interface Displayable {
    /**
     * Retrieves a concise and formatted string representation of the object 
     * intended for user output (not for debugging).
     * @return The formatted summary string.
     */
    String displayObject();
}

package fhtw.bsa1.projects.extras;

public class Util {
    /**	vt100 escape sequences*/
    public static final String ESC = "\033";
    /** cursor home:  `[H` - Move cursor to upper left corner. */
    public static final String CURSORHOME = "[H";
    /** clear screen: `[2J` - Clear entire screen. */
    public static final String CLEARSCREEN = "[2J";

    /**
     * `private` Constructor to prevent instantiations of this utility class.
     */
    private Util() {}

    /**
     * Clears the terminal screen (supporting vt100 commands).
     */
    public static void clearScreen() {
        System.out.print(ESC + CURSORHOME + ESC + CLEARSCREEN);
        System.out.flush();
    }
}

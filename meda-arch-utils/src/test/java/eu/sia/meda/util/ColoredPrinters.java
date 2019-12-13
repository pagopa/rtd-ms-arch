package eu.sia.meda.util;

/** To print a colored message into the stdin */
public enum ColoredPrinters {

    PRINT_BLACK(TestUtils.ANSI_BLACK, TestUtils.ANSI_BRIGHT_BLACK),
    PRINT_RED(TestUtils.ANSI_RED, TestUtils.ANSI_BRIGHT_RED),
    PRINT_GREEN(TestUtils.ANSI_GREEN, TestUtils.ANSI_BRIGHT_GREEN),
    PRINT_YELLOW(TestUtils.ANSI_YELLOW, TestUtils.ANSI_BRIGHT_YELLOW),
    PRINT_BLUE(TestUtils.ANSI_BLUE, TestUtils.ANSI_BRIGHT_BLUE),
    PRINT_PURPLE(TestUtils.ANSI_PURPLE, TestUtils.ANSI_BRIGHT_PURPLE),
    PRINT_CYAN(TestUtils.ANSI_CYAN, TestUtils.ANSI_BRIGHT_CYAN),
    PRINT_WHITE(TestUtils.ANSI_WHITE, TestUtils.ANSI_BRIGHT_WHITE);

    private final String color;
    private final String brightColor;
    ColoredPrinters(String color, String brightColor){
        this.color=color;
        this.brightColor=brightColor;
    }

    public void print(String msg){
        print(msg, false);
    }

    public void printBright(String msg){
        print(msg, true);
    }

    public void print(String msg, boolean bright){
        System.out.printf("%s%s%s", bright?brightColor:color, msg, TestUtils.ANSI_RESET);
    }

    public void println(String msg){
        println(msg, false);
    }

    public void printlnBright(String msg){
        println(msg, true);
    }

    public void println(String msg, boolean bright){
        print(String.format("%s%n", msg), bright);
    }
}

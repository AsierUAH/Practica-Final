package practicafinal.app;

import java.io.PrintStream;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        PrintStream original = System.err;
        System.setErr(new PrintStream(original) {
            public void println(String s) {
                if (s != null && s.contains("Unsupported JavaFX configuration")) return;
                super.println(s);
            }
        });
        Application.launch(App.class, args);
    }
}

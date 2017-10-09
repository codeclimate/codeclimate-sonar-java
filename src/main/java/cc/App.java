package cc;

import org.sonarlint.cli.CustomMain;
import org.sonarlint.cli.util.System2;

public class App {
    public static void main(String[] args) {
        execute(args, System2.INSTANCE);
    }

    public static void execute(String[] args, System2 system) {
        CustomMain.execute(args, system);
    }
}

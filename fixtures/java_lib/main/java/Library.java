/*
 * This Java source file was generated by the Gradle 'init' task.
 */
// FIXME

import java.util.List;
import java.util.Set;

public class Library {
  List myList; // Noncompliant
  Set mySet; // Noncompliant

  public static void main(String[] args) {
  }

	public void foo() {
		for (int i = 1; i != 10; i += 2) {
			for (int k = 0; k < 20; k++) {
				System.out.println("Hello");
			}
		}
	}

        public boolean bar() {
          String multi = """
              this is a single line string""";
          String textBlock = """
                \"\"\" this \nis
                text  block!
                !!!!
              """;
          Pattern p = Pattern.compile(".*|a");
          Matcher m = p.matcher(multi);
          return m.matches();
        }
}

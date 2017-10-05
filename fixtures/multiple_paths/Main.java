import pkg1;

public class Main {
    public void main(String[] args) {
        for (int k = 0; k < 20; i++) {  // cause issue
            System.out.println(new Class1());
        }
    }
}
public class Example02 {
    public void foo(int x, int y) {
        if (x > 0) {
            System.out.println("Positive");
            for (int i = 0; i < x; i++) {
                if (i % 2 == 0) {
                    System.out.println("Even");
                } else {
                    System.out.println("Odd");
                }
            }
        } else if (x < 0) {
            System.out.println("Negative");
        } else {
            System.out.println("Zero");
        }
    }
}

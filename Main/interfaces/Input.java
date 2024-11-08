package interfaces;

import java.util.Scanner;

public interface Input {
    default String getString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

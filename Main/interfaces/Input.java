package interfaces;

import java.util.Scanner;

public interface Input {
    Scanner scanner = new Scanner(System.in);

    default String getString() {
        return scanner.nextLine();
    }
}



package com._3JLOYBOJLK;

import java.util.Scanner;

public class Menu {

    public void show() {
        System.out.println("=== FILMOTEKA ===\n" +
                "1. \uD83D\uDCCB Show current collection movies\n" +
                "2. \uD83D\uDCCB Show collection movies from File\n" +
                "3. ➕ Add movie\n" +
                "4. ❌ Delete movie\n" +
                "5. \uD83D\uDD0D Search by director (Current collection movies)\n" +
                "6. \uD83D\uDD0D Search by director (Collection movies from file)\n" +
                "7. \uD83D\uDCBE Load collection from file\n" +
                "8. \uD83D\uDCC2 Save collection to file\n" +
                "9. \uD83D\uDEAA Exit");
    }

    public int getChoice(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}

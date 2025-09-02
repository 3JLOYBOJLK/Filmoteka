package com._3JLOYBOJLK;

import java.util.Scanner;

public class Menu {

    public void show() {
        System.out.println("=== FILMOTEKA ===\n" +
                "1. \uD83D\uDCCB Show collection movies\n" +
                "2. ➕ Add movie\n" +
                "3. ❌ Remove movie\n" +
                "4. \uD83D\uDD0D Search by director\n" +
                "5. \uD83D\uDCBE Load collection\n" +
                "6. \uD83D\uDCC2 Save collection\n" +
                "7. \uD83D\uDDD1\uFE0F Delete collection\n" +
                "8. \uD83D\uDEAA Exit");
    }

    public int getChoice(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}

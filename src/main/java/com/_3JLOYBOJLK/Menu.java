package com._3JLOYBOJLK;

import java.util.Scanner;

public class Menu {

    public void show() {
        System.out.println("=== FILMOTEKA ===\n" +
                "1. \uD83D\uDCCB Показать все фильмы\n" +
                "2. ➕ Добавить фильм\n" +
                "3. ❌ Удалить фильм\n" +
                "4. \uD83D\uDD0D Найти по режиссеру\n" +
                "5. \uD83D\uDCBE Загрузить из файла\n" +
                "6. \uD83D\uDCC2 Сохранить в файл\n" +
                "7. \uD83D\uDEAA Выход");
    }

    public int getChoice(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}

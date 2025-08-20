package com._3JLOYBOJLK;

public class ColorPrinter {

    public static void printlnDefault(String message) {
        System.out.println(ConsoleColors.BLACK_BOLD + message + ConsoleColors.RESET);
    }

    public static String colorize(Object text, String color) {
        String colorCode;
        switch (color.toUpperCase()) {
            case "RED": colorCode = ConsoleColors.RED; break;
            case "GREEN": colorCode = ConsoleColors.GREEN; break;
            case "BLUE": colorCode = ConsoleColors.BLUE; break;
            default: colorCode = ConsoleColors.BLACK_BOLD;
        }
        return colorCode + text + ConsoleColors.RESET;
    }

    public static void printlnFullColored(Object text, String color) {
        System.out.println(colorize(text, color));
    }
}
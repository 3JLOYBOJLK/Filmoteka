package com._3JLOYBOJLK;

import java.nio.file.Paths;

public class AppConfiguration {
    public static final String CURRENT_COLLECTION_DIR = "src/main/resources/";
    public static final String FILE_COLLECTION_DIR = "src/main/CreatablesFiles/";
    public static final String defaultPath = Paths.get(CURRENT_COLLECTION_DIR+"movies.csv").toAbsolutePath().toString();
    public static final String CANCELLED = "CANCELLED";
}


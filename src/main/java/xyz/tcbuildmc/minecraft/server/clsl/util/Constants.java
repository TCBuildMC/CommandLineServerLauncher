package xyz.tcbuildmc.minecraft.server.clsl.util;

import xyz.tcbuildmc.common.util.base.ObjectUtils;

import java.io.File;

public final class Constants {
    public static final class LauncherProperty {
        public static final String NAME = "CLSL";

        public static final String VERSION = ObjectUtils.requiresNonNullOrElse(Constants.LauncherProperty.class.getPackage().getImplementationVersion(),
                "0.0.0+known.0");

        public static final String CONFIG_FILE_NAME = "config.toml";

        public static final File CONFIG_PATH = new File("config");

        public static final File CONFIG_FILE = new File(LauncherProperty.CONFIG_PATH, LauncherProperty.CONFIG_FILE_NAME);

        public static final String CONFIG_VERSION = "1.0";
    }

    public static final class EnvironmentProperty {
        public static final int JVM_VERSION_REQUIRED = 8;

        public static final long MEMORY_REQUIRED = 512L;
    }
}

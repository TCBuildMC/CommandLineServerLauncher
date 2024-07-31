package xyz.tcbuildmc.minecraft.server.clsl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.DefaultParsers;
import xyz.tcbuildmc.common.i18n.Translations;
import xyz.tcbuildmc.minecraft.server.clsl.command.CommandManager;
import xyz.tcbuildmc.minecraft.server.clsl.command.CommandResult;
import xyz.tcbuildmc.minecraft.server.clsl.config.CLSLConfig;
import xyz.tcbuildmc.minecraft.server.clsl.extension.ExtensionManager;
import xyz.tcbuildmc.minecraft.server.clsl.util.Constants;

import java.util.Map;

public class CLSLMain {
    public static final Logger log = LoggerFactory.getLogger(Constants.LauncherProperty.NAME);
    public static CLSLConfig config;
    private static boolean running = false;
    public static CommandManager cm;
    public static ExtensionManager em;

    public static void main(String[] args) {
        log.info("{}: {}", Constants.LauncherProperty.NAME, Constants.LauncherProperty.VERSION);

        // Start
        config = initConfig();

        initI18n(config.getLanguage());
        log.info(Translations.getTranslation("init.language"));

        checkJvmVersion();
        checkJvmMemory();

        running = true;

        // do stuff...
        cm = new CommandManager();

        em = new ExtensionManager();

        if (args.length > 1) {
            String[] otherArgs = new String[args.length - 1];
            for (int i = 0; i < args.length - 1; i++) {
                otherArgs[i] = args[i + 1];
            }
            cm.runCommand(args[0], otherArgs);
        } else if (args.length == 1) {
            cm.runCommand(args[0], (String[]) null);
        }

        // Shutdown
        running = false;
        ConfigApi.getInstance().write(CLSLConfig.class, new CLSLConfig(), Constants.LauncherProperty.CONFIG_FILE, DefaultParsers.toml4j(true));

        System.exit(0);
    }

    public static void checkJvmVersion() {
        int version = (int) (Float.parseFloat(System.getProperty("java.class.version", "0"))) - 44;

        if (version < Constants.EnvironmentProperty.JVM_VERSION_REQUIRED) {
            log.error(Translations.getTranslation("err.env.jvm_version", Constants.EnvironmentProperty.JVM_VERSION_REQUIRED));
            System.exit(1);
        }
    }

    public static void checkJvmMemory() {
        if (config.isIgnoreMemoryCheck()) {
            return;
        }

        long maxMemory = Runtime.getRuntime().maxMemory() >> 20;

        if (maxMemory < Constants.EnvironmentProperty.MEMORY_REQUIRED) {
            log.error(Translations.getTranslation("err.env.jvm_memory", Constants.EnvironmentProperty.MEMORY_REQUIRED));
            System.exit(2);
        }
    }

    public static void initI18n(@NotNull String languageProvided) {
        Translations.addLanguageSupport("zh_cn");

        String lang = Translations.getLocalLanguage();
        Map<String, String> translations = Translations.getTranslationsFromClasspath("lang",
                (languageProvided.isEmpty() ? lang : languageProvided),
                "json",
                DefaultParsers.gson());
        Translations.setTranslations(translations);
    }

    public static CLSLConfig initConfig() {
        if (!Constants.LauncherProperty.CONFIG_FILE.exists()) {
            if (!Constants.LauncherProperty.CONFIG_PATH.exists()) {
                Constants.LauncherProperty.CONFIG_PATH.mkdirs();
            }

            ConfigApi.getInstance().write(CLSLConfig.class, new CLSLConfig(), Constants.LauncherProperty.CONFIG_FILE, DefaultParsers.toml4j(true));
        }

        return ConfigApi.getInstance().read(CLSLConfig.class, Constants.LauncherProperty.CONFIG_FILE, DefaultParsers.toml4j(true));
    }

    @Contract(pure = true)
    public static boolean isRunning() {
        return running;
    }
}

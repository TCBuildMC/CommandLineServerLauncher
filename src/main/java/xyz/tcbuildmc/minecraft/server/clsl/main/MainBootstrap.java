package xyz.tcbuildmc.minecraft.server.clsl.main;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.tcbuildmc.common.config.api.ConfigApi;
import xyz.tcbuildmc.common.config.api.parser.DefaultParsers;
import xyz.tcbuildmc.common.i18n.Translations;
import xyz.tcbuildmc.minecraft.server.clsl.command.CommandManager;
import xyz.tcbuildmc.minecraft.server.clsl.command.HelpCommand;
import xyz.tcbuildmc.minecraft.server.clsl.command.ShutdownCommand;
import xyz.tcbuildmc.minecraft.server.clsl.config.CLSLConfig;
import xyz.tcbuildmc.minecraft.server.clsl.util.Constants;

import java.util.Map;
import java.util.Scanner;

public class MainBootstrap {
    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.LauncherProperty.NAME);

    public static boolean RUNNING = false;
    public static CLSLConfig CONFIG;
    public static CommandManager COMMAND_MANAGER;

    public static void main(String[] args) {
        LOGGER.info("{}: {}", Constants.LauncherProperty.NAME, Constants.LauncherProperty.VERSION);

        CONFIG = initConfig();

        initI18n(CONFIG.getLanguage());
        LOGGER.info(Translations.getTranslation("init.language"));

        checkJvmVersion();
        checkJvmMemory();

        COMMAND_MANAGER = new CommandManager();
        COMMAND_MANAGER.registerCommand(new HelpCommand());
        COMMAND_MANAGER.registerCommand(new ShutdownCommand());

        Scanner scanner = new Scanner(System.in);

        RUNNING = true;
        for (;;) {
            String text = scanner.next();
            COMMAND_MANAGER.call(text);
        }
    }

    public static void checkJvmVersion() {
        int version = (int) (Float.parseFloat(System.getProperty("java.class.version", "0"))) - 44;

        if (version < Constants.EnvironmentProperty.JVM_VERSION_REQUIRED) {
            LOGGER.error(Translations.getTranslation("err.env.jvm_version", Constants.EnvironmentProperty.JVM_VERSION_REQUIRED));
            System.exit(1);
        }
    }

    public static void checkJvmMemory() {
        if (CONFIG.isIgnoreMemoryCheck()) {
            return;
        }

        long maxMemory = Runtime.getRuntime().maxMemory() >> 20;

        if (maxMemory < Constants.EnvironmentProperty.MEMORY_REQUIRED) {
            LOGGER.error(Translations.getTranslation("err.env.jvm_memory", Constants.EnvironmentProperty.MEMORY_REQUIRED));
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

    public static void shutdown() {
        LOGGER.info(Translations.getTranslation("shutdown"));
        RUNNING = false;
        ConfigApi.getInstance().write(CLSLConfig.class, new CLSLConfig(), Constants.LauncherProperty.CONFIG_FILE, DefaultParsers.toml4j(true));
    }
}

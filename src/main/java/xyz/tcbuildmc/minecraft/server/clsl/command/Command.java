package xyz.tcbuildmc.minecraft.server.clsl.command;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.tcbuildmc.common.i18n.Translations;
import xyz.tcbuildmc.minecraft.server.clsl.main.MainBootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
public abstract class Command {
    private final String name;

    private final List<String> aliases;

    private final Logger commandLogger;

    public Command(String name) {
        this(name, new ArrayList<>());
    }

    public Command(String name, List<String> aliases) {
        this(name, aliases, LoggerFactory.getLogger(name.toLowerCase(Locale.ROOT)));
    }

    @Contract(pure = true)
    private Command(@NotNull String name, List<String> aliases, Logger commandLogger) {
        this.name = name.toLowerCase(Locale.ROOT);
        this.aliases = aliases;
        this.commandLogger = commandLogger;
    }

    public abstract CommandResult execute();

    public void handleFailResult() {
        MainBootstrap.LOGGER.error(Translations.getTranslation("command.fail"));
    }
}

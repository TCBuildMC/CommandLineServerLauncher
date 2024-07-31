package xyz.tcbuildmc.minecraft.server.clsl.command;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import xyz.tcbuildmc.common.i18n.Translations;
import xyz.tcbuildmc.minecraft.server.clsl.main.MainBootstrap;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandManager {
    private final List<Command> commands;

    @Contract(pure = true)
    public CommandManager(List<Command> commands) {
        this.commands = commands;
    }

    public CommandManager() {
        this(new ArrayList<>());
    }

    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    public void unregisterCommand(Command command) {
        this.commands.remove(command);
    }

    public void call(String name) {
        for (Command command : this.commands) {
            if (command.getName().equals(name)) {
                CommandResult result = command.execute();

                if (result.isFailed()) {
                    command.handleFailResult();
                }
                return;
            }

            for (String alias : command.getAliases()) {
                if (alias.equals(name)) {
                    CommandResult result = command.execute();

                    if (result.isFailed()) {
                        command.handleFailResult();
                    }
                    return;
                }
            }
        }

        MainBootstrap.LOGGER.error(Translations.getTranslation("command.not_found"));
    }
}

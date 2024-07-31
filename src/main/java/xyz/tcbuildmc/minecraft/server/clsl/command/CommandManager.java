package xyz.tcbuildmc.minecraft.server.clsl.command;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import xyz.tcbuildmc.common.i18n.Translations;
import xyz.tcbuildmc.minecraft.server.clsl.CLSLMain;

import java.util.ArrayList;
import java.util.List;

public final class CommandManager {
    private final List<Command> commands;

    @Contract(pure = true)
    private CommandManager(List<Command> commands) {
        this.commands = commands;
    }

    public CommandManager() {
        this(new ArrayList<>());
    }

    public void register(Command c) {
        this.commands.add(c);
    }

    public void unregister(Command c) {
        this.commands.removeIf(o -> o.equals(c));
    }

    public void runCommand(String name, @Nullable String... args) {
        for (Command c : this.commands) {
            if (c.getMergedOptions().contains(name)) {
                CommandResult result = c.execute(args);

                if (result.isFailed()) {
                    c.handleFailedResult();
                }

                return;
            }
        }

        CLSLMain.log.error(Translations.getTranslation("command.not_found"));
    }
}

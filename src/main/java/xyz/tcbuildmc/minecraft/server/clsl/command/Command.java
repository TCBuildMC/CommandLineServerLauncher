package xyz.tcbuildmc.minecraft.server.clsl.command;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    private final String name;
    private final List<String> aliases;

    @Contract(pure = true)
    public Command(String name, List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public abstract CommandResult execute(@Nullable String... args);

    public abstract void handleFailedResult();

    public boolean hasAliases() {
        return this.aliases != null && !this.aliases.isEmpty();
    }

    public List<String> getMergedOptions() {
        List<String> list = new ArrayList<>();
        list.add(this.name);
        if (this.hasAliases()) {
            list.addAll(this.aliases);
        }
        return list;
    }
}

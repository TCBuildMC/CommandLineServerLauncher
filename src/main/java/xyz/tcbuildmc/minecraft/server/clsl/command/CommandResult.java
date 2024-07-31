package xyz.tcbuildmc.minecraft.server.clsl.command;

import org.jetbrains.annotations.Contract;

public enum CommandResult {
    SUCCESS,
    FAIL;

    @Contract(pure = true)
    public static CommandResult success(boolean b) {
        return (b ? CommandResult.SUCCESS : CommandResult.FAIL);
    }

    @Contract(pure = true)
    public boolean isFailed() {
        return this == CommandResult.FAIL;
    }
}

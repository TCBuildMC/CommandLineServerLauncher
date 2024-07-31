package xyz.tcbuildmc.minecraft.server.clsl.command;

import org.jetbrains.annotations.Contract;

public enum CommandResult {
    SUCCESS,
    FAIL;

    @Contract(pure = true)
    public boolean isFailed() {
        return this == CommandResult.FAIL;
    }
}

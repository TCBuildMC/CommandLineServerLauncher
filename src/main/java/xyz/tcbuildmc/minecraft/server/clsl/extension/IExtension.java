package xyz.tcbuildmc.minecraft.server.clsl.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.tcbuildmc.minecraft.server.clsl.CLSLMain;
import xyz.tcbuildmc.minecraft.server.clsl.command.CommandManager;

public interface IExtension {
    void load();

    default void unload() {}

    default void reload() {}

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    default CommandManager getCommandManager() {
        return CLSLMain.cm;
    }
}

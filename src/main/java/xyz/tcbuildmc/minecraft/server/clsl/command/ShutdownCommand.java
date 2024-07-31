package xyz.tcbuildmc.minecraft.server.clsl.command;

import xyz.tcbuildmc.common.util.collection.CollectionUtils;
import xyz.tcbuildmc.minecraft.server.clsl.main.MainBootstrap;

public class ShutdownCommand extends Command {
    public ShutdownCommand() {
        super("shutdown", CollectionUtils.ofArrayList("exit", "end", "stop"));
    }

    @Override
    public CommandResult execute() {
        MainBootstrap.shutdown();
        return CommandResult.FAIL;
    }

    @Override
    public void handleFailResult() {
        System.exit(0);
    }
}

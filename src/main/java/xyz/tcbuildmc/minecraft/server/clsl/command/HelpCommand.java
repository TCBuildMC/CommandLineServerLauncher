package xyz.tcbuildmc.minecraft.server.clsl.command;

import xyz.tcbuildmc.common.util.collection.CollectionUtils;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", CollectionUtils.ofArrayList("h"));
    }

    @Override
    public CommandResult execute() {
        return CommandResult.SUCCESS;
    }
}

package xyz.tcbuildmc.minecraft.server.clsl.extension;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public final class ExtensionManager {
    private final List<IExtension> extensions;

    @Contract(pure = true)
    private ExtensionManager(List<IExtension> extensions) {
        this.extensions = extensions;
    }

    public ExtensionManager() {
        this(new ArrayList<>());
    }

    public void register(IExtension e) {
        if (!this.extensions.contains(e)) {
            this.extensions.add(e);
        }
    }
}

package xyz.tcbuildmc.minecraft.server.clsl.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.tcbuildmc.common.config.api.annotation.TomlComment;

@Data
@NoArgsConstructor
public class CLSLConfig {
    @TomlComment("The Language of CLSL. Leave it empty that CLSL will auto detect your System Language.")
    private String language = "";

    @TomlComment("Ignore the memory check. This is an advanced setting. (Unedifying is recommended)")
    private boolean ignoreMemoryCheck = false;
}

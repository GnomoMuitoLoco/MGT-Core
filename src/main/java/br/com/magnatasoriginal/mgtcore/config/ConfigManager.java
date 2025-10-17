package br.com.magnatasoriginal.mgtcore.config;

import br.com.magnatasoriginal.mgtcore.MGTCore;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigManager {

    public static final ModConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        COMMON = new CommonConfig(builder);
        COMMON_SPEC = builder.build();
    }
    @SuppressWarnings("unused")
    public static void register() {
        // Registro correto em NeoForge 21.1.211
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        MGTCore.LOGGER.info("[MGT-Core] Configuração registrada com sucesso.");
    }

    public static class CommonConfig {
        public final ModConfigSpec.ConfigValue<String> serverName;
        public final ModConfigSpec.BooleanValue debugEnabled;

        CommonConfig(ModConfigSpec.Builder builder) {
            builder.push("general");

            serverName = builder
                    .comment("Nome do servidor exibido em mensagens")
                    .define("serverName", "Servidor Magnatas");

            debugEnabled = builder
                    .comment("Ativa logs de debug do MGT-Core")
                    .define("debugEnabled", false);

            builder.pop();
        }
    }
}

package br.com.magnatasoriginal.mgtcore;

import br.com.magnatasoriginal.mgtcore.config.ConfigManager;
import br.com.magnatasoriginal.mgtcore.events.CoreEvents;
import br.com.magnatasoriginal.mgtcore.events.types.ServerOfflineEvent;
import br.com.magnatasoriginal.mgtcore.events.types.ServerOnlineEvent;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;

/**
 * MGT-Core
 * Núcleo de utilitários, eventos e serviços para os mods da família MGT.
 * Este mod não adiciona conteúdo visível, apenas fornece infraestrutura.
 */
@Mod(MGTCore.MODID)
public class MGTCore {
    public static final String MODID = "mgtcore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MGTCore(IEventBus modEventBus) {
        LOGGER.info("[MGT-Core] Inicializando núcleo...");

        // Registrar config (usa o SPEC definido no ConfigManager)
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_SPEC);

        // Registrar listeners do ciclo de vida
        modEventBus.addListener(this::commonSetup);

        // Registrar eventos globais (servidor, etc.)
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("[MGT-Core] Common setup executado.");

        // Agora é seguro acessar configs
        String serverName = ConfigManager.COMMON.serverName.get();
        boolean debug = ConfigManager.COMMON.debugEnabled.get();

        LOGGER.info("[MGT-Core] Nome do servidor: {}", serverName);
        if (debug) {
            LOGGER.debug("[MGT-Core] Debug ativado!");
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("[MGT-Core] Servidor está iniciando...");
        CoreEvents.post(new ServerOnlineEvent(event.getServer()));
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        LOGGER.info("[MGT-Core] Servidor está desligando...");
        CoreEvents.post(new ServerOfflineEvent(event.getServer()));
    }
}

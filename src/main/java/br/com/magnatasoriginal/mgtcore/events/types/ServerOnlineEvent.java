package br.com.magnatasoriginal.mgtcore.events.types;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.Event;

/**
 * Evento disparado quando o servidor é iniciado e está online.

 * - Publicado pelo MGT-Core no onServerStarting
 * - Pode ser consumido por outros mods para inicializar integrações
 */
@SuppressWarnings("unused")
public class ServerOnlineEvent extends Event {

    private final MinecraftServer server;

    @SuppressWarnings("unused")
    public ServerOnlineEvent(MinecraftServer server) {
        this.server = server;
    }

    /**
     * Retorna a instância do servidor que acabou de iniciar.
     */
    @SuppressWarnings("unused")
    public MinecraftServer getServer() {
        return server;
    }
}

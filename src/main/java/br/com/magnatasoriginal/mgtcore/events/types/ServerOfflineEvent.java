package br.com.magnatasoriginal.mgtcore.events.types;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.Event;

/**
 * Evento disparado quando o servidor está desligando.

 * - Publicado pelo MGT-Core no onServerStopping
 * - Pode ser consumido por outros mods para encerrar conexões ou salvar dados
 */
@SuppressWarnings("unused")
public class ServerOfflineEvent extends Event {

    private final MinecraftServer server;

    @SuppressWarnings("unused")
    public ServerOfflineEvent(MinecraftServer server) {
        this.server = server;
    }

    /**
     * Retorna a instância do servidor que está desligando.
     */
    @SuppressWarnings("unused")
    public MinecraftServer getServer() {
        return server;
    }
}

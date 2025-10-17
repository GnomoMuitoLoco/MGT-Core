package br.com.magnatasoriginal.mgtcore.events.types;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;

/**
 * Evento disparado quando um jogador envia uma mensagem no chat global.

 * - Publicado pelo MGT-Chat
 * - Consumido por outros mods (ex: MGT-Discord)
 */
@SuppressWarnings("unused")
public class GlobalChatMessageEvent extends Event {

    private final ServerPlayer player;
    private final String message;

    /**
     * Cria um novo evento de mensagem global.
     *
     * @param player  Jogador que enviou a mensagem
     * @param message Conteúdo da mensagem
     */
    @SuppressWarnings("unused")
    public GlobalChatMessageEvent(ServerPlayer player, String message) {
        this.player = player;
        this.message = message;
    }

    /**
     * Retorna o jogador que enviou a mensagem.
     */
    @SuppressWarnings("unused")
    public ServerPlayer getPlayer() {
        return player;
    }

    /**
     * Retorna o conteúdo da mensagem enviada.
     */
    @SuppressWarnings("unused")
    public String getMessage() {
        return message;
    }
}

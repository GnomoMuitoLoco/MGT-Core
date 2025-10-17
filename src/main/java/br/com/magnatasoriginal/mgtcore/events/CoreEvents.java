package br.com.magnatasoriginal.mgtcore.events;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

/**
 * CoreEvents

 * Esta classe centraliza o acesso ao Event Bus interno do NeoForge,
 * permitindo que os mods MGT publiquem e escutem eventos
 * personalizados sem depender diretamente uns dos outros.

 * Exemplo de uso:
 *   - Publicar evento: CoreEvents.post(new GlobalChatMessageEvent(player, msg));
 *   - Registrar listener: CoreEvents.register(new MeuListener());
 */
public class CoreEvents {

    // Usamos o Event Bus global do NeoForge (NeoForge.EVENT_BUS).
    // Ele é thread-safe e já integrado ao ciclo de vida do servidor.
    private static final IEventBus EVENT_BUS = NeoForge.EVENT_BUS;

    /**
     * Publica um evento no barramento.
     * Todos os listeners registrados que escutam esse tipo de evento
     * serão notificados.
     *
     * @param event Instância do evento a ser disparado
     */
    public static void post(Event event) {
        EVENT_BUS.post(event);
    }

    /**
     * Registra um listener no barramento.
     * O listener pode ser uma classe com métodos anotados com @SubscribeEvent.
     *
     * @param listener Instância do listener
     */
    public static void register(Object listener) {
        EVENT_BUS.register(listener);
    }

    /**
     * Retorna o Event Bus interno, caso algum mod precise
     * registrar diretamente nele.
     *
     * @return IEventBus do NeoForge
     */
    public static IEventBus getBus() {
        return EVENT_BUS;
    }
}

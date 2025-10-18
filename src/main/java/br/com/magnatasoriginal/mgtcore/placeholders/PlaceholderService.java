package br.com.magnatasoriginal.mgtcore.placeholders;

import br.com.magnatasoriginal.mgtcore.config.ConfigManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * PlaceholderService

 * Serviço central para resolver placeholders em mensagens.
 * Exemplo de uso:
 *   String msg = PlaceholderService.resolve("Olá {player}, bem-vindo ao {server_name}!", player);
 */
@SuppressWarnings("unused")
public class PlaceholderService {

    // Placeholders simples (baseados em um jogador)
    private static final Map<String, Function<ServerPlayer, String>> SIMPLE_PLACEHOLDERS = new HashMap<>();

    // Placeholders de contexto (envolvem remetente, destinatário e mensagem)
    private static final Map<String, TriFunction<ServerPlayer, ServerPlayer, String, String>> CONTEXT_PLACEHOLDERS = new HashMap<>();

    static {
        // Placeholders padrão (simples)
        register("player", player -> player.getName().getString());
        register("uuid", player -> player.getUUID().toString());
        register("world", player -> player.level().dimension().location().toString());
        register("server_name", player -> ConfigManager.COMMON.serverName.get());

        // Placeholders de contexto (para tells, reply, etc.)
        registerContext("send_player", (sender, receiver, msg) -> sender.getName().getString());
        registerContext("receive_player", (sender, receiver, msg) -> receiver.getName().getString());
        registerContext("message", (sender, receiver, msg) -> msg);
    }

    /**
     * Registra um novo placeholder simples.
     */
    public static void register(String key, Function<ServerPlayer, String> value) {
        SIMPLE_PLACEHOLDERS.put(key.toLowerCase(), value);
    }

    /**
     * Registra um novo placeholder de contexto.
     */
    public static void registerContext(String key, TriFunction<ServerPlayer, ServerPlayer, String, String> value) {
        CONTEXT_PLACEHOLDERS.put(key.toLowerCase(), value);
    }

    /**
     * Resolve placeholders simples em uma string.
     */
    public static String resolve(String input, ServerPlayer player) {
        String result = input;
        for (Map.Entry<String, Function<ServerPlayer, String>> entry : SIMPLE_PLACEHOLDERS.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (result.contains(placeholder)) {
                result = result.replace(placeholder, entry.getValue().apply(player));
            }
        }
        return result;
    }

    /**
     * Resolve placeholders de contexto (ex: tells).
     */
    public static String resolveContext(String input, ServerPlayer sender, ServerPlayer receiver, String message) {
        String result = input;

        // Primeiro substitui placeholders de contexto
        for (Map.Entry<String, TriFunction<ServerPlayer, ServerPlayer, String, String>> entry : CONTEXT_PLACEHOLDERS.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (result.contains(placeholder)) {
                result = result.replace(placeholder, entry.getValue().apply(sender, receiver, message));
            }
        }

        // Depois aplica os simples (ex: {server_name}, {world}, etc.)
        result = resolve(result, sender);

        return result;
    }

    /**
     * Interface funcional para placeholders com 3 parâmetros.
     */
    @FunctionalInterface
    public interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }
}

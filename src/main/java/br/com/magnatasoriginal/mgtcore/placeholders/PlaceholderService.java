package br.com.magnatasoriginal.mgtcore.placeholders;

import br.com.magnatasoriginal.mgtcore.config.ConfigManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * PlaceholderService

 * Serviço central para resolver placeholders em mensagens.
 * Exemplo de uso:
 *   String msg = PlaceholderService.resolve("Olá {player}, bem-vindo ao {server_name}!", player);
 */
@SuppressWarnings("unused")
public class PlaceholderService {

    // Mapa de placeholders registrados dinamicamente
    private static final Map<String, Function<ServerPlayer, String>> PLACEHOLDERS = new HashMap<>();

    static {
        // Placeholders padrão
        register("player", player -> player.getName().getString());
        register("uuid", player -> player.getUUID().toString());
        register("world", player -> player.level().dimension().location().toString());
        register("server_name", player -> ConfigManager.COMMON.serverName.get());
    }

    /**
     * Registra um novo placeholder.
     *
     * @param key   Nome do placeholder (sem chaves)
     * @param value Função que gera o valor com base no jogador
     */
    public static void register(String key, Function<ServerPlayer, String> value) {
        PLACEHOLDERS.put(key.toLowerCase(), value);
    }

    /**
     * Resolve placeholders em uma string.
     *
     * @param input  Texto com placeholders (ex: "Olá {player}")
     * @param player Jogador de referência
     * @return Texto com placeholders substituídos
     */
    public static String resolve(String input, ServerPlayer player) {
        String result = input;
        for (Map.Entry<String, Function<ServerPlayer, String>> entry : PLACEHOLDERS.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (result.contains(placeholder)) {
                result = result.replace(placeholder, entry.getValue().apply(player));
            }
        }
        return result;
    }
}

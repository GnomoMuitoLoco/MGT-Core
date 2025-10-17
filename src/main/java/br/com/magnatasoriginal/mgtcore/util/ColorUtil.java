package br.com.magnatasoriginal.mgtcore.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ColorUtil

 * Utilitário para traduzir códigos de cor em mensagens.
 * Suporta:
 *   - &a, &b, &c ... (códigos estilo Bukkit)
 *   - §a, §b, §c ... (códigos vanilla)
 *   - &#RRGGBB (hexadecimal)
 */
@SuppressWarnings("unused")
public class ColorUtil {

    // Regex para capturar hexadecimais no formato &#RRGGBB
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    /**
     * Traduz códigos de cor (&, § e hex) para um Component do Minecraft.
     *
     * @param input Texto com códigos de cor
     * @return Component formatado
     */
    public static Component translate(String input) {
        if (input == null) return Component.empty();

        // Substitui & por § (compatibilidade com Bukkit/Spigot)
        String colored = input.replace("&", "§");

        // Substitui hexadecimais (&#RRGGBB) por §x§R§R§G§G§B§B
        Matcher matcher = HEX_PATTERN.matcher(colored);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                replacement.append('§').append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        matcher.appendTail(buffer);

        // Interpreta os códigos § e aplica estilos
        return parseLegacySection(buffer.toString());
    }

    private static Component parseLegacySection(String input) {
        MutableComponent result = Component.literal("");
        Style current = Style.EMPTY;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (ch == '§' && i + 1 < input.length()) {
                // flush buffer
                if (!sb.isEmpty()) {
                    result.append(Component.literal(sb.toString()).setStyle(current));
                    sb.setLength(0);
                }

                char code = Character.toLowerCase(input.charAt(++i));
                ChatFormatting fmt = ChatFormatting.getByCode(code);
                if (fmt != null) {
                    if (fmt == ChatFormatting.RESET) {
                        current = Style.EMPTY;
                    } else {
                        current = Style.EMPTY.applyFormat(fmt);
                    }
                }
                continue;
            }

            sb.append(ch);
        }

        if (!sb.isEmpty()) {
            result.append(Component.literal(sb.toString()).setStyle(current));
        }

        return result;
    }
}

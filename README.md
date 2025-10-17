# 🧩 MGT-Core

**MGT-Core** é o núcleo da família de mods **Magnatas Original**.  
Ele não é um mod jogável por si só, mas sim uma **biblioteca base** que fornece utilitários, APIs e integrações para outros mods como **MGT-Chat**, **MGT-Discord**, **MGT-Vip**, **MGT-Shop**, e futuros módulos.

---

## 🎯 Objetivo

Centralizar funcionalidades comuns para evitar duplicação de código entre os mods da família **MGT**.  
Com isso, cada mod pode focar apenas em sua lógica principal, enquanto o Core oferece suporte compartilhado.

---

## ⚙️ O que já implementa

- **Utilitários de cores** (`ColorUtil`)  
  - Conversão de códigos `&` e `§` em cores do Minecraft.  
  - Suporte a cores hexadecimais (`&#RRGGBB`).  

- **Integração com configs**  
  - Estrutura para leitura de arquivos `.toml`.  
  - Base para centralizar opções de formatação e comportamento.  

- **Publicação local**  
  - Configurado para ser publicado no Maven Local (`~/.m2/repository`) e consumido por outros mods.  

---

## 🛠️ O que vai implementar

- **Sistema de placeholders**  
  - Exemplo: `{sender_player_displayname}`, `{receiver_player_name}`, `{message}`.  
  - Usado para formatar mensagens privadas, globais e locais.  

- **Formatação configurável de mensagens**  
  - Definição de formatos no `core-config.toml`.  
  - Exemplo:
    ```toml
    tellFormatTo = "&fSussurrou para {receiver_player_displayname}: {message}"
    tellFormatFrom = "{sender_player_displayname} sussurrou: {message}"
    ```

- **Integração com ranks/permissões**  
  - Suporte a mods como FTB Ranks para prefixos e permissões.  

- **API pública**  
  - Métodos utilitários para logging, mensagens e integração entre mods.  

---

## 📌 Observação

Este repositório existe apenas para **documentar e organizar o núcleo dos mods MGT**.  
Ele **não é distribuído separadamente** e só faz sentido em conjunto com os outros mods da família.

---

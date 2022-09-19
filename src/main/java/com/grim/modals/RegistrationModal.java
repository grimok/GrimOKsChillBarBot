package com.grim.modals;

import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;


public class RegistrationModal extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getButton().getId().equals("login-button")){
            if (!event.getUser().isBot()){
                TextInput name = TextInput.create("user-name","İsminiz:", TextInputStyle.SHORT).setRequired(true).setMinLength(3).setMaxLength(16).build();

                TextInput age = TextInput.create("user-age","Yaşınız:",TextInputStyle.SHORT).setMinLength(2).setMaxLength(3).setRequired(true).build();

                Modal modal = Modal.create("modal-reg","Lütfen Aşağıdaki Formu Tamamlayın").addActionRows(ActionRow.of(name),ActionRow.of(age)).build();

                event.replyModal(modal).queue();
            }
        }
    }
}

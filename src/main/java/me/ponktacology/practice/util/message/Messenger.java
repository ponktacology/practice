package me.ponktacology.practice.util.message;

import me.ponktacology.practice.Messages;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@UtilityClass
public class Messenger {

    public static void message(Recipient recipient, String message) {
        recipient.receive(MessageUtil.color(message));
    }

    public static void message(Player recipient, String message) {
        recipient.sendMessage(MessageUtil.color(message));
    }

    public static void message(Recipient recipient, String ... messages) {
        for (String message : messages) {
            message(recipient, message);
        }
    }

    public static void message(Player recipient, String ... messages) {
        for (String message : messages) {
            message(recipient, message);
        }
    }

    public static void messageError(Recipient recipient, String error) {
        message(recipient, ChatColor.RED + "Error: ".concat(error));
    }

    public static void messageError(Player recipient, String error) {
        message(recipient, ChatColor.RED + "Error: ".concat(error));
    }

    public static void messageSuccess(Recipient recipient, String message) {
        message(recipient, ChatColor.GREEN.toString().concat(message));
    }

    public static void messageSuccess(Player recipient, String message) {
        message(recipient, ChatColor.GREEN.toString().concat(message));
    }

    public static void message(Recipient recipient, Messages message) {
        message(recipient, message.get());
    }

    public static void message(Recipient recipient, Messages message, String placeholder, Object value) {
        message(recipient, message.match(placeholder, value));
    }

    public static void message(Recipient recipient, Messages message, MessagePattern... patterns) {
        message(recipient, message.match(patterns));
    }

}

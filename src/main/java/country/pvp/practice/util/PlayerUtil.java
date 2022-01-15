package country.pvp.practice.util;

import lombok.experimental.UtilityClass;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class PlayerUtil {

    public static void clearInventory(Player player) {
        player.getOpenInventory().close();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getInventory().setContents(new ItemStack[36]);
        player.updateInventory();
    }

    public static void resetPlayer(Player player) {
        resetPlayer(player, true);
    }

    public static void resetPlayer(Player player, boolean resetHeldSlot) {
        if (resetHeldSlot)
            player.getInventory().setHeldItemSlot(0);
        clearInventory(player);
        player.setHealth(20.0D);
        player.setSaturation(20.0F);
        player.setFallDistance(0.0F);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setMaximumNoDamageTicks(20);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.getActivePotionEffects().forEach(it -> player.removePotionEffect(it.getType()));
    }
}
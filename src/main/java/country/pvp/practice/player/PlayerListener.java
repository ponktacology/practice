package country.pvp.practice.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    protected final PlayerManager playerManager;

    public PracticePlayer get(Player player) {
        return playerManager.get(player);
    }

    public PracticePlayer get(PlayerEvent event) {
        return get(event.getPlayer());
    }
}

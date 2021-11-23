package country.pvp.practice.kit.editor;

import com.google.inject.Inject;
import country.pvp.practice.ladder.Ladder;
import country.pvp.practice.lobby.LobbyService;
import country.pvp.practice.player.PlayerListener;
import country.pvp.practice.player.PlayerManager;
import country.pvp.practice.player.PracticePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitEditorListener extends PlayerListener {

    private final LobbyService lobbyService;

    @Inject
    public KitEditorListener(PlayerManager playerManager, LobbyService lobbyService) {
        super(playerManager);
        this.lobbyService = lobbyService;
    }

    @EventHandler(ignoreCancelled = true)
    public void interactEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        PracticePlayer practicePlayer = get(event);
        if (!practicePlayer.isInEditor()) {
            return;
        }

        PlayerEditingData kitEditingData = practicePlayer.getStateData();
        Ladder ladder = kitEditingData.getLadder();

        switch (event.getClickedBlock().getType()) {
            case CHEST:
                new KitEditorChest(ladder).openMenu(event.getPlayer());
                break;
            case ANVIL:
                new KitEditorMenu(practicePlayer, ladder).openMenu(event.getPlayer());
                break;
            case SIGN_POST:
                lobbyService.moveToLobby(practicePlayer);
                break;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void dropItem(PlayerDropItemEvent event) {
        PracticePlayer practicePlayer = get(event);
        if (!practicePlayer.isInEditor()) return;

        event.getItemDrop().remove();
    }
}
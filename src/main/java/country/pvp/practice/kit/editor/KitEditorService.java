package country.pvp.practice.kit.editor;

import com.google.inject.Inject;
import country.pvp.practice.ladder.Ladder;
import country.pvp.practice.player.PlayerSession;
import country.pvp.practice.player.PlayerUtil;
import country.pvp.practice.player.data.PlayerState;
import country.pvp.practice.settings.PracticeSettings;
import country.pvp.practice.visibility.VisibilityUpdater;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class KitEditorService {

    private final VisibilityUpdater visibilityUpdater;
    private final PracticeSettings practiceSettings;

    public void moveToEditor(PlayerSession player, Ladder ladder) {
        player.setState(PlayerState.EDITING_KIT, new SessionEditingData(ladder));
        PlayerUtil.clearInventory(player.getPlayer());
        visibilityUpdater.update(player);
        player.teleport(practiceSettings.getEditorLocation());
        ladder.getKit().apply(player);
    }
}

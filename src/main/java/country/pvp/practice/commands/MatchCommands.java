package country.pvp.practice.commands;

import com.google.inject.Inject;
import country.pvp.practice.duel.PlayerDuelRequest;
import country.pvp.practice.duel.PlayerDuelService;
import country.pvp.practice.kit.editor.KitChooseMenuProvider;
import country.pvp.practice.ladder.Ladder;
import country.pvp.practice.match.Match;
import country.pvp.practice.message.Messager;
import country.pvp.practice.player.PlayerManager;
import country.pvp.practice.player.PlayerSession;
import me.vaperion.blade.command.annotation.*;
import org.bukkit.entity.Player;

public class MatchCommands extends PlayerCommands {

    private final PlayerDuelService playerDuelService;
    private final KitChooseMenuProvider kitChooseMenuProvider;

    @Inject
    public MatchCommands(PlayerManager playerManager, PlayerDuelService playerDuelService, KitChooseMenuProvider kitChooseMenuProvider) {
        super(playerManager);
        this.playerDuelService = playerDuelService;
        this.kitChooseMenuProvider = kitChooseMenuProvider;
    }

    @Command("match cancel")
    public void cancel(@Sender Player sender, @Name("match") Match<?> match, @Combined @Optional("Cancelled by the staff member") @Name("reason") String reason) {
        match.cancel(reason);
        Messager.messageSuccess(sender, "Successfully cancelled this match.");
    }

    @Command("spectate")
    public void specate(@Sender Player sender, @Name("player") PlayerSession player) {
        PlayerSession playerSession = get(sender);

        if (!playerSession.isInLobby()) {
            Messager.messageError(playerSession, "You must be in lobby in order to spectate someone.");
            return;
        }

        if (!player.isInMatch()) {
            Messager.messageError(playerSession, "This player is not in a match right now.");
            return;
        }

        Match match = player.getCurrentMatch();
        match.startSpectating(playerSession, player);
    }

    @Command("duel")
    public void duel(@Sender Player sender, @Name("player") PlayerSession invitee, @Optional @Name("ladder") Ladder ladder) {
        PlayerSession inviter = get(sender);

        if (inviter.equals(invitee)) {
            Messager.messageError(inviter, "You can't invite yourself for a duel.");
            return;
        }

        if (ladder != null) {
            playerDuelService.inviteForDuel(inviter, invitee, ladder);
        } else {
            kitChooseMenuProvider
                    .provide((l) -> playerDuelService.inviteForDuel(inviter, invitee, l))
                    .openMenu(sender);
        }
    }

    @Command("accept")
    public void accept(@Sender Player sender, @Name("player") PlayerSession player) {
        PlayerSession invitee = get(sender);

        if (!invitee.hasDuelRequest(player)) {
            Messager.messageError(sender, "You have not received duel request from this player.");
            return;
        }

        PlayerDuelRequest duelRequest = invitee.getDuelRequest(player);
        playerDuelService.acceptInvite(invitee, duelRequest);
    }
}
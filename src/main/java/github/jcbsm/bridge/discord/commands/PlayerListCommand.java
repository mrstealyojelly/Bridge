package github.jcbsm.bridge.discord.commands;

import github.jcbsm.bridge.discord.ApplicationCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerListCommand extends ApplicationCommand {

    public PlayerListCommand() {
        super("list", "List the players online.");
    }

    /**
     * Runs the interaction reply
     * @param event Event
     */
    @Override
    public void run(SlashCommandInteractionEvent event) {

        // If server is empty, reply with appropriate message
        if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
            event.reply("No players are currently online.").setEphemeral(true).queue();

        // Otherwise, display player list.
        } else {

            StringBuilder msg = new StringBuilder();

            // title
            msg.append("## " + Bukkit.getServer().getOnlinePlayers().size() + " Player(s) Online:" + System.lineSeparator());

            // Append each player to the list
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                msg.append("- " + player.getDisplayName() + System.lineSeparator());
            }

            event.reply(msg.toString()).setEphemeral(true).queue();

        }

    }
}

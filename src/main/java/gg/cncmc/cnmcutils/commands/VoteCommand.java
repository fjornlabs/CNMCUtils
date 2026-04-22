package gg.cncmc.cnmcutils.commands;

import com.mojang.brigadier.context.CommandContext;
import gg.cncmc.cnmcutils.CNMCUtils;
import gg.cncmc.cnmcutils.config.VoteConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.*;

public class VoteCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("vote")
                    .executes(VoteCommand::execute)
                    .then(literal("reload")
                            .requires(src -> src.hasPermissionLevel(2)) // ops only
                            .executes(VoteCommand::reload)
                    )
            );
        });
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        VoteConfig cfg = CNMCUtils.config;
        ServerCommandSource source = context.getSource();

        source.sendFeedback(() ->
                Text.literal(cfg.header).formatted(Formatting.byName(cfg.headerColor), Formatting.BOLD), false);

        for (VoteConfig.VoteLink link : cfg.links) {
            MutableText label = Text.literal("[" + link.label + "] ")
                    .formatted(Formatting.byName(cfg.labelColor));

            MutableText clickable = Text.literal("[Click here]")
                    .formatted(Formatting.byName(cfg.clickColor), Formatting.UNDERLINE)
                    .styled(style -> style
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link.url))
                            .withHoverEvent(new HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    Text.literal(link.url).formatted(Formatting.GRAY)
                            ))
                    );

            MutableText line = label.append(clickable);
            source.sendFeedback(() -> line, false);
        }

        source.sendFeedback(() ->
                Text.literal(cfg.footer).formatted(Formatting.byName(cfg.footerColor)), false);
        return 1;
    }

    private static int reload(CommandContext<ServerCommandSource> context) {
        CNMCUtils.config = VoteConfig.load();
        context.getSource().sendFeedback(() ->
                Text.literal("[Vøte] Config reloaded!").formatted(Formatting.GREEN), false);
        return 1;
    }
}
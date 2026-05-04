package gg.cncmc.cnmcutils.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static gg.cncmc.cnmcutils.utils.LuckPermsHook.hasPermission;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DonorTagCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("donortag")
                    .requires(src -> hasPermission(src, "cnmc.donortag"))
                    .then(literal("clear")
                            .executes(DonorTagCommand::clear))
                    .then(argument("tag", StringArgumentType.word())
                            .suggests((context, builder) -> {
                                // Add your autocomplete suggestions here
                                builder.suggest("Emperor");
                                builder.suggest("Empress");
                                builder.suggest("Governor");
                                builder.suggest("Lord");
                                builder.suggest("King");
                                builder.suggest("His Grace");
                                builder.suggest("Her Grace");
                                builder.suggest("Lady");
                                builder.suggest("Sir");
                                builder.suggest("Dame");
                                builder.suggest("Baron");
                                builder.suggest("Baroness");
                                builder.suggest("Duke");
                                builder.suggest("Duchess");
                                builder.suggest("Prince");
                                builder.suggest("Princess");
                                builder.suggest("Count");
                                builder.suggest("Countess");
                                builder.suggest("President");
                                builder.suggest("Vice President");
                                builder.suggest("Senator");
                                builder.suggest("Minister");
                                builder.suggest("Margrave");
                                builder.suggest("Marquis");
                                builder.suggest("Holiness");
                                builder.suggest("Vizier");
                                builder.suggest("Shah");
                                builder.suggest("Sheik");
                                builder.suggest("Tsar");
                                builder.suggest("Prophet");
                                builder.suggest("CEO");
                                builder.suggest("Representative");
                                builder.suggest("Speaker");
                                builder.suggest("General");
                                builder.suggest("Commander");
                                builder.suggest("Admiral");
                                builder.suggest("Field Marshal");
                                builder.suggest("Khan");
                                builder.suggest("Khagan");
                                builder.suggest("Chancellor");
                                builder.suggest("Burgermeister");
                                builder.suggest("Burgrave");
                                builder.suggest("General Secretary");
                                builder.suggest("Supreme Leader");
                                builder.suggest("Mister");
                                builder.suggest("Mistress");
                                builder.suggest("Madam");
                                builder.suggest("Prime Minister");
                                builder.suggest("Queen");
                                builder.suggest("Judge");
                                builder.suggest("Builder");
                                builder.suggest("Warrior");
                                builder.suggest("Master");
                                builder.suggest("Chieftan");
                                builder.suggest("Chieftess");
                                builder.suggest("Dr.");

                                return builder.buildFuture();
                            })
                            .executes(DonorTagCommand::execute)
            ));
        });
    }

    private static int execute(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();
        String tag = StringArgumentType.getString(ctx, "tag");

        if (!(source.getEntity() instanceof PlayerEntity player)) {
            source.sendFeedback(() -> Text.literal("Only players can use this!"), false);
            return 0;
        }

        LuckPerms luckPerms = LuckPermsProvider.get();

        luckPerms.getUserManager().loadUser(player.getUuid()).thenAcceptAsync(user -> {
            if (user == null) return;

            user.data().clear(NodeType.INHERITANCE.predicate(
                    node -> node.getGroupName().startsWith("donor-")
            ));

            InheritanceNode newTag = InheritanceNode.builder("donor-" + tag).build();
            user.data().add(newTag);

            luckPerms.getUserManager().saveUser(user);
        });

        return 1;
    }

    private static int clear(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource src = ctx.getSource();

        if (!(src.getEntity() instanceof PlayerEntity player)) {
            src.sendFeedback(() -> Text.literal("Only players can use this!"), false);
            return 0;
        }

        LuckPerms luckPerms = LuckPermsProvider.get();

        luckPerms.getUserManager().loadUser(player.getUuid()).thenAcceptAsync(user -> {
            if (user == null) return;

            user.data().clear(NodeType.INHERITANCE.predicate(
                    node -> node.getGroupName().startsWith("donor-")
            ));
        });

        return 1;
    }
}

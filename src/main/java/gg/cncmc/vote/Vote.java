package gg.cncmc.vote;

import gg.cncmc.vote.commands.VoteCommand;
import gg.cncmc.vote.config.VoteConfig;
import net.fabricmc.api.ModInitializer;

public class Vote implements ModInitializer {

    public static VoteConfig config;

    @Override
    public void onInitialize() {
        config = VoteConfig.load();
        VoteCommand.register();
    }
}
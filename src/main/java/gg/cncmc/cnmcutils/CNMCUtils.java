package gg.cncmc.cnmcutils;

import gg.cncmc.cnmcutils.commands.VoteCommand;
import gg.cncmc.cnmcutils.config.VoteConfig;
import net.fabricmc.api.ModInitializer;

public class CNMCUtils implements ModInitializer {

    public static VoteConfig config;

    @Override
    public void onInitialize() {
        config = VoteConfig.load();
        VoteCommand.register();
    }
}
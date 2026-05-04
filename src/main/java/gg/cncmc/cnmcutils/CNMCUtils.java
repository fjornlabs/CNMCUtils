package gg.cncmc.cnmcutils;

import gg.cncmc.cnmcutils.commands.DonorTagCommand;
import gg.cncmc.cnmcutils.commands.VoteCommand;
import gg.cncmc.cnmcutils.config.VoteConfig;
import net.fabricmc.api.ModInitializer;

public class CNMCUtils implements ModInitializer {

    public static VoteConfig voteconfig;

    @Override
    public void onInitialize() {
        voteconfig = VoteConfig.load();
        VoteCommand.register();
        DonorTagCommand.register();
    }
}
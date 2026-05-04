package gg.cncmc.cnmcutils.utils;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.server.command.ServerCommandSource;

public class LuckPermsHook {
    public static boolean hasPermission(ServerCommandSource source, String permission) {
        try {
            User user = LuckPermsProvider.get()
                    .getUserManager()
                    .getUser(source.getPlayer().getUuid());

            if (user == null) return source.hasPermissionLevel(2); // also fallback here

            return user.getCachedData()
                    .getPermissionData()
                    .checkPermission(permission)
                    .asBoolean();
        } catch (Exception e) {
            return source.hasPermissionLevel(2); // fallback if LuckPerms isn't present
        }
    }
}
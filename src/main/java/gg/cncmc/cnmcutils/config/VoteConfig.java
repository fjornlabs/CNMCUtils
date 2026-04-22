package gg.cncmc.cnmcutils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VoteConfig {

    public static class VoteLink {
        public String label;
        public String url;

        public VoteLink(String label, String url) {
            this.label = label;
            this.url = url;
        }
    }

    public String header = "=== Vote for CNMC! ===";
    public String footer = "Thanks for supporting us!";
    public List<VoteLink> links = new ArrayList<>(List.of(
            new VoteLink("Planet Minecraft", "https://www.planetminecraft.com/server/cnmc/"),
            new VoteLink("Minecraft Servers", "https://minecraftservers.org/vote/685582")
    ));
    public String headerColor = "GOLD";
    public String footerColor = "YELLOW";
    public String labelColor = "AQUA";
    public String clickColor = "GREEN";

    // ----------------------------------------------------------------

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("vote.json");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static VoteConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                return GSON.fromJson(reader, VoteConfig.class);
            } catch (Exception e) {
                System.err.println("[Vøte] Failed to read config, using defaults: " + e.getMessage());
            }
        }

        // File doesn't exist yet — write defaults
        VoteConfig defaults = new VoteConfig();
        defaults.save();
        return defaults;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (Exception e) {
            System.err.println("[Vøte] Failed to save config: " + e.getMessage());
        }
    }
}
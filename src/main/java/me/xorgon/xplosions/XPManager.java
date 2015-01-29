package me.xorgon.xplosions;

import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLProcessor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Xplosions manager class.
 */
public class XPManager {

    private Double explosionFactor;
    private Double destructionRatio;

    private File file;
    private YAMLProcessor config;
    private XplosionsPlugin plugin;

    public XPManager(XplosionsPlugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "config.yml");
        config = new YAMLProcessor(file, false, YAMLFormat.EXTENDED);
    }

    public Double getExplosionFactor() {
        return explosionFactor;
    }

    public void setExplosionFactor(Double explosionFactor) {
        this.explosionFactor = explosionFactor;
    }

    public Double getDestructionRatio() {
        return destructionRatio;
    }

    public void setDestructionRatio(Double destructionRatio) {
        this.destructionRatio = destructionRatio;
    }

    public void load() {
        try {
            config.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }


        explosionFactor = (Double) config.getProperty("explosionFactor");
        if (explosionFactor == null || explosionFactor == 0.0) {
            explosionFactor = 1.0;
        }

        destructionRatio = (Double) config.getProperty("destructionRatio");
        if (destructionRatio == null || destructionRatio == 0.0) {
            destructionRatio = 0.5;
        }

    }

    public void save() {
        config.setHeader("Configuration for Xplosions.");
        config.setProperty("explosionFactor", explosionFactor);
        config.setComment("explosionFactor", "The explosion factor is a factor describing the power of the explosion (default = 1.0).");
        config.setProperty("destructionRatio", destructionRatio);
        config.setComment("explosionFactor", "The destruction ratio is the ratio governing what proportion of the exploded blocks are destroyed upon landing.");
        config.save();
    }

}

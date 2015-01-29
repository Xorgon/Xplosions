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
        if (!file.exists()) {
            try {
                file.createNewFile();
                saveNew();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
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


        explosionFactor = config.getDouble("explosionFactor");
        if (explosionFactor == null) {
            explosionFactor = 1.0;
        }

        destructionRatio = config.getDouble("destructionRatio");
        if (destructionRatio == null) {
            destructionRatio = 0.5;
        }

    }

    public void saveNew() {
        config = new YAMLProcessor(file, false, YAMLFormat.EXTENDED);
        config.setHeader("#Configuration for Xplosions.");
        config.setProperty("explosionFactor", 1.0);
        config.setComment("explosionFactor", "The explosion factor is a factor describing the power of the explosion (default = 1.0).");
        config.setProperty("destructionRatio", 0.5);
        config.setComment("destructionRatio", "The destruction ratio is the ratio governing what proportion of the exploded blocks are destroyed upon landing (default = 0.5).");
        config.save();
    }

}

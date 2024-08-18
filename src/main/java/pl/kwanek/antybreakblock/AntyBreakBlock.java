package pl.kwanek.antybreakblock;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AntyBreakBlock extends JavaPlugin implements Listener {

    private List<String> allowedBlocks;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        
        saveDefaultConfig();
        loadConfig();
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig() {
        FileConfiguration config = this.getConfig();
        allowedBlocks = config.getStringList("allowed-blocks");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Sprawdzanie czy gracz ma permisję na omijanie ochrony
        if (event.getPlayer().hasPermission("blockprotection.bypass")) {
            return;
        }

        // Pobieranie materiału bloku
        Material blockType = event.getBlock().getType();

        // Sprawdzanie czy blok jest na liście dozwolonych
        if (!allowedBlocks.contains(blockType.toString())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Nie możesz niszczyć tego bloku!");
        }
    }
}

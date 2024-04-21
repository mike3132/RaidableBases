package net.resolutemc.raidablebases.Items;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WandItem {

    private ItemStack wand;

    public WandItem() {
        this.createWandItem();
    }

    public ItemStack getWand() {
        return this.wand;
    }

    public void createWandItem() {
        ItemStack item = new ItemStack(Material.GOLDEN_AXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ColorTranslate.chatColor("&bLeft click block to set position 1"));
        lore.add(ColorTranslate.chatColor("&dRight click block to set position 2"));
        meta.setDisplayName(ColorTranslate.chatColor("&5Base Wand"));
        meta.setLore(lore);

        NamespacedKey key = new NamespacedKey(RaidableBases.getInstance(), "Wand-Item-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "WandItem-Key");
        meta.addEnchant(Enchantment.LUCK, 10, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        wand = item;
    }


}

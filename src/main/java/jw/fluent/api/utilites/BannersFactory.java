package jw.fluent.api.utilites;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BannersFactory
{
    private static ItemStack createXBanner() {
        ItemStack banner = new ItemStack(Material.CYAN_BANNER);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CROSS));
        meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
        for (var flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        banner.setItemMeta(meta);
        return banner;
    }

    private static ItemStack createZBanner() {
        ItemStack banner = new ItemStack(Material.CYAN_BANNER);
        ItemMeta meta = banner.getItemMeta();
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
        for (var flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        banner.setItemMeta(meta);
        return banner;
    }


    private static ItemStack createYBanner() {
        ItemStack banner = new ItemStack(Material.CYAN_BANNER);
        ItemMeta meta = banner.getItemMeta();
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.CYAN, PatternType.HALF_HORIZONTAL_MIRROR));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
        for (var flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        banner.setItemMeta(meta);
        return banner;
    }
}

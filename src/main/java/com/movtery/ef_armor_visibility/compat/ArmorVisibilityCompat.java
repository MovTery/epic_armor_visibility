package com.movtery.ef_armor_visibility.compat;

import com.diontryban.armor_visibility.ArmorVisibility;
import com.diontryban.armor_visibility.options.ArmorVisibilityOptions;
import com.movtery.ef_armor_visibility.EpicArmorVisibility;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import yesman.epicfight.compat.ICompatModule;

import static com.diontryban.armor_visibility.client.ArmorVisibilityClient.hideAllArmor;
import static com.diontryban.armor_visibility.client.ArmorVisibilityClient.hideMyArmor;

public class ArmorVisibilityCompat implements ICompatModule {
    @Override
    public void onModEventBus(IEventBus iEventBus) {

    }

    @Override
    public void onForgeEventBus(IEventBus iEventBus) {

    }

    @Override
    public void onModEventBusClient(IEventBus iEventBus) {
        EpicArmorVisibility.addCallback(this::shouldSkipRender);
    }

    @Override
    public void onForgeEventBusClient(IEventBus iEventBus) {

    }

    private boolean shouldSkipRender(LivingEntity entity, EquipmentSlot slot) {
        ArmorVisibilityOptions options = (ArmorVisibilityOptions) ArmorVisibility.OPTIONS.get();

        boolean shouldSkip = switch (slot) {
            case HEAD -> options.togglesHelmet;
            case CHEST -> options.togglesChestplate;
            case LEGS -> options.togglesLeggings;
            case FEET -> options.togglesBoots;
            default -> false;
        };

        if (shouldSkip) {
            if (options.playersOnly && !(entity instanceof Player)) return false;
            if (hideAllArmor) return true;
            return hideMyArmor && entity.equals(Minecraft.getInstance().player);
        } else {
            return false;
        }
    }
}

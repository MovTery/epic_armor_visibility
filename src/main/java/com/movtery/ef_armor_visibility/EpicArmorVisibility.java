package com.movtery.ef_armor_visibility;

import com.mojang.logging.LogUtils;
import com.movtery.ef_armor_visibility.compat.ArmorVisibilityCompat;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import yesman.epicfight.compat.ICompatModule;

import java.util.ArrayList;
import java.util.List;

@Mod(EpicArmorVisibility.MODID)
public class EpicArmorVisibility {
    public static final String MODID = "epic_armor_visibility";

    private static final List<SkipArmorCallback> callbacks = new ArrayList<>();

    public static void addCallback(SkipArmorCallback callback) {
        callbacks.add(callback);
    }

    public static boolean checkSkipRender(LivingEntity entity, EquipmentSlot slot) {
        return callbacks.stream()
                .allMatch(callback -> callback.shouldSkip(entity, slot));
    }

    public EpicArmorVisibility(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);

        boolean isCompatLoaded = false;
        if (ModList.get().isLoaded("armor_visibility")) {
            ICompatModule.loadCompatModule(context, ArmorVisibilityCompat.class);
            isCompatLoaded = true;
        }

        if (!isCompatLoaded) {
            addCallback(this::defaultCallback);
        }
    }

    private boolean defaultCallback(LivingEntity entity, EquipmentSlot slot) {
        return true;
    }
}

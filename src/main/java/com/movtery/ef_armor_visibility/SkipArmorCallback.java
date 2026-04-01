package com.movtery.ef_armor_visibility;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public interface SkipArmorCallback {
    boolean shouldSkip(LivingEntity entity, EquipmentSlot slot);
}

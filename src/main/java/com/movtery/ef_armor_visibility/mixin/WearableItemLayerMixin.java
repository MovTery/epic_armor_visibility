package com.movtery.ef_armor_visibility.mixin;

import com.movtery.ef_armor_visibility.EpicArmorVisibility;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.renderer.patched.layer.WearableItemLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(WearableItemLayer.class)
public abstract class WearableItemLayerMixin<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends HumanoidModel<E>, AM extends HumanoidMesh> extends ModelRenderLayer<E, T, M, HumanoidArmorLayer<E, M, M>, AM> {
    public WearableItemLayerMixin(AssetAccessor<AM> mesh) {
        super(mesh);
    }

    @Redirect(
            method = "renderLayer*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack redirectGetItemBySlot(LivingEntity entity, EquipmentSlot slot) {
        ItemStack item = entity.getItemBySlot(slot);

        if (slot.getType() != EquipmentSlot.Type.ARMOR) return item;

        if (EpicArmorVisibility.checkSkipRender(entity, slot)) {
            return ItemStack.EMPTY;
        }

        return item;
    }
}

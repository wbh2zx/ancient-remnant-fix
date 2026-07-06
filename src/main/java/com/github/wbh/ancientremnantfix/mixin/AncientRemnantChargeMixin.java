package com.github.wbh.ancientremnantfix.mixin;

import com.kurome.ageofmythology.utils.MorphUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MorphUtil.class, remap = false)
public abstract class AncientRemnantChargeMixin {

    private static final Logger LOGGER = LogUtils.getLogger();

    static {
        LOGGER.info("[ARF] MorphUtil mixin loaded");
    }

    @Inject(
        method = "ChargeBlockBreaking(Lnet/minecraft/world/entity/LivingEntity;DZ)V",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private static void beforeChargeBlockBreaking(LivingEntity entity, double inflate, boolean flag,
                                                   CallbackInfo ci) {
        if (entity.level().isClientSide) return;

        AABB aabb = entity.getBoundingBox().inflate(inflate, 0.2D, inflate);
        for (BlockPos pos : BlockPos.betweenClosed(
                Mth.floor(aabb.minX), Mth.floor(entity.getY()), Mth.floor(aabb.minZ),
                Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
            String name = ForgeRegistries.BLOCKS.getKey(
                    entity.level().getBlockState(pos).getBlock()).toString();
            if (name.equals("minecraft:ancient_debris")) {
                entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0);
                ci.cancel();
                LOGGER.info("[ARF] BLOCKED ancient_debris at ({},{},{})", pos.getX(), pos.getY(), pos.getZ());
                return;
            }
        }
    }
}

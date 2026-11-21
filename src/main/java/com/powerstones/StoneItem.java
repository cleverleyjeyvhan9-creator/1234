package com.powerstones;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class StoneItem extends Item {

    public final StoneDef def;

    public StoneItem(Settings settings, StoneDef def) {
        super(settings);
        this.def = def;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (def.skill == null) {
            return ActionResult.PASS;
        }
        if (world instanceof ServerWorld serverWorld && user instanceof ServerPlayerEntity serverPlayer) {
            def.skill.run(serverPlayer, serverWorld);
            if (def.cooldownSeconds > 0) {
                user.getItemCooldownManager().set(user.getStackInHand(hand), def.cooldownSeconds * 20);
            }
        }
        return ActionResult.SUCCESS;
    }
}

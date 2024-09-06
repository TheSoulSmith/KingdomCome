/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.kingdomcome.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KingdomComeModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> LIMIT_CHAT_TO_KINGDOM = GameRules.register("limitChatToKingdom", GameRules.Category.CHAT, GameRules.BooleanValue.create(false));
}

package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;
import net.mcreator.kingdomcome.init.KingdomComeModGameRules;

public class ChatGlobalProcedureProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (("").equals((entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)
				|| world.getLevelData().getGameRules().getBoolean(KingdomComeModGameRules.LIMIT_CHAT_TO_KINGDOM)
						&& !(entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).IsKing) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Kingdom Come: Limit Chat To Kingdom is enabled in this world"), false);
		} else {
			if ((entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).ChatKingdom) {
				{
					boolean _setval = false;
					entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.ChatKingdom = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal("Kingdom Come: Set your chat to global"), false);
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal("Kingdom Come: Your chat was already set to global"), false);
			}
		}
	}
}

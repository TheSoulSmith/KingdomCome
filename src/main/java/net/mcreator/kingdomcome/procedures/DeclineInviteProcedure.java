package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

public class DeclineInviteProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			String _setval = "";
			entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.InvitedTo = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}

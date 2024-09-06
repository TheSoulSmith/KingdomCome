package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

public class InvitedToProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return (entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).InvitedTo;
	}
}

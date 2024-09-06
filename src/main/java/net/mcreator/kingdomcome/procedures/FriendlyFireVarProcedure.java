package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

public class FriendlyFireVarProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).FriendlyFire;
	}
}

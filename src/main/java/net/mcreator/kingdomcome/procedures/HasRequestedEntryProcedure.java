package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.HashMap;
import java.util.ArrayList;

import com.ibm.icu.util.Output;

public class HasRequestedEntryProcedure {
	public static boolean execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return false;
		boolean Output = false;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getDisplayName().getString()).contains(guistate.containsKey("text:PlayerSearchBox") ? ((EditBox) guistate.get("text:PlayerSearchBox")).getValue() : "")) {
				Output = !(entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Accepted
						&& ((entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)
								.equals((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom);
				break;
			}
		}
		return Output;
	}
}

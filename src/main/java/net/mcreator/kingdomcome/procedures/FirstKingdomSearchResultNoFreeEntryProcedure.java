package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.HashMap;
import java.util.ArrayList;

import com.ibm.icu.util.Output;

public class FirstKingdomSearchResultNoFreeEntryProcedure {
	public static boolean execute(LevelAccessor world, Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return false;
		boolean Output = false;
		String KingdomName = "";
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).IsKing
					&& ((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)
							.contains(guistate.containsKey("text:KingdomSearchBox") ? ((EditBox) guistate.get("text:KingdomSearchBox")).getValue() : "")) {
				Output = (entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).FreeEntry;
				KingdomName = (entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom;
				break;
			}
		}
		return !(Output || ((entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).InvitedTo).equals(KingdomName));
	}
}

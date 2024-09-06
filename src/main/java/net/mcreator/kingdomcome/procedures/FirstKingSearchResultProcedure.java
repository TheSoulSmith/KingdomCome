package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.HashMap;
import java.util.ArrayList;

import com.ibm.icu.util.Output;

public class FirstKingSearchResultProcedure {
	public static Entity execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return null;
		boolean foundOutput = false;
		Entity Output = null;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).IsKing
					&& ((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)
							.contains(guistate.containsKey("text:KingdomSearchBox") ? ((EditBox) guistate.get("text:KingdomSearchBox")).getValue() : "")) {
				Output = entityiterator;
				break;
			}
		}
		return Output;
	}
}

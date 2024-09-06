package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.HashMap;
import java.util.ArrayList;

public class FirstKingSearchResultNameProcedure {
	public static String execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return "";
		String output = "";
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).IsKing
					&& ((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)
							.contains(guistate.containsKey("text:KingdomSearchBox") ? ((EditBox) guistate.get("text:KingdomSearchBox")).getValue() : "")) {
				output = entityiterator.getDisplayName().getString();
				break;
			}
		}
		return output;
	}
}

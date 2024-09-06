package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import java.util.HashMap;
import java.util.ArrayList;

public class FoundPlayerSearchResultProcedure {
	public static boolean execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return false;
		boolean Found = false;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getDisplayName().getString()).contains(guistate.containsKey("text:PlayerSearchBox") ? ((EditBox) guistate.get("text:PlayerSearchBox")).getValue() : "")) {
				Found = true;
				break;
			}
		}
		return Found;
	}
}

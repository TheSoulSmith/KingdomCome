package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import java.util.HashMap;
import java.util.ArrayList;

import com.ibm.icu.util.Output;

public class FirstPlayerSearchResultProcedure {
	public static Entity execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return null;
		Entity Output = null;
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getDisplayName().getString()).contains(guistate.containsKey("text:PlayerSearchBox") ? ((EditBox) guistate.get("text:PlayerSearchBox")).getValue() : "")) {
				Output = entityiterator;
				break;
			}
		}
		return Output;
	}
}

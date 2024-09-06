package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import java.util.HashMap;
import java.util.ArrayList;

import com.ibm.icu.util.Output;

public class FirstPlayerSearchResultNameProcedure {
	public static String execute(LevelAccessor world, HashMap guistate) {
		if (guistate == null)
			return "";
		Entity Output = null;
		String outputtext = "";
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if ((entityiterator.getDisplayName().getString()).contains(guistate.containsKey("text:PlayerSearchBox") ? ((EditBox) guistate.get("text:PlayerSearchBox")).getValue() : "")) {
				Output = entityiterator;
				break;
			}
		}
		return outputtext;
	}
}

package net.mcreator.kingdomcome.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.kingdomcome.network.KingdomComeModVariables;

import java.util.ArrayList;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class FoundKingdomProcedureProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		boolean foundexistingkingdomwithname = false;
		if (("").equals((entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)) {
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if (((new Object() {
					public String getMessage() {
						try {
							return MessageArgument.getMessage(arguments, "kingdomname").getString();
						} catch (CommandSyntaxException ignored) {
							return "";
						}
					}
				}).getMessage()).equals((entityiterator.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KingdomComeModVariables.PlayerVariables())).Kingdom)) {
					foundexistingkingdomwithname = true;
				}
			}
			if (foundexistingkingdomwithname) {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal("Kingdom Come: There is already a kingdom with that name"), false);
			} else {
				if (((new Object() {
					public String getMessage() {
						try {
							return MessageArgument.getMessage(arguments, "kingdomname").getString();
						} catch (CommandSyntaxException ignored) {
							return "";
						}
					}
				}).getMessage()).length() < 5) {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal("Kingdom Come: Your kingdom name was too short (must be at least five characters)"), false);
				} else {
					{
						String _setval = (new Object() {
							public String getMessage() {
								try {
									return MessageArgument.getMessage(arguments, "kingdomname").getString();
								} catch (CommandSyntaxException ignored) {
									return "";
								}
							}
						}).getMessage();
						entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Kingdom = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						boolean _setval = true;
						entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.IsKing = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						boolean _setval = true;
						entity.getCapability(KingdomComeModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Accepted = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
				}
			}
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Kingdome Come: You are already part of a kingdom, leave or disband it to create a new one"), false);
		}
	}
}

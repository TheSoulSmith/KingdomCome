
package net.mcreator.kingdomcome.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.kingdomcome.world.inventory.KingGuiMenu;
import net.mcreator.kingdomcome.procedures.RevokeInviteProcedure;
import net.mcreator.kingdomcome.procedures.InviteProcedure;
import net.mcreator.kingdomcome.procedures.BanishProcedure;
import net.mcreator.kingdomcome.procedures.AcceptEntryRequestProcedure;
import net.mcreator.kingdomcome.KingdomComeMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KingGuiButtonMessage {
	private final int buttonID, x, y, z;

	public KingGuiButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public KingGuiButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(KingGuiButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(KingGuiButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level;
		HashMap guistate = KingGuiMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			InviteProcedure.execute(world, entity, guistate);
		}
		if (buttonID == 1) {

			RevokeInviteProcedure.execute(world, guistate);
		}
		if (buttonID == 2) {

			AcceptEntryRequestProcedure.execute(world, entity, guistate);
		}
		if (buttonID == 3) {

			BanishProcedure.execute(world, guistate);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		KingdomComeMod.addNetworkMessage(KingGuiButtonMessage.class, KingGuiButtonMessage::buffer, KingGuiButtonMessage::new, KingGuiButtonMessage::handler);
	}
}

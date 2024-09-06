
package net.mcreator.kingdomcome.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.kingdomcome.KingdomComeMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KingdomComeGuiMessage {
	int type, pressedms;

	public KingdomComeGuiMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public KingdomComeGuiMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(KingdomComeGuiMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(KingdomComeGuiMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
		});
		context.setPacketHandled(true);
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		KingdomComeMod.addNetworkMessage(KingdomComeGuiMessage.class, KingdomComeGuiMessage::buffer, KingdomComeGuiMessage::new, KingdomComeGuiMessage::handler);
	}
}

package net.mcreator.kingdomcome.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;

import net.mcreator.kingdomcome.world.inventory.CitizenGuiMenu;
import net.mcreator.kingdomcome.procedures.KingdomProviderProcedure;
import net.mcreator.kingdomcome.procedures.KingProviderProcedure;
import net.mcreator.kingdomcome.procedures.KingNameProviderProcedure;
import net.mcreator.kingdomcome.network.CitizenGuiButtonMessage;
import net.mcreator.kingdomcome.KingdomComeMod;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

public class CitizenGuiScreen extends AbstractContainerScreen<CitizenGuiMenu> {
	private final static HashMap<String, Object> guistate = CitizenGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_leave;

	public CitizenGuiScreen(CitizenGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("kingdom_come:textures/screens/citizen_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		if (KingProviderProcedure.execute(world, entity) instanceof LivingEntity livingEntity) {
			InventoryScreen.renderEntityInInventoryRaw(this.leftPos + 54, this.topPos + 163, 30, 0f, 0, livingEntity);
		}
		this.renderTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, Component.translatable("gui.kingdom_come.citizen_gui.label_kingdom"), 3, 3, -12829636);
		this.font.draw(poseStack,

				KingdomProviderProcedure.execute(entity), 51, 3, -12829636);
		this.font.draw(poseStack, Component.translatable("gui.kingdom_come.citizen_gui.label_king"), 3, 16, -12829636);
		this.font.draw(poseStack,

				KingNameProviderProcedure.execute(world, entity), 36, 16, -12829636);
	}

	@Override
	public void init() {
		super.init();
		button_leave = new Button(this.leftPos + 123, this.topPos + 143, 51, 20, Component.translatable("gui.kingdom_come.citizen_gui.button_leave"), e -> {
			if (true) {
				KingdomComeMod.PACKET_HANDLER.sendToServer(new CitizenGuiButtonMessage(0, x, y, z));
				CitizenGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		});
		guistate.put("button:button_leave", button_leave);
		this.addRenderableWidget(button_leave);
	}
}

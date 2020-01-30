package net.aer.render.render3D;

import net.aer.Aer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;


/**
 * Utils for rendering ingame poly structures
 */
public class RenderUtils3D {
	private Minecraft minecraft = Aer.minecraft;

	/**
	 * Draws a solid bounding box
	 */
	public static void drawSolidBoundingBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glEnd();
	}

	/**
	 * Draws a outline box
	 */
	public static void drawOutlinedBoundingBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glEnd();
	}

	/**
	 * Draws a cross bounding box
	 */
	public static void drawCrossBoundingBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

		GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
		GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
		GL11.glEnd();
	}

	/**
	 * Draws a node
	 */
	public static void drawNode(AxisAlignedBB bb) {
		double midX = (bb.minX + bb.maxX) / 2;
		double midY = (bb.minY + bb.maxY) / 2;
		double midZ = (bb.minZ + bb.maxZ) / 2;

		GL11.glVertex3d(midX, midY, bb.maxZ);
		GL11.glVertex3d(bb.minX, midY, midZ);

		GL11.glVertex3d(bb.minX, midY, midZ);
		GL11.glVertex3d(midX, midY, bb.minZ);

		GL11.glVertex3d(midX, midY, bb.minZ);
		GL11.glVertex3d(bb.maxX, midY, midZ);

		GL11.glVertex3d(bb.maxX, midY, midZ);
		GL11.glVertex3d(midX, midY, bb.maxZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(bb.maxX, midY, midZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(bb.minX, midY, midZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(midX, midY, bb.minZ);

		GL11.glVertex3d(midX, bb.maxY, midZ);
		GL11.glVertex3d(midX, midY, bb.maxZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(bb.maxX, midY, midZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(bb.minX, midY, midZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(midX, midY, bb.minZ);

		GL11.glVertex3d(midX, bb.minY, midZ);
		GL11.glVertex3d(midX, midY, bb.maxZ);
	}

	/**
	 * Draws an arrow with a Vec3d start and end
	 */
	public static void drawArrow(Vec3d from, Vec3d to) {
		double startX = from.xCoord;
		double startY = from.yCoord;
		double startZ = from.zCoord;

		double endX = to.xCoord;
		double endY = to.yCoord;
		double endZ = to.zCoord;

		GL11.glPushMatrix();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(startX, startY, startZ);
		GL11.glVertex3d(endX, endY, endZ);
		GL11.glEnd();

		GL11.glTranslated(endX, endY, endZ);
		GL11.glScaled(0.1, 0.1, 0.1);

		double angleX = Math.atan2(endY - startY, startZ - endZ);
		GL11.glRotated(Math.toDegrees(angleX) + 90, 1, 0, 0);

		double angleZ = Math.atan2(endX - startX,
				Math.sqrt(Math.pow(endY - startY, 2) + Math.pow(endZ - startZ, 2)));
		GL11.glRotated(Math.toDegrees(angleZ), 0, 0, 1);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(0, 2, 1);
		GL11.glVertex3d(-1, 2, 0);

		GL11.glVertex3d(-1, 2, 0);
		GL11.glVertex3d(0, 2, -1);

		GL11.glVertex3d(0, 2, -1);
		GL11.glVertex3d(1, 2, 0);

		GL11.glVertex3d(1, 2, 0);
		GL11.glVertex3d(0, 2, 1);

		GL11.glVertex3d(1, 2, 0);
		GL11.glVertex3d(-1, 2, 0);

		GL11.glVertex3d(0, 2, 1);
		GL11.glVertex3d(0, 2, -1);

		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(1, 2, 0);

		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(-1, 2, 0);

		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(0, 2, -1);

		GL11.glVertex3d(0, 0, 0);
		GL11.glVertex3d(0, 2, 1);
		GL11.glEnd();

		GL11.glPopMatrix();
	}

	/**
	 * Draws a outline bounding box with a blockposition, colour, and linewidth
	 */
	public static void drawOutlinedBlockESP(BlockPos blockIn, float red, float green, float blue, float alpha, float lineWidth) {
		double x = (blockIn.getX() - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (blockIn.getY() - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (blockIn.getZ() - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);

		drawOutlinedBlockESP(x, y, z, red, green, blue, alpha, lineWidth);
	}

	/**
	 * Draws a outline bounding box with a blockposition, colour, and linewidth
	 */
	public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/**
	 * Draws a bounding box with a blockposition, colour, and linewidth
	 */
	public static void drawBlockESP(BlockPos blockIn, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		double x = (blockIn.getX() - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (blockIn.getY() - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (blockIn.getZ() - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);

		drawBlockESP(x, y, z, red, green, blue, alpha, lineRed, lineGreen, lineBlue, lineAlpha, lineWidth);
	}


	/**
	 * Draws a bounding box with a blockposition, colour, and linewidth
	 */
	public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawSolidBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/**
	 * Draws a solid bounding box with a blockposition, colour, and linewidth
	 */
	public static void drawSolidBlockESP(BlockPos blockIn, float red, float green, float blue, float alpha) {
		double x = (blockIn.getX() - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (blockIn.getY() - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (blockIn.getZ() - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);

		drawSolidBlockESP(x, y, z, red, green, blue, alpha);
	}


	/**
	 * Draws a solid bounding box with a blockposition, colour, and linewidth
	 */
	public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawSolidBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/**
	 * Draws a tracer line to a entity, from the players eyes.
	 */
	public static void drawTracerLine(Entity entityIn, float red, float green, float blue, float alpha, float lineWdith) {
		double x = (entityIn.posX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (entityIn.posY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (entityIn.posZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);
		Vec3d eyes = new Vec3d(0.0D, 0.0D, 100.0D)
				.rotatePitch((float) Math.toRadians(-Aer.minecraft.player.rotationPitch))
				.rotateYaw((float) Math.toRadians(-Aer.minecraft.player.rotationYaw));
		drawTracerLine(x, y, z, eyes.xCoord, eyes.yCoord, eyes.zCoord, red, green, blue, alpha, lineWdith);
	}

	/**
	 * Draws a tracer line to the centre of a block position, from the players eyes
	 */
	public static void drawTracerLine(BlockPos pos, float red, float green, float blue, float alpha, float lineWdith) {
		double x = (pos.getX() - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX) + 0.5d;
		double y = (pos.getY() - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (pos.getZ() - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ) + 0.5d;
		Vec3d eyes = new Vec3d(0.0D, 0.0D, 100.0D)
				.rotatePitch((float) Math.toRadians(-Aer.minecraft.player.rotationPitch))
				.rotateYaw((float) Math.toRadians(-Aer.minecraft.player.rotationYaw));
		drawTracerLine(x, y, z, eyes.xCoord, eyes.yCoord, eyes.zCoord, red, green, blue, alpha, lineWdith);
	}

	/**
	 * Draws a tracer line between the centres of two block positions
	 */
	public static void drawTracerLine(BlockPos pos, BlockPos pos1, float red, float green, float blue, float alpha, float lineWdith) {
		double x = (pos.getX() - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX) + 0.5d;
		double y = (pos.getY() - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (pos.getZ() - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ) + 0.5d;

		double x1 = (pos1.getX() - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX) + 0.5d;
		double y1 = (pos1.getY() - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z1 = (pos1.getZ() - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ) + 0.5d;

		drawTracerLine(x, y, z, x1, y1, z1, red, green, blue, alpha, lineWdith);
	}

	/**
	 * Draws a tracer line between two positions
	 */
	public static void drawTracerLine(double x, double y, double z, double x1, double y1, double z1, float red, float green, float blue, float alpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(2);
		GL11.glVertex3d(x1, y1, z1);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/**
	 * Draws a outline bounding box with colour and linewidth
	 */
	public static void drawOutlinedESPBox(AxisAlignedBB box, float red, float green, float blue, float alpha, float lineWidth) {
		double x = (box.maxX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (box.maxY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (box.maxZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);
		double x2 = (box.minX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y2 = (box.minY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z2 = (box.minZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/**
	 * Draws a bounding box with colour and linewidth
	 */
	public static void drawESPBox(AxisAlignedBB box, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		double x = (box.maxX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (box.maxY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (box.maxZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);
		double x2 = (box.minX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y2 = (box.minY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z2 = (box.minZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawSolidBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	/**
	 * Draws a solid bounding box with colour and linewidth
	 */
	public static void drawSolidESPBox(AxisAlignedBB box, float red, float green, float blue, float alpha) {
		double x = (box.maxX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y = (box.maxY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z = (box.maxZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);
		double x2 = (box.minX - Aer.minecraft.player.posX) + (Aer.minecraft.player.posX - Aer.minecraft.getRenderManager().renderPosX);
		double y2 = (box.minY - Aer.minecraft.player.posY) + (Aer.minecraft.player.posY - Aer.minecraft.getRenderManager().renderPosY);
		double z2 = (box.minZ - Aer.minecraft.player.posZ) + (Aer.minecraft.player.posZ - Aer.minecraft.getRenderManager().renderPosZ);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawSolidBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

}

package steve6472.sge.gui.floatingdialog;

import org.joml.Matrix4f;
import steve6472.sge.gfx.FrameBuffer;
import steve6472.sge.gfx.Sprite;
import steve6472.sge.gfx.Tessellator;
import steve6472.sge.gfx.VertexObjectCreator;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gfx.shaders.DialogShader;
import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Util;
import steve6472.sge.main.events.WindowSizeEvent;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.07.2020
 * Project: FloatingDialogs
 *
 ***********************/
public class DialogManager
{
	private boolean active;
	private final List<FloatingDialog> dialogs;
	private final int model;

	public DialogManager()
	{
		dialogs = new ArrayList<>();

		model = VertexObjectCreator.createVAO();
		VertexObjectCreator.storeFloatDataInAttributeList(0, 3, new float[] {-1, 1, 0, -1, -1, 0, 1, -1, 0, 1, -1, 0, 1, 1, 0, -1, 1, 0}); // Vertex
		VertexObjectCreator.storeFloatDataInAttributeList(1, 2, new float[] {0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1}); // Texture
		VertexObjectCreator.unbindVAO();
	}

	public void addDialog(FloatingDialog dialog)
	{
		dialogs.add(dialog);
	}

	public void removeDialog(FloatingDialog dialog)
	{
		dialog.getFrameBuffer().deleteFrameBuffer();
		dialogs.remove(dialog);
	}

	public boolean isActive()
	{
		return active;
	}

	public void tick(MainApp main)
	{
		active = false;
		for (FloatingDialog dialog : this.dialogs)
		{
			if (!dialog.isVisible())
				continue;

			int mx = Util.clamp(0, dialog.getWidth(), main.getMouseX());
			int my = Util.clamp(0, dialog.getHeight(), main.getMouseY());

			if (dialog.isActive())
			{
				main.getMouseHandler().spoof(mx, my);
				active = true;
			} else
			{
				main.getMouseHandler().spoof(-1, -1);
			}
			dialog.tick();
		}
	}

	public void render(MainApp main, DialogShader shader, Matrix4f viewMatrix, float yaw, float pitch)
	{
		shader.bind();
		shader.setView(viewMatrix);
		Shader.releaseShader();
		Matrix4f dialogMatrix = new Matrix4f();

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);

		for (FloatingDialog dialog : this.dialogs)
		{
			if (!dialog.isVisible())
				continue;

			// Render the GUI
			if (dialog.repaint())
			{
				main.getSpriteRender().basicResizeOrtho(new WindowSizeEvent(dialog.getWidth(), dialog.getHeight()));
				Font.update(new Matrix4f().ortho(0.0F, (float) dialog.getWidth(), (float) dialog.getHeight(), 0.0F, 1.0F, -1.0F));

				dialog.getFrameBuffer().bindFrameBuffer(dialog.getWidth(), dialog.getHeight());
				FrameBuffer.clearCurrentBuffer();
				dialog.renderGui();
				dialog.getFrameBuffer().unbindCurrentFrameBuffer(main.getWidth(), main.getHeight());
			}
		}

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		Font.update(new Matrix4f().ortho(0.0F, (float)main.getWidth(), (float)main.getHeight(), 0.0F, 1.0F, -1.0F));
		main.getSpriteRender().basicResizeOrtho(new WindowSizeEvent(main.getWidth(), main.getHeight()));
		glViewport(0, 0, main.getWidth(), main.getHeight());

		for (FloatingDialog dialog : this.dialogs)
		{
			if (!dialog.isVisible())
				continue;

			// Render floating dialog

			final float w = dialog.getSizeX();
			final float h = dialog.getSizeY();
			final float x = dialog.getX();
			final float y = dialog.getY();
			final float z = dialog.getZ();
			Sprite.bind(0, dialog.getFrameBuffer().texture);
			shader.bind();

			// Make the dialog face the camera
			dialogMatrix.identity();
			dialogMatrix.translate(x, y, z);
			dialogMatrix.translate(x, y - h / 2.0f, z - w / 2.0f);
			dialogMatrix.rotate(yaw, 0.0f, 1.0f, 0.0f);
			dialogMatrix.rotate(pitch, 1.0f, 0.0f, 0.0f);
			dialogMatrix.translate(-x, -y + h / 2.0f, -z + w / 2.0f);
			dialogMatrix.scale(dialog.getSizeX() * dialog.getScaleModifier(), dialog.getSizeY() * dialog.getScaleModifier(), 0);

			shader.setTransformation(dialogMatrix);

			VertexObjectCreator.basicRender(model, 2, 6, Tessellator.TRIANGLES);
		}
	}
}

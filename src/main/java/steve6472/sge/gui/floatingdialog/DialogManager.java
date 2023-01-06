package steve6472.sge.gui.floatingdialog;

import org.joml.Matrix4f;
import steve6472.sge.gfx.FrameBuffer;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.Tessellator;
import steve6472.sge.gfx.VertexObjectCreator;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gfx.shaders.DialogShader;
import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.events.WindowSizeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

/**********************
 * Created by steve6472
 * On date: 19.07.2020
 * Project: FloatingDialogs
 *
 ***********************/
public class DialogManager
{
	private boolean active;
	private final List<FloatingDialog> dialogs;
	private final int model;
	private FloatingDialog firstActiveDialog;

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

	public void removeAllDialogs()
	{
		for (Iterator<FloatingDialog> iterator = dialogs.iterator(); iterator.hasNext(); )
		{
			FloatingDialog dialog = iterator.next();
			dialog.getFrameBuffer().deleteFrameBuffer();
			iterator.remove();
		}
	}

	public boolean isActive()
	{
		return active;
	}

	public FloatingDialog getFirstActiveDialog()
	{
		return firstActiveDialog;
	}

	public void tick()
	{
		active = false;
		firstActiveDialog = null;
		for (FloatingDialog dialog : this.dialogs)
		{
			if (dialog.shouldBeRemoved() || !dialog.isVisible())
				continue;

			if (dialog.isActive())
			{
				if (firstActiveDialog == null)
					firstActiveDialog = dialog;
				active = true;
				dialog.tick();
			} else
			{
				dialog.nonactiveTick();
			}
		}
	}

	public void render(MainApp main, DialogShader shader, Matrix4f viewMatrix, float yaw, float pitch, int mainBuffer)
	{
		shader.bind();
		shader.setView(viewMatrix);
		Shader.releaseShader();
		Matrix4f dialogMatrix = new Matrix4f();

		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);

		float[] f = new float[4];
		glGetFloatv(GL_COLOR_CLEAR_VALUE, f);

		glClearColor(0, 0, 0, 0);

		for (Iterator<FloatingDialog> iterator = this.dialogs.iterator(); iterator.hasNext(); )
		{
			FloatingDialog dialog = iterator.next();
			if (dialog.shouldBeRemoved())
			{
				iterator.remove();
				continue;
			}

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
			}
		}

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glClearColor(f[0], f[1], f[2], f[3]);

		glViewport(0, 0, main.getWidth(), main.getHeight());
		glBindFramebuffer(GL_FRAMEBUFFER, mainBuffer);

		Font.update(new Matrix4f().ortho(0.0F, (float)main.getWidth(), (float)main.getHeight(), 0.0F, 1.0F, -1.0F));
		main.getSpriteRender().basicResizeOrtho(new WindowSizeEvent(main.getWidth(), main.getHeight()));

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
			StaticTexture.bind(0, dialog.getFrameBuffer().texture);
			shader.bind();

			// Make the dialog face the camera
			dialogMatrix.identity();
			dialogMatrix.translate(x, y, z);
			dialogMatrix.rotate(yaw, 0.0f, 1.0f, 0.0f);
			dialogMatrix.rotate(pitch, 1.0f, 0.0f, 0.0f);
			dialogMatrix.scale(dialog.getSizeX() * dialog.getScaleModifier(), dialog.getSizeY() * dialog.getScaleModifier(), 0);

			shader.setTransformation(dialogMatrix);

			VertexObjectCreator.basicRender(model, 2, 6, Tessellator.TRIANGLES);
		}
	}
}

package com.steve6472.sge.gfx;

import com.steve6472.sge.main.MainApp;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 25.01.2020
 * Project: SJP
 *
 ***********************/
public class GBuffer
{
	public int frameBuffer;
	public int depth;
	public int texture, normal, position;

	private int width, height;

	private static List<Integer> buffers = new ArrayList<>();
	private static List<Integer> attachments = new ArrayList<>();

	public GBuffer(MainApp main)
	{
		this(main.getWidth(), main.getHeight());
	}

	public GBuffer(int width, int height)
	{
		this.width = width;
		this.height = height;
		frameBuffer = createFrameBuffer();
		texture = createTextureAttachment(0, GL_RGBA, GL_UNSIGNED_BYTE);
		position = createTextureAttachment(1, GL_RGB16F, GL_FLOAT);
		normal = createTextureAttachment(2, GL_RGB16F, GL_FLOAT);
		depth = createDepthBufferAttachment();

		IntBuffer drawBuffers = BufferUtils.createIntBuffer(3);
		drawBuffers.put(GL_COLOR_ATTACHMENT0);
		drawBuffers.put(GL_COLOR_ATTACHMENT1);
		drawBuffers.put(GL_COLOR_ATTACHMENT2);
		drawBuffers.flip();
		GL20.glDrawBuffers(drawBuffers);

		unbindCurrentFrameBuffer();
	}

	private int createDepthBufferAttachment()
	{
		int depthBuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
		return depthBuffer;
	}

	private int createTextureAttachment(int colorAttachement, int internalFormat, int type)
	{
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, GL_RGBA, type, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + colorAttachement, texture, 0);
		attachments.add(texture);
		return texture;
	}

	public void clear()
	{
		bindFrameBuffer();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		unbindCurrentFrameBuffer();
	}

	public static void cleanUp()
	{
		for (int i : buffers)
		{
			glDeleteFramebuffers(i);
		}
		for (int i : attachments)
		{
			glDeleteTextures(i);
		}
	}

	public static void clearCurrentBuffer()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void deleteFrameBuffer()
	{
		buffers.remove((Integer) frameBuffer);
		attachments.remove((Integer) texture);

		glDeleteFramebuffers(frameBuffer);
		glDeleteTextures(texture);
	}

	public void bindFrameBuffer()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		glViewport(0, 0, width, height);
	}

	public void unbindCurrentFrameBuffer()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, width, height);
	}

	private int createFrameBuffer()
	{
		int frameBuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		buffers.add(frameBuffer);
		return frameBuffer;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}

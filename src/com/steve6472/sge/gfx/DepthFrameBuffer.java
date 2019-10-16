package com.steve6472.sge.gfx;

import com.steve6472.sge.main.MainApp;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 29.9.2018
 * Project: 3DTest
 *
 * Simple Texture Frame Buffer
 *
 ***********************/
public class DepthFrameBuffer
{
	public int frameBuffer, texture, depthBuffer;

	private boolean nearest;

	private static List<Integer> buffers = new ArrayList<>();
	private static List<Integer> textures = new ArrayList<>();

	public DepthFrameBuffer()
	{
		nearest = true;
	}

	public DepthFrameBuffer(MainApp main)
	{
		this(main.getWidth(), main.getHeight());
	}

	public DepthFrameBuffer(int width, int height)
	{
		frameBuffer = createFrameBuffer();
		texture = createTextureAttachment(width, height);
		depthBuffer = createDepthBufferAttachment(width, height);

		unbindCurrentFrameBuffer(width, height);
		nearest = true;
	}

	public DepthFrameBuffer(int width, int height, boolean nearest)
	{
		this.nearest = nearest;
		frameBuffer = createFrameBuffer();
		texture = createTextureAttachment(width, height);
		depthBuffer = createDepthBufferAttachment(width, height);

		unbindCurrentFrameBuffer(width, height);
	}

	public void clear(int w, int h)
	{
		bindFrameBuffer(w, h);
		glClear(GL_COLOR_BUFFER_BIT);
		unbindCurrentFrameBuffer(w, h);
	}

	public static void cleanUp()
	{
		for (int i : buffers)
		{
			glDeleteFramebuffers(i);
		}
		for (int i : textures)
		{
			glDeleteTextures(i);
		}
	}

	public void bindToRead(int colorAttachment)
	{
		Sprite.bind(0, texture);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, frameBuffer);
		GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0 + colorAttachment);
	}

	public static void clearCurrentBuffer()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void deleteFrameBuffer()
	{
		buffers.remove(frameBuffer);
		textures.remove(texture);

		glDeleteFramebuffers(frameBuffer);
		glDeleteTextures(texture);
	}

	public void bindFrameBuffer(MainApp main)
	{
		bindFrameBuffer(main.getWidth(), main.getHeight());
	}

	public void bindFrameBuffer(int width, int height)
	{
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		glViewport(0, 0, width, height);
	}

	public void resize(int width, int height)
	{
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
	}

	public void unbindCurrentFrameBuffer(MainApp main)
	{
		unbindCurrentFrameBuffer(main.getWidth(), main.getHeight());
	}

	public void unbindCurrentFrameBuffer(int width, int height)
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, width, height);
	}

	public int createFrameBuffer()
	{
		int frameBuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
//		glDrawBuffer(GL_COLOR_ATTACHMENT0);
		buffers.add(frameBuffer);
		return frameBuffer;
	}

	public int createTextureAttachment(int width, int height)
	{
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, nearest ? GL_NEAREST : GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, nearest ? GL_NEAREST : GL_LINEAR);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);
		textures.add(texture);
		return texture;
	}

	private int createDepthBufferAttachment(int width, int height)
	{
		int depthBuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
		return depthBuffer;
	}

	public int createTextureAttachment(int width, int height, int colorAttachment)
	{
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, nearest ? GL_NEAREST : GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, nearest ? GL_NEAREST : GL_LINEAR);
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + colorAttachment, texture, 0);
		textures.add(texture);
		return texture;
	}
}

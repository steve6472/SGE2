package steve6472.sge.main.node.core;

import org.joml.Vector2f;
import org.joml.Vector4f;
import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.Component;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.dialog.AdvancedMoveableDialog;
import steve6472.sge.main.KeyList;
import steve6472.sge.main.MainApp;
import steve6472.sge.main.Util;

import java.util.Iterator;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class GuiNode extends AdvancedMoveableDialog
{
	public static final Vector4f NODE_LINE_COLOR_OUTPUT = new Vector4f(1, 1, 1, 1);
	public static final Vector4f NODE_LINE_COLOR_INPUT = new Vector4f(1, 1, 1, 1);
	public static float NODE_LINE_SEGMENTS = 16f;
	public static float NODE_LINE_CONTROL_POINT_WEIGHT = 0.75f;

	private final AbstractNode node;
	private Button closeButton;

	public GuiNode(AbstractNode node)
	{
		this.node = node;
		node.guiNode = this;
	}

	public AbstractNode getNode()
	{
		return node;
	}

	@Override
	public void init(MainApp mainApp)
	{
		autoSize();
		initSize();

		for (int i = 0; i < node.inputData.getSize(); i++)
		{
			NodeJoint b = new NodeJoint(node, i, true, node.inputData.getNames()[i], node.inputData.getTypes()[i]);
			b.setSize(15, 15);
			b.setRelativeLocation(7, 25 + i * 20);
			addComponent(b);
		}

		for (int i = 0; i < node.outputData.getSize(); i++)
		{
			NodeJoint b = new NodeJoint(node, i, false, node.outputData.getNames()[i], node.outputData.getTypes()[i]);
			b.setSize(15, 15);
			b.setRelativeLocation(getWidth() - 22, 25 + i * 20);
			addComponent(b);
		}

		closeButton = initCloseButton();
		closeButton.addClickEvent(c -> {
			for (int i = 0; i < node.inputData.getSize(); i++)
			{
				NodePair con = node.inputConnections[i];
				if (con == null)
					continue;

				con.getNode().disconnectOutput(con.getIndex(), node, i);
				node.disconnectInput(i);
			}
			for (int i = 0; i < node.outputData.getSize(); i++)
			{
				NodePairList con = node.outputConnections[i];
				//				List<Pair<AbstractNode, Integer>> con = outputConnections[i];
				if (con == null)
					continue;

				for (Iterator<NodePair> iterator = con.iterator(); iterator.hasNext(); )
				{
					NodePair p = iterator.next();
					p.getNode().disconnectInput(p.getIndex());
					//TODO: Disconnect output ?
				}
				/*
				for (Iterator<Pair<AbstractNode, Integer>> iterator = con.iterator(); iterator.hasNext(); )
				{
					Pair<AbstractNode, Integer> p = iterator.next();
					p.getA().disconnectInput(p.getB());

					if (outputConnections.containsKey(i))
					{
						if (p.getB() == i && p.getA() == this)
							iterator.remove();
					}
				}*/
			}
		});
	}

	@Override
	public void tick()
	{
		if (getMain().isKeyPressed(KeyList.L_SHIFT) && isCursorInComponent())
			setLocation(getX() / 25 * 25, getY() / 25 * 25);
		node.tick();
	}

	private static final Vector2f BEZIER_OUT = new Vector2f();

	public void bezierCurve(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y, float time, float timeEnd)
	{
		float x0 = Util.calculateValue(time, timeEnd, p0x, p1x);
		float y0 = Util.calculateValue(time, timeEnd, p0y, p1y);

		float x1 = Util.calculateValue(time, timeEnd, p1x, p2x);
		float y1 = Util.calculateValue(time, timeEnd, p1y, p2y);

		float x2 = Util.calculateValue(time, timeEnd, p2x, p3x);
		float y2 = Util.calculateValue(time, timeEnd, p2y, p3y);

		float px0 = Util.calculateValue(time, timeEnd, x0, x1);
		float py0 = Util.calculateValue(time, timeEnd, y0, y1);

		float px1 = Util.calculateValue(time, timeEnd, x1, x2);
		float py1 = Util.calculateValue(time, timeEnd, y1, y2);

		float px = Util.calculateValue(time, timeEnd, px0, px1);
		float py = Util.calculateValue(time, timeEnd, py0, py1);

		BEZIER_OUT.set(px, py);
	}

	@Override
	public void render()
	{
		renderTitle(node.getName());
		renderNode();

		for (int i = 0; i < node.outputData.getSize(); i++)
		{
			if (node.outputConnections[i] == null)
				continue;

			NodePairList pairs = node.outputConnections[i];

			for (NodePair p : pairs.getList())
			{
				glBegin(GL_LINE_STRIP);
				glColor4f(NODE_LINE_COLOR_OUTPUT.x, NODE_LINE_COLOR_OUTPUT.y, NODE_LINE_COLOR_OUTPUT.z, NODE_LINE_COLOR_OUTPUT.w);
				final int startX = getX() + getWidth() - 14;
				final int startY = getY() + 32 + i * 20;
				final int endX = p.getNode().guiNode.getX() + 14;
				final int endY = p.getNode().guiNode.getY() + p.getIndex() * 20 + 32;
				for (float j = 0; j < 1f; j += 1f / NODE_LINE_SEGMENTS)
				{
					bezierCurve(startX, startY, startX + Math.abs(endX - startX) * NODE_LINE_CONTROL_POINT_WEIGHT, startY, endX - Math
						.abs(endX - startX) * NODE_LINE_CONTROL_POINT_WEIGHT, endY, endX, endY, j, 1f);
					glVertex2f(BEZIER_OUT.x, BEZIER_OUT.y);
				}
				glVertex2i(endX, endY);
				glEnd();
			}
		}

		for (int i = 0; i < node.inputData.getSize(); i++)
		{
			if (node.inputConnections[i] == null)
				continue;

			NodePair p = node.inputConnections[i];

			glBegin(GL_LINE_STRIP);
			glColor4f(NODE_LINE_COLOR_INPUT.x, NODE_LINE_COLOR_INPUT.y, NODE_LINE_COLOR_INPUT.z, NODE_LINE_COLOR_INPUT.w);
			final int startX = getX() + 14;
			final int startY = getY() + 32 + i * 20;
			final int endX = p.getNode().guiNode.getX() + p.getNode().guiNode.getWidth() - 14;
			final int endY = p.getNode().guiNode.getY() + p.getIndex() * 20 + 32;
			for (float j = 0; j < 1f; j += 1f / NODE_LINE_SEGMENTS)
			{
				bezierCurve(startX, startY, startX - Math.abs(endX - startX) * NODE_LINE_CONTROL_POINT_WEIGHT, startY, endX + Math
					.abs(endX - startX) * NODE_LINE_CONTROL_POINT_WEIGHT, endY, endX, endY, j, 1f);
				glVertex2f(BEZIER_OUT.x, BEZIER_OUT.y);
			}
			glVertex2i(endX, endY);
			glEnd();
		}

		if (getMain().isKeyPressed(KeyList.R_SHIFT) && isCursorInComponent())
		{
			int y = 1;
			StringBuilder sb = new StringBuilder();
			sb.append(node.getUuid().toString());
			for (NodePair i : node.inputConnections)
			{
				y++;
				if (i == null)
				{
					sb.append("\n null -> null -> null");
				} else
				{
					sb.append("\n").append(i.getIndex()).append(" -> ").append(i.getNode().getUuid()).append(" -> ").append(i);
				}
			}
			sb.append("\n").append("-".repeat(50));
			for (NodePairList i : node.outputConnections)
			{
				if (i == null)
					continue;

				for (NodePair c : i.getList())
				{
					if (c == null)
					{
						sb.append("\n").append("null").append(" -> ").append("null").append(" -> ").append("null");
					} else
					{
						sb.append("\n").append(i).append(" -> ").append(c.getNode().getUuid()).append(" -> ").append(c.getIndex());
					}
					y++;
				}
			}
			Font.render(getX() + 2, getY() - 10 * y, sb.toString());

			for (int i = 0; i < node.outputConnections.length; i++)
			{
				Font.render(getX() + getWidth() - 2, 25 + i * 20 + getY(), "" + node.outputStates[i]);
			}

			for (int i = 0; i < node.inputConnections.length; i++)
			{
				Font.render(getX() - 3 - Font.getTextWidth("" + node.inputStates[i], 1), 29 + i * 20 + getY(), "" + node.inputStates[i]);
			}
		}

		for (int i = 0; i < node.inputConnections.length; i++)
		{
			if (node.isManual(i))
			{
				final int textWidth = Font.getTextWidth("" + node.inputStates[i], 1);
				SpriteRender.fillRect(getX() - 7 - textWidth, 25 + i * 20 + getY(), textWidth + 7, 16, 0.25f, 0.25f, 0.25f, 1f);
				SpriteRender.renderBorder(getX() - 9 - textWidth, 23 + i * 20 + getY(), textWidth + 11, 20, 0, 0, 0, 1f);
				Font.render(getX() - 3 - textWidth, 29 + i * 20 + getY(), "" + node.inputStates[i]);
			}
		}
	}

	public Button getCloseButton()
	{
		return closeButton;
	}

	protected void renderNode() {}

	protected void initSize() {}

	protected void autoSize()
	{
		autoSize(-1, -1);
	}

	protected void autoSize(int width, int height)
	{
		int maxSize = Font.getTextWidth(node.getName(), 1);
		int maxLeft = 0;
		int maxRight = 0;

		for (String s : node.inputData.getNames())
			maxLeft = Math.max(maxLeft, Font.getTextWidth(s, 1));
		for (String s : node.outputData.getNames())
			maxRight = Math.max(maxRight, Font.getTextWidth(s, 1));

		maxSize = Math.max(maxSize, maxLeft + maxRight + 30);

		setSize(width == -1 ? maxSize + 30 : width, height == -1 ? Math.max(node.inputData.getSize(), node.outputData.getSize()) * 20 + 27 : height);
	}

	@Override
	protected void addComponent(Component component)
	{
		super.addComponent(component);
		getMain().getEventHandler().register(component);
	}

	@Override
	public boolean freezeGui()
	{
		return false;
	}

	@Override
	public boolean disableEvents()
	{
		return false;
	}

	@Override
	protected boolean keepInWindow()
	{
		return false;
	}
}

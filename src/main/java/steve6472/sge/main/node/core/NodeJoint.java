package steve6472.sge.main.node.core;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.ToggleButton;
import steve6472.sge.main.Log;
import steve6472.sge.main.util.Pair;

import java.util.function.Consumer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 15.04.2020
 * Project: NoiseGenerator
 *
 ***********************/
public class NodeJoint extends ToggleButton
{
	private static final boolean ENABLE_MULTIFLOW = false;

	private final AbstractNode node;
	private final int index;
	private final boolean isInput;
	private final JointType type;

	private Consumer<Button> createToggle()
	{
		return button ->
		{
			Pair<AbstractNode, NodeJoint> from = Nodes.selectedFrom;

			if (from == null && isInput && node.hasInput(index))
			{
				Nodes.disconnect(node, index);
				setToggled(false);
				return;
			}

			if (from == null)
			{
				Nodes.selectedFrom = new Pair<>(node, this);
			} else
			{
				if (from.getB() == this)
				{
					Nodes.selectedFrom = null;
					Log.err("Node can not be connected to itself!");
					setToggled(false);
				} else
				{
					if (isInput == from.getB().isInput)
					{
						Log.err("Can not connect input to input or output to output!");
					} else
					{
						if (from.getA() == node)
						{
							Log.err("Node can not be connected to itself!");
						}
						else if (isInput)
						{
							if (node.hasInput(index))
							{
								Log.err("Input join can have only one connection!");
							} else
							{
								if (type != from.getB().type)
								{
									Log.err("Types are not compatible!");
								} else
								{
									if (type == JointTypeRegistry.FLOW && from.getA().hasOutput(from.getB().index) && !ENABLE_MULTIFLOW)
									{
										Log.err("Flow can have only one output!");
									} else
									{
										node.connectInput(index, from.getA(), from.getB().index);
										from.getA().connectOutput(from.getB().index, node, index);
									}
								}
							}
						} else
						{
							if (from.getA().hasInput(from.getB().index))
							{
								Log.err("Input join can have only one connection!");
							} else
							{
								if (type != from.getB().type)
								{
									Log.err("Types are not compatible!");
								} else
								{
									if (type == JointTypeRegistry.FLOW && node.hasOutput(index) && !ENABLE_MULTIFLOW)
									{
										Log.err("Flow can have only one output!");
									} else
									{
										node.connectOutput(index, from.getA(), from.getB().index);
										from.getA().connectInput(from.getB().index, node, index);
									}
								}
							}
						}
					}
					from.getB().setToggled(false);
					setToggled(false);
					Nodes.selectedFrom = null;
				}
			}
		};
	}

	public NodeJoint(AbstractNode node, int index, boolean isInput, String text, JointType type)
	{
		super(text);
		this.node = node;
		this.index = index;
		this.isInput = isInput;
		this.type = type;
		this.addClickEvent(createToggle());
	}

	protected void renderText()
	{
		if (this.isToggled())
		{
			SpriteRender.fillRect(getX(), getY(), getWidth(), getHeight(), getScheme().hoveredFill.x, getScheme().hoveredFill.y, getScheme().hoveredFill.z, 0.5f);
		}

		if (getText() == null)
			return;

		int y = getY() + getHeight() / 2 - 4;

		if (isInput)
		{
			Font.render(getX() + getWidth() + 2, y, getText());
		} else
		{
			int width = Font.getTextWidth(getText(), 1);
			Font.render(getX() - width - 3, y, getText());
		}
	}

	@Override
	public void render()
	{
		// TODO: Change the texture of the button
		super.render();

		if (isInput)
		{
			if (node.hasInput(index))
				Font.renderCustom(getX() + getWidth() / 2 - 4, getY() + getHeight() / 2 - 4, 1f, type.colorTag, type.connected);
			else
				Font.renderCustom(getX() + getWidth() / 2 - 4, getY() + getHeight() / 2 - 4, 1f, type.colorTag, type.unconnected);
		} else
		{
			if (node.hasOutput(index))
				Font.renderCustom(getX() + getWidth() / 2 - 4, getY() + getHeight() / 2 - 4, 1f, type.colorTag, type.connected);
			else
				Font.renderCustom(getX() + getWidth() / 2 - 4, getY() + getHeight() / 2 - 4, 1f, type.colorTag, type.unconnected);
		}
	}
}

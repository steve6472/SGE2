package steve6472.sge.gui.planner;

import steve6472.sge.gfx.SpriteRender;
import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gfx.font.Font;
import steve6472.sge.main.MainApp;
import steve6472.sge.gui.components.Button;
import steve6472.sge.gui.components.CheckBox;
import steve6472.sge.gui.components.ItemList;
import steve6472.sge.gui.components.TextField;

import java.util.function.Consumer;

/**********************
 * Created by steve6472
 * On date: 02.06.2019
 * Project: SJP
 *
 ***********************/
class BoxOptionsDialog extends PlannerDialog
{
	private Planner.Box box;

	private Button dimensions;
	private Button freePosition;
	private CheckBox isRelative;
	private TextField name;

	BoxOptionsDialog(Planner.Box box, Planner planner, Consumer<Button> close)
	{
		super(planner, close);
		this.box = box;
	}

	@Override
	public void Init(MainApp main)
	{
		dimensions = new Button("Dimensions");
		dimensions.setRelativeLocation(7, 29);
		dimensions.setSize(80, 30);
		dimensions.addClickEvent(c ->
		{
			setAllEnabled(false);
			PositionDialog dialog = new PositionDialog(box, planner, this);
			main.showDialog(dialog).center();
		});
		addComponent(dimensions);

		freePosition = new Button("Free Move");
		freePosition.setRelativeLocation(7, 69);
		freePosition.setSize(80, 30);
		freePosition.addClickEvent(c -> {
			setAllEnabled(false);
			close();
			main.showDialog(new FreePosition(box, planner, this));
		});
		addComponent(freePosition);

		name = new TextField();
		name.setText(box.name);
		name.endCarret();
		name.setRelativeLocation(7, 109);
		name.setSize(160, 20);
		name.addKeyEvent((c, k) -> box.name = name.getText());
		addComponent(name);

		isRelative = new CheckBox();
		isRelative.setSelectedChar(CustomChar.CROSS);
		isRelative.setRelativeLocation(160, 34);
		isRelative.setSize(20, 20);
		addComponent(isRelative);

		ItemList il = new ItemList(9);
		il.setRelativeLocation(-120, 0);
		il.setSize(120, 184);
		il.setMultiselect(false);
		addComponent(il);
		il.addItem("Button");
		il.addItem("CheckBox");
		il.addItem("ProgressBar");
		il.addItem("ScrollBar");
		il.addItem("Slider");
		il.addItem("SliderVertical");
		il.addItem("TextField");
		il.addItem("ToggleButton");
		il.addChangeEvent(c -> box.component = switch (il.getSelectedItemsIndices().get(0)) {
			case 0 -> planner.cr.button;
			case 1 -> planner.cr.checkBox;
			case 2 -> planner.cr.progressBar;
			case 3 -> planner.cr.scrollBar;
			case 4 -> planner.cr.slider;
			case 5 -> planner.cr.sliderVertical;
			case 6 -> planner.cr.textField;
			case 7 -> planner.cr.toggleButton;
			default -> null;
		});

		if (box.component == planner.cr.button) il.getItems().get(0).setSelected(true);
		if (box.component == planner.cr.checkBox) il.getItems().get(1).setSelected(true);
		if (box.component == planner.cr.progressBar) il.getItems().get(2).setSelected(true);
		if (box.component == planner.cr.scrollBar) il.getItems().get(3).setSelected(true);
		if (box.component == planner.cr.slider) il.getItems().get(4).setSelected(true);
		if (box.component == planner.cr.sliderVertical) il.getItems().get(5).setSelected(true);
		if (box.component == planner.cr.textField) il.getItems().get(6).setSelected(true);
		if (box.component == planner.cr.toggleButton) il.getItems().get(7).setSelected(true);
	}

	void setAllEnabled(boolean enabled)
	{
		getComponents().forEach(g -> {
			if (g instanceof Button) ((Button) g).setEnabled(enabled);
			if (g instanceof TextField) ((TextField) g).setEditable(enabled);
		});
	}

	@Override
	public void tick()
	{
	}

	@Override
	public void render()
	{
		renderTitle(box.name);
		SpriteRender.renderSingleBorder(x + 97, y + 29, 90, 30,
				0.6f, 0.6f, 0.6f, 1,
				0, 0.2f, 0.3f, 0.2f);
		Font.render("Relative", x + 102, y + 39);
	}

	@Override
	protected int[] getSize()
	{
		return new int[] {256, 145};
	}

	@Override
	protected boolean canMove()
	{
		return super.canMove() && dimensions.isEnabled();
	}
}

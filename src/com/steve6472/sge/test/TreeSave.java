package com.steve6472.sge.test;

import com.steve6472.sge.main.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 25.04.2019
 * Project: SJP
 *
 ***********************/
public class TreeSave
{
	public static void main(String[] args)
	{
		new TreeSave().main();
	}

	void main()
	{
		Tree tree = new Tree("Oak");
		showTree(tree);
		saveTree(tree);
		System.out.println(out);
		loadTree(out);
	}

	String out = "";

	List<?> outTree = new ArrayList<>();

	void loadTree(String data)
	{
		Object root = null;
		String s = "";
		for (char c : data.toCharArray())
		{
			if (c == '}')
				continue;

			if (c == '{')
			{
				if (!s.isBlank())
				{
					if (root == null)
					{
						root = createInstance(s.split(":")[0], s.split(":")[1]);
					} else
					{
						System.out.println(createInstance(s.split(":")[0], s.split(":")[1]));
					}
				}
				s = "";
			} else
			{
				s += c;
			}
		}
		System.out.println(createInstance(s.split(":")[0], s.split(":")[1]));
	}

	private Object createInstance(String type, String data)
	{
		if (type.equals("Tree"))
		{
			return new Tree(data);
		} else if (type.equals("Branch"))
		{
			Branch b = new Branch();
			String[] d = data.split(";");
			b.fruits = new Fruit[2];
			b.fruits[0] = new Apple(Integer.parseInt(d[0]));
			b.fruits[1] = new Jelly(Integer.parseInt(d[1]));
			System.out.print(Arrays.toString(d) + " ");
			return b;
		}

		throw new IllegalArgumentException();
	}


	void saveTree(Tree tree)
	{
		out += "{" + tree.getClass().getSimpleName() + ":" + tree.getSave();

		for (Branch branch : tree.branches)
		{
			saveBranch(branch);
		}

		out += "}";
	}

	void saveBranch(Branch branch)
	{
		out += "{" + branch.getClass().getSimpleName() + ":" + branch.getSave();

		for (Branch branch1 : branch.branches)
		{
			saveBranch(branch1);
		}

		out += "}";
	}

	void showTree(Tree tree)
	{
		System.out.println("-Tree");
		System.out.println(" └──Type: " + tree.type);

		for (Branch branch : tree.branches)
		{
			showBranch(branch, 0);
		}
	}

	void showBranch(Branch branch, int distance)
	{
		System.out.println(getDistance(distance) + " └──Branch");
		System.out.println(getDistance(distance) + "    └──Fruits");

		for (int i = 0; i < branch.fruits.length; i++)
		{
			if (i == branch.fruits.length - 1)
				System.out.println(getDistance(distance) + "       └───" + branch.fruits[i].getName() + ": " + branch.fruits[i].count);
			else
				System.out.println(getDistance(distance) + "       ├───" + branch.fruits[i].getName() + ": " + branch.fruits[i].count);
		}

		for (Branch branch1 : branch.branches)
		{
			showBranch(branch1, distance + 1);
		}
	}

	String getDistance(int distance)
	{
		if (distance > 0)
			return "    ".repeat(Math.max(0, distance));
		return "   ".repeat(Math.max(0, distance));
	}

	interface Saveable
	{
		String getSave();

		void load(String[] s);
	}

	class Tree implements Saveable
	{
		Tree(String type)
		{
			this.type = type;
			branches = new Branch[RandomUtil.randomInt(3, 3)];

			for (int i = 0; i < branches.length; i++)
			{
				branches[i] = new Branch(50);
			}
		}

		String type;
		Branch[] branches;

		@Override
		public String getSave()
		{
			return type;
		}

		@Override
		public void load(String[] s)
		{
			type = s[0];
		}
	}

	class Branch implements Saveable
	{
		Branch()
		{
		}

		Branch(double branchProbability)
		{
			if (branchProbability > 0)
				branches = new Branch[RandomUtil.randomInt(0, 2)];
			else
				branches = new Branch[0];

			for (int i = 0; i < branches.length; i++)
			{
				branches[i] = new Branch(branchProbability - RandomUtil.randomDouble(80, 95));
			}

			fruits = new Fruit[2];
			fruits[0] = new Apple();
			fruits[1] = new Jelly();
		}

		Branch[] branches;
		Fruit[] fruits;

		@Override
		public String getSave()
		{
			StringBuilder save = new StringBuilder();

			for (Fruit fruit : fruits)
			{
				save.append(fruit.getSave()).append(";");
			}

			save.deleteCharAt(save.length() - 1);

			return save.toString();
		}

		@Override
		public void load(String[] s)
		{

		}
	}

	abstract class Fruit implements Saveable
	{
		int count;
		public abstract String getName();
	}

	class Apple extends Fruit
	{
		Apple()
		{
			count = RandomUtil.randomInt(2, 9);
		}

		Apple(int count)
		{
			this.count = count;
		}

		@Override
		public String getName()
		{
			return "Apple";
		}

		@Override
		public String getSave()
		{
			return "" + count;
		}

		@Override
		public void load(String[] s)
		{
			count = Integer.parseInt(s[0]);
		}
	}

	class Jelly extends Fruit
	{
		Jelly()
		{
			count = RandomUtil.randomInt(0, 2);
		}

		Jelly(int count)
		{
			this.count = count;
		}

		@Override
		public String getName()
		{
			return "Jelly";
		}

		@Override
		public String getSave()
		{
			return "" + count;
		}

		@Override
		public void load(String[] s)
		{
			count = Integer.parseInt(s[0]);
		}
	}
}

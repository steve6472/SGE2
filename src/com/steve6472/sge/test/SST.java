package com.steve6472.sge.test;

import com.steve6472.sge.main.smartsave.SmartSave;

import java.io.File;
import java.io.IOException;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 04.07.2019
 * Project: SJP
 *
 ***********************/
public class SST
{
	public static void main(String[] args) throws IOException
	{
		File f = new File("output_test.txt");
		if (!f.exists()) f.createNewFile();

		SmartSave.openOutput(f);
		SmartSave.writeData("8", "eight");
//		SmartSave.writeData("test", "This is a test string");
//		SmartSave.writeData("lorem", "Lorem Ipsum");
//		SmartSave.writeData("number", 177013);
//		SmartSave.writeData("numbers", new int[] {0, 1, 2, 4, 8, 16, 32, 64});
		SmartSave.closeOutput();

		/*
		SmartSave.openInput(f);
		SmartSave.readFull();
		System.out.println(SmartSave.get("test"));
		System.out.println(SmartSave.get("lorem"));
		System.out.println(SmartSave.get("number"));
		System.out.println(Arrays.toString((int[]) SmartSave.get("numbers")));
		SmartSave.closeInput();
		*/
	}
}

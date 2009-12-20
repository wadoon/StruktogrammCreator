package weigl.stgr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import weigl.stgr.controller.Builder;
import weigl.stgr.controller.StgrBuilder;
import weigl.stgr.controller.XmlBuilder;
import weigl.stgr.exception.BuildException;
import weigl.stgr.model.StgrModel;

public class StgrCli {
	public static void main(String[] args) throws BuildException, IOException {
		if (args.length != 2) {
			usage();
			System.exit(2);
		}

		Builder[] b = new Builder[] { new XmlBuilder(), new StgrBuilder() };

		File input = new File(args[0]);

		for (Builder builder : b) {
			if (builder.isSuitableFor(input)) {
				StgrModel sm = builder.parse(input);				
				BufferedImage bi = sm.getImage();
				ImageIO.write(bi, 
						suffix(args[1])
						, new File(args[1]));
				return;
			}
		}

	}

	private static String suffix(String string) {
		return string.substring(string.indexOf('.')+1);
	}

	private static void usage() {
		System.out.println("java -jar XXXX <InputFile> <OutputFile>");
	}
}

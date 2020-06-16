package com.mj.file;

import java.io.*;
import java.lang.reflect.ParameterizedType;

public class Files {

	public static void writeToFile(String filePath, Object data) {
		writeToFile(filePath, data, false);
	}

	public static void writeToFile(String filePath, Object data, boolean append) {
		if (filePath == null || data == null) return;

		try {
			File file = new File(filePath);
			File pFile = file.getParentFile();
			if (pFile != null) {
				pFile.mkdirs();
			}

			try (
					FileWriter writer = new FileWriter(file, append);
					BufferedWriter out = new BufferedWriter(writer)
			) {
				out.write(data.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件夹下的内容
	 *
	 * @param dir        文件路径
	 * @param extensions 文件的扩展名
	 */
	public static FileInfo read(String dir, String[] extensions) {
		if (dir == null) return null;

		File dirFile = new File(dir);
		if (!dirFile.exists()) return null;

		FileInfo info = new FileInfo();
		//根据扩展名过滤文件
		dirFile.listFiles(subFile -> {
			String subFilepath = subFile.getAbsolutePath();
			if (subFile.isDirectory()) {
				info.append(read(subFilepath, extensions));
			} else if (extensions != null && extensions.length > 0) {
				for (String e : extensions) {
					if (subFilepath.endsWith("." + e)) {
						info.setFiles(info.getFiles() + 1);
						info.append(read(subFilepath));
					}
				}
			}
			return false;
		});


		return info;
	}


	public static FileInfo read(String file) {
		if (file == null) return null;
		File f = new File(file);
		if (!f.exists()) return null;
		FileInfo info = new FileInfo();
		try {

			try (BufferedReader br = new BufferedReader(new FileReader(f))
			) {
				int lines = 0;
				StringBuilder content = new StringBuilder();
				String str;
				while ((str = br.readLine()) != null) {
					lines++;
					content.append(str);
					content.append("\n");
				}
				info.setLines(lines);
				info.setContent(content.toString());

			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

}

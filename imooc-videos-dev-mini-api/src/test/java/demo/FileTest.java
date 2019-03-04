package demo;

import java.io.File;

/**
 * @author 作者 lyj
 * @version 创建时间：2019年3月4日 下午7:15:19
 * 类说明
 */
public class FileTest {
	
	public static void main(String[] args) {
        // This is the path where the file's name you want to take.
        String path = "F://vedios//190304A3D07GRS80//face";
        getFile(path);
    }

    private static void getFile(String path) {
        File file = new File(path);
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                // only take file name
                System.out.println("^^^^^" + array[i].getName());
                // take file path and name
                System.out.println("#####" + array[i]);
                // take file path and name
                System.out.println("*****" + array[i].getPath());
            } else if (array[i].isDirectory()) {
                getFile(array[i].getPath());
            }
        }
    }

}

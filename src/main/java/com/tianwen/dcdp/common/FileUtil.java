package com.tianwen.dcdp.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件操作工具类
 * 
 * @author lixinglei
 * @date 2012-04-10
 * @description 封装对于文件操作的各种常用方法
 */
public class FileUtil {
	/**
	 * 缓冲大小
	 */
	public static final int BUFFER_SIZE = 4096;

	/**
	 * 返回文件扩展名 例如：.mp3
	 * 
	 * @param file
	 * @return
	 */
	public static final String getFileExtendsName(File file) {
		if (file == null)
			return null;
		String fileName = file.getName();
		if (fileName.indexOf(".") == -1) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf("."));
		}
	}
	
	/**
	 * 获取文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getExtendsName(String fileName){
		if(fileName.indexOf(".") == -1){
			return fileName;
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 返回文件扩展名 例如: .mp3
	 * 
	 * @param fileName
	 * @return
	 */
	public static final String getFileExtendsName(String fileName) {
		if (General.isEmpty(fileName)) {
			return "";
		}
		if (fileName.indexOf(".") == -1) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * 获取文件名 不带扩展名
	 * @param file
	 * @return
	 */
	public static final String getFileName(File file){
		return getFileName(file.getName());
	}
	
	/**
	 * 获取文件名 不带扩展名
	 * @param fileName
	 * @return
	 */
	public static final String getFileName(String fileName){
		if(fileName.indexOf(".") != -1)
			return fileName.substring(0, fileName.lastIndexOf("."));
		return fileName;
	}

	/**
	 * 上传文件到目标文件夹
	 * 
	 * @param sourceFile
	 * @param destDir
	 * @throws IOException
	 */
	public static void uploadFileToDir(File sourceFile, File destDir)
			throws IOException {
		copyFileToDir(sourceFile, destDir);
	}

	/**
	 * 上传文件到目标文件
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void uploadFile(File sourceFile, File destFile)
			throws IOException {
		copyFile(sourceFile, destFile);
	}

	/**
	 * 文件夹不存在则新建
	 * 
	 * @param dirPath
	 *            如"D:/Books"
	 */
	public static boolean mkdirs(String dirPath) throws FileNotFoundException {
		if (dirPath == null || dirPath.equals("")) {
			throw new FileNotFoundException("指定文件夹" + dirPath + "为空，无法创建");
		}
		File file = new File(dirPath);
		return mkdirs(file);

	}

	/**
	 * 文件夹不存在则新建
	 * 
	 * @param directory
	 *            如"D:/Books"
	 */
	public static boolean mkdirs(File directory) {
		if (!directory.exists()) {
			return directory.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * 获取sourceDir目录下所有后缀为suffix的文件（文件夹忽略，不返回），如果suffix为""或null，则返回全部文件条目
	 * 
	 * @param sourceDir
	 * @param suffix
	 * @return
	 * @throws IOException
	 */
	public static List<File> getFilesFromDir(File sourceDir, String suffix)
			throws IOException {
		// 如果不是文件夹，则返回null
		if (!sourceDir.isDirectory()) {
			return null;
		}
		// 如果fileName为""或null，则返回全部文件条目
		boolean isAll = true;
		if (!(suffix == null || suffix.equals(""))) {
			isAll = false;
		}
		// 如果文件夹下没有文件则返回空列表
		File[] fileArray = sourceDir.listFiles();
		if (fileArray == null || fileArray.length < 1) {
			return new ArrayList<File>();
		}

		List<File> fileList = new ArrayList<File>();
		// 文件或文件夹的全路径名
		for (int i = 0; i < fileArray.length; i++) {
			File tempFile = fileArray[i];
			// 如果是文件
			if (tempFile.isFile()) {
				// 如果要获取全部，或者后缀名匹配，则添加到列表
				if (isAll || tempFile.getCanonicalPath().endsWith(suffix)) {
					fileList.add(tempFile);
				}
			} else {
				fileList.addAll(getFilesFromDir(tempFile, suffix));
			}
		}
		return fileList;
	}

	/**
	 * 获取targetDir目录下所有后缀为suffix的文件条目，如果suffix为""或null，则返回全部文件条目
	 * 
	 * @param targetDir
	 * @return
	 */
	public static List<String> getFilesFromDir(String targetDir, String suffix)
			throws IOException {
		if (General.isEmpty(targetDir)) {
			return null;
		}

		List<File> fileList = getFilesFromDir(new File(targetDir), suffix);
		if (fileList == null || fileList.size() < 1) {
			return null;
		}

		List<String> listFileName = new ArrayList<String>();
		for (int i = 0; i < fileList.size(); i++) {
			listFileName.add(fileList.get(i).getCanonicalPath());
		}
		return listFileName;

	}

	/**
	 * 从一个输入流复制内容到一个输出流，不附带关闭流操作
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int count = 0;
		while ((count = in.read(buffer)) != -1) {
			out.write(buffer, 0, count);
		}
		out.flush();
	}

	/**
	 * 将一个指定文件复制到另外一个文件，目标文件的父目录不存在，则抛异常
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 * @throws IOException
	 *             if the copying fails.
	 */
	public static void copyFile(String sourceFilePath, String destFilePath)
			throws IOException {
		copyFile(new File(sourceFilePath), new File(destFilePath));
	}

	/**
	 * 将一个指定文件复制到另外一个文件，目标文件的父目录不存在，则抛异常
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 *             if the copying fails.
	 */
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		copyFile(sourceFile, destFile, false);
	}

	/**
	 * 将一个指定文件复制到另外一个文件,可以指定如果目标文件的父目录不存在，是否直接新建
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 * @param autoMkdirs
	 * @throws IOException
	 *             if the copying fails.
	 */
	public static void copyFile(String sourceFilePath, String destFilePath,
			boolean autoMkdirs) throws IOException {
		copyFile(new File(sourceFilePath), new File(destFilePath), autoMkdirs);
	}

	/**
	 * 将一个指定文件复制到另外一个文件,可以指定如果目标文件的父目录不存在，是否直接新建
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @param autoMkdirs
	 * @throws IOException
	 *             if the copying fails.
	 */
	public static void copyFile(File sourceFile, File destFile,
			boolean autoMkdirs) throws IOException {
		// 如果源文件和目标文件是同一个，则直接返回
		/*
		 * if(sourceFile.getCanonicalPath().equals(destFile.getCanonicalPath())){
		 * return ; }
		 */

		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			if (autoMkdirs) {
				mkdirs(destFile.getParent());
			}
			out = new FileOutputStream(destFile);
			in = new FileInputStream(sourceFile);
			copy(in, out);
		} finally {
			close(in);
			close(out);
		}
	}

	/**
	 * 将文件copy到指定目录
	 * 
	 * @param sourceFilePath
	 * @param destDirPath
	 */
	public static void copyFileToDir(String sourceFilePath, String destDirPath)
			throws IOException {
		copyFileToDir(new File(sourceFilePath), new File(destDirPath));
	}

	/**
	 * 将一个指定文件复制到一个文件夹中
	 * 
	 * @param sourceFile
	 * @param destDir
	 * @throws IOException
	 *             if the copying fails.
	 */
	public static void copyFileToDir(File sourceFile, File destDir)
			throws IOException {
		if (destDir == null) {
			throw new NullPointerException("目标文件夹不能为空");
		}
		if (!destDir.exists() || !destDir.isDirectory()) {
			throw new IllegalArgumentException(destDir + "不是一个文件夹");
		}
		File destFile = new File(destDir, sourceFile.getName());
		copyFile(sourceFile, destFile);
	}

	/**
	 * 复制文件夹到另外一个文件夹
	 * 
	 * @param sourceDir
	 * @param destDir
	 * @throws IOException
	 */
	public static void copyDirectory(File sourceDir, File destDir)
			throws IOException {
		if (sourceDir == null || !sourceDir.exists()) {
			throw new IOException("源文件夹为空或不存在！");
		}
		if (destDir == null || !destDir.exists()) {
			throw new IOException("目标文件夹为空或不存在！");
		}
		if (!sourceDir.isDirectory()) {
			throw new IOException(sourceDir + "源文件夹不是一个文件夹！");
		}
		if (sourceDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
			throw new IOException("源文件夹和目标文件夹是同一个文件夹！");
		}

		File[] sourceFiles = sourceDir.listFiles();
		if (sourceFiles != null && sourceFiles.length > 0) {
			for (File sourceFile : sourceFiles) {
				File copiedFile = new File(destDir, sourceFile.getName());
				if (sourceFile.isDirectory()) {
					copyDirectory(sourceFile, new File(destDir, sourceFile
							.getName()));
				} else {
					copyFile(sourceFile, copiedFile, true);
				}
			}
		}

	}

	/**
	 * 重命名文件
	 * 
	 * @param scrFile
	 *            源文件
	 * @param destFile
	 *            改为的文件
	 */
	public static boolean rename(File scrFile, File destFile) {
		return scrFile.renameTo(destFile);
	}

	/**
	 * 重命名文件
	 * 
	 * @param scrFilePath
	 * @param destFilePath
	 */
	public static boolean rename(String scrFilePath, String destFilePath) {
		return new File(scrFilePath).renameTo(new File(destFilePath));
	}

	/**
	 * 对于可以关闭的流（如OutputStream、InputStream、Reader和Writer等）或者其他设备进行关闭操作
	 * 
	 * @param device
	 */
	public static void close(Closeable device) {
		if (null != device) {
			try {
				if (device instanceof Flushable) {
					((Flushable) device).flush();
				}
				device.close();
			} catch (IOException e) {
				// logger
			}
		}
	}

	/**
	 * 关闭ZipFile,忽略关闭时的异常,用于finally块
	 * 
	 * @param device
	 */
	public static void close(ZipFile device) {
		if (null != device) {
			try {
				device.close();
			} catch (IOException e) {
				// logger
			}
		}
	}

	/**
	 * 删除一个非空文件，如果要删除的文件为空，则不做操作
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 删除一组非空文件，如果要删除的文件为空，则不做操作
	 * 
	 * @param file
	 */
	public static void deleteFiles(File[] files) {
		for (File file : files) {
			deleteFile(file);
		}
	}
	
	public static void deleteFileRf(File file) {
		if (file != null) {
			if (file.isFile()) {
				deleteFile(file);
			} else {
				File[] files = file.listFiles();
				if (files != null) {
					for (File f : files) {
						deleteFileRf(f);
					}					
				}
				deleteFile(file);
			}
			
		}
	}
	
	/**
	 * 清空一个文件夹下的所有文件与目录
	 * 
	 * @param dir
	 */
	public static void clearDirectory(File dir) {
		if (dir.exists() == false || dir.isDirectory() == false) {
			return;
		}

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				clearDirectory(files[i]);
			}
			files[i].delete();
		}
	}

	/**
	 * 将指定文件路径根据系统信息格式化
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getCanonicalPath(String filePath) {
		return filePath.replace('/', File.separatorChar).replace('/',
				File.separatorChar);
	}

	/**
	 * 将指定的文件解压到当前目录下的temp文件夹中
	 * 
	 * @param zipFileName
	 */
	public static String uncompressFileToTemp(String zipFileName,
			String parentPath) throws IOException {

		if (General.isEmpty(zipFileName) || General.isEmpty(parentPath)) {
			return null;
		}

		StringBuilder sb = new StringBuilder("");
		sb.append(zipFileName.trim()).append(File.separator).append(
				parentPath.trim());

		uncompressFileToDir(zipFileName, sb.toString());
		// 解压文件路径为YYYYMMDD/bookid/
		sb = new StringBuilder("");
		return sb.append(
				General.substringBeforLast(zipFileName, File.separator))
				.append(File.separator).append(parentPath).toString();

	}

	/**
	 * 将指定的文件解压到指定目录的下
	 * @param zipFileName
	 * @param targetFilePath 为""或null则解压到当前目录的temp文件夹中
	 */
	public static void uncompressFileToDir(String zipFileName,
			String targetFilePath) throws IOException {

		if (General.isEmpty(zipFileName)) {
			throw new FileNotFoundException("指定的文件" + zipFileName + "为空！");
		}
		if (General.isEmpty(targetFilePath)) {
			throw new FileNotFoundException("指定的文件" + targetFilePath + "为空！");
		}
		File file = new File(targetFilePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFileName);
			uncompressFileToDir(zipFile, file);			
		} finally {
			close(zipFile);			
		}
		
	}

	/**
	 * 解压zipFile到指定文件夹destDir
	 * 
	 * @param sourceZipFile
	 * @param destDir
	 * @throws IOException
	 */
	public static void uncompressFileToDir(ZipFile sourceZipFile, File destDir)
			throws IOException {
		InputStream in = null;
		OutputStream out = null;
		ZipEntry zipEntry = null;
		File destFile = null;
		try {
			Enumeration<?> enumeration = sourceZipFile.entries();
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				if (zipEntry.isDirectory()) {
					continue;
				}
				in = sourceZipFile.getInputStream(zipEntry);
				destFile = new File(destDir, zipEntry.getName());
				mkdirs(destFile.getParentFile());
				out = new FileOutputStream(destFile);
				copy(in, out);
			}
		} finally {
			close(in);
			close(out);
		}
	}
	
	public static File[] listSortedFiles(File dirFile) {

		File[] files = dirFile.listFiles();

		FileWrapper[] fileWrappers = new FileWrapper[files.length];
		for (int i = 0; i < files.length; i++) {
			fileWrappers[i] = new FileWrapper(files[i]);
		}

		Arrays.sort(fileWrappers);

		File[] sortedFiles = new File[files.length];
		for (int i = 0; i < files.length; i++) {
			sortedFiles[i] = fileWrappers[i].getFile();
		}

		return sortedFiles;
	}
	
	 public static void main(String[] args) {
			File f = new File("/home/seven/a/b/c/d.txt");
			try {
				mkdirs(f.getParent());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
		/**
		 * 移动文件
		 * @param sourceFile
		 * @param destFile
		 */
		public static void moveFile(File sourceFile, File destFile){
			if(destFile.getParentFile().exists() == false || destFile.getParentFile().isDirectory() == false){
				destFile.getParentFile().mkdirs();
			}
			sourceFile.renameTo(destFile);
		}
}

@SuppressWarnings("rawtypes")
class FileWrapper implements Comparable {
    /** File */
    private File file;
    
    public FileWrapper(File file) {
        this.file = file;
    }
     
    public int compareTo(Object obj) {
        FileWrapper castObj = (FileWrapper)obj;
        
        if(this.file.getName().indexOf("jpg_Page") != -1 && castObj.getFile().getName().indexOf("jpg_Page") != -1){
        	String n1 = this.file.getName().substring(this.file.getName().indexOf("jpg_Page") + 8);
        	String n2 = castObj.getFile().getName().substring(castObj.getFile().getName().indexOf("jpg_Page") + 8);
        	if(n1.indexOf(".") != -1)
        		n1 = n1.substring(0, n1.indexOf("."));
        	if(n2.indexOf(".") != -1)
        		n2 = n2.substring(0, n2.indexOf("."));
        	
        	if(General.isNumber(n1) && General.isNumber(n2)){
        		int num1 = Integer.parseInt(n1);
        		int num2 = Integer.parseInt(n2);
        		if(num1 > num2)
        			return 1;
        		else if(num1 < num2)
        			return -1;
        		else return 0;
        	}else
        		return 0;
        }
        
        
                
//        if (this.file.getName().compareToIgnoreCase(castObj.getFile().getName()) > 0) {
//            return 1;
//        } else if (this.file.getName().compareToIgnoreCase(castObj.getFile().getName()) < 0) {
//            return -1;
//        } else {
//            return 0;
//        }
        
        return 0;
    }
    

    
    public File getFile() {
        return this.file;
    }
    /**
     * 
     * @param path
     * @return
     */
    public boolean isNotEmpty(String path){
    	File file = new File(path);
		if (file.isDirectory()) {
	         String[] files = file.list();
	         if (files.length > 0) {
	            return true;
	         }
	      }
		return false;
    }
    
   
}

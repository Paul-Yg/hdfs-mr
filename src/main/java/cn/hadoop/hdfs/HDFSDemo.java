package cn.hadoop.hdfs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class HDFSDemo {
	
	private FileSystem fs;

	@Before
	public void init() throws Exception {
		//首先创建FileSystem实现类（工具类）
		fs = FileSystem.get(new URI("hdfs://192.168.75.128:9000"),new Configuration(),"root");
	}

	public static void main(String[] args) throws Exception {
		/**
		 * 1.根据文件系统的类型找到FileSystem实现类DistributedFileSystem，通过反射,实例化该对象。
		 * 2.DistributedFileSystem持有DFSClient的引用，DFSClient持有代理对象（ClientProtocol）引用
		 * 	   ClientProtocol接口的实现类是在服务器端实现的(通过hadoop的RPC机制得到服务端的代理对象）
		 */
	    FileSystem fs = FileSystem.get(new URI("hdfs://192.168.75.128:9000"),new Configuration());
	    /**
	     * 在open里面抓取块的信息，客户端通过hadoop的RPC机制跟Namenode的进程进行通信，将要下载的文件
	     * 作为方法的参数传给Namenode进程里面的某个类，Namenode查询的元数据信息作为返回值返回给客户端
	     * 客户端把块的信息作为流的成员变量
	     */
	    InputStream in =fs.open(new Path("/test"));
		OutputStream out = new FileOutputStream("e://db");
		IOUtils.copyBytes(in, out, 4096, true);  
	}

	@Test
	public void testUpLoad() throws Exception {
		//读取本地文件系统的文件,返回输入流
		InputStream in = new FileInputStream("e://LICENSE.txt");
		//在HDFS上创建一个文件，返回输出流
		OutputStream out =fs.create(new Path("/db"));
		//输入-》输出
		IOUtils.copyBytes(in, out, 4096, true);
	}
	
	@Test
	public void testDownload() throws Exception {
		//从HDFS上下载到本地文件系统上
		fs.copyToLocalFile(new Path("/jdk1.7"),new Path("c://"));
	}
	
	@Test
	public void testMkdir() throws Exception {
		boolean flag = fs.mkdirs(new Path("/GY"));
		System.out.println(flag);
	}
	
	@Test
	public void testDelete() throws Exception {
		boolean flag = fs.delete(new Path("/GY"), false);
		System.out.println(flag);
	}
}



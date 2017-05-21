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
		//���ȴ���FileSystemʵ���ࣨ�����ࣩ
		fs = FileSystem.get(new URI("hdfs://192.168.75.128:9000"),new Configuration(),"root");
	}

	public static void main(String[] args) throws Exception {
		/**
		 * 1.�����ļ�ϵͳ�������ҵ�FileSystemʵ����DistributedFileSystem��ͨ������,ʵ�����ö���
		 * 2.DistributedFileSystem����DFSClient�����ã�DFSClient���д������ClientProtocol������
		 * 	   ClientProtocol�ӿڵ�ʵ�������ڷ�������ʵ�ֵ�(ͨ��hadoop��RPC���Ƶõ�����˵Ĵ������
		 */
	    FileSystem fs = FileSystem.get(new URI("hdfs://192.168.75.128:9000"),new Configuration());
	    /**
	     * ��open����ץȡ�����Ϣ���ͻ���ͨ��hadoop��RPC���Ƹ�Namenode�Ľ��̽���ͨ�ţ���Ҫ���ص��ļ�
	     * ��Ϊ�����Ĳ�������Namenode���������ĳ���࣬Namenode��ѯ��Ԫ������Ϣ��Ϊ����ֵ���ظ��ͻ���
	     * �ͻ��˰ѿ����Ϣ��Ϊ���ĳ�Ա����
	     */
	    InputStream in =fs.open(new Path("/test"));
		OutputStream out = new FileOutputStream("e://db");
		IOUtils.copyBytes(in, out, 4096, true);  
	}

	@Test
	public void testUpLoad() throws Exception {
		//��ȡ�����ļ�ϵͳ���ļ�,����������
		InputStream in = new FileInputStream("e://LICENSE.txt");
		//��HDFS�ϴ���һ���ļ������������
		OutputStream out =fs.create(new Path("/db"));
		//����-�����
		IOUtils.copyBytes(in, out, 4096, true);
	}
	
	@Test
	public void testDownload() throws Exception {
		//��HDFS�����ص������ļ�ϵͳ��
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



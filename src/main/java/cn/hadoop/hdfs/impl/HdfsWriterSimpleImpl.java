package cn.hadoop.hdfs.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hadoop.hdfs.HdfsWriter;

public class HdfsWriterSimpleImpl implements HdfsWriter {

	private static final Logger logger = LoggerFactory.getLogger(HdfsWriterSimpleImpl.class);
	
	private Writer writer;
	
	static final Configuration conf = new Configuration();
	
	private final Text key = new Text();
	
	private final Text value = new Text();
	
	private String path;
	
	private String fileName;
	
	/**
	 * 指定HDFS path，在其目录下将写入文件，以毫秒时间戳命名，如果文件写入失败，则自动更换新的文件
	 * @param path
	 */
	public HdfsWriterSimpleImpl(String path) {
		logger.info("HDFS Url: " + conf.get("fs.default.name"));
		this.path = path;
		createNewWriter();
	}
	
	private void createNewWriter() {
		try {
			this.fileName = this.path+"/"+System.currentTimeMillis();
			FileSystem fileSystem = FileSystem.get(URI.create(""), conf,"root");
			Path path = new Path(fileName);
			if (fileSystem.exists(path)) {
				throw new Exception("File " + fileName + " exists");
			}
			logger.info("Create HDFS writer, fileName: " + fileName);
			writer = SequenceFile.createWriter(conf, Writer.file(path), Writer.keyClass(Text.class), Writer.valueClass(Text.class));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void write(String key, String value) {
		try {
			this.key.set(key);
			this.value.set(value);
			writer.append(this.key, this.value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void close() {
		logger.info("HDFS writer close.");
		try {
			writer.close();
		} catch (IOException e) {
			logger.warn("close HDFS writer exception.", e);
		}
	}
	
	public static void main(String[] args) {
		HdfsWriterSimpleImpl hdfsWriterSimpleImpl = new HdfsWriterSimpleImpl("/writerToHDFS");
		for (int i = 0; i < 100; i++) {
			hdfsWriterSimpleImpl.write(new Random().nextInt(100)+"", new Random().nextInt(100)+"");
		}
		hdfsWriterSimpleImpl.close();
	}

}

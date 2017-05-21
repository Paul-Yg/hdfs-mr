package cn.hadoop.hdfs;

public interface HdfsWriter extends Cloneable{

	void write(String key,String value);
}

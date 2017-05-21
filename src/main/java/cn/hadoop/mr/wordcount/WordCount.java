package cn.hadoop.mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * 
 * @author admin
 * 1.���������ҵ���߼���ȷ��������������ݵ���ʽ
 * 2.�Զ���һ���࣬�����Ҫ�̳�org.apache.hadoop.mapreduce.Mapper����дmap()����
 *    ʵ�־���ҵ���߼������µ�<K,V>�����
 * 3.�Զ���һ���࣬�����Ҫ�̳�org.apache.hadoop.mapreduce.Reducer����дreduce()����
 *    ʵ�־���ҵ���߼������µ�<K,V>�����
 * 4.���Զ����Mapper��Reducerͨ��job������װ������
 *   
 */
public class WordCount {
     
	public static void main(String[] args) throws Exception {
		//���Լ������Mapper��Reducer֪ͨMapReduce����Mapper��Reducer��װ����
		//����Ҫ�ύMapReduce��ҵ�����һ��Job����
		Job job = Job.getInstance(new Configuration());
		
		/**
		 * ע�⣺mian�������ڵ���
		 */
		job.setJarByClass(WordCount.class);
		
		//�����Զ�����Ҫ�ύ��Mapper��class������
		job.setMapperClass(WCMapper.class);
		//����Mapper�����K����
		job.setMapOutputKeyClass(Text.class);
		//����Mapper�����V����
		job.setMapOutputValueClass(LongWritable.class);
		//����Mapper��ȡ��������ݣ��������ȡ���ݣ�·��
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.75.128:50070/wordinput"));     //�ļ���hadoop��HDFS���ļ�ϵͳ��
		
		job.setReducerClass(WCRudecer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputKeyClass(LongWritable.class);
		
		job.setCombinerClass(WCRudecer.class);
		
		//RudeceҪ��������ɵ�����д�ص�HDFS�ϣ�Ҫָ����һ��·��
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.75.128:50070/wordoutput"));
		
		//job��װ��ɣ����������ķ�ʽ����ҵ�ύ
		job.waitForCompletion(true);
	}

	
}

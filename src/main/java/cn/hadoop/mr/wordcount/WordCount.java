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
 * 1.分析具体的业务逻辑，确定输入输出的数据的样式
 * 2.自定义一个类，这个类要继承org.apache.hadoop.mapreduce.Mapper，重写map()方法
 *    实现具体业务逻辑，将新的<K,V>输出。
 * 3.自定义一个类，这个类要继承org.apache.hadoop.mapreduce.Reducer，重写reduce()方法
 *    实现具体业务逻辑，将新的<K,V>输出。
 * 4.将自定义的Mapper和Reducer通过job对象组装起来。
 *   
 */
public class WordCount {
     
	public static void main(String[] args) throws Exception {
		//将自己定义的Mapper和Reducer通知MapReduce，将Mapper和Reducer组装起来
		//将需要提交MapReduce作业抽象成一个Job对象
		Job job = Job.getInstance(new Configuration());
		
		/**
		 * 注意：mian方法所在的类
		 */
		job.setJarByClass(WordCount.class);
		
		//设置自定义需要提交的Mapper的class的类型
		job.setMapperClass(WCMapper.class);
		//设置Mapper输出的K类型
		job.setMapOutputKeyClass(Text.class);
		//设置Mapper输出的V类型
		job.setMapOutputValueClass(LongWritable.class);
		//设置Mapper读取输入的数据（在那里读取数据）路径
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.75.128:50070/wordinput"));     //文件在hadoop的HDFS的文件系统上
		
		job.setReducerClass(WCRudecer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputKeyClass(LongWritable.class);
		
		job.setCombinerClass(WCRudecer.class);
		
		//Rudece要将计算完成的数据写回到HDFS上，要指定以一个路径
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.75.128:50070/wordoutput"));
		
		//job组装完成，以面向对象的方式将作业提交
		job.waitForCompletion(true);
	}

	
}

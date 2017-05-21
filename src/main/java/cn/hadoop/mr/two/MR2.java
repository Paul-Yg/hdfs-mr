package cn.hadoop.mr.two;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/** 
 * 数据去重问题：
 * 原始数据：
 * file1：                        file2:            输出：
 * 2012-3-1 a  		2012-3-1 b		2012-3-1 a
 * 2012-3-2 b       2012-3-2 a		2012-3-1 b	
 * 2012-3-3 c 		2012-3-3 b		2012-3-2 a
 * 2012-3-4 d		2012-3-4 d		2012-3-2 b
 * 2012-3-5 a		2012-3-5 a		2012-3-3 b
 * 2012-3-6 b		2012-3-6 c		2012-3-3 c
 * 2012-3-7 c		2012-3-6 d		2012-3-4 d
 * 2012-3-4 d		2012-3-3 c		2012-3-5 a
 * 2012-3-3 c						2012-3-6 b
 * 									2012-3-6 c
 * 									2012-3-7 c
 *									2012-3-7 d
 *
 *说明：数据去重的最终目的是让原始数据中出现超过一次的数据在输出
 *文件中只出现一次，我们自然就想到将同一个数据的所有记录都交给一台reduce
 *机器，无论这个数据出现多少次，只要在最终结果输出一次就可以了
 *具体的就是reduce的输入应该以数据作为key，而对value-list没有要求
 *当reduce接收到一个<key,value-list>时就直接将key复制到输出的
 *key中，并将value设为空值。
 */
public class MR2 {
	
		public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(MR2.class);
			job.setMapperClass(DeMapper.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);
			FileInputFormat.setInputPaths(job, new Path(args[0]));
			job.setReducerClass(DeReucer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			job.setNumReduceTasks(1);
			Path outputDir = new Path(args[1]);
			FileSystem fs = FileSystem.get(conf);
			if(fs.exists(outputDir)) {
				fs.delete(outputDir, true);
				System.out.println("outputDir has existed,but it has deleted !");
			}
			FileOutputFormat.setOutputPath(job, outputDir);
			System.exit(job.waitForCompletion(true)?0:1);
		}
}

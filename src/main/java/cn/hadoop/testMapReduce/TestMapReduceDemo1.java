package cn.hadoop.testMapReduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 数据去重：最终目的是让原始数据中出现次数超过一次的数据，在输出文件中出现一次
 * @author Administrator
 *
 */
public class TestMapReduceDemo1 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf =  new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(TestMapReduceDemo1.class);
		job.setMapperClass(DemoMapper1.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		job.setReducerClass(DemoReducer1.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
		
	}

	public static class DemoMapper1 extends Mapper<LongWritable, Text, Text, Text> {

		private Text k = new Text();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			k = value;
			context.write(k, new Text(""));
		}
	}
	
	public static class DemoReducer1 extends Reducer<Text, Text, Text, Text> {
		
		@Override
		protected void reduce(Text key, Iterable<Text> values,Context context)
				throws IOException, InterruptedException {
			context.write(key,new Text(""));
		}
		
	}
		
}

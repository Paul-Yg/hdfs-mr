package cn.hadoop.mr.one;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * MapReduce入门级程序：查找当前年份最高的温度
 * @author Administrator
 *
 */
public class JobMain {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "max_temperature_job");
		job.setJarByClass(JobMain.class);
		job.setMapperClass(NewMaxTemperatureMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		job.setReducerClass(NewMaxTemperatureReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		Path outPath = new Path(args[1]);
		FileSystem fs= FileSystem.get(conf);
		if(fs.exists(outPath)) {
			fs.delete(outPath, true);
			System.out.println("outPath has existed,but it has deleted !");
		}
		job.setNumReduceTasks(1);
		FileOutputFormat.setOutputPath(job, outPath);
		System.exit(job.waitForCompletion(true)?0:1);
	}

}

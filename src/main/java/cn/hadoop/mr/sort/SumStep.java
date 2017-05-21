package cn.hadoop.mr.sort;

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

public class SumStep {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(SumStep.class);
		job.setMapperClass(SubMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(InfoBean.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(SubReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(InfoBean.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
		
	}

	private static class SubMapper extends Mapper<LongWritable, Text, Text, InfoBean> {
		
		private Text k = new Text();
		private InfoBean v = new InfoBean();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] arrayLine = line.split("\t");
			String user = arrayLine[0];
			double in = Double.parseDouble(arrayLine[1]);
			double out = Double.parseDouble(arrayLine[2]);
			k.set(user);
			v.set(user, in, out);
			context.write(k, v);
		}
	}
	
	public static class SubReducer extends Reducer<Text, InfoBean, Text, InfoBean> {

		private InfoBean v = new InfoBean();
		@Override
		protected void reduce(Text key, Iterable<InfoBean> values,Context context)
				throws IOException, InterruptedException {
			double in_sum = 0;
			double out_sum = 0;
			for (InfoBean bean : values) {
				in_sum += bean.getIncome();
				out_sum += bean.getExpense();
			} 
			v.set("", in_sum, out_sum);
			context.write(key, v);
		}
	}
}

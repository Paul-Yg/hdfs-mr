package cn.hadoop.mr.datacount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataCount {
	
	public static void main(String[] args) throws Exception {
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf);
			job.setJarByClass(DataCount.class);
			
			job.setMapperClass(DataCountMapper.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(DataBean.class);
			FileInputFormat.setInputPaths(job, new Path(args[0]));
			
			job.setReducerClass(DataCountReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(DataBean.class);
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			job.setPartitionerClass(ProviderPartitioner.class);
			job.setNumReduceTasks(Integer.parseInt(args[2]));
			
			job.waitForCompletion(true);
	}

	public static class DataCountMapper extends Mapper<LongWritable, Text, Text, DataBean> {

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			//接受数据
			String line = value.toString();
			//切分数据
			String[] fields = line.split("\t");
			String telNo = fields[1];
			long up = Long.parseLong(fields[8]);
			long down = Long.parseLong(fields[9]);
			context.write(new Text(telNo), new DataBean(telNo, up, down));
		}
	}
	
	public static class DataCountReducer extends Reducer<Text,DataBean, Text, DataBean> {

		@Override
		protected void reduce(Text k2, Iterable<DataBean> v2s,Context context)
				throws IOException, InterruptedException {
				long up_sum =0;
				long down_sum = 0;
				
				for (DataBean dataBean : v2s) {
					up_sum+=dataBean.getUpPayLoad();
					down_sum += dataBean.getDownPayLoad();
				}
				DataBean dataBean = new DataBean("", up_sum, down_sum);
				context.write(k2, dataBean);
			
		}
		
	}
	
	private static class ProviderPartitioner extends Partitioner<Text, DataBean>{
		
		private static Map<String,Integer> maps = new HashMap<String, Integer>();
		
		static {
			maps.put("136", 1);
			maps.put("137", 1);
			maps.put("138", 1);
			maps.put("139", 1);
			maps.put("182", 2);
			maps.put("159", 2);
			maps.put("150", 2);
			maps.put("134", 3);
			maps.put("183", 3);
			
		}

		@Override
		public int getPartition(Text key, DataBean value, int numPartitions) {
			String str = key.toString();
			String accountNo = str.substring(0, 3);
			Integer code = maps.get(accountNo);
			if(code==null) {
				code = 0;
			} 
			return code;
		}
	}
}

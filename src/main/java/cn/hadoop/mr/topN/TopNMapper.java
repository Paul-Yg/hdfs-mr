package cn.hadoop.mr.topN;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *找出付费金额（payment）最大的几笔订单
 * 
 *模拟：orderId userId payment productId
 *1,9819,100,121
 *2,8919,2000,111
 *3,2813,1234,22
 *4,324,7897,1001
 *....
 *100,2222,10,100
 *101,989,9918,102
 *...
 *
 *求Top N=5的结果
 */
public class TopNMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable>{
	
	int len;
	int[] top;
	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, IntWritable, IntWritable>.Context context)
					throws IOException, InterruptedException {
		String line = value.toString().trim();
		if(line.length()>0) {
			String[] arr = line.split(",");
			if(arr.length==4) {
				int pay_ment = Integer.parseInt(arr[2]);
				add(pay_ment);
			}
		}
	}
	
	private void add(int payment) {
		top[0] = payment;
		Arrays.sort(top);
	}

	@Override
	protected void cleanup(
			Mapper<LongWritable, Text, IntWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		for (int i = 1; i < len+1; i++) {
			context.write(new IntWritable(top[i]), new IntWritable(top[i]));
		}
	}

	//从配置文件中获取
	@Override
	protected void setup(
			Mapper<LongWritable, Text, IntWritable, IntWritable>.Context context)
			throws IOException, InterruptedException {
		len = context.getConfiguration().getInt("N", 10);
		top = new int[len+1];
	}
	
	
}

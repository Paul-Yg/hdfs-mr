package cn.hadoop.mr.wordcount;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCRudecer extends Reducer<Text, LongWritable, Text, LongWritable>{

	@Override
	protected void reduce(Text k2, Iterable<LongWritable> v2s,Context context)
			throws IOException, InterruptedException {
		//接受数据
		Text k3 = k2;
		//定义一个计数器
		long counter = 0;
		//循环遍历
		for (LongWritable v3 : v2s) {
			counter +=v3.get();
		}
		context.write(k3, new LongWritable(counter));
	}
}

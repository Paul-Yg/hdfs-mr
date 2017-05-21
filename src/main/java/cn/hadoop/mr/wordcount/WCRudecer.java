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
		//��������
		Text k3 = k2;
		//����һ��������
		long counter = 0;
		//ѭ������
		for (LongWritable v3 : v2s) {
			counter +=v3.get();
		}
		context.write(k3, new LongWritable(counter));
	}
}

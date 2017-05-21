package cn.hadoop.mr.two;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DeReucer extends Reducer<Text, Text, Text, NullWritable> {
	
	@Override
	protected void reduce(Text key, Iterable<Text> values,Context context) throws 
		IOException,InterruptedException {
		
		context.write(key, NullWritable.get());
	}
}

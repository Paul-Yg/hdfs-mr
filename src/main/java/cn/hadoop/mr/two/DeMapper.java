package cn.hadoop.mr.two;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DeMapper extends Mapper<LongWritable, Text, Text, Text> {

	private Text value = new Text("");
	
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		if(line.trim().length()>0) {
			context.write(new Text(line), value);
		}
	}
}

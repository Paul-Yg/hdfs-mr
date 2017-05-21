package cn.hadoop.mr.join;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinReducer extends Reducer<IntWritable, Emplyee, NullWritable, Text> {

	
	@Override
	protected void reduce(IntWritable key, Iterable<Emplyee> values,Context context)
			throws IOException, InterruptedException {
		Emplyee e = null;
		List<Emplyee> lists = new ArrayList<Emplyee>();
		
		for (Emplyee emplyee : values) {
			if(emplyee.getFlag()==0) {   //≤ø√≈±Ì
				e = emplyee;
			} else {
				lists.add(emplyee);
			}
		}
		
		for (Emplyee emplyee : lists) {
			emplyee.setDname(e.getDname());
			context.write(NullWritable.get(), new Text(emplyee.toString()));
		}
	}
	
}

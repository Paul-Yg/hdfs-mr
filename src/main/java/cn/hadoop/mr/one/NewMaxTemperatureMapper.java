package cn.hadoop.mr.one;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 0067011990999991950051507004888888880500001N9+00781+9999999999999999999999
 * 
 * @author Administrator
 * ����˵����
 *  ��15-19���ַ�year
 *  ��45-50��ʾ�¶ȣ�+��ʾ���ϣ�-��ʾ���£����¶�ֵ����Ϊ9999,9999��ʾ�쳣����
 *  ��50λֵֻ����0,1,4,5,9��������
 *  
 */
public class NewMaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	private static final int MISSINT = 9999;

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String year = line.substring(15, 19);
		String tmp = line.substring(45, 46);
		int val = 0;
		if("+".equals(tmp)) {
			val = Integer.parseInt(line.substring(46,50));
		} else {
			val = Integer.parseInt(line.substring(45,50));
		}
		if (val != MISSINT && line.substring(50, 51).matches("[01459]")) {
			context.write(new Text(year), new IntWritable(val));
		}
	}
	
	/*
	 * public static void main(String[] args) throws Exception{
		String line = "0067011990999991950051507004888888880500001N9-01001+9999999999999999999999";
		String year = line.substring(15, 19);
		String tmp = line.substring(45, 46);
		int val = 0;
		if("+".equals(tmp)) {
			val = Integer.parseInt(line.substring(46,50));
		} else {
			val = Integer.parseInt(line.substring(45,50));
		}
		if (val != MISSINT && line.substring(50, 51).matches("[01459]")) {
			System.out.println("year:"+year+"  val:"+val);
		}
	}
	*/
}

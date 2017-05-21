package cn.hadoop.mr.url;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 日志分析：分析非结构化文件
 * 要求:区分统计"GET"和"POST" URL访问量
 * 结果为：访问方式，URL，访问量
 * 27.19.74.143 - - [30/May/2013:17:38:20 +0800] "GET /static/image/common/faq.gif HTTP/1.1" 200 1127
 * 110.52.250.126 - - [30/May/2013:17:38:20 +0800] "POST /data/cache/style_1_widthauto.css?y7a HTTP/1.1" 200 1292
 *
 */
public class UrlMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private IntWritable val = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String line = value.toString().trim();
		String tmp = handlerLog(line);
		context.write(new Text(tmp), val);
	}
	
	private String handlerLog(String line) {
		String result = "";
		if(line.length()>18) {
			if(line.indexOf("GET")>0) {
				result = line.substring(line.indexOf("GET"), line.indexOf("HTTP")).trim();
			} else if(line.indexOf("POST")>0) {
				result = line.substring(line.indexOf("POST"), line.indexOf("HTTP")).trim();
			}
		}
		return result;
	}
}
 
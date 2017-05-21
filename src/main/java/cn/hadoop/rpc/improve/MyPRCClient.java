package cn.hadoop.rpc.improve;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.RPC;

import cn.hadoop.rpc.Bizable;

/**
 * 客户端需要getServer得到一个RPC服务端。
 * 指定协议的实例，在指定的地址和端口上启动服务。
 * @author admin
 */

public class MyPRCClient {
	private MyRPCProtocal protocal = null;
	
	public MyPRCClient() throws IOException {
		InetSocketAddress address = new InetSocketAddress("192.168.8.100" , 9527);
		protocal = RPC.getProxy(MyRPCProtocal.class, MyRPCProtocal.versionID, address, new Configuration());        //得到服务端的代理对象
	}
	public void call(String s) {
		final Text string = protocal.test(new Text(s));
		System.out.println(string.toString());
	}
	
	public static void main(String[] args) throws IOException {
		MyPRCClient client = new MyPRCClient();
		client.call("RPC");
	}
}


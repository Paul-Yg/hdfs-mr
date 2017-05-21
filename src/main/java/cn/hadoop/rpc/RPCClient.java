package cn.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
/**
 * Datanode: getProxy得到一个RPC客户端。getProxy方法会使用动态代理，
 * 	创建一个指定服务端的代理。一个远程对象需要建立一个代理
 * @author admin
 *
 */
public class RPCClient {

	public static void main(String[] args) throws Exception {
		Bizable proxy =	RPC.getProxy(Bizable.class, 1001, new InetSocketAddress("192.168.100.101", 9527), new Configuration());
		String result = proxy.sayHi("RPC");
		System.out.println(result);
		RPC.stopProxy(proxy);
	}

}

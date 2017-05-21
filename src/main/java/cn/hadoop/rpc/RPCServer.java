package cn.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

/**
 * NameNode端。调用方需要getServer得到一个RPC服务端。指定协议的实例，在指定的
 * 地址和端口上启动服务。
 * @author admin
 *
 */
public class RPCServer implements Bizable {
	
	public String sayHi(String name) {
		return "Hi~ "+name;
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Server server = new RPC.Builder(conf).setProtocol(Bizable.class).setInstance(new RPCServer()).setBindAddress("192.168.100.101").setPort(9527).build();
		server.start();
	}
}

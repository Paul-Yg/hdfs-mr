package cn.hadoop.rpc.improve;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

public class MyRPCServer implements MyRPCProtocal{

	/**
	 *  ipc.Server的主要功能是接收来自客户端的RPC请求，经过调用相应的函数获取结果后，返回给对应的客户端.
	 * 
	 */
	Server server = null;
	
	public MyRPCServer() throws Exception{
		RPC.Builder ins =new RPC.Builder(new Configuration());
		ins.setInstance(this);
		ins.setBindAddress("192.168.8.100");
		ins.setPort(9527);
		ins.setProtocol(MyRPCProtocal.class);
		server = ins.build();                     //创建一个Server实例
		server.start();
		server.join();
	}
	
	public long getProtocolVersion(String protocol, long clientVersion)
			throws IOException {
		return MyRPCProtocal.versionID;
	}

	public ProtocolSignature getProtocolSignature(String protocol,
			long clientVersion, int clientMethodsHash) throws IOException {
		return new ProtocolSignature();
	}

	public Text test(Text t) {
		if(t.toString().equals("RPC")) {
			return new Text("true");
		} 
		return new Text("false");
	}

	public static void main(String[] args) throws Exception {
		new MyRPCServer();
	}
}

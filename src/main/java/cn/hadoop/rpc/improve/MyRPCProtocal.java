package cn.hadoop.rpc.improve;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * 用户需要自己定义一个协议接口来封装所有客户端向服务端发送的请求，
 * 该接口必须继承自org.apache.hadoop.ipc.VersionedProtocol。
 * 接口中的versionID用来标识不同的协议。
 * @author admin
 *
 */
public interface MyRPCProtocal extends VersionedProtocol {

	public static final long versionID = 13L;
	
	public Text test(Text t);
}

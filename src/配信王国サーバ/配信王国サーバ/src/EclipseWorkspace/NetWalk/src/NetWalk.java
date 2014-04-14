
import Data.Data;
import Frame.Frame;
import Server.NetWalkServer;
import Server.PolicyServer;
import Thread.SyncThread;


public class NetWalk
{
	/******************************
	 * ���C��
	*******************************/
	public static void main(String[] args)
	{
		// GUI�N��
		new Frame();
		
		// �|���V�[�T�[�o�N��
		new PolicyServer(843);
		
		// NetWalkServer�N��
		new NetWalkServer(2236);
		
		// Data������
		new Data();
		
		// �����X���b�h
		//new SyncThread();
	}
}

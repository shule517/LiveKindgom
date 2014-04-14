package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Frame.Frame;

/******************************
 * NetWalk�҂��󂯃X���b�h
*******************************/
public class NetWalkServer extends Thread
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	private int portNo = 0;			// �|�[�g�ԍ�
	static boolean isClose = false;	// �I������
	
	/******************************
	 * �R���X�g���N�^
	*******************************/
	public NetWalkServer(int portNo)
	{		
		// �|�[�g�ԍ�����
		this.portNo = portNo;
		
		// �X���b�h���J�n
		start();
	}
	
	/******************************
	 * ���C�����[�v
	*******************************/
	public void run()
	{
		try
		{
			ServerSocket server = new ServerSocket(portNo);
			Frame.appendDebugText("�T�[�o(Port:" + portNo + ")���N�����܂���");
			
			// �ڑ��҂����[�v
			while (isClose == false)
			{
				Socket socket = server.accept();
				Frame.appendDebugText("�ڑ� �F" + socket.getInetAddress().getHostAddress());
				
				new NetWalkClient(socket);
			}
		}
		catch (IOException e)
		{
			Frame.appendDebugText(e.toString());
		}
	}
	
	/******************************
	 * �I������
	*******************************/
	static void close()
	{
		Frame.appendDebugText("NetWalkServer : �I������");
		isClose = true;
	}
}

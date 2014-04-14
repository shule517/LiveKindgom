package Server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import Frame.Frame;

public class PolicyServer extends Thread
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	private int portNo = 0;			// �|�[�g�ԍ�
	static boolean isClose = false;	// �I������

	/******************************
	 * �R���X�g���N�^
	*******************************/
	public PolicyServer(int portNo)
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
			//Frame.appendPolicyText("�T�[�o(Port:" + portNo + ")���N�����܂���");
			
			// �ڑ��҂����[�v
			while (isClose == false)
			{
				Socket socket = server.accept();
				//Frame.appendPolicyText("�ڑ� " + socket.getInetAddress().getHostAddress());
				
				new PolicyClient(socket);
			}
		}
		catch (IOException e)
		{
			Frame.appendPolicyText(e.toString());
		}
	}
	
	/******************************
	 * �I������
	*******************************/
	static public void close()
	{
		isClose = true;
	}
}

/******************************
 * �|���V�[�N���C�A���g�X���b�h
*******************************/
class PolicyClient extends Thread
{
	/******************************
	 * �����o�ϐ�
	*******************************/

	// �\�P�b�g
	private Socket socket = null;
	
	/******************************
	 * �R���X�g���N�^
	*******************************/
	PolicyClient(Socket socket)
	{
		this.socket = socket;
		
		start();
	}
	/******************************
	 * ���C�����[�v
	*******************************/
	public void run()
	{
		// �o�̓X�g���[�����쐬
		PrintWriter out = null;

		try
		{
			out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream(), "UTF8"), true);
		}
		catch (UnsupportedEncodingException e)
		{
			Frame.appendPolicyText(e.toString());
		}
		catch (IOException e)
		{
			Frame.appendPolicyText(e.toString());
		}
		
		// Policy�t�@�C���𑗐M
		out.print("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>");
		out.flush();

		// �I��
		out.close();
		
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			Frame.appendPolicyText(e.toString());
		}
	}
}

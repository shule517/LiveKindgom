package Lib;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import Data.Data;
import Data.UserData;
import Frame.Frame;
import Thread.SendThread;
import Lib.PacketHeader;

public class Network
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	private SendThread sendThread = null; // ���M�X���b�h
	private BufferedReader in = null;	// ���̓X�g���[��
	private PrintWriter out = null;		// �o�̓X�g���[��
	private Socket socket = null;		// �\�P�b�g
	public boolean isAlive = true;		// �I���t���O
	private String myId = "";			// id
	
	/******************************
	 * �R���X�g���N�^
	*******************************/
	public Network(Socket socket)
	{
		// �\�P�b�g
		this.socket = socket;
		
		// ������
		try
		{
			out = new PrintWriter( new OutputStreamWriter(socket.getOutputStream(), "UTF8"), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sendThread = new SendThread(out);
		}
		catch (IOException e)
		{
			Frame.appendDebugText("Network.Network " + e.getMessage());
		}
	}

	/******************************
	 * �p�P�b�g��M
	*******************************/
	public PacketAnalyzer receive()
	{
		// �p�P�b�g����
		PacketAnalyzer pa = null;
		
		try
		{
			String packet = "";
			int c = in.read();
			
			// �P�p�P�b�g�ǂݍ���
			while (c != '\n')
			{
				// �ؒf�`�F�b�N
				if (c == -1)
				{
					throw new IOException("�ؒf");
				}
				
				packet += (char)c;
				c = in.read();
			}
			
			Frame.appendDebugText("��M(" + packet + ") ID(" + myId + ") " + socket);

			// �p�P�b�g����
			pa = new PacketAnalyzer(packet);
		}
		catch (IOException e)
		{
			// �ؒf
			close();
		}
		catch (Exception e)
		{
			Frame.appendDebugText("Network.receive " + e.getMessage());
			
			// �ؒf
			close();
		}
		
		return pa;
	}
	
	/******************************
	 * �I������
	*******************************/
	private void close()
	{
		Frame.appendDebugText("�ؒf�FID(" + myId + ")" + socket);
		
		// �N���C�A���g�f�[�^������
		Data.user.remove(myId);
		
		// ����̐l�ɓ`����@�i�Ƃ肠���������ȊO�̑S���ɑ���j
		for(Map.Entry<String, UserData> user : Data.user.get().entrySet())
		{
			if (myId != user.getKey())
				user.getValue().network.sendRemoveCharactor(myId);
		}
		
		// ���
		try
		{
			in.close();
			out.close();
			socket.close();
			sendThread.close();
		}
		catch (IOException e)
		{
			Frame.appendDebugText("Network.close : " + e.getMessage());
		}
		
		// �I���t���O
		isAlive = false;
	}

	/******************************
	 * �p�P�b�g���M�F�L�����N�^�[�폜
	*******************************/
	private void sendRemoveCharactor(String id)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketRemoveCharactor);
		
		// ���b�Z�[�W
		pc.addStrData(id);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F���O�C������
	 * 1 :�@���O�C������
	 * 0 : ���O�C�����s
	*******************************/
	public void sendLoginResult(boolean result)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketLoginResult);
		
		if (result)
		{
			// ���O�C���F��
			pc.addIntData(1);
		}
		else
		{
			// ���O�C�����s
			pc.addIntData(0);
		}
		
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * ID���󂯎��
	*******************************/
	public void setID(String id)
	{
		this.myId = id;
	}

	/******************************
	 * �p�P�b�g���M�F�`���b�g
	*******************************/
	public void sendChat(String id, String chatMessage)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketChat);
	
		// ���b�Z�[�W
		pc.addStrData(id);
		pc.addStrData(chatMessage);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�L�����N�^�[�ǉ�
	*******************************/
	public void sendAddCharactor(String id, int x, int y)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketAddCharactor);
		
		// ���b�Z�[�W
		pc.addStrData(id);
		pc.addIntData(x);
		pc.addIntData(y);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�L�����N�^�[�ړ�
	*******************************/
	public void sendMoveCharactor(String id, int x, int y)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketMoveCharactor);
		
		// ���b�Z�[�W
		pc.addStrData(id);
		pc.addIntData(x);
		pc.addIntData(y);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�z�M�J�n
	*******************************/
	public void sendLiveStart(String id, String title, String detail)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketLiveStart);
		
		// ���b�Z�[�W
		pc.addStrData(id);
		pc.addStrData(title);
		pc.addStrData(detail);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�z�M�I��
	*******************************/
	public void sendListStop(String id)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketLiveStop);
		
		// ���b�Z�[�W
		pc.addStrData(id);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�z�M�p�`���b�g
	 * id : ������ID
	 * liveID : �z�M��ID
	 * livechatMessage : �`���b�g���e
	*******************************/
	public void sendLiveChat(String id, String liveID, String livechatMessage)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketLiveChat);
		
		// ���b�Z�[�W
		pc.addStrData(id);
		pc.addStrData(liveID);
		pc.addStrData(livechatMessage);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�����J�n
	 * id : ������ID
	 * liveID : �z�M��ID
	*******************************/
	public void sendListenStart(String id, String liveID)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketListenStart);
		
		// ���b�Z�[�W
		pc.addStrData(id);
		pc.addStrData(liveID);
	
		// ���M
		sendThread.addPacket(pc);
	}

	/******************************
	 * �p�P�b�g���M�F�����I��
	 * id : ������ID
	*******************************/
	public void sendListenStop(String id)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketListenStop);
		
		// ���b�Z�[�W
		pc.addStrData(id);
	
		// ���M
		sendThread.addPacket(pc);
	}
	
	/******************************
	 * �p�P�b�g���M�F��������
	 * id : ������ID
	*******************************/
	public void sendOxOx(String id)
	{
		PacketCreater pc = new PacketCreater(PacketHeader.PacketOxOx);
		
		// ���b�Z�[�W
		pc.addStrData(id);
	
		// ���M
		sendThread.addPacket(pc);
	}
}

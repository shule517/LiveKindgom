package Data;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Lib.Network;


public class UsetList
{
	/******************************
	 * �����o�ϐ�
	*******************************/
	private Map<String, UserData> user = Collections.synchronizedMap(new HashMap<String, UserData>());
	
	/******************************
	 * ���[�U�f�[�^���擾
	*******************************/
	public Map<String, UserData> get()
	{
		return user;
	}
	
	/******************************
	 * ���[�U�f�[�^���擾
	*******************************/
	public UserData get(String id)
	{
		return user.get(id);
	}
	
	/******************************
	 * ���[�U�f�[�^��ǉ�
	*******************************/
	public void add(String id, Network network)
	{
		UserData ud = new UserData();
		ud.network = network;

		user.put(id, ud);
	}
	
	/******************************
	 * ���[�U�f�[�^�����݂��邩
	*******************************/
	public boolean checkID(String id)
	{
		if (user.containsKey(id))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/******************************
	 * ���[�U�f�[�^������
	*******************************/
	public void remove(String id)
	{
		user.remove(id);
	}
	
	/******************************
	 * MAP����
	*******************************
	public void synck(String id)
	{
		UserData ud = Data.user.get(id);

		for (Map.Entry<String, UserData> e : user.entrySet()) 
		{
			ud.SynckList.add(e.getKey());
		}
	}
	*/
}

package com.sdwhNrcc.util;

public class Constant {

	public static final String SERVICE_IP_SDWH="sdwh.nrcc.com.cn";
	public static final int SERVICE_PORT_SDWH=8102;
	
	public static final String SERVICE_IP_LZQ="218.201.121.116";
	public static final int SERVICE_PORT_LZQ=18009;
	
	public static final int SDWH=1;
	public static final int LZQ=2;
	
	public static final int WEI_FANG=1;
	public static final int HE_ZE=2;
	public static final int ZI_BO=3;
	public static final int TAI_AN=4;
	
	public static final String SERVICE_IP_STR="serviceIp";
	public static final String SERVICE_PORT_STR="servicePort";
	public static final String CLIENT_ID_STR="clientId";
	public static final String CLIENT_SECRET_STR="clientSecret";
	
	/**
	 * RabbitMq����������Ϣ��ip
	 */
	public static final String CONN_FACTORY_HOST="127.0.0.1";
	/**
	 * RabbitMq����������Ϣ�Ķ˿ں�
	 */
	public static final int CONN_FACTORY_PORT=5672;
	/**
	 * RabbitMq����������Ϣ���û���
	 */
	public static final String CONN_FACTORY_USERNAME1="admin";
	public static final String CONN_FACTORY_USERNAME2="guest";
	/**
	 * RabbitMq����������Ϣ������
	 */
	public static final String CONN_FACTORY_PASSWORD1="admin";
	public static final String CONN_FACTORY_PASSWORD2="guest";
	
	/**
	 * 1.3�汾�Ľӿڱ�ʶ
	 */
	public static final int VERSION_1_3=1;
	/**
	 * 3.1�汾�Ľӿڱ�ʶ
	 */
	public static final int VERSION_3_1=2;
	
	
	/**
	 * Ϋ�����о�ϸ�������޹�˾
	 */
	public static final int WFRZJXHYXGS=1;
	/**
	 * ɽ�������²��ϿƼ����޹�˾
	 */
	public static final int SDFLXCLKJYXGS=2;
	/**
	 * Ϋ�����λ������޹�˾
	 */
	public static final int WFPXHGYXGS=3;
	/**
	 * �Ͳ���Ǭ�������޹�˾
	 */
	public static final int ZBXQHGYXGS=4;
	/**
	 * ɽ�������²������޹�˾
	 */
	public static final int SDBFXCLYXGS=5;
	/**
	 * ������������Ƽ����޹�˾
	 */
	public static final int CYSRHSWKJYXGS=6;
	/**
	 * ɽ�����������Ƽ����޹�˾
	 */
	public static final int SDLTXDKJYXGS=7;
	
	
	//Ϋ��������¼�˺�start
	/**
	 * Ϋ�������û���
	 */
	public static final String USERNAME_WEI_FANG="weifang_report_data";
	/**
	 * Ϋ����������(���ܺ��)
	 */
	public static final String PASSWORD_WEI_FANG="Rnh5ajIwMjAh";
	//Ϋ��������¼�˺�end

	//���������¼�˺�start
	/**
	 * ��������û���
	 */
	public static final String USERNAME_HE_ZE="heze_report_data";
	/**
	 * �����������(���ܺ��)
	 */
	public static final String PASSWORD_HE_ZE="Rnh5ajIwMjAh";
	//���������¼�˺�end

	//̩��������¼�˺�start
	/**
	 * ̩�������û���
	 */
	public static final String USERNAME_TAI_AN="taian_report_data";
	/**
	 * ̩����������(���ܺ��)
	 */
	public static final String PASSWORD_TAI_AN="Rnh5ajIwMjAh";
	//̩��������¼�˺�end
	
	//Ϋ�����λ������޹�˾start
	/**
	 * ���η�����ip
	 */
	public static final String SERVICE_IP_WFPXHGYXGS="222.173.86.130";
	/**
	 * ���η������˿�
	 */
	public static final int SERVICE_PORT_WFPXHGYXGS=90;
	/**
	 * ������Ŀ����
	 */
	public static final String SYSTEM_NAME_WFPXHGYXGS="��Ϣ�����輰��Ա��λϵͳ";
	/**
	 * ����9λ��ҵ����
	 */
	public static final String AREA_CODE_WFPXHGYXGS="370770029";
	/**
	 * ����ͳһ������ô���
	 */
	public static final String DATA_ID_WFPXHGYXGS="91370786MA3BX6981P";
	/**
	 * �����⻧id
	 */
	public static final String TENANT_ID_WFPXHGYXGS="sc22050664";
	/**
	 * �����û�id
	 */
	public static final String USER_ID_WFPXHGYXGS="pxhg";
	/**
	 * ��������
	 */
	public static final String PASSWORD_WFPXHGYXGS="pxhg";
	/**
	 * ������Կ
	 */
	public static final String CLIENT_SECRET_WFPXHGYXGS="F4A1D30F";
	/**
	 * �������ݿ���
	 */
	public static final String DATABASE_NAME_WFPXHGYXGS="sdwh_nrcc_wfpxhgyxgs";
	//Ϋ�����λ������޹�˾end
	
	//ɽ�������²��ϿƼ����޹�˾(����)start
	/**
	 * ���ַ�����ip
	 */
	public static final String SERVICE_IP_SDFLXCLKJYXGS="222.173.86.130";
	/**
	 * ���ַ������˿�
	 */
	public static final int SERVICE_PORT_SDFLXCLKJYXGS=90;
	/**
	 * ������Ŀ����
	 */
	public static final String SYSTEM_NAME_SDFLXCLKJYXGS="��Ϣ�����輰��Ա��λϵͳ";
	/**
	 * ����9λ��ҵ����
	 */
	public static final String AREA_CODE_SDFLXCLKJYXGS="371710271";
	/**
	 * ����ͳһ������ô���
	 */
	public static final String DATA_ID_SDFLXCLKJYXGS="91371700MA3P1UEYX9";
	/**
	 * �����⻧id
	 */
	public static final String TENANT_ID_SDFLXCLKJYXGS="sc22050640";
	/**
	 * �����û�id
	 */
	public static final String USER_ID_SDFLXCLKJYXGS="flcl";
	/**
	 * ��������
	 */
	public static final String PASSWORD_SDFLXCLKJYXGS="flcl";
	/**
	 * ������Կ
	 */
	public static final String CLIENT_SECRET_SDFLXCLKJYXGS="71B35547";
	/**
	 * �������ݿ���
	 */
	public static final String DATABASE_NAME_SDFLXCLKJYXGS="sdwh_nrcc_sdflxclkjyxgs";
	//ɽ�������²��ϿƼ����޹�˾(����)end
	
	//�Ͳ���Ǭ�������޹�˾start
	/**
	 * ��Ǭ������ip
	 */
	public static final String SERVICE_IP_ZBXQHGYXGS="192.168.1.10";//������Ŀ��������Ǭ���ط�������ʱ���õķ�����ip
	//public static final String SERVICE_IP_ZBXQHGYXGS="222.173.86.130";//������Ŀ��������������ʱ���õķ�����ip
	/**
	 * ��Ǭ�������˿�
	 */
	public static final int SERVICE_PORT_ZBXQHGYXGS=80;//������Ŀ��������Ǭ���ط�������ʱ���õĶ˿ں�
	//public static final int SERVICE_PORT_ZBXQHGYXGS=2001;//������Ŀ��������������ʱ���õĶ˿ں�
	/**
	 * ��Ǭ��ȫƽ̨�û���
	 */
	public static final String AQPT_USERNAME_ZBXQHGYXGS="�Ͳ���Ǭ�������޹�˾";
	/**
	 * ��Ǭ��ȫƽ̨����(md5���ܺ��)
	 */
	public static final String AQPT_PASSWORD_ZBXQHGYXGS="389b185ffcc27a00a4f3588b2ec1f3c4";
	/**
	 * ��Ǭ��Ŀ����
	 */
	public static final String SYSTEM_NAME_ZBXQHGYXGS="��Ա��λ����Ƶ����";
	/**
	 * ��Ǭ9λ��ҵ����
	 */
	public static final String AREA_CODE_ZBXQHGYXGS="370310421";
	/**
	 * ��Ǭͳһ������ô���
	 */
	public static final String DATA_ID_ZBXQHGYXGS="9137030569062440XF";
	/**
	 * ��Ǭ�⻧id
	 */
	public static final String TENANT_ID_ZBXQHGYXGS="sc22060717";
	/**
	 * ��Ǭ�û�id
	 */
	public static final String USER_ID_ZBXQHGYXGS="xqhg";
	/**
	 * ��Ǭ����
	 */
	public static final String PASSWORD_ZBXQHGYXGS="123456";
	/**
	 * ��Ǭ��Կ
	 */
	public static final String CLIENT_SECRET_ZBXQHGYXGS="56C981BD";
	/**
	 * ��Ǭ���ݿ���
	 */
	public static final String DATABASE_NAME_ZBXQHGYXGS="lzqaqpt_zbxqhgyxgs";
	//�Ͳ���Ǭ�������޹�˾end
	
	//ɽ�������²������޹�˾start
	/**
	 * ���������ip
	 */
	//public static final String SERVICE_IP_SDBFXCLYXGS="nl.yz-cloud.com";
	public static final String SERVICE_IP_SDBFXCLYXGS="112.6.184.89";
	/**
	 * ����������˿�
	 */
	//public static final int SERVICE_PORT_SDBFXCLYXGS=443;
	public static final int SERVICE_PORT_SDBFXCLYXGS=90;
	/**
	 * ������Ŀ����
	 */
	public static final String SYSTEM_NAME_SDBFXCLYXGS="��Ա��λ����Ƶ������������ҵ��˫��Ԥ������";
	/**
	 * ����9λ��ҵ����
	 */
	public static final String AREA_CODE_SDBFXCLYXGS="370980250";
	/**
	 * ����ͳһ������ô���
	 */
	public static final String DATA_ID_SDBFXCLYXGS="91370982MA3QL3EY61";
	/**
	 * �����⻧id
	 */
	public static final String TENANT_ID_SDBFXCLYXGS="sc22090841";
	/**
	 * �����û�id
	 */
	public static final String USER_ID_SDBFXCLYXGS="BFCL";
	/**
	 * ��������
	 */
	public static final String PASSWORD_SDBFXCLYXGS="BFCL";
	/**
	 * ������Կ
	 */
	//public static final String CLIENT_SECRET_SDBFXCLYXGS="6D993921";
	public static final String CLIENT_SECRET_SDBFXCLYXGS="131BB83D";
	/**
	 * �������ݿ���
	 */
	public static final String DATABASE_NAME_SDBFXCLYXGS="sdwh_nrcc_sdbfxclyxgs";
	//ɽ�������²������޹�˾end
	
	//Ϋ�����о�ϸ�������޹�˾start
	/**
	 * ���з�����ip
	 */
	public static final String SERVICE_IP_WFRZJXHYXGS="124.70.38.226";
	/**
	 * ���з������˿�
	 */
	public static final int SERVICE_PORT_WFRZJXHYXGS=8081;
	/**
	 * ������Ŀ����
	 */
	public static final String SYSTEM_NAME_WFRZJXHYXGS="��Ա��λ";
	/**
	 * ����9λ��ҵ����
	 */
	public static final String AREA_CODE_WFRZJXHYXGS="370710560";
	/**
	 * ����ͳһ������ô���
	 */
	public static final String DATA_ID_WFRZJXHYXGS="913707865728981511";
	/**
	 * �����⻧id
	 */
	public static final String TENANT_ID_WFRZJXHYXGS="sc21090414";
	/**
	 * �����û�id
	 */
	public static final String USER_ID_WFRZJXHYXGS="test";
	/**
	 * ��������
	 */
	public static final String PASSWORD_WFRZJXHYXGS="test";
	/**
	 * �������ݿ���
	 */
	public static final String DATABASE_NAME_WFRZJXHYXGS="sdwh_nrcc_wfrzjxhyxgs";
	//Ϋ�����о�ϸ�������޹�˾end
	
	//������������Ƽ����޹�˾start
	/**
	 * �𺣷�����ip
	 */
	public static final String SERVICE_IP_CYSRHSWKJYXGS="192.168.124.154";
	/**
	 * �𺣷������˿�
	 */
	public static final int SERVICE_PORT_CYSRHSWKJYXGS=80;
	/**
	 * ����Ŀ����
	 */
	public static final String SYSTEM_NAME_CYSRHSWKJYXGS="��Ա��λ";
	/**
	 * ��9λ��ҵ����
	 */
	public static final String AREA_CODE_CYSRHSWKJYXGS="";
	/**
	 * ��ͳһ������ô���
	 */
	public static final String DATA_ID_CYSRHSWKJYXGS="91370786050925490G";
	
	/**
	 * ���⻧id
	 */
	public static final String TENANT_ID_CYSRHSWKJYXGS="sc21100449";
	/**
	 * ���û�id
	 */
	public static final String USER_ID_CYSRHSWKJYXGS="rhsw";
	/**
	 * ������
	 */
	public static final String PASSWORD_CYSRHSWKJYXGS="rhsw";
	/**
	 * ����Կ
	 */
	public static final String CLIENT_SECRET_CYSRHSWKJYXGS="B962D6A9";
	/**
	 * �����ݿ���
	 */
	public static final String DATABASE_NAME_CYSRHSWKJYXGS="sdwh_nrcc_cysrhswkjyxgs";
	//������������Ƽ����޹�˾end
	
	//ɽ�����������Ƽ����޹�˾start
	/**
	 * ����������Ŀ����
	 */
	public static final String SYSTEM_NAME_SDLTXDKJYXGS="��Ա��λ";
	/**
	 * ��������9λ��ҵ����
	 */
	public static final String AREA_CODE_SDLTXDKJYXGS="";
	/**
	 * ��������ͳһ������ô���
	 */
	public static final String DATA_ID_SDLTXDKJYXGS="";
	//ɽ�����������Ƽ����޹�˾end
	
	
	
	
}

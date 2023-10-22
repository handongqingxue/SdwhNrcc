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
	 * RabbitMq接收推送消息的ip
	 */
	public static final String CONN_FACTORY_HOST="127.0.0.1";
	/**
	 * RabbitMq接收推送消息的端口号
	 */
	public static final int CONN_FACTORY_PORT=5672;
	/**
	 * RabbitMq接收推送消息的用户名
	 */
	public static final String CONN_FACTORY_USERNAME1="admin";
	public static final String CONN_FACTORY_USERNAME2="guest";
	/**
	 * RabbitMq接收推送消息的密码
	 */
	public static final String CONN_FACTORY_PASSWORD1="admin";
	public static final String CONN_FACTORY_PASSWORD2="guest";
	
	/**
	 * 1.3版本的接口标识
	 */
	public static final int VERSION_1_3=1;
	/**
	 * 3.1版本的接口标识
	 */
	public static final int VERSION_3_1=2;
	
	
	/**
	 * 潍坊润中精细化工有限公司
	 */
	public static final int WFRZJXHYXGS=1;
	/**
	 * 山东福林新材料科技有限公司
	 */
	public static final int SDFLXCLKJYXGS=2;
	/**
	 * 潍坊普鑫化工有限公司
	 */
	public static final int WFPXHGYXGS=3;
	/**
	 * 淄博鑫乾化工有限公司
	 */
	public static final int ZBXQHGYXGS=4;
	/**
	 * 山东宝沣新材料有限公司
	 */
	public static final int SDBFXCLYXGS=5;
	/**
	 * 昌邑市瑞海生物科技有限公司
	 */
	public static final int CYSRHSWKJYXGS=6;
	/**
	 * 山东蓝天消毒科技有限公司
	 */
	public static final int SDLTXDKJYXGS=7;
	
	
	//潍坊地区登录账号start
	/**
	 * 潍坊地区用户名
	 */
	public static final String USERNAME_WEI_FANG="weifang_report_data";
	/**
	 * 潍坊地区密码(加密后的)
	 */
	public static final String PASSWORD_WEI_FANG="Rnh5ajIwMjAh";
	//潍坊地区登录账号end

	//菏泽地区登录账号start
	/**
	 * 菏泽地区用户名
	 */
	public static final String USERNAME_HE_ZE="heze_report_data";
	/**
	 * 菏泽地区密码(加密后的)
	 */
	public static final String PASSWORD_HE_ZE="Rnh5ajIwMjAh";
	//菏泽地区登录账号end

	//泰安地区登录账号start
	/**
	 * 泰安地区用户名
	 */
	public static final String USERNAME_TAI_AN="taian_report_data";
	/**
	 * 泰安地区密码(加密后的)
	 */
	public static final String PASSWORD_TAI_AN="Rnh5ajIwMjAh";
	//泰安地区登录账号end
	
	//潍坊普鑫化工有限公司start
	/**
	 * 普鑫服务器ip
	 */
	public static final String SERVICE_IP_WFPXHGYXGS="222.173.86.130";
	/**
	 * 普鑫服务器端口
	 */
	public static final int SERVICE_PORT_WFPXHGYXGS=90;
	/**
	 * 普鑫项目名称
	 */
	public static final String SYSTEM_NAME_WFPXHGYXGS="信息化建设及人员定位系统";
	/**
	 * 普鑫9位企业编码
	 */
	public static final String AREA_CODE_WFPXHGYXGS="370770029";
	/**
	 * 普鑫统一社会信用代码
	 */
	public static final String DATA_ID_WFPXHGYXGS="91370786MA3BX6981P";
	/**
	 * 普鑫租户id
	 */
	public static final String TENANT_ID_WFPXHGYXGS="sc22050664";
	/**
	 * 普鑫用户id
	 */
	public static final String USER_ID_WFPXHGYXGS="pxhg";
	/**
	 * 普鑫密码
	 */
	public static final String PASSWORD_WFPXHGYXGS="pxhg";
	/**
	 * 普鑫密钥
	 */
	public static final String CLIENT_SECRET_WFPXHGYXGS="F4A1D30F";
	/**
	 * 普鑫数据库名
	 */
	public static final String DATABASE_NAME_WFPXHGYXGS="sdwh_nrcc_wfpxhgyxgs";
	//潍坊普鑫化工有限公司end
	
	//山东福林新材料科技有限公司(菏泽)start
	/**
	 * 福林服务器ip
	 */
	public static final String SERVICE_IP_SDFLXCLKJYXGS="222.173.86.130";
	/**
	 * 福林服务器端口
	 */
	public static final int SERVICE_PORT_SDFLXCLKJYXGS=90;
	/**
	 * 福林项目名称
	 */
	public static final String SYSTEM_NAME_SDFLXCLKJYXGS="信息化建设及人员定位系统";
	/**
	 * 福林9位企业编码
	 */
	public static final String AREA_CODE_SDFLXCLKJYXGS="371710271";
	/**
	 * 福林统一社会信用代码
	 */
	public static final String DATA_ID_SDFLXCLKJYXGS="91371700MA3P1UEYX9";
	/**
	 * 福林租户id
	 */
	public static final String TENANT_ID_SDFLXCLKJYXGS="sc22050640";
	/**
	 * 福林用户id
	 */
	public static final String USER_ID_SDFLXCLKJYXGS="flcl";
	/**
	 * 福林密码
	 */
	public static final String PASSWORD_SDFLXCLKJYXGS="flcl";
	/**
	 * 福林密钥
	 */
	public static final String CLIENT_SECRET_SDFLXCLKJYXGS="71B35547";
	/**
	 * 福林数据库名
	 */
	public static final String DATABASE_NAME_SDFLXCLKJYXGS="sdwh_nrcc_sdflxclkjyxgs";
	//山东福林新材料科技有限公司(菏泽)end
	
	//淄博鑫乾化工有限公司start
	/**
	 * 鑫乾服务器ip
	 */
	public static final String SERVICE_IP_ZBXQHGYXGS="192.168.1.10";//这是项目部署在鑫乾本地服务器上时设置的服务器ip
	//public static final String SERVICE_IP_ZBXQHGYXGS="222.173.86.130";//这是项目部署在外网测试时设置的服务器ip
	/**
	 * 鑫乾服务器端口
	 */
	public static final int SERVICE_PORT_ZBXQHGYXGS=80;//这是项目部署在鑫乾本地服务器上时设置的端口号
	//public static final int SERVICE_PORT_ZBXQHGYXGS=2001;//这是项目部署在外网测试时设置的端口号
	/**
	 * 鑫乾安全平台用户名
	 */
	public static final String AQPT_USERNAME_ZBXQHGYXGS="淄博鑫乾化工有限公司";
	/**
	 * 鑫乾安全平台密码(md5加密后的)
	 */
	public static final String AQPT_PASSWORD_ZBXQHGYXGS="389b185ffcc27a00a4f3588b2ec1f3c4";
	/**
	 * 鑫乾项目名称
	 */
	public static final String SYSTEM_NAME_ZBXQHGYXGS="人员定位、视频分析";
	/**
	 * 鑫乾9位企业编码
	 */
	public static final String AREA_CODE_ZBXQHGYXGS="370310421";
	/**
	 * 鑫乾统一社会信用代码
	 */
	public static final String DATA_ID_ZBXQHGYXGS="9137030569062440XF";
	/**
	 * 鑫乾租户id
	 */
	public static final String TENANT_ID_ZBXQHGYXGS="sc22060717";
	/**
	 * 鑫乾用户id
	 */
	public static final String USER_ID_ZBXQHGYXGS="xqhg";
	/**
	 * 鑫乾密码
	 */
	public static final String PASSWORD_ZBXQHGYXGS="123456";
	/**
	 * 鑫乾密钥
	 */
	public static final String CLIENT_SECRET_ZBXQHGYXGS="56C981BD";
	/**
	 * 鑫乾数据库名
	 */
	public static final String DATABASE_NAME_ZBXQHGYXGS="lzqaqpt_zbxqhgyxgs";
	//淄博鑫乾化工有限公司end
	
	//山东宝沣新材料有限公司start
	/**
	 * 宝沣服务器ip
	 */
	//public static final String SERVICE_IP_SDBFXCLYXGS="nl.yz-cloud.com";
	public static final String SERVICE_IP_SDBFXCLYXGS="112.6.184.89";
	/**
	 * 宝沣服务器端口
	 */
	//public static final int SERVICE_PORT_SDBFXCLYXGS=443;
	public static final int SERVICE_PORT_SDBFXCLYXGS=90;
	/**
	 * 宝沣项目名称
	 */
	public static final String SYSTEM_NAME_SDBFXCLYXGS="人员定位、视频分析、特殊作业、双重预防机制";
	/**
	 * 宝沣9位企业编码
	 */
	public static final String AREA_CODE_SDBFXCLYXGS="370980250";
	/**
	 * 宝沣统一社会信用代码
	 */
	public static final String DATA_ID_SDBFXCLYXGS="91370982MA3QL3EY61";
	/**
	 * 宝沣租户id
	 */
	public static final String TENANT_ID_SDBFXCLYXGS="sc22090841";
	/**
	 * 宝沣用户id
	 */
	public static final String USER_ID_SDBFXCLYXGS="BFCL";
	/**
	 * 宝沣密码
	 */
	public static final String PASSWORD_SDBFXCLYXGS="BFCL";
	/**
	 * 宝沣密钥
	 */
	//public static final String CLIENT_SECRET_SDBFXCLYXGS="6D993921";
	public static final String CLIENT_SECRET_SDBFXCLYXGS="131BB83D";
	/**
	 * 宝沣数据库名
	 */
	public static final String DATABASE_NAME_SDBFXCLYXGS="sdwh_nrcc_sdbfxclyxgs";
	//山东宝沣新材料有限公司end
	
	//潍坊润中精细化工有限公司start
	/**
	 * 润中服务器ip
	 */
	public static final String SERVICE_IP_WFRZJXHYXGS="124.70.38.226";
	/**
	 * 润中服务器端口
	 */
	public static final int SERVICE_PORT_WFRZJXHYXGS=8081;
	/**
	 * 润中项目名称
	 */
	public static final String SYSTEM_NAME_WFRZJXHYXGS="人员定位";
	/**
	 * 润中9位企业编码
	 */
	public static final String AREA_CODE_WFRZJXHYXGS="370710560";
	/**
	 * 润中统一社会信用代码
	 */
	public static final String DATA_ID_WFRZJXHYXGS="913707865728981511";
	/**
	 * 润中租户id
	 */
	public static final String TENANT_ID_WFRZJXHYXGS="sc21090414";
	/**
	 * 润中用户id
	 */
	public static final String USER_ID_WFRZJXHYXGS="test";
	/**
	 * 润中密码
	 */
	public static final String PASSWORD_WFRZJXHYXGS="test";
	/**
	 * 润中数据库名
	 */
	public static final String DATABASE_NAME_WFRZJXHYXGS="sdwh_nrcc_wfrzjxhyxgs";
	//潍坊润中精细化工有限公司end
	
	//昌邑市瑞海生物科技有限公司start
	/**
	 * 瑞海服务器ip
	 */
	public static final String SERVICE_IP_CYSRHSWKJYXGS="192.168.124.154";
	/**
	 * 瑞海服务器端口
	 */
	public static final int SERVICE_PORT_CYSRHSWKJYXGS=80;
	/**
	 * 瑞海项目名称
	 */
	public static final String SYSTEM_NAME_CYSRHSWKJYXGS="人员定位";
	/**
	 * 瑞海9位企业编码
	 */
	public static final String AREA_CODE_CYSRHSWKJYXGS="";
	/**
	 * 瑞海统一社会信用代码
	 */
	public static final String DATA_ID_CYSRHSWKJYXGS="91370786050925490G";
	
	/**
	 * 瑞海租户id
	 */
	public static final String TENANT_ID_CYSRHSWKJYXGS="sc21100449";
	/**
	 * 瑞海用户id
	 */
	public static final String USER_ID_CYSRHSWKJYXGS="rhsw";
	/**
	 * 瑞海密码
	 */
	public static final String PASSWORD_CYSRHSWKJYXGS="rhsw";
	/**
	 * 瑞海密钥
	 */
	public static final String CLIENT_SECRET_CYSRHSWKJYXGS="B962D6A9";
	/**
	 * 瑞海数据库名
	 */
	public static final String DATABASE_NAME_CYSRHSWKJYXGS="sdwh_nrcc_cysrhswkjyxgs";
	//昌邑市瑞海生物科技有限公司end
	
	//山东蓝天消毒科技有限公司start
	/**
	 * 蓝天消毒项目名称
	 */
	public static final String SYSTEM_NAME_SDLTXDKJYXGS="人员定位";
	/**
	 * 蓝天消毒9位企业编码
	 */
	public static final String AREA_CODE_SDLTXDKJYXGS="";
	/**
	 * 蓝天消毒统一社会信用代码
	 */
	public static final String DATA_ID_SDLTXDKJYXGS="";
	//山东蓝天消毒科技有限公司end
	
	
	
	
}

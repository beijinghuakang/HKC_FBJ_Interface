package com.hkc.project.standard;

import com.hkc.util.OutMsg;
import com.hkc.util.ReadLocalConfig;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author csc
 * @create 2019-02-14 11:27
 */
public class ReadDrugInfo implements  Runnable {
    private Connection localConn;
    private boolean nativeState = false;

    public boolean createLocalConn() {
        ReadLocalConfig local = new ReadLocalConfig();
        String localIp = local.localXml.get("localIp");
        String localName = local.localXml.get("localName");
        String localUserName = local.localXml.get("localUserName");
        String localPwd = local.localXml.get("localPwd");
        String localDriver = local.localXml.get("localDriver");
      //  localOverdue = local.localXml.get("localOverdue");
        try {
            Class.forName(localDriver);
            localConn = DriverManager.getConnection("jdbc:mysql://" + localIp
                            + ":3306/" + localName
                            + "?useUnicode=true&amp;characterEncoding=UTF-8",//
                    localUserName,//
                    localPwd);//
            nativeState = true;
            OutMsg.println("清理数据信息的local数据库连接建立成功:" + localConn.toString());
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            nativeState = false;
            OutMsg.println("清理数据信息的local数据库连接失败");
            OutMsg.println(e);
        }
        return false;
    }
    @Override
    public void run() {

    }
}

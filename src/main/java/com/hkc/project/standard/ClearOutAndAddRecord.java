package com.hkc.project.standard;

import com.hkc.util.OutMsg;
import com.hkc.util.ReadLocalConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author csc
 * @create 2019-02-13 8:44
 */
public class ClearOutAndAddRecord implements Runnable{

    private Connection localConn;
    private boolean nativeState = false;
    private  String  localOverdue;//过期数据保存天数

    /**
     * 本地链接
     * @return
     */
    public boolean createLocalConn() {
        ReadLocalConfig local = new ReadLocalConfig();
        String localIp = local.localXml.get("localIp");
        String localName = local.localXml.get("localName");
        String localUserName = local.localXml.get("localUserName");
        String localPwd = local.localXml.get("localPwd");
        String localDriver = local.localXml.get("localDriver");
         localOverdue = local.localXml.get("localOverdue");
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
        while (true){
            //
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // 判断本地连接
            while (!nativeState && !createLocalConn()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                }
            }
            //
            OutMsg.println("**************开始清理过期数据信息***************");
            try {
                PreparedStatement localPs1 = localConn.prepareStatement("DELETE from  machine_in_detl  where TO_DAYS(NOW())-TO_DAYS(In_time)<"+Integer.parseInt(localOverdue));
                localPs1.executeUpdate();
                OutMsg.println("1.清理加药记录过期"+localOverdue+"天的数据完成");
                PreparedStatement localPs2 = localConn.prepareStatement("DELETE from  machine_out_detl  where TO_DAYS(NOW())-TO_DAYS(In_time)<"+Integer.parseInt(localOverdue));
                localPs2.executeUpdate();
                OutMsg.println("2.清理出药记录过期"+localOverdue+"天的数据完成");


            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                OutMsg.println("清理数据线程进入休眠");
                Thread.sleep(1000*60*60*24);//一天循环一次

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

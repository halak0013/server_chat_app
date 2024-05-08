
//Bismillahirrahmanirrahim
package com.bismih.server_chat_app;

import com.bismih.server_chat_app.utils.Db_helper;
import com.bismih.server_chat_app.utils.Db_proccess;

/**
 *
 * @author bismih
 */
public class Server_chat_app {

    public static void main(String[] args) {
        Db_helper db_helper = new Db_helper();
        // Db_proccess.add_user("Ahmet", "ahmet", "1234");
        // Db_proccess.add_project("p5", 2, "p5_link");
        int r = 4;
        int s = 3;
        //Db_proccess.addMsg("Nasılsın", "text", r, s, 1);
        //Db_proccess.addMsg("Elhamdülillah, sen nasılsın", "text", s, r, 1);
        System.out.println(Db_proccess.getMsgs(1, r, s));
        db_helper.closeConnection();
    }
}

package com.qihoo.qmev3.deferred.out;

public class QETaskController {

    public static QETaskController getInstance(){
        return new QETaskController();
    }

    public long threadId(){
        return 44;
    }

    public long runOnly(Runnable runnable){
        return 44;
    }



}

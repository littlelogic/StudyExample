package leavesc.hello.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:45
 * 描述：
 */
public class AIDLServiced extends Service {

    private static final String TAG = "AIDLService";

    private RemoteCallbackList<IOnOperationCompletedListener> callbackList;
    private IOperationManager.Stub stub = new IOperationManager.Stub() {
        public void operation(Parameter parameter1,Parameter parameter2) throws RemoteException{
            Parameter result = new Parameter(parameter1.getParam() * parameter2.getParam());
            //在操作 RemoteCallbackList 前，必须先调用其 beginBroadcast 方法
            //此外，beginBroadcast 必须和 finishBroadcast配套使用
            int count = callbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                IOnOperationCompletedListener listener = callbackList.getBroadcastItem(i);
                if (listener != null) listener.onOperationCompleted(result);
            }
            callbackList.finishBroadcast();
        }
        public void registerListener(IOnOperationCompletedListener bb) throws RemoteException {
            callbackList.register(bb);
        }
        public void unregisterListener(IOnOperationCompletedListener aa) throws RemoteException{
            callbackList.unregister(aa);
        }
    };
    public AIDLServiced() {
        callbackList = new RemoteCallbackList<>();
    }
    @Override public IBinder onBind(Intent intent) {
        return stub;
    }

}

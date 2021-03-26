package com.example.wujiawen.ExampleDragFillBlankView;//com.example.wujiawen.ExampleDragFillBlankView.ContentProviderActi

import android.os.Bundle;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 拖拽填空题
 * Created by yangle on 2017/10/9.
 * <p>
 * Website：http://www.yangle.tech
 * <p>
 * GitHub：https://github.com/alidili
 * <p>
 * CSDN：http://blog.csdn.net/kong_gu_you_lan
 * <p>
 * JianShu：http://www.jianshu.com/u/34ece31cd6eb
 */

public class MainActivity extends BaseActivity {

    DragFillBlankView dfbvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drag_fill_blank);
        dfbvContent=(DragFillBlankView)this.findViewById(R.id.dfbv_content);
        initData();
    }

    private void initData() {
        String content = "纷纷扬扬的________下了半尺多厚。天地间________的一片。我顺着顺着着________工地走了四十多公里，" +
                "只听见各种机器的吼声，可是看不见人影，也看不见工点。一进灵官峡，我就心里发慌。";

        // 选项集合
        List<String> optionList = new ArrayList<>();
        optionList.add("白茫茫");
        optionList.add("雾蒙蒙");
        optionList.add("铁路");
        optionList.add("公路");
        optionList.add("大雪");

        // 答案范围集合
        List<AnswerRange> rangeList = new ArrayList<>();
        rangeList.add(new AnswerRange(5, 13));
        rangeList.add(new AnswerRange(23, 31));
        rangeList.add(new AnswerRange(38+3, 46+3));

        dfbvContent.setData(content, optionList, rangeList);
    }
}

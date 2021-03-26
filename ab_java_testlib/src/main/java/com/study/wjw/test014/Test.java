package com.study.wjw.test014;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//import sun.swing.SwingUtilities2;


/**
 *


 */
public class Test implements Cloneable{


   /* public Test(){

    }*/

    public Test(int value){
        System.out.println("Test-construct-int->");
    }

    public Test(Integer value){
        System.out.println("Test-construct-Integer->");
    }

    void add(int value){

    }

    void add(int ... value){

    }

    /*void add(int[] value){

    }*/



    int here = 0;

    public static void main(String[] args) {
        Test aTest_b = new Test(new Integer(4));
        Test aTest_a = new Test(2);
        aTest_a.test_01();
    }

    /*



     */
    public void test_01(){
        Test mTest = new Test(2);
        System.out.println("Test-1->"+mTest);
        try {
            mTest = null;
            mTest = Test.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Test-2a->"+mTest);
        try {
            mTest = null;
            mTest = (Test)Class.forName("com.study.wjw.test014.Test").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Test-2b->"+mTest);

        try {
            mTest = null;
            ///Constructor<Test> constructor = Test.class.getConstructor(int.class);
            Constructor<Test> constructor = Test.class.getConstructor(Integer.class);
            mTest = constructor.newInstance(4);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("Test-3->"+mTest);

        mTest = (Test) mTest.clone();
        System.out.println("Test-4->"+mTest);


    }



    @Override
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }







    //=====================================================================


    public void test_02(){

    }


    public static void maindd(String[] args) throws IOException {
        FileInputStream fs = new FileInputStream("E:\\ipr\\司机手册.doc");
        HWPFDocument doc = new HWPFDocument(fs);
        Range r = doc.getRange();
        PicturesTable pTable = doc.getPicturesTable();
        String docText = "";
        for (int x = 0; x < r.numSections(); x++) {
            org.apache.poi.hwpf.usermodel.Section s = r.getSection(x);
            for (int y = 0; y < s.numParagraphs(); y++) {
                Paragraph p = s.getParagraph(y);
                for (int z = 0; z < p.numCharacterRuns(); z++) {
                    CharacterRun run = p.getCharacterRun(z);
                    if (pTable.hasPicture(run)) {
                        System.out.print("has pic\n");
                        docText+="*******************************************\r\n";
                    } else {
                        System.out.print("no pic\n");
                    }

                }
                docText += p.text();
            }
        }
        System.out.println(docText);
    }


}
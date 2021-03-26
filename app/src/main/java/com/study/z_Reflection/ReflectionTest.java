package com.study.z_Reflection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.z_Reflection.annotation.AnnoAll;
import com.study.z_Reflection.annotation.OutPackageClassHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 *


 System.out.println(TAG+"-testClass-"
 +  "-className->"+className
 +  "-className->"+person.getClass().getName()
 );

 */
public class ReflectionTest {

    public static String TAG = ReflectionTest.class.getSimpleName();//类名称
    public static String PAGName = ReflectionTest.class.getName();//类全路径名称
    public static String Package = ReflectionTest.class.getPackage().getName();//类所在的包名

    {
        System.out.println(TAG+"-init-"
                +  "-TAG->"+TAG
                +  "-PAGName->"+ PAGName
                +  "-Package->"+Package
        );

    }

    public static void main(String[] args) {
        if (args != null && args.length > 0 && args[0].equals("1314")) {
            System.out.println(TAG+"-反射运行main-");
            return;
        }

        ReflectionTest mReflectionTest = new ReflectionTest();
        try {
            mReflectionTest.test_FinalField();
            mReflectionTest.testClass();
            mReflectionTest.testClassLoader();
            mReflectionTest.testMethod();
            mReflectionTest.testField();
            mReflectionTest.testConstructor();
            mReflectionTest.reflectStaticMain();
            mReflectionTest.reflectionOutClass();

            mReflectionTest.testGenerics();
            mReflectionTest.testGetSuperClassGenericType();
            mReflectionTest.testGenericType();

            mReflectionTest.testAnnotation();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *

     获取Class对象的三种方式
     　　1.通过类名获取      类名.class
     　　2.通过对象获取      对象名.getClass()
     　　3.通过全类名获取    Class.forName(全类名)

     */
    public void testClass() throws Exception {
        Class clazz = null;

        //1.通过类名
        clazz = Person.class;

        //2.通过对象名
        //这种方式是用在传进来一个对象，却不知道对象类型的时候使用
        Person person = new Person();
        clazz = person.getClass();
        //上面这个例子的意义不大，因为已经知道person类型是Person类，再这样写就没有必要了
        //如果传进来是一个Object类，这种做法就是应该的
        Object obj = new Person();
        clazz = obj.getClass();


        //3.通过全类名(会抛出异常)
        //一般框架开发中这种用的比较多，因为配置文件中一般配的都是全类名，通过这种方式可以得到Class实例
        String className = clazz.getName();//全路径（有类名称）

        System.out.println(TAG+"-testClass-"
                +  "-className->"+className
                +  "-className->"+person.getClass().getName()
        );
        clazz = Class.forName(className);

        //字符串的例子
        clazz = String.class;
        clazz = "javaTest".getClass();
        clazz = Class.forName("java.lang.String");

        System.out.println();

        //利用Class对象的newInstance方法创建一个类的实例
        obj =  clazz.newInstance();
        System.out.println(obj);
        ///------------
        int[] int_array = new int[]{};
        int[][] int_array_b = new int[][]{};
        int[][] int_array_c = new int[][]{};
        List<Integer> list = new ArrayList<>();
        /**
         * int_array_b 和 int_array_c的Class类型是一个
         */
        System.out.println(TAG+"-testClass-"
                +  "-int_array.getClass().getName()->"+int_array.getClass().getName() + "\n"
                +  "-int_array_b.getClass().getName()->"+int_array_b.getClass().getName()
                +  "-int_array_b.getClass().hashCode()->"+int_array_b.getClass().hashCode()+ "\n"
                +  "-int_array_c.getClass().getName()->"+int_array_c.getClass().getName()
                +  "-int_array_c.getClass().hashCode()->"+int_array_c.getClass().hashCode()+ "\n"
                +  "-list.getClass().getName()->"+list.getClass().getName()
        );

    }

    /*
     类装载器是用来把类(class)装载进 JVM 的。JVM 规范定义了两种类型的类装载器：
     启动类装载器(bootstrap)和用户自定义装载器(user-defined class loader)。
     JVM在运行时会产生3个类加载器组成的初始化加载器层次结构 ，如下图所示

     使用类加载器获取当前类目录下的文件
　　  首先，系统类加载器可以加载当前项目src目录下面的所有类，如果文件也放在src下面，也可以用类加载器来加载
　　  调用 getResourceAsStream 获取类路径下的文件对应的输入流.

      Class类的常用方法
      static Class forName(String name) 返回指定类名 name 的 Class 对象
      Object newInstance() 调用缺省构造函数，返回该Class对象的一个实例
      Object newInstance(Object []args) 调用当前格式构造函数，返回该Class对象的一个实例
      getName()  返回此Class对象所表示的实体（类、接口、数组类、基本类型或void）名称
      Class getSuperClass() 返回当前Class对象的父类的Class对象
      Class [] getInterfaces() 获取当前Class对象的接口
      ClassLoader getClassLoader() 返回该类的类加载器
      Class getSuperclass() 返回表示此Class所表示的实体的超类的Class

     */
    public void testClassLoader() throws ClassNotFoundException, FileNotFoundException {
        //1. 获取一个系统的类加载器(可以获取，当前这个类PeflectTest就是它加载的)
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        //2. 获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
        classLoader = classLoader.getParent();
        System.out.println(classLoader);

        //3. 获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
        classLoader = classLoader.getParent();
        System.out.println(classLoader);


        //4. 测试当前类由哪个类加载器进行加载（系统类加载器）:
        classLoader = Class.forName(PAGName).getClassLoader();
        System.out.println(classLoader);

        //5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类）
        classLoader = Class.forName("java.lang.Object").getClassLoader();
        System.out.println(classLoader);



        ///---------------------------------------
        //src目录下，直接加载 ????????????????
        //放在内部文件夹，要写全路径
        InputStream in2 = null;
        String path_a = Package.replace(".","/") + "/test2.txt";
        in2 = this.getClass().getClassLoader().getResourceAsStream(path_a);

        System.out.println(TAG+"-testClass-"
                +  "-in2->"+in2
                +  "-path_a->"+path_a
                +  "-in2-str->"+ioToSting(in2)
        );


    }

    private String ioToSting(InputStream in2){
        try {
            if ( in2 != null ) {
                // size 为字串的长度 ，这里一次性读完
                int size = in2.available();
                byte[] buffer = new byte[size];
                in2.read(buffer);
                in2.close();
                String str = new String(buffer, "UTF-8");
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //=========================================

    public void testMethod() throws Exception{
        Student mStudent = new Student("");
        //todo 1 获取Class字节码
        Class clazz_student = mStudent.getClass();

        //todo 2 获取指定的方法,需要参数名称和参数列表，无参则不需要写
        Method method = clazz_student.getDeclaredMethod("setScore", int.class);
        System.out.println(method);
        /** todo
         * 禁止安全检查，可以提高效率
         * 运行效率，正常调用效率1，正常反射调用30，禁止安全检查反射调用3.5
         * 但是很多反射会提高开发的效率
         */
        method.setAccessible(true);
        //todo 3 invoke执行
        Object return_back = method.invoke(mStudent,2);
        if (return_back != null) {
            try {
                boolean b_back = (boolean)return_back;
                System.out.println(b_back);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Method method2 = Person.class.getDeclaredMethod("setName", String.class);
        System.out.println(method2);
        method2.setAccessible(true);
        return_back = method2.invoke(mStudent,"nill");

        //警告: 最后一个参数使用了不准确的变量类型的 varargs 方法的非 varargs 调用;
        //Method method3 = Student.class.getDeclaredMethod("setGrade", null);
        Method method3 = Student.class.getDeclaredMethod("setGrade");
        System.out.println(method3);
        method3.setAccessible(true);
        return_back = method3.invoke(mStudent);
        System.out.println(return_back);

        Method method4 = Student.class.getDeclaredMethod("setGrade");
        System.out.println(method4);
        method4.setAccessible(true);
        return_back = method4.invoke(mStudent);
        System.out.println(return_back);

        Method method5 = Student.class.getDeclaredMethod("setGrade",Void.class);
        System.out.println(method5);
        method5.setAccessible(true);
        Void nullVoid = null;
        return_back = method5.invoke(mStudent,nullVoid);
        //-不可以return_back = method5.invoke(mStudent,null);
        System.out.println(return_back);

        //------------------------------------
        Method[] methods = null;

        /**
         * 返回本类，父类...至Object类，中的所有public方法(覆盖的方法，只显示最子类的)
         * clazz_student.getMethod()理解同 clazz_student.getMethods()
         */
        methods = clazz_student.getMethods();
        for (Method hMethod : methods) {
            System.out.println(TAG+"-testMethod-clazz_student.getMethods-"
                    +  "-hMethod.toString()->"+hMethod.toString()
            );
        }

        /**
         * 返回本类声明的所有方法
         */
        methods = clazz_student.getDeclaredMethods();
        for (Method hMethod : methods) {
            System.out.println(TAG+"-testMethod-clazz_student.getDeclaredMethods-"
                    +  "-hMethod.toString()->"+hMethod.toString()
            );
        }

        methods = clazz_student.getSuperclass().getDeclaredMethods();
        for (Method hMethod : methods) {
            System.out.println(TAG+"-testMethod-clazz_student.getSuperclass().getDeclaredMethods-"
                    +  "-hMethod.toString()->"+hMethod.toString()
            );
        }

        return_back = invoke(mStudent,"setTall","Hight_A");
        System.out.println(TAG+"-testMethod-mStudent-setTall-return_back-"+ return_back);
        //警告: 最后一个参数使用了不准确的变量类型的 varargs 方法的非 varargs 调用;
        ///return_back = invoke(mStudent,"toString",null);
        return_back = invoke(mStudent,"toString");

        System.out.println(TAG+"-testMethod-mStudent-toString-return_back-"+ return_back);

    }

    /**
     *
     * @param obj: 某个类的一个对象
     * @param methodName: 类的一个方法的方法名.
     * 该方法也可能是私有方法, 还可能是该方法在父类中定义的(私有)方法
     * @param args: 调用该方法需要传入的参数
     * @return: 调用方法后的返回值
     */
    public static Object invoke(Object obj, String methodName, Object ... args){
        //1. 获取 Method 对象
        Class[] parameterTypes = null;
        if (args == null) {
        } else {
            parameterTypes = new Class[args.length];
            for(int i = 0; i < args.length; i++){
                parameterTypes[i] = args[i].getClass();
            }
        }
        try {
            Method method = getMethod(obj.getClass(), methodName, parameterTypes);
            method.setAccessible(true);
            //2. 执行 Method 方法
            //3. 返回方法的返回值
            return method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取 clazz 的 methodName 方法. 该方法可能是私有方法, 还可能在父类中(私有方法)
     * 如果在该类中找不到此方法，就向他的父类找，一直到Object类为止
     　　　* 这个方法的另一个作用是根据一个类名，一个方法名，追踪到并获得此方法
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class clazz, String methodName,Class ... parameterTypes){
        for(;; clazz = clazz.getSuperclass()){
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) { }

            if (clazz == Object.class) {
                return null;
            }
        }
    }

    //=========================================

    public void test_FinalField() throws Exception{
        Student mStudent = new Student("");

        //todo 1 获取Class字节码
        Class clazz_student = mStudent.getClass();//1-

        //todo 2 获取指定字段
        Field field = clazz_student.getDeclaredField("final_grade");
        System.out.println(field);

        //todo 3.1 字段赋值
        field.setAccessible(true);//private使用----
        field.set(mStudent, 86);

        //todo 3.2 字段获取
        field.setAccessible(true);//private使用----
        Object return_back = field.get(mStudent);
        System.out.println(return_back);
        //--------------------------

        Field[] fields = null;
        /**
         * 返回本类，父类...至Object类，中的所有public字段(覆盖的方法，只显示最子类的)
         * clazz_student.getField()理解同 clazz_student.getMethods()
         */
        fields = clazz_student.getFields();
        for (Field hField : fields) {
            System.out.println(TAG+"-test_FinalField-clazz_student.getFields-"
                    +  "-hField.toString()->"+hField.toString()
            );
        }
    }

    public void testField() throws Exception{
        Student mStudent = new Student("");

        //todo 1 获取Class字节码
        Class clazz_student = mStudent.getClass();//1-

        //todo 2 获取指定字段
        Field field = clazz_student.getDeclaredField("grade");
        System.out.println(field);

        //todo 3.1 字段赋值
        field.setAccessible(true);//private使用----
        field.set(mStudent, 86);

        //todo 3.2 字段获取
        field.setAccessible(true);//private使用----
        Object return_back = field.get(mStudent);
        System.out.println(return_back);
        //--------------------------

        Field[] fields = null;
        /**
         * 返回本类，父类...至Object类，中的所有public字段(覆盖的方法，只显示最子类的)
         * clazz_student.getField()理解同 clazz_student.getMethods()
         */
        fields = clazz_student.getFields();
        for (Field hField : fields) {
            System.out.println(TAG+"-testField-clazz_student.getFields-"
                    +  "-hField.toString()->"+hField.toString()
            );
        }

        /**
         * 返回本类声明的所有字段
         */
        fields = clazz_student.getDeclaredFields();
        for (Field hField : fields) {
            System.out.println(TAG+"-testField-clazz_student.getDeclaredFields-"
                    +  "-hField.toString()->"+hField.toString()
            );
        }

        fields = clazz_student.getSuperclass().getDeclaredFields();
        for (Field hField : fields) {
            System.out.println(TAG+"-testField-clazz_student.getSuperclass().getDeclaredFields-"
                    +  "-hField.toString()->"+hField.toString()
            );
        }

        field = getField(mStudent.getClass(),"age");
        setFieldValue(mStudent,field,32);
        return_back = getFieldValue(mStudent,field);
        System.out.println(TAG+"-testField-return_back-"+ return_back );
        System.out.println(TAG+"-testField-99--------------------------------");

    }

    public Object getFieldValue(Object obj, Field field) throws Exception{
        field.setAccessible(true);
        return field.get(obj);
    }

    public void setFieldValue(Object obj, Field field, Object val) throws Exception {
        field.setAccessible(true);
        field.set(obj, val);
    }

    public Field getField(Class clazz, String fieldName){
        Field field = null;
        for(;;clazz = clazz.getSuperclass()){
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //e.printStackTrace();
            }
            if (clazz == Object.class) {
                return null;
            }
        }
    }

    //=========================================

    public void testConstructor() throws Exception{
        Student mStudent = new Student("");
        Class clazz_student = mStudent.getClass();

        // 获取某一个，需要参数列表
        Constructor<Person> constructor = null;
        try {
            // 只能获取公共的构造方法
            constructor = clazz_student.getConstructor(float.class);
        } catch (Exception e) {
        }
        System.out.println(TAG+"-testConstructor-"+ "-getConstructor-constructor->"+constructor);
        constructor = clazz_student.getDeclaredConstructor(float.class);
        System.out.println(TAG+"-testConstructor-"+ "-getDeclaredConstructor-constructor->"+constructor);
        constructor.setAccessible(true);//----
        Object obj = constructor.newInstance(.5f);
        System.out.println(TAG+"-testConstructor-"+  "-obj->"+obj);

        //=======================================

        // 1. 获取全部 Constructor 对象,返回本类声明的所有public构造方法
        Constructor<Student>[] constructors_Student = (Constructor<Student>[]) clazz_student.getDeclaredConstructors();
        System.out.println(TAG+"-testConstructor-getDeclaredConstructors-constructors_Student.length->"+constructors_Student.length);
        for(Constructor<Student> hConstructor: constructors_Student){
            System.out.println(TAG+"-testConstructor-for-getDeclaredConstructors-"
                    +  "-constructors_Student.toString()->"+hConstructor.toString()
            );
        }

        // 获取本类Student声明的所有public构造方法
        constructors_Student = (Constructor<Student>[]) clazz_student.getConstructors();
        System.out.println(TAG+"-testConstructor-getConstructors-constructors_Student.length->"+constructors_Student.length);
        for(Constructor<Student> hConstructor: constructors_Student){
            System.out.println(TAG+"-testConstructor-for-getConstructors-"
                    +  "-constructors_Student.toString()->"+hConstructor.toString()
            );
        }

        // 获取Person类声明的所有public构造方法
        Constructor<Person>[] constructors_Person = (Constructor<Person>[]) clazz_student.getSuperclass().getConstructors();
        System.out.println(TAG+"-testConstructor-getConstructors-constructors_Person.length->"+constructors_Person.length);
        for(Constructor<Person> hConstructor: constructors_Person){
            System.out.println(TAG+"-testConstructor-for-getConstructors-"
                    +  "-constructors_Person.toString()->"+hConstructor.toString()
            );
        }

        System.out.println(TAG+"-testConstructor-99--------------------------------");
    }

    //=========================================

    public void reflectStaticMain() throws Exception{
        //1、获取Student对象的字节码
        Class clazz = Class.forName(PAGName);

        //2、获取main方法,//第一个参数：方法名称，第二个参数：方法形参的类型，
        Method methodMain = clazz.getMethod("main", String[].class);
        //3、调用main方法
        // methodMain.invoke(null, new String[]{"a","b","c"});
        //第一个参数，对象类型，因为方法是static静态的，所以为null可以，
        // 第二个参数是String数组，这里要注意在jdk1.4时是数组，jdk1.5之后是可变参数
        //这里拆的时候将  new String[]{"a","b","c"} 拆成3个对象。。。所以需要将它强转。
        methodMain.invoke(null, (Object)new String[]{"1314","b","c"});//方式一
        // methodMain.invoke(null, new Object[]{new String[]{"a","b","c"}});//方式二
    }

    public void reflectionOutClass() throws Exception{
        /*
         外部类只有两种修饰符，只能用public和默认，不能用其他修饰符，内部类除外
         如何利用java反射机制获得private class与其它package中default class的实例
         目前做不到
         可以借助，OutPackageClassHelper这样的类间接实现，并且mOutPackageClass不为null
         */

        Field class_Field = OutPackageClassHelper.class.getDeclaredField("mOutPackageClass");
        class_Field.setAccessible(true);
        Object class_Object = class_Field.get(OutPackageClassHelper.class.newInstance());

        Class clazz = Class.forName("com.study.z_Reflection.annotation.OutPackageClass");
        clazz = class_Object.getClass();
        Field value_Field = clazz.getDeclaredField("value");
        value_Field.setAccessible(true);

        System.out.println(TAG+"-reflectionOutClass"
                +"-value-a->"+value_Field.get(class_Object)
        );

        Method setValue_Method= clazz.getDeclaredMethod("setValue",int.class);
        setValue_Method.setAccessible(true);
        setValue_Method.invoke(class_Object,49921);

        System.out.println(TAG+"-reflectionOutClass"
                +"-value-b->"+value_Field.get(class_Object)
        );

    }

    //=========================================

    public void testGenerics() throws Exception {
        System.out.println(TAG+"-testGenerics-01");

        //--反射方法的其它使用之---通过反射越过泛型检查
        ArrayList<String> strList = new ArrayList<>();
        strList.add("aaa");

        //-报错strList.add(100);
        //获取ArrayList的Class对象，反向的调用add()方法，添加数据
        Class listClass = strList.getClass(); //得到 strList 对象的字节码 对象
        //获取add()方法
        Method m = listClass.getMethod("add", Object.class);
        //调用add()方法
        m.invoke(strList, 100);
        m.invoke(strList, false);

        //遍历集合
        for(Object obj : strList){
            System.out.println(obj);
        }

        //----------------
        //--不对 ArrayList<Integer> IntegerList_a = new ArrayList<Object>();
        //--不对ArrayList<Object> IntegerList_b = new ArrayList<Integer>();
        ArrayList<Integer> IntegerList_c = new ArrayList<Integer>();
        ArrayList<Integer> IntegerList = new ArrayList<>();
        IntegerList.add(11);

        ArrayList<String> StringList = new ArrayList<>();
        System.out.println(TAG+"-testGenerics"
                + "-StringList.hashCode()->" + StringList.hashCode()
                + "-IntegerList.hashCode()->" + IntegerList.hashCode()
        );

        Method m_IntegerList = IntegerList.getClass().getMethod("add",Object.class);
        /*Type[] paramType = m_IntegerList.getGenericParameterTypes();
         System.out.println(paramType.getActualTypeArguments()[0]);
        System.out.println(TAG+"-testGenerics-m_IntegerList.getActualTypeArguments->"+paramType.getActualTypeArguments()[0]);*/
        System.out.println(TAG+"-testGenerics-m_IntegerList.getGenericParameterTypes->"+m_IntegerList.getGenericParameterTypes()[0]);

        boolean mark = (boolean)m_IntegerList.invoke(IntegerList, "a");
        System.out.println(TAG+"-testGenerics-IntegerList.invoke(strList, 101-mark->"+mark);
        List IntegerList_bb = IntegerList;//兼容性做法
        IntegerList_bb.add("b");

        for(Object obj : IntegerList){
            System.out.println(obj);
        }

        //---------------------------------
        /*
            问：既然泛型类型在编译时就被擦除了，那类似 Gson 这种 json 解析框架是如何解析数据到泛型类型 Bean 结构的呢？
            答：本题其实一箭双雕，即考察了对于 Gson 框架是否熟悉又考察了 Java 泛型与反射的关系及泛型的实质。
            由于在运行期间无法通过 getClass() 得知 T 的具体类型，所以 Gson 通过借助 TypeToken 类来解决这个问题，使用样例如下
         */
        ArrayList<String> list = new ArrayList<>();
        list.add("java");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        String gStr = new Gson().toJson(list, type);
        System.out.println(TAG+"-testGenerics-toJson->"+gStr);
        ArrayList<String> gList = new Gson().fromJson(gStr, type);

    }

    //---------------------------

    class DAO<T> {
        //根据id获取一个对象
        T get(Integer id){

            return null;
        }

        //保存一个对象
        void save(T entity){

        }
    }

    class PersonDAO extends DAO<Person> {

    }

    public  void testGetSuperClassGenericType(){
        /*

         6.1 getGenericSuperClass: 获取带泛型参数的父类, 返回值为: BaseDao<Employee, String>
    　　  6.2 Type 的子接口: ParameterizedType
    　　  6.3 可以调用 ParameterizedType 的 Type[] getActualTypeArguments() 获取泛型参数的数组.
         */
        Class clazz = PersonDAO.class;
        //PersonDAO.class
        Class argClazz = getSuperClassGenricType(clazz, 0);
        System.out.println(argClazz);
    }

    @SuppressWarnings("unchecked")
    public  Class getSuperGenericType(Class clazz){
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型
     * 如: public EmployeeDao extends BaseDao<Employee, String>
     * @param clazz: 子类对应的 Class 对象
     * @param index: 子类继承父类时传入的泛型的索引. 从 0 开始
     * @return
     */
    @SuppressWarnings("unchecked")
    public  Class getSuperClassGenricType(Class clazz, int index){
        Type type = clazz.getGenericSuperclass();
        if(!(type instanceof ParameterizedType)){
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type [] args = parameterizedType.getActualTypeArguments();
        if(args == null){
            return null;
        }
        if(index < 0 || index > args.length - 1){
            return null;
        }
        Type arg = args[index];
        if(arg instanceof Class){
            return (Class) arg;
        }
        return null;
    }


    //-------------------------------

    class Foo<T extends CharSequence> {

        public List<Bar> children = new ArrayList<Bar>();

        public List<StringBuilder> foo(List<String> foo) {
            return null;
        }

        public void bar(List<? extends String> param) {
            //empty
        }
    }

    class Bar extends Foo<String> {
    }


    /*
        前面已经说了，Java的泛型是伪泛型。为什么说Java的泛型是伪泛型呢？因为，在编译期间，所有的泛型信息都会被擦除掉
        要区分原始类型和泛型变量的类型
        在调用泛型方法的时候，可以指定泛型，也可以不指定泛型。
        在不指定泛型的情况下，泛型变量的类型为 该方法中的几种类型的同一个父类的最小级，直到Object。
        在指定泛型的时候，该方法中的几种类型必须是该泛型实例类型或者其子类。

        泛型的擦除机制实际上擦除的是除结构化信息外的所有东西（结构化信息指与类结构相关的信息，
        而不是与程序执行流程有关的，即与类及其字段和方法的类型参数相关的元数据都会被保留下来通过反射获取到）
     */
    public void testGenericType() throws Exception{
        ParameterizedType type = (ParameterizedType) Bar.class.getGenericSuperclass();
        System.out.println(type.getActualTypeArguments()[0]);
        ParameterizedType fieldType = (ParameterizedType) Foo.class.getField("children").getGenericType();
        System.out.println(fieldType.getActualTypeArguments()[0]);
        ParameterizedType paramType = (ParameterizedType) Foo.class.getMethod("foo", List.class).getGenericParameterTypes()[0];
        System.out.println(paramType.getActualTypeArguments()[0]);
        System.out.println(Foo.class.getTypeParameters()[0].getBounds()[0]);
    }


    //=========================================


    public void testAnnotation() throws Exception{
        Student mStudent = new Student("");

        Field score_Field = Student.class.getDeclaredField("score");
        AnnoAll mAnnoAll = score_Field.getAnnotation(AnnoAll.class);
        int value = mAnnoAll.age();
        score_Field.setAccessible(true);
        score_Field.set(mStudent,value);
        //-----
        int score_value = (int)score_Field.get(mStudent);
        System.out.println(TAG+"-testAnnotation"
                +"-score_value-a->"+score_value
        );

        Method setScore_Method = Student.class.getDeclaredMethod("setScore",int.class);
        mAnnoAll = setScore_Method.getAnnotation(AnnoAll.class);
        if (mAnnoAll != null) {
            setScore_Method.setAccessible(true);
            setScore_Method.invoke(mStudent,mAnnoAll.age());
            score_value = (int)score_Field.get(mStudent);
            System.out.println(TAG+"-testAnnotation"
                    +"-score_value-b->"+score_value
            );
        }

        mAnnoAll = Student.class.getAnnotation(AnnoAll.class);
        if (mAnnoAll != null) {
            System.out.println(TAG+"-testAnnotation"
                    +"-mAnnoAll.age()->"+mAnnoAll.age()
            );

        }

    }





}



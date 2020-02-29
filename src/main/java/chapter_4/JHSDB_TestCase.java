package chapter_4;

// 153页
// 这3个变量所指向的对象，存放在哪里？
public class JHSDB_TestCase {

    static class Test {
        // staticObj 随着Test的类型信息存放在：方法区（jdk8及以后，是元空间）
        static ObjectHolder staticObj = new ObjectHolder();
        // instanceObj 随着Test的对象实例存放在：Java堆
        ObjectHolder instanceObj = new ObjectHolder();

        void foo () {
            // localObj 存放在foo()方法栈帧的局部变量表中
            ObjectHolder localObj = new ObjectHolder();
            // 这里设一个断点
            System.out.println("done");
        }
    }

    private static class ObjectHolder {}

    public static void main(String[] args) {
        Test test = new JHSDB_TestCase.Test();
        test.foo();
    }
}

package oom;

/**
 * 虚拟机栈和本地方法栈溢出
 * VM Args: -Xss128k
 *
 * Created by liubingguang on 2017/6/23.
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void setStackLength() {
        stackLength++;
        setStackLength();
    }

    public static void main(String[] args) {
        JavaVMStackSOF sof = new JavaVMStackSOF();
        try {
            sof.setStackLength();
        } catch (Throwable e) {
            System.out.println(sof.stackLength);
            throw e;
        }
    }
}

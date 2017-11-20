package guava.a_base_utilities;

import com.google.common.base.Preconditions;
import org.junit.Test;

/**
 * 前置条件
 *
 * @author LBG - 2017/11/16 0016
 */
public class PreconditionsDemo {

    /**
     * checkArgument(boolean)
     */
    @Test
    public void checkArgument() {
        Preconditions.checkArgument(1 < 0, "1咋可能小于0");
    }

    /**
     * checkNotNull(T)
     */
    @Test
    public void checkNotNull() {
        Preconditions.checkNotNull(null, "咋能为null呢");
    }

}

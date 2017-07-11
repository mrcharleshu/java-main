package com.charles.common.hash;

/**
 * 我们为编写的类重写hashCode方法时，可能会看到如下所示的代码，
 * 其实我们不太理解为什么要使用这样的乘法运算来产生哈希码（散列码），
 * 而且为什么这个数是个素数，为什么通常选择31这个数？前两个问题的答案你可以自己百度一下，
 * 选择31是因为可以用移位和减法运算来代替乘法，从而得到更好的性能。
 * 说到这里你可能已经想到了：31 * num 等价于(num << 5) – num，左移5位相当于乘以2的5次方再减去自身就相当于乘以31，
 * 现在的VM都能自动完成这个优化
 */
public class PhoneNumber {
    private int areaCode;
    private String prefix;
    private String lineNumber;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + areaCode;
        result = prime * result
                + ((lineNumber == null) ? 0 : lineNumber.hashCode());
        result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PhoneNumber other = (PhoneNumber) obj;
        if (areaCode != other.areaCode) {
            return false;
        }
        if (lineNumber == null) {
            if (other.lineNumber != null) {
                return false;
            }
        } else if (!lineNumber.equals(other.lineNumber)) {
            return false;
        }
        if (prefix == null) {
            if (other.prefix != null) {
                return false;
            }
        } else if (!prefix.equals(other.prefix)) {
            return false;
        }
        return true;
    }

}
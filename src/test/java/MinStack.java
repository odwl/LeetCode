import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Stack;




public class MinStack {

    private final Stack<Map.Entry<Integer, Integer>> stack;

    public MinStack() {
        stack = new Stack();
    }

    public void push(int val) {
        Integer min = stack.isEmpty() ? val : Math.min(stack.peek().getValue(), val) ;
        stack.push(new AbstractMap.SimpleImmutableEntry<>(val,min));
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek().getKey();
    }

    public int getMin() {
        return stack.peek().getValue();
    }

    @Test()
    public void testEasy() {
        MinStack obj = new MinStack();
        obj.push(5);
        obj.push(3);
        obj.push(4);
        Assert.assertEquals(4, obj.top());
        Assert.assertEquals(3, obj.getMin());

        obj.pop();
        obj.pop();

        Assert.assertEquals(5, obj.top());
        Assert.assertEquals(5, obj.getMin());

        obj.push(6);
        Assert.assertEquals(5, obj.getMin());
    }}

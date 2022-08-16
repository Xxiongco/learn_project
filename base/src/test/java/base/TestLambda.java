package base;

public class TestLambda {


    /**

     返回消息

     base.TestLambda@355da254
     base.TestLambda@355da254
     hello world

     也就是说lambda中使用this，这个this是创建这个lambda的对象

     */

    public static void main(String[] args) {
        TestLambda testLambda = new TestLambda();

        System.out.println(testLambda);

        Say say = testLambda.createSay();

        say.say("hello world");

    }

    public Say createSay() {
        return (String message) -> {
            System.out.println(this);
            System.out.println(message);
            return message;
        };
    }

}

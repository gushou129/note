# abstract

## 是什么

使用`abstract`声明的类为抽象类，使用`abstract`声明的方法为抽象方法，如果一个类中定义了抽象方法，则这个类必须声明为抽象类。

## 有什么用

使用抽象类做父类的子类拥有更强的可拓展性。各个子类之间的父类方法重写差异较大时可使用抽象类做父类。

## 注意

抽象类无法创建实例对象。

### 为什么

如果抽象类可以创建实例对象，因为抽象类中含有抽象方法，该抽象方法无方法体（无具体实现），则该实例对象无法发挥应有作用。故无法创建实例对象。

## 使用方式[模板方式]

### 简单的使用一下abstract

需求：
- 某加油站推出了2种支付卡，一种是预存10000的金卡，后续加油享受8折优惠，另一种是预存5000的银卡,后续加油享受8.5折优惠。
- 请分别实现2种卡片进入收银系统后的逻辑，卡片需要包含主人名称，余额，支付功能。


一个抽象父类：

```java
public abstract class Card {
    private String name;
    private double money;

    public abstract void pay();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
```

一个子类：
```java
public class GoldCard extends Card{
    private static final double DISCOUNT = 0.8;
    @Override
    public void pay() {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入您的消费：");
        double amount = sc.nextDouble();

        double discountPrice = amount * DISCOUNT;
        System.out.println("您的折扣价为：" + discountPrice);
        
        double balance = getMoney() - discountPrice;
        setMoney(balance);
        System.out.println("您的余额为：" + getMoney());
    }
}
```

看起来是能运行的，也没有什么大问题。
可是如果现在我们需要新添加一种卡的类型，比如银卡的话，几乎需要将金卡子类中的`pay()`方法重新抄一遍再将`DISCOUNT`的值修改为0.85才能实现。这是否又是在降低代码复用性呢？

### 优化后的代码

一个抽象父类：
```java
public abstract class Card {
    private String name;
    private double money;

    // 模板方法，建议使用final修饰更加安全
    public final void pay() {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入您的消费：");
        double amount = sc.nextDouble();
        //在此处调用了抽象方法 getDiscount()
        double discountPrice = amount * getDiscount();
        System.out.println("您的折扣价为：" + discountPrice);

        double balance = getMoney() - discountPrice;
        setMoney(balance);
        System.out.println("您的余额为：" + getMoney());
    }

    public abstract double getDiscount();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
```

一个子类：
```java
public class GoldCard extends Card{
    private static final double DISCOUNT = 0.8;
    @Override
    public double getDiscount() {
        return DISCOUNT;
    }
}
```

这个时候当我们在添加另一种卡类就可以不用在复制粘贴这些代码了。使用抽象方法在父类中充当形参，避免了代码的重复，提高了可读性。

**同时建议将模板方法使用`final`修饰，更加安全。**

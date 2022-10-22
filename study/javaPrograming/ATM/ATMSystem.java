package ATM;



import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        accounts.add(new Account("12312312", "123", "123", 1000, 1000));
        accounts.add(new Account("12345678", "123", "123", 1000, 1000));
        while (true){
            helloPrint();
            processCode(accounts);
        }
    }

    /**
     * 欢迎界面
     */
    public static void helloPrint(){
        System.out.println("------欢迎进入ATM系统-------");
        System.out.println("1.login");
        System.out.println("2.register");
        System.out.println("请输入命令1、2选择对应操作：");
    }

    public static void processCode(ArrayList<Account> accounts){
        Scanner sc = new Scanner(System.in);

        // 处理输入的指令
        int code = sc.nextInt();
        switch (code) {
            case 1:
                login(accounts);
                break;
            case 2:
                register(accounts);
                break;
            default:
                System.out.println("您输入有误，请重试。");
        }
    }

    /**
     * 用户开户功能的实现
     * @param accounts  接收注册用户的账户集合
     */
    private static void register(ArrayList<Account> accounts) {
        System.out.println("---------开户界面----------");
        Account ac = new Account();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您的姓名：");
        ac.setName(sc.next());
        while (true){
            System.out.println("请输入您的密码：");
            String password = sc.next();
            System.out.println("请您再次确认密码：");
            if (password.equals(sc.next())){
                ac.setPassword(password);
                break;
            }
            System.out.println("您的两次输入有误，请重试。");
        }
        System.out.println("请设置当日取现额度：");
        ac.setQouta(sc.nextDouble());
        // setID
        ac.setIdCard(createID(accounts));

        accounts.add(ac);

        System.out.println("恭喜您，" + ac.getName() + "先生/女士开户成功，您的卡号是：" + ac.getIdCard());
    }

    /**
     * 创建唯一的用户卡号ID
     * @param accounts  获取所有用户的卡号ID
     * @return  唯一的卡号
     */
    private static String createID(ArrayList<Account> accounts) {
        Random r = new Random();
        String num = "1234567890";
        StringBuilder sb;

        // 创建唯一的卡号id
        while (true) {
            sb = new StringBuilder();
            boolean appear = false;
            // 创建id
            for (int i = 0; i < 8; i++) {
                sb.append(num.toCharArray()[r.nextInt(10)]);
            }
            // 检验id是否重复
            for (Account account : accounts) {
                if (account.getIdCard().equals(String.valueOf(sb))) {
                    appear = true;
                    break;
                }
            }
            // 没有重复则跳出循环
            if (!appear) {
                break;
            }
        }
        return String.valueOf(sb);
    }

    /**
     * 输入卡号与密码进行用户操作
     * @param accounts  用于验证账户密码
     */
    private static void login(ArrayList<Account> accounts) {
        if (accounts.size() == 0) {
            System.out.println("对不起，系统中无用户，请先开户，再试试");
            return;
        }
        Account ac = verify(accounts);

        if (ac == null) {
            System.out.println("查无此卡，请重试。");
            return;
        }

        while (true) {
            userPanelPrint();
            if (userPanelProcess(ac)) {
                break;
            }
        }
    }

    private static boolean userPanelProcess(Account ac) {
        Scanner sc = new Scanner(System.in);
        int code = sc.nextInt();


        switch (code) {
            case 1:
                // 查询
                detail(ac);
                break;
            case 2:
                // 存款
                save(ac);
                break;
            case 3:
                // 取款
                take(ac);
                break;
            case 4:
                // 转账
                transfer(ac, accounts);
                break;
            case 5:
                // 修改密码
                change(ac);
                break;
            case 6:
                // 退出
                System.out.println("感谢您的使用。");
                return true;
            case 7:
                // 注销
                delete(ac, accounts);
                System.out.println("感谢您的使用。");
                return true;
            default:
                System.out.println("您的输入有误请重试");
        }

        return false;
    }

    private static void delete(Account ac, ArrayList<Account> accounts) {
        System.out.println("确认您需要注销吗：");
        System.out.println("1.注销");
        System.out.println("2.取消");
        Scanner sc = new Scanner(System.in);
        int code = sc.nextInt();
        switch (code) {
            case 1:
                accounts.remove(ac);
                System.out.println("您已成功注销");
                break;
            case 2:
                break;
        }
    }

    private static void change(Account ac) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您的新密码：");
        String newPassword = sc.next();
        while (true) {
            System.out.println("请确认您的新密码：");
            if (!newPassword.equals(sc.next())) {
                System.out.println("输入有误，请重试。");
            } else {
                ac.setPassword(newPassword);
                break;
            }
        }
    }

    private static void transfer(Account ac, ArrayList<Account> accounts) {
        if (accounts.size() < 2) {
            System.out.println("当前系统中暂无可转账用户，请重试。");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入转账卡号：");
        String anotherId = sc.next();
        boolean esse = false;
        Account a = new Account();

        for (Account account : accounts) {
            a = account;
            if (a.getIdCard().equals(anotherId)) {
                esse = true;
                break;
            }
        }
        if (!esse) {
            System.out.println("无此卡号，请重试。");
            return;
        }
        System.out.println("请输入转账金额");
        double transMoney = sc.nextDouble();
        if (transMoney > ac.getQouta()) {
            System.out.println("您转出的金额大于您的限额，请重新输入。");
            System.out.println("您的限额为：" + ac.getMoney());
            return;
        } else if (transMoney > ac.getMoney()) {
            System.out.println("您转出的金额大于您的存款，请重新输入。");
            return;
        }
        a.setMoney(a.getMoney() + transMoney);
        ac.setMoney(ac.getMoney() - transMoney);
        System.out.println("您现在的余额为：" + ac.getMoney());
    }

    private static void take(Account ac) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您要取走的金额：");
        double takeMoney = sc.nextDouble();
        if (takeMoney > ac.getQouta()) {
            System.out.println("您取走的金额大于您的限额，请重新输入。");
            System.out.println("您的限额为：" + ac.getMoney());
            return;
        } else if (takeMoney > ac.getMoney()) {
            System.out.println("您取走的金额大于您的存款，请重新输入。");
            return;
        }
        ac.setMoney(ac.getMoney() - takeMoney);
        System.out.println("您现在的存款为：" + ac.getMoney());
    }

    private static void save(Account ac) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入您存的金额：");

        ac.setMoney(ac.getMoney() + sc.nextDouble());
        System.out.println("您现在的存款为：" + ac.getMoney());
    }

    private static void detail(Account ac) {
        System.out.println("您的账户信息如下：");
        System.out.println("卡号：" + ac.getIdCard());
        System.out.println("姓名：" + ac.getName());
        System.out.println("余额：" + ac.getMoney());
        System.out.println("当次取现额度：" + ac.getQouta());
    }

    /**
     * 用户界面打印
     */
    private static void userPanelPrint() {
        System.out.println("----------ATM用户操作界面---------");
        System.out.println("1.查询；");
        System.out.println("2.存款；");
        System.out.println("3.取款；");
        System.out.println("4.转账；");
        System.out.println("5.修改密码；");
        System.out.println("6.退出；");
        System.out.println("7.注销当前用户；");
        System.out.println("请输入操作：");
    }

    /**
     * 验证账户卡号密码
     * @param accounts  获取账户卡号密码用于校验
     */
    private static Account verify(ArrayList<Account> accounts) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------登陆界面-----------");
        System.out.println("请输入您的卡号：");
        String s = sc.next();
        for (Account account : accounts) {
            boolean loginRight = false;
            if (account.getIdCard().equals(s)) {
                System.out.println("请输入您的密码：");
                // 输入登陆密码
                while (true) {
                    if (account.getPassword().equals(sc.next())) {
                        System.out.println("恭喜卡号为：" + account.getIdCard() + "的用户登陆成功！");
                        loginRight = true;
                        break;
                    } else {
                        System.out.println("您的密码错误请重试。");
                    }
                }
            }
            if (loginRight) {
                return account;
            }
        }
        System.out.println("您的卡号不存在，请重试。");
        return null;
    }

}

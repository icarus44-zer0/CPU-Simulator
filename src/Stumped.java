import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;

//@SuppressWarnings("all")
public class Stumped {

    private static Stack<Integer> stack;
    private static ArrayList<String> arr;
    private static int[] mem;
    private static int inputCMD = 0;
    private static boolean isInput = true;
    private static int counter = 0;

    public static void main(String[] args) {
        mem = new int[4095];
        arr = new ArrayList<String>();
        stack = new Stack<Integer>();

        try {
            //File myObj = new File("C:\\Users\\Icarus44\\IdeaProjects\\STumpED\\src\\input.txt");
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                arr.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            inputCMD = Integer.parseInt(args[1]);
            isInput = true;
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        while (!(arr.get(counter).equals("0F00"))) {
            execute(arr.get(counter));
        }

    }

    private static void execute(String args) {
        counter++;
        try {
            TimeUnit.MILLISECONDS.sleep(0);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /**
         * debug
         */
//        if (!stack.empty()) {
//            System.out.print(" Stack: " + stack.toString());
//            System.out.print("\n arg: " + args);
//            System.out.print(" " + "Counter: " + counter);
//        }

        String opcode = args.substring(0, 1);
        String dataHex = args.substring(1);
        int data = HEXTODEC(dataHex);
        switch (opcode) {
            case "0":
                CONTROL(data);
                break;
            case "1":
                PUSHI(dataHex);
                // System.out.print(opcode);
                // System.out.println(": PUSHI");
                break;
            case "2":
                PUSHA(data);
                // System.out.print(opcode);
                // System.out.println(": PUSHA");
                break;
            case "3":
                POPA(data);
                // System.out.print(opcode);
                // System.out.println(": POPA");
                break;
            case "4":
                JMPA(dataHex);
                // System.out.print(opcode);
                // System.out.println(": JMPA");
                break;
            case "5":
                JZA(dataHex);
                // System.out.print(opcode);
                // System.out.println(": JZA");
                break;
            case "6":
                JNZA(dataHex);
                // System.out.print(opcode);
                // System.out.println(": JNZA");
                break;
            case "D":
                IN(data);
                // System.out.print(opcode);
                // System.out.println(": IN");
                break;
            case "E":
                OUT(data);
                // System.out.print(opcode);
                // System.out.println(": OUT");
                break;
            case "F":
                ALU(data);
                // System.out.print(opcode);
                // System.out.println(": ALU");
                break;
            default:
                break;
        }

    }

    private static void CONTROL(int data) {
        switch (data) {
            case 0: // 0x000
                // System.out.print(DECTOHEX(data));
                // System.out.println(": NOP");
                NOP(data);
                break;
            case 256: // 0x100
                PUSHPC(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": PUSHPC");
                break;
            case 512: // 0x200
                POPPC(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": POPPC");
                break;
            case 768: // 0x300
                LD(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": LD");
                break;
            case 1024: // 0x400
                ST();
                // System.out.print(DECTOHEX(data));
                // System.out.println(": ST");
                break;
            case 1280: // 0x500
                DUP(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": DUP");
                break;
            case 1536: // 0x600
                DROP(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": DROP");
                break;
            case 1792: // 0x700
                OVER(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": NOP");
                break;
            case 2048: // 0x800
                DNEXT(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": DNEXT");
                break;
            case 2304: // 0x900
                SWAP(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": SWAP");
                break;
            case 3840: // 0xF00
                HALT(data);
                // System.out.print(DECTOHEX(data));
                // System.out.println(": HALT");
                break;
            default:
                break;
        }
    }

    private static void ALU(int data) {

        switch (data) {
            case 0:
                ADD(data);
                // System.out.print(data);
                // System.out.println(": ADD");
                break;
            case 1:
                SUB(data);
                // System.out.print(data);
                // System.out.println(": SUB");
                break;
            case 2:
                MUL(data);
                // System.out.print(data);
                // System.out.println(": MUL");
                break;
            case 3:
                DIV(data);
                // System.out.print(data);
                // System.out.println(": DIV");
                break;
            case 4:
                MOD(data);
                // System.out.print(data);
                // System.out.println(": MOD");
                break;
            case 5:
                SHL(data);
                // System.out.print(data);
                // System.out.println(": SHL");
                break;
            case 6:
                SHR(data);
                // System.out.print(data);
                // System.out.println(": SHR");
                break;
            case 7:
                BAND(data);
                // System.out.print(data);
                // System.out.println(": BAND");
                break;
            case 8:
                BOR(data);
                // System.out.print(data);
                // System.out.println(": BOR");
                break;
            case 9:
                BXOR(data);
                // System.out.print(data);
                // System.out.println(": BXOR");
                break;
            case 10:
                AND(data);
                // System.out.print(data);
                // System.out.println(": AND");
                break;
            case 11:
                OR(data);
                // System.out.print(data);
                // System.out.println(": OR");
                break;
            case 12:
                EQ(data);
                // System.out.print(data);
                // System.out.println(": EQ");
                break;
            case 13:
                NE(data);
                // System.out.print(data);
                // System.out.println(": NE");
                break;
            case 14:
                GE(data);
                // System.out.print(data);
                // System.out.println(": GE");
                break;
            case 15:
                LE(data);
                // System.out.print(data);
                // System.out.println(": LE");
                break;
            case 16:
                GT(data);
                // System.out.print(data);
                // System.out.println(": GT");
                break;
            case 17:
                LT(data);
                // System.out.print(data);
                // System.out.println(": LT");
                break;
            case 18:
                NEG(data);
                // System.out.print(data);
                // System.out.println(": NEG");
                break;
            case 19:
                BNOT(data);
                // System.out.print(data);
                // System.out.println(": BNOT");
                break;
            case 20:
                NOT(data);
                // System.out.print(data);
                // System.out.println(": NOT");
                break;
            default:
                break;
        }
    }

    // 3 bit hex to int
    private static int HEXTODEC(String data) {
        int ret = Integer.parseInt(data, 16);
        return ret;
    }

    // 3 bit hex to int
    private static String DECTOHEX(int data) {
        String ret = Integer.toHexString(data);
        return ret;
    }

    // Do Nothing
    private static void NOP(int data) {

    }

    // Stop CPU Execution
    private static void HALT(int data) {
        System.exit(42);
    }

    // PC top
    private static void PUSHPC(int data) {
        stack.push(counter);
    }

    // top PC
    private static void POPPC(int data) {
        counter = stack.pop();
    }

    // mem[top] top
    private static void LD(int data) {
        int top = stack.pop();
        stack.push(mem[top]);
    }

    // top mem[next]
    private static void ST() {
        int top = stack.pop();
        int next = stack.pop();
        mem[next] = top;
    }

    // Copies top of stack
    private static void DUP(int data) {
        int top = stack.pop();

        stack.push(top);
        stack.push(top);
    }

    // Removes top of stack
    private static void DROP(int data) {
        stack.pop();
    }

    // Copies next to top
    private static void OVER(int data) {
        int top = stack.pop();
        int next = stack.pop();

        stack.push(next);
        stack.push(top);
        stack.push(next);
    }

    // Drops next
    private static void DNEXT(int data) {
        int top = stack.pop();
        stack.pop();

        stack.push(top);
    }

    // Swaps top and next
    private static void SWAP(int data) {
        int top = stack.pop();
        int next = stack.pop();

        stack.push(top);
        stack.push(next);
    }

    private static void PUSHI(String dataHex) {
        String builder = "";
        for (int i = 0; i < dataHex.length(); i++) {
            switch (dataHex.substring(i, i + 1)) {
                case "0":
                    builder += "0000";
                    break;
                case "1":
                    builder += "0001";
                    break;
                case "2":
                    builder += "0010";
                    break;
                case "3":
                    builder += "0011";
                    break;
                case "4":
                    builder += "0100";
                    break;
                case "5":
                    builder += "0101";
                    break;
                case "6":
                    builder += "0110";
                    break;
                case "7":
                    builder += "0111";
                    break;
                case "8":
                    builder += "1000";
                    break;
                case "9":
                    builder += "1001";
                    break;
                case "A":
                    builder += "1010";
                    break;
                case "B":
                    builder += "1011";
                    break;
                case "C":
                    builder += "1100";
                    break;
                case "D":
                    builder += "1101";
                    break;
                case "E":
                    builder += "1110";
                    break;
                case "F":
                    builder += "1111";
                    break;
                default:
                    break;
            }
        }
        String sign = builder.substring(0, 1);
        String sExt = "";

        if (sign.equals("1")) {
            for (int i = builder.length(); i < 16; i++) {
                sExt += "1";
            }
        } else if (sign.equals("0")) {
            for (int i = builder.length(); i < 16; i++) {
                sExt += "0";
            }
        }
        builder = sExt + builder;

        String b2HEX = "";

        for (int i = 0; i < builder.length(); i += 4) {
            switch (builder.substring(i, i + 4)) {
                case "0000":
                    b2HEX += "0";
                    break;
                case "0001":
                    b2HEX += "1";
                    break;
                case "0010":
                    b2HEX += "2";
                    break;
                case "0011":
                    b2HEX += "3";
                    break;
                case "0100":
                    b2HEX += "4";
                    break;
                case "0101":
                    b2HEX += "5";
                    break;
                case "0110":
                    b2HEX += "6";
                    break;
                case "0111":
                    b2HEX += "7";
                    break;
                case "1000":
                    b2HEX += "8";
                    break;
                case "1001":
                    b2HEX += "9";
                    break;
                case "1010":
                    b2HEX += "A";
                    break;
                case "1011":
                    b2HEX += "B";
                    break;
                case "1100":
                    b2HEX += "C";
                    break;
                case "1101":
                    b2HEX += "D";
                    break;
                case "1110":
                    b2HEX += "E";
                    break;
                case "1111":
                    b2HEX += "F";
                    break;
                default:
                    break;
            }
        }

        short st = (short) Integer.parseInt(b2HEX, 16);
        stack.push((int) st);

    }

    private static void PUSHA(int data) {
        int top = mem[data];
        stack.push(top);
    }

    private static void POPA(int data) {
        mem[data] = stack.pop();
    }

    /**
     *
     * @param dataHex
     */
    private static void JMPA(String dataHex) {
        counter = HEXTODEC(dataHex);
        execute(arr.get(counter));
    }

    /**
     *
     * @param dataHex
     */
    private static void JZA(String dataHex) {
        if (!stack.empty()) {
            int test = stack.pop();
            if (test == 0) {
                counter = HEXTODEC(dataHex);
                execute(arr.get(counter));
            }
        }
    }

    /**
     *
     * @param dataHex
     */
    private static void JNZA(String dataHex) {
        if (!stack.empty()) {
            int test = stack.pop();
            if (test != 0) {
                counter = HEXTODEC(dataHex);
                execute(arr.get(counter));
            }
        }
    }

    private static void IN(int data) {
        if (isInput) {
            stack.push(inputCMD);
        }
    }

    private static void OUT(int data) {
        //System.out.println("\nOUTPUT:" + stack.pop());
        System.out.println(stack.pop());
    }

    private static void ADD(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next + top;
        stack.push(output);
    }

    private static void SUB(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next - top;
        stack.push(output);
    }

    private static void MUL(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next * top;
        stack.push(output);
    }

    private static void DIV(int data) {

        int top = stack.pop();
        int next = stack.pop();

        if (top == 0) {
            execute("0F00");
        } else {
            int output = next / top;
            stack.push(output);
        }
    }

    private static void MOD(int data) {
        int top = stack.pop();
        int next = stack.pop();
        if (top == 0) {
            execute("0F00");
        } else {
            int output = next % top;
            stack.push(output);
        }
    }

    private static void SHL(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next << top;
        stack.push(output);
    }

    private static void SHR(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next >> top;
        stack.push(output);
    }

    private static void BAND(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next & top;
        stack.push(output);
    }

    private static void BOR(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next | top;
        stack.push(output);
    }

    private static void BXOR(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = next ^ top;
        stack.push(output);
    }

    private static void AND(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = 0;
        boolean a = false;
        boolean b = false;

        if (next != 0) {
            a = true;
        }
        if (top != 0) {
            b = true;
        }

        if (a && b) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void OR(int data) {
        int top = stack.pop();
        int next = stack.pop();
        int output = 0;
        boolean a = false;
        boolean b = false;

        if (next != 0) {
            a = true;
        }
        if (top != 0) {
            b = true;
        }

        if (a || b) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void EQ(int data) {
        int top = stack.pop();
        int next = stack.pop();

        if (next == top) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void NE(int data) {
        int top = stack.pop();
        int next = stack.pop();

        if (next != top) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void GE(int data) {
        int top = stack.pop();
        int next = stack.pop();

        if (next >= top) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void LE(int data) {
        int top = stack.pop();
        int next = stack.pop();

        if (next <= top) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void GT(int data) {
        int top = stack.pop();
        int next = stack.pop();

        if (next > top) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void LT(int data) {
        int top = stack.pop();
        int next = stack.pop();

        if (next < top) {
            stack.push(1);
        } else {
            stack.push(0);
        }
    }

    private static void NEG(int data) {
        int top = stack.pop();
        top = -top;
        stack.add(top);
    }

    private static void BNOT(int data) {
        int top = stack.pop();

        top = ~top;
        stack.push(top);
    }

    private static void NOT(int data) {
        int top = stack.pop();
        stack.push(top);
    }
}
package com.concurrency;

import javafx.beans.binding.When;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @Date 2020/11/29 17:20
 * @Author by LSY
 * @Description 管道输入输出流
 */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //将输出流和输入流进行连接，否则在使用时会抛出IoException
        out.connect(in);

        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int received;
        try {
            while((received = System.in.read()) != -1){
                out.write(received);
            }
        }finally {
            out.close();
        }
    }

    static class Print implements Runnable{
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int received;
            try {
                while ((received = in.read()) != -1){
                    System.out.print((char) received);
                }
            }catch (IOException e){

            }
        }
    }
}

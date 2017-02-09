package com.qinghuaci.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Description:
 * servlet3.0默认是不支持异步的通过asyncSupported=true,打开
 * User: zhouq
 * Date: 2017/2/9
 */

@WebServlet(name = "SecondServlet", urlPatterns = {"/secondServlet"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = null;

        resp.setContentType("text/html");
        try {
            out = resp.getWriter();
            out.print("servlets starts:" + new Date() + "<br>");
            out.flush();

            AsyncContext asyncContext = req.startAsync();

            asyncContext.addListener(new AsyncListener() {

                public void onComplete(AsyncEvent asyncEvent) throws IOException {
                    //将流在这里关闭
                    asyncEvent.getSuppliedResponse().getWriter().close();
                    System.out.println("asynContext finished....");
                }

                public void onError(AsyncEvent arg0) throws IOException {
                    System.out.println("异步调用出错：" + new Date());
                }

                public void onStartAsync(AsyncEvent arg0) throws IOException {
                    System.out.println("异步调用开始：" + new Date());
                }

                public void onTimeout(AsyncEvent arg0) throws IOException {
                    System.out.println("异步调用超时：" + new Date());
                }
            });

            new Thread(new MyThread(asyncContext)).start();//线程

            out.print("servlets ends:" + new Date() + "<br>");
            out.flush();

        } finally {
            //一开是在这里关闭了,关了,后面就用不成了 :）
    /*if(null != out){
        out.close();
		out = null;
	}*/
        }
    }

    class MyThread implements Runnable {

        private AsyncContext asyncContext;

        public MyThread(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        public void run() {
            PrintWriter out = null;

            try {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out = asyncContext.getResponse().getWriter();
                out.println("myTask starts:" + new Date() + "<br>");
                out.flush();


                out.print("myTask ends:" + new Date() + "<br>");
                out.flush();
                asyncContext.complete();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

			/*if(null != out){
				out.close();
				out = null;
			}*/
            }
        }
    }
}
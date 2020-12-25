package shudu;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class demo {
	public static int[][] x= new int[9][9];
	
	public static void readFile() {
		File fp=new File("D:\\eclipse\\demo\\shudu\\includ.txt");
		InputStream i=null;
		try {
			i=new FileInputStream(fp);
			//fp可能为空
			int temp;
			int j=0,k=0;//定义一个赋值时候使用的下标
			while((temp=i.read())!=-1) {
				if((char) temp>='0'&&(char) temp<='9') {
					int num = Integer.valueOf(""+(char) temp);
					x[j][k]=num;
					k++;
					if(k==9) {
						k=0;
						j++;
					}
				}
			}
		}catch(Exception e) {	
			e.printStackTrace();
		}finally {
			try {
				i.close();
				//可能i并没有指向,简单说就是有没开启的可能
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static final int N=3;
    public static void main(String[] args) {

    	readFile();
    	System.out.println("文件读取结果：");
    	for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                System.out.print(x[i][j]+" ");
            }
            System.out.println();
        }
    	System.out.println("数独解题结果：");
        function(x,0,0);

    }

    private static void function(int[][] x, int r, int c) {
        if (r>=x.length) {
            show(x);
            return;
        }
        if (c==0&&(r==x.length/N||r==x.length/N*2||r==x.length)) {
            if (!checkedbox(x,r)) {
                return;
            };

        }
        if (c>=x.length) {
            function(x, r+1, 0);
            return;
        }

        if (x[r][c]==0) {
            for (int i = 1; i <= x.length; i++) {
                if (checked(x,r,c,i)) {
                    x[r][c]=i;
                    function(x, r, c+1);
                    x[r][c]=0;
                }
            }
        }else{
            function(x, r, c+1);
        }
    }
    private static boolean checkedbox(int[][] x, int r) {
        for (int k = 0; k < x.length; k+=x.length/N) {
            Map<Integer, Integer> map=new HashMap<>();
            for (int i = r-N; i < r; i++) {
                for (int j = k; j < k+x.length/N; j++) {
                    if (map.containsKey(x[i][j])) {
                        return false;
                    }
                    map.put(x[i][j], 1);
                }
            }

        }
        return true;
    }

    private static boolean checked(int[][] x, int r, int c, int i) {
        for (int j = 0; j < x.length; j++) {
            if (x[j][c]==i) {
                return false;
            }
            if (x[r][j]==i) {
                return false;
            }
        }
        return true;
    }

    private static void show(int[][] x) {
    	
    	
    	
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                System.out.print(x[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    

        File file = new File("./shudu.txt");
        OutputStream out = null;
        try {
        	out = new FileOutputStream(file);
        	String str = new String();
        	for(int i=0;i<x.length;i++) {
        		str = str+Arrays.toString(x[i])+"\n";
        	}
        	byte[] b = str.getBytes();
        	out.write(b, 0, b.length);
        	out.flush();
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
			try {
				out.close();
				System.out.println("运行结果已保存至文件。");
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
    }
}
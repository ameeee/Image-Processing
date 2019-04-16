
/**
 * File: ReadWriteImage.java
 * Description:
 * Read and write image.
 */
package Exc1;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PixelReplication {

	public static void main(String args[]) throws IOException {
		BufferedImage image = null;

		// read image file
		image = readimage("C:/Users/user/Pictures/1.jpg");
		BufferedImage image2=readimage("C:/Users/user/Pictures/Document.jpg");
		int[][] im = img2matrix(image);
		int[][] im2 = img2matrix(image2);
		int max = 0;
		int c = 0;
		for (int i = 0; i < im.length; i++) {
			c = 0;
			for (int j = 0; j < im[i].length; j++) {
				c++;
			}
			
			if (max < c) {
				max=c;
			}
		}

		//writeimage("C:/Users/user/Pictures/4.jpg",basicarith(im,im2,"subtract") , "jpg");
	}
	public static int[][]zeroOrder(int[][]im,int col){
		int [][]n=new int[2*im.length-1][2*im[0].length-1];
		for (int i = 0; i < n.length; i++) {
		for (int j = 0; j < n[i].length; j++) {
			if(i%2==0 && j%2==0) {
				n[i][j]=im[i/2][j/2];
			}
			else if(i%2!=0){
				n[i][j]=(im[i/2][j/2]+im[i/2+1][j/2])/2;
			}
			else {
				n[i][j]=(im[i/2][j/2]+im[i/2][j/2+1])/2;
			}
		}
			
		}
		return n;
	}
	
public static int[][] kzoom(int[][]im,int k,int col){
	int[][]n=new int[(k*(im.length-1) + 1)][(k *(col- 1) + 1)];
	//filling 2d with orig
	for (int i = 0; i < im.length; i++) {
		for (int j = 0; j < im[i].length; j++) {
				n[i*k][j*k]=im[i][j];
		}
		
	}
	
	//row zooming
	for (int i = 0; i < n.length; i++) {
		for (int j = 0; j < n[i].length; j++) {
			if(i%k==0 && j%k==0 && j!=n[i].length-1){
				int op=(n[i][j+k]-n[i][j])/k;
				for (int kk = 1; kk<k; kk++) {
				n[i][j+kk]=op*kk+(n[i][j]);
			}
			}
		}
	
	}
	//column zooming
	for (int i = 0; i < n.length; i++) {
		for (int j = 0; j < n[i].length; j++) {
			if(i%k==0 && i!=n.length-1){
				int op=(n[i+k][j]-n[i][j])/k;
				for (int kk = 1; kk< k; kk++) {
				n[i+kk][j]=op*kk+n[i][j];
				
			}
			}
		}
	
	}
	return n;
}
public static int[][] basicarith(int[][]im,int [][]im2,String s){
	int r=Math.max(im.length,im2.length);
	int c=Math.max(im[0].length,im2[0].length);
		int [][]exp=new int[r][c];
		int[][]exp2=new int[r][c];
		for (int i = 0; i < im2.length; i++) {
			for (int j = 0; j < im2[i].length; j++) {
				exp[i][j]=im2[i][j];
			}
		}
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				exp2[i][j]=im[i][j];
			}
		}
	int [][]pic=new int[r][c];
	int max=Integer.MIN_VALUE;
	int min=Integer.MAX_VALUE;
	if(s.equals("add")){
	for (int i = 0; i < pic.length; i++) {
		for (int j = 0; j < pic[i].length; j++) {
			pic[i][j]=exp[i][j]+exp2[i][j];
			if(pic[i][j]>max){
				max=pic[i][j];
			}
			if(pic[i][j]<min){
				min=pic[i][j];
			}

		}
	}
	}
	else if(s.equals("subtract")){
		for (int i = 0; i < pic.length; i++) {
			for (int j = 0; j < pic[i].length; j++) {
				pic[i][j]=exp[i][j]-exp2[i][j];
				if(pic[i][j]>max){
					max=pic[i][j];
				}
				if(pic[i][j]<min){
					min=pic[i][j];
				}

			}
		}
	}
	else if(s.equals("divide")){
		for (int i = 0; i < pic.length; i++) {
			for (int j = 0; j < pic[i].length; j++) {
				if(exp2[i][j]!=0){
				pic[i][j]=exp[i][j]/exp2[i][j];
				}
				if(pic[i][j]>max){
					max=pic[i][j];
				}
				if(pic[i][j]<min){
					min=pic[i][j];
				}

			}
		}
	}
	for (int i = 0; i < pic.length; i++) {
		for (int j = 0; j < pic[i].length; j++) {
			pic[i][j]=255*(pic[i][j]-min)/(max- min);

		}
	}
	return pic;
}
public static int[][] pixelrep(int[][] im, int col) {
		int[][] n = new int[im.length * 2][col * 2];
		for (int i = 0; i < n.length; i++) {
			for (int j = 0; j < n[i].length; j++) {
				n[i][j] = im[i / 2][j / 2];

			}
		}
		return n;
	}
	public static int[][]imgShrinking(int[][]im){
		int [][]n=new int[im.length/2][im[0].length/2];
	
		for (int i = 0; i <im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
			if(i%2==0) {
				continue;
			}
			if(j%2==0)
				continue;
			n[i/2][j/2]=im[i][j];
			
		
		}
	}
		return n;
	}
	public static BufferedImage readimage(String path) {
		BufferedImage image = null;
		File f = null;
		try {
			f = new File(path);
			image = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return image;
	}

	public static void writeimage(String path, int[][] im, String ext) {
		File f = null;
		try {
			f = new File(path);
			ImageIO.write(matrix2img(im), ext, f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public static int[][] img2matrix(BufferedImage bi) {
		int p, a, r, g, b;
		int[][] C = new int[bi.getHeight()][bi.getWidth()];
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {
				p = bi.getRGB(j, i);
				a = (p >> 24) & 0xff;
				r = (p >> 16) & 0xff;
				g = (p >> 8) & 0xff;
				b = p & 0xff;
				C[i][j] = (r + g + b) / 3;
			}
		}
		return C;
	}

	public static BufferedImage matrix2img(int[][] im) {
		BufferedImage bi = new BufferedImage(im[0].length, im.length, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				bi.setRGB(j, i, im[i][j] << 16 | im[i][j] << 8 | im[i][j]);
			}
		}
		return bi;
	}
}// class ends here
